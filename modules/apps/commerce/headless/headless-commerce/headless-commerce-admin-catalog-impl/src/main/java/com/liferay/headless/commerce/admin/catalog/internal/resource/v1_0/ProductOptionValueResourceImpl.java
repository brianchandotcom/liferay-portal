/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductOptionValueUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionValueResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-option-value.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductOptionValueResource.class
)
@CTAware
public class ProductOptionValueResourceImpl
	extends BaseProductOptionValueResourceImpl {

	@Override
	public void deleteProductOptionValue(Long id) throws Exception {
		_cpDefinitionOptionValueRelService.deleteCPDefinitionOptionValueRel(id);
	}

	@NestedField(
		parentClass = ProductOption.class, value = "productOptionValues"
	)
	@Override
	public Page<ProductOptionValue> getProductOptionIdProductOptionValuesPage(
			Long id, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(id);

		BaseModelSearchResult<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRelBaseModelSearchResult =
				_cpDefinitionOptionValueRelService.
					searchCPDefinitionOptionValueRels(
						cpDefinitionOptionRel.getCompanyId(),
						cpDefinitionOptionRel.getGroupId(),
						cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
						search, pagination.getStartPosition(),
						pagination.getEndPosition(), sorts);

		int totalItems =
			_cpDefinitionOptionValueRelService.
				searchCPDefinitionOptionValueRelsCount(
					cpDefinitionOptionRel.getCompanyId(),
					cpDefinitionOptionRel.getGroupId(),
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(), search);

		return Page.of(
			transform(
				cpDefinitionOptionValueRelBaseModelSearchResult.getBaseModels(),
				cpDefinitionOptionValueRel -> _toProductOptionValue(
					cpDefinitionOptionValueRel)),
			pagination, totalItems);
	}

	@Override
	public ProductOptionValue getProductOptionValue(Long id) throws Exception {
		return _toProductOptionValue(
			_cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRel(
				id));
	}

	@Override
	public ProductOptionValue patchProductOptionValue(
			Long id, ProductOptionValue productOptionValue)
		throws Exception {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRel(
				id);

		long cpInstanceId = 0;

		CPInstance cpInstance = _cpInstanceService.fetchCProductInstance(
			cpDefinitionOptionValueRel.getCProductId(),
			cpDefinitionOptionValueRel.getCPInstanceUuid());

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		Map<String, String> nameMap = productOptionValue.getName();

		if ((cpDefinitionOptionValueRel != null) && (nameMap == null)) {
			nameMap = LanguageUtils.getLanguageIdMap(
				cpDefinitionOptionValueRel.getNameMap());
		}

		return _toProductOptionValue(
			_cpDefinitionOptionValueRelService.updateCPDefinitionOptionValueRel(
				id, cpInstanceId,
				GetterUtil.get(
					productOptionValue.getKey(),
					cpDefinitionOptionValueRel.getKey()),
				LanguageUtils.getLocalizedMap(nameMap),
				cpDefinitionOptionValueRel.isPreselected(),
				cpDefinitionOptionValueRel.getPrice(),
				GetterUtil.getDouble(
					productOptionValue.getPriority(),
					cpDefinitionOptionValueRel.getPriority()),
				cpDefinitionOptionValueRel.getQuantity(),
				cpDefinitionOptionValueRel.getUnitOfMeasureKey(),
				_serviceContextHelper.getServiceContext(
					cpDefinitionOptionValueRel.getGroupId())));
	}

	@Override
	public ProductOptionValue postProductOptionIdProductOptionValue(
			Long id, ProductOptionValue productOptionValue)
		throws Exception {

		return _addOrUpdateProductOptionValue(id, productOptionValue);
	}

	private ProductOptionValue _addOrUpdateProductOptionValue(
			long productOptionId, ProductOptionValue productOptionValue)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				productOptionId);

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			ProductOptionValueUtil.addOrUpdateCPDefinitionOptionValueRel(
				_cpDefinitionOptionValueRelService, productOptionValue,
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				_serviceContextHelper.getServiceContext(
					cpDefinitionOptionRel.getGroupId()));

		return _toProductOptionValue(cpDefinitionOptionValueRel);
	}

	private ProductOptionValue _toProductOptionValue(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		return _productOptionValueDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId(),
				contextAcceptLanguage.getPreferredLocale()),
			cpDefinitionOptionValueRel);
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductOptionValueDTOConverter)"
	)
	private DTOConverter<CPDefinitionOptionValueRel, ProductOptionValue>
		_productOptionValueDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}