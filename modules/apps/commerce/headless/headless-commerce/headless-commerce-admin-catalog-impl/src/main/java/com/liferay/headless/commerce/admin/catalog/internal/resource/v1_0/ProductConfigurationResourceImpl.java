/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPConfigurationEntryService;
import com.liferay.commerce.product.service.CPConfigurationListService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.service.CPDAvailabilityEstimateService;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationList;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductConfigurationDTOConverterContext;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductConfigurationUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductConfigurationResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.math.BigDecimal;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-configuration.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductConfigurationResource.class
)
@CTAware
public class ProductConfigurationResourceImpl
	extends BaseProductConfigurationResourceImpl {

	@Override
	public void deleteProductConfiguration(Long id) throws Exception {
		_cpConfigurationEntryService.deleteCPConfigurationEntry(id);
	}

	@Override
	public void deleteProductConfigurationByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CPConfigurationEntry cpConfigurationEntry =
			_cpConfigurationEntryService.
				getCPConfigurationEntryByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		deleteProductConfiguration(
			cpConfigurationEntry.getCPConfigurationEntryId());
	}

	@Override
	public ProductConfiguration getProductByExternalReferenceCodeConfiguration(
			String externalReferenceCode)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return getProductIdConfiguration(cpDefinition.getCProductId());
	}

	@Override
	public ProductConfiguration getProductConfiguration(Long id)
		throws Exception {

		return _toProductConfiguration(
			_cpConfigurationEntryService.getCPConfigurationEntry(id));
	}

	@Override
	public ProductConfiguration getProductConfigurationByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CPConfigurationEntry cpConfigurationEntry =
			_cpConfigurationEntryService.
				getCPConfigurationEntryByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getProductConfiguration(
			cpConfigurationEntry.getCPConfigurationEntryId());
	}

	@Override
	public Page<ProductConfiguration>
			getProductConfigurationListByExternalReferenceCodeProductConfigurationsPage(
				String externalReferenceCode, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				getCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getProductConfigurationListIdProductConfigurationsPage(
			cpConfigurationList.getCPConfigurationListId(), search, filter,
			pagination, sorts);
	}

	@NestedField(
		parentClass = ProductConfigurationList.class,
		value = "productConfigurations"
	)
	@Override
	public Page<ProductConfiguration>
			getProductConfigurationListIdProductConfigurationsPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.getCPConfigurationList(id);

		long companyId = cpConfigurationList.getCompanyId();

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CPConfigurationEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			object -> {
				SearchContext searchContext = (SearchContext)object;

				searchContext.setAttribute(
					CPField.CP_CONFIGURATION_LIST_ID,
					cpConfigurationList.getCPConfigurationListId());
				searchContext.setCompanyId(companyId);
			},
			sorts,
			document -> _toProductConfiguration(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@NestedField(parentClass = Product.class, value = "productConfiguration")
	@Override
	public ProductConfiguration getProductIdConfiguration(
			@NestedFieldId(value = "productId") Long id)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + id);
		}

		return _toProductConfiguration(cpDefinition.getCPDefinitionId());
	}

	@Override
	public Response patchProductByExternalReferenceCodeConfiguration(
			String externalReferenceCode,
			ProductConfiguration productConfiguration)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return patchProductIdConfiguration(
			cpDefinition.getCProductId(), productConfiguration);
	}

	@Override
	public ProductConfiguration patchProductConfiguration(
			Long id, ProductConfiguration productConfiguration)
		throws Exception {

		CPConfigurationEntry cpConfigurationEntry =
			_cpConfigurationEntryService.getCPConfigurationEntry(id);

		return _toProductConfiguration(
			_cpConfigurationEntryService.updateCPConfigurationEntry(
				GetterUtil.getString(
					productConfiguration.getExternalReferenceCode(),
					cpConfigurationEntry.getExternalReferenceCode()),
				cpConfigurationEntry.getCPConfigurationEntryId(),
				cpConfigurationEntry.getCPConfigurationListId(),
				ProductConfigurationUtil.getAllowedOrderQuantities(
					productConfiguration.getAllowedOrderQuantities(),
					cpConfigurationEntry.getAllowedOrderQuantities()),
				GetterUtil.getBoolean(
					productConfiguration.getAllowBackOrder(),
					cpConfigurationEntry.isBackOrders()),
				GetterUtil.getLong(
					productConfiguration.getAvailabilityEstimateId(),
					cpConfigurationEntry.getCommerceAvailabilityEstimateId()),
				GetterUtil.getString(
					productConfiguration.getInventoryEngine(),
					cpConfigurationEntry.getCPDefinitionInventoryEngine()),
				GetterUtil.getBoolean(
					productConfiguration.getDisplayAvailability(),
					cpConfigurationEntry.isDisplayAvailability()),
				GetterUtil.getBoolean(
					productConfiguration.getDisplayStockQuantity(),
					cpConfigurationEntry.isDisplayStockQuantity()),
				GetterUtil.getString(
					productConfiguration.getLowStockAction(),
					cpConfigurationEntry.getLowStockActivity()),
				BigDecimalUtil.get(
					productConfiguration.getMaxOrderQuantity(),
					cpConfigurationEntry.getMaxOrderQuantity()),
				BigDecimalUtil.get(
					productConfiguration.getMinOrderQuantity(),
					cpConfigurationEntry.getMinOrderQuantity()),
				BigDecimalUtil.get(
					productConfiguration.getMinStockQuantity(),
					cpConfigurationEntry.getMinStockQuantity()),
				BigDecimalUtil.get(
					productConfiguration.getMultipleOrderQuantity(),
					cpConfigurationEntry.getMultipleOrderQuantity())));
	}

	@Override
	public ProductConfiguration
			patchProductConfigurationByExternalReferenceCode(
				String externalReferenceCode,
				ProductConfiguration productConfiguration)
		throws Exception {

		CPConfigurationEntry cpConfigurationEntry =
			_cpConfigurationEntryService.
				getCPConfigurationEntryByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return patchProductConfiguration(
			cpConfigurationEntry.getCPConfigurationEntryId(),
			productConfiguration);
	}

	@Override
	public Response patchProductIdConfiguration(
			Long id, ProductConfiguration productConfiguration)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + id);
		}

		CPConfigurationEntry masterCPConfigurationEntry =
			cpDefinition.fetchMasterCPConfigurationEntry();

		if (masterCPConfigurationEntry != null) {
			_cpConfigurationEntryService.updateCPConfigurationEntry(
				GetterUtil.getString(
					productConfiguration.getExternalReferenceCode(),
					masterCPConfigurationEntry.getExternalReferenceCode()),
				masterCPConfigurationEntry.getCPConfigurationEntryId(),
				masterCPConfigurationEntry.getCPConfigurationListId(),
				ProductConfigurationUtil.getAllowedOrderQuantities(
					productConfiguration.getAllowedOrderQuantities(),
					masterCPConfigurationEntry.getAllowedOrderQuantities()),
				GetterUtil.getBoolean(
					productConfiguration.getAllowBackOrder(),
					masterCPConfigurationEntry.isBackOrders()),
				GetterUtil.getLong(
					productConfiguration.getAvailabilityEstimateId(),
					masterCPConfigurationEntry.
						getCommerceAvailabilityEstimateId()),
				GetterUtil.getString(
					productConfiguration.getInventoryEngine(),
					masterCPConfigurationEntry.
						getCPDefinitionInventoryEngine()),
				GetterUtil.getBoolean(
					productConfiguration.getDisplayAvailability(),
					masterCPConfigurationEntry.isDisplayAvailability()),
				GetterUtil.getBoolean(
					productConfiguration.getDisplayStockQuantity(),
					masterCPConfigurationEntry.isDisplayStockQuantity()),
				GetterUtil.getString(
					productConfiguration.getLowStockAction(),
					masterCPConfigurationEntry.getLowStockActivity()),
				BigDecimalUtil.get(
					productConfiguration.getMaxOrderQuantity(),
					masterCPConfigurationEntry.getMaxOrderQuantity()),
				BigDecimalUtil.get(
					productConfiguration.getMinOrderQuantity(),
					masterCPConfigurationEntry.getMinOrderQuantity()),
				BigDecimalUtil.get(
					productConfiguration.getMinStockQuantity(),
					masterCPConfigurationEntry.getMinStockQuantity()),
				BigDecimalUtil.get(
					productConfiguration.getMultipleOrderQuantity(),
					masterCPConfigurationEntry.getMultipleOrderQuantity()));
		}

		ProductConfigurationUtil.updateCPDefinitionInventory(
			_cpDefinitionInventoryService, productConfiguration,
			cpDefinition.getCPDefinitionId());

		ProductConfigurationUtil.updateCPDAvailabilityEstimate(
			_cpdAvailabilityEstimateService, productConfiguration,
			cpDefinition.getCPDefinitionId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public ProductConfiguration
			postProductConfigurationListByExternalReferenceCodeProductConfiguration(
				String externalReferenceCode,
				ProductConfiguration productConfiguration)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				getCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return postProductConfigurationListIdProductConfiguration(
			cpConfigurationList.getCPConfigurationListId(),
			productConfiguration);
	}

	@Override
	public ProductConfiguration
			postProductConfigurationListIdProductConfiguration(
				Long id, ProductConfiguration productConfiguration)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					GetterUtil.getString(
						productConfiguration.getEntityExternalReferenceCode()),
					contextCompany.getCompanyId());

		if (cpDefinition == null) {
			cpDefinition = _cpDefinitionService.getCPDefinition(
				GetterUtil.getLong(productConfiguration.getEntityId()));
		}

		return _toProductConfiguration(
			_cpConfigurationEntryService.addCPConfigurationEntry(
				GetterUtil.getString(
					productConfiguration.getExternalReferenceCode()),
				cpDefinition.getGroupId(),
				_portal.getClassNameId(CPDefinition.class.getName()),
				cpDefinition.getCPDefinitionId(), id,
				ProductConfigurationUtil.getAllowedOrderQuantities(
					productConfiguration.getAllowedOrderQuantities(), null),
				GetterUtil.getBoolean(productConfiguration.getAllowBackOrder()),
				GetterUtil.getLong(
					productConfiguration.getAvailabilityEstimateId()),
				GetterUtil.getString(productConfiguration.getInventoryEngine()),
				GetterUtil.getBoolean(
					productConfiguration.getDisplayAvailability()),
				GetterUtil.getBoolean(
					productConfiguration.getDisplayStockQuantity()),
				GetterUtil.getString(productConfiguration.getLowStockAction()),
				BigDecimalUtil.get(
					productConfiguration.getMaxOrderQuantity(),
					new BigDecimal(1000)),
				BigDecimalUtil.get(
					productConfiguration.getMinOrderQuantity(), BigDecimal.ONE),
				BigDecimalUtil.get(
					productConfiguration.getMinStockQuantity(), BigDecimal.ONE),
				BigDecimalUtil.get(
					productConfiguration.getMultipleOrderQuantity(),
					BigDecimal.ONE)));
	}

	private Map<String, Map<String, String>> _getActions(
		CPConfigurationEntry cpConfigurationEntry) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			() -> addAction(
				"UPDATE", cpConfigurationEntry.getCPConfigurationEntryId(),
				"deleteProductConfiguration",
				_cpConfigurationEntryModelResourcePermission)
		).put(
			"get",
			() -> addAction(
				"VIEW", cpConfigurationEntry.getCPConfigurationEntryId(),
				"getProductConfiguration",
				_cpConfigurationEntryModelResourcePermission)
		).put(
			"update",
			() -> addAction(
				"UPDATE", cpConfigurationEntry.getCPConfigurationEntryId(),
				"patchProductConfiguration",
				_cpConfigurationEntryModelResourcePermission)
		).build();
	}

	private ProductConfiguration _toProductConfiguration(
			CPConfigurationEntry cpConfigurationEntry)
		throws Exception {

		return _productConfigurationDTOConverter.toDTO(
			new ProductConfigurationDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpConfigurationEntry),
				cpConfigurationEntry.getCPConfigurationEntryId(),
				_dtoConverterRegistry, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private ProductConfiguration _toProductConfiguration(Long cpDefinitionId)
		throws Exception {

		return _productConfigurationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, cpDefinitionId,
				contextAcceptLanguage.getPreferredLocale(), null, null));
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPConfigurationEntry)"
	)
	private ModelResourcePermission<CPConfigurationEntry>
		_cpConfigurationEntryModelResourcePermission;

	@Reference
	private CPConfigurationEntryService _cpConfigurationEntryService;

	@Reference
	private CPConfigurationListService _cpConfigurationListService;

	@Reference
	private CPDAvailabilityEstimateService _cpdAvailabilityEstimateService;

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductConfigurationDTOConverter)"
	)
	private DTOConverter<CPDefinitionInventory, ProductConfiguration>
		_productConfigurationDTOConverter;

}