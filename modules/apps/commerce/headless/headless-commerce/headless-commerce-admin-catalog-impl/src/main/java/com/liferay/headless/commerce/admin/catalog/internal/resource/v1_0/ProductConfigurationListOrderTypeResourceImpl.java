/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.commerce.product.service.CPConfigurationListRelService;
import com.liferay.commerce.product.service.CPConfigurationListService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationList;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationListOrderType;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductConfigurationListOrderTypeResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-configuration-list-order-type.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductConfigurationListOrderTypeResource.class
)
public class ProductConfigurationListOrderTypeResourceImpl
	extends BaseProductConfigurationListOrderTypeResourceImpl {

	@Override
	public void deleteProductConfigurationListOrderType(Long id)
		throws Exception {

		_cpConfigurationListRelService.deleteCPConfigurationListRel(id);
	}

	@Override
	public Page<ProductConfigurationListOrderType>
			getProductConfigurationListByExternalReferenceCodeProductConfigurationListOrderTypesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list order type with " +
					"external reference code " + externalReferenceCode);
		}

		return getProductConfigurationListIdProductConfigurationListOrderTypesPage(
			cpConfigurationList.getCPConfigurationListId(), null, null,
			pagination, null);
	}

	@NestedField(
		parentClass = ProductConfigurationList.class,
		value = "productConfigurationListOrderTypes"
	)
	@Override
	public Page<ProductConfigurationListOrderType>
			getProductConfigurationListIdProductConfigurationListOrderTypesPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		return Page.of(
			_toProductConfigurationListOrderTypes(
				_cpConfigurationListRelService.
					getCommerceOrderTypeCPConfigurationListRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition())),
			pagination,
			_cpConfigurationListRelService.
				getCommerceOrderTypeCPConfigurationListRelsCount(id, search));
	}

	@Override
	public ProductConfigurationListOrderType
			postProductConfigurationListByExternalReferenceCodeProductConfigurationListOrderType(
				String externalReferenceCode,
				ProductConfigurationListOrderType
					productConfigurationListOrderType)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list order type with " +
					"external reference code " + externalReferenceCode);
		}

		return postProductConfigurationListIdProductConfigurationListOrderType(
			cpConfigurationList.getCPConfigurationListId(),
			productConfigurationListOrderType);
	}

	@Override
	public ProductConfigurationListOrderType
			postProductConfigurationListIdProductConfigurationListOrderType(
				Long id,
				ProductConfigurationListOrderType
					productConfigurationListOrderType)
		throws Exception {

		CPConfigurationListRel cpConfigurationListRel =
			_addCPConfigurationListRel(
				_cpConfigurationListService.getCPConfigurationList(id),
				productConfigurationListOrderType);

		return _toProductConfigurationListOrderType(
			cpConfigurationListRel.getCPConfigurationListRelId());
	}

	private CPConfigurationListRel _addCPConfigurationListRel(
			CPConfigurationList cpConfigurationList,
			ProductConfigurationListOrderType productConfigurationListOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.
				fetchCommerceOrderTypeByExternalReferenceCode(
					GetterUtil.getString(
						productConfigurationListOrderType.
							getOrderTypeExternalReferenceCode()),
					cpConfigurationList.getCompanyId());

		if (commerceOrderType == null) {
			commerceOrderType = _commerceOrderTypeService.getCommerceOrderType(
				productConfigurationListOrderType.getOrderTypeId());
		}

		return _cpConfigurationListRelService.addCPConfigurationListRel(
			CommerceOrderType.class.getName(),
			commerceOrderType.getCommerceOrderTypeId(),
			cpConfigurationList.getCPConfigurationListId());
	}

	private Map<String, Map<String, String>> _getActions(
			long cpConfigurationListRelId)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", cpConfigurationListRelId,
				"deleteProductConfigurationListOrderType",
				_cpConfigurationListRelModelResourcePermission)
		).build();
	}

	private ProductConfigurationListOrderType
			_toProductConfigurationListOrderType(Long cpConfigurationListRelId)
		throws Exception {

		return _productConfigurationListOrderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpConfigurationListRelId), _dtoConverterRegistry,
				cpConfigurationListRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<ProductConfigurationListOrderType>
		_toProductConfigurationListOrderTypes(
			List<CPConfigurationListRel> cpConfigurationListRels) {

		return transform(
			cpConfigurationListRels,
			cpConfigurationListRel -> _toProductConfigurationListOrderType(
				cpConfigurationListRel.getCPConfigurationListRelId()));
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPConfigurationListRel)"
	)
	private ModelResourcePermission<CPConfigurationListRel>
		_cpConfigurationListRelModelResourcePermission;

	@Reference
	private CPConfigurationListRelService _cpConfigurationListRelService;

	@Reference
	private CPConfigurationListService _cpConfigurationListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductConfigurationListOrderTypeDTOConverter)"
	)
	private DTOConverter
		<CPConfigurationListRel, ProductConfigurationListOrderType>
			_productConfigurationListOrderTypeDTOConverter;

}