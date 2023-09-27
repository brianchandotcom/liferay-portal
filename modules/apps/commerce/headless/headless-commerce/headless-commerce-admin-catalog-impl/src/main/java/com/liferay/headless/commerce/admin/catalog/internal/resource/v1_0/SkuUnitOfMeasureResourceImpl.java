/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.CommercePriceEntryPriceException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuUnitOfMeasureResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sku-unit-of-measure.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = SkuUnitOfMeasureResource.class
)
public class SkuUnitOfMeasureResourceImpl
	extends BaseSkuUnitOfMeasureResourceImpl {

	@Override
	public void deleteSkuUnitOfMeasure(Long id) throws Exception {
		_cpInstanceUnitOfMeasureService.deleteCPInstanceUnitOfMeasure(id);
	}

	@Override
	public Page<SkuUnitOfMeasure>
			getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find SKU with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			transform(
				_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasures(
					cpInstance.getCPInstanceId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toSkuUnitOfMeasure),
			pagination,
			_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
				cpInstance.getCPInstanceId()));
	}

	@NestedField(parentClass = Sku.class, value = "skuUnitOfMeasures")
	@Override
	public Page<SkuUnitOfMeasure> getSkuIdSkuUnitOfMeasuresPage(
			Long id, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasures(
					id, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toSkuUnitOfMeasure),
			pagination,
			_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
				id));
	}

	@Override
	public SkuUnitOfMeasure getSkuUnitOfMeasure(Long id) throws Exception {
		return _toSkuUnitOfMeasure(
			_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasure(id));
	}

	@Override
	public SkuUnitOfMeasure patchSkuUnitOfMeasure(
			Long id, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasure(id);

		Map<String, String> nameMap = skuUnitOfMeasure.getName();

		if ((nameMap == null) || nameMap.isEmpty()) {
			nameMap = LanguageUtils.getLanguageIdMap(
				cpInstanceUnitOfMeasure.getNameMap());
		}

		cpInstanceUnitOfMeasure =
			_cpInstanceUnitOfMeasureService.updateCPInstanceUnitOfMeasure(
				id, cpInstanceUnitOfMeasure.getCPInstanceId(),
				GetterUtil.get(
					skuUnitOfMeasure.getActive(),
					cpInstanceUnitOfMeasure.isActive()),
				(BigDecimal)GetterUtil.get(
					skuUnitOfMeasure.getIncrementalOrderQuantity(),
					cpInstanceUnitOfMeasure.getIncrementalOrderQuantity()),
				cpInstanceUnitOfMeasure.getKey(),
				LanguageUtils.getLocalizedMap(nameMap),
				GetterUtil.get(
					skuUnitOfMeasure.getPrecision(),
					cpInstanceUnitOfMeasure.getPrecision()),
				GetterUtil.get(
					skuUnitOfMeasure.getPrimary(),
					cpInstanceUnitOfMeasure.isPrimary()),
				GetterUtil.get(
					skuUnitOfMeasure.getPriority(),
					cpInstanceUnitOfMeasure.getPriority()),
				(BigDecimal)GetterUtil.get(
					skuUnitOfMeasure.getRate(),
					cpInstanceUnitOfMeasure.getRate()),
				cpInstanceUnitOfMeasure.getSku());

		if ((skuUnitOfMeasure.getBasePrice() != null) ||
			(skuUnitOfMeasure.getPromoPrice() != null)) {

			CPInstance cpInstance = _cpInstanceService.getCPInstance(
				cpInstanceUnitOfMeasure.getCPInstanceId());

			if (skuUnitOfMeasure.getBasePrice() != null) {
				_updateCommercePriceEntry(
					cpInstance, cpInstanceUnitOfMeasure,
					skuUnitOfMeasure.getBasePrice(),
					CommercePriceListConstants.TYPE_PRICE_LIST);
			}

			if (skuUnitOfMeasure.getPromoPrice() != null) {
				_updateCommercePriceEntry(
					cpInstance, cpInstanceUnitOfMeasure,
					skuUnitOfMeasure.getPromoPrice(),
					CommercePriceListConstants.TYPE_PROMOTION);
			}
		}

		return _toSkuUnitOfMeasure(cpInstanceUnitOfMeasure);
	}

	@Override
	public SkuUnitOfMeasure postSkuByExternalReferenceCodeSkuUnitOfMeasure(
			String externalReferenceCode, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find SKU with external reference code " +
					externalReferenceCode);
		}

		return _toSkuUnitOfMeasure(
			_addOrUpdateCPInstanceUnitOfMeasure(cpInstance, skuUnitOfMeasure));
	}

	@Override
	public SkuUnitOfMeasure postSkuIdSkuUnitOfMeasure(
			Long id, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		return _toSkuUnitOfMeasure(
			_addOrUpdateCPInstanceUnitOfMeasure(
				_cpInstanceService.getCPInstance(id), skuUnitOfMeasure));
	}

	private CPInstanceUnitOfMeasure _addOrUpdateCPInstanceUnitOfMeasure(
			CPInstance cpInstance, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			_cpInstanceUnitOfMeasureService.addOrUpdateCPInstanceUnitOfMeasure(
				cpInstance.getCPInstanceId(),
				GetterUtil.get(skuUnitOfMeasure.getActive(), true),
				(BigDecimal)GetterUtil.getNumber(
					skuUnitOfMeasure.getIncrementalOrderQuantity(),
					BigDecimal.ONE),
				skuUnitOfMeasure.getKey(),
				LanguageUtils.getLocalizedMap(skuUnitOfMeasure.getName()),
				GetterUtil.getInteger(skuUnitOfMeasure.getPrecision()),
				GetterUtil.get(
					skuUnitOfMeasure.getPrimary(),
					_isDefaultPrimary(cpInstance.getCPInstanceId())),
				GetterUtil.getDouble(skuUnitOfMeasure.getPriority()),
				(BigDecimal)GetterUtil.getNumber(
					skuUnitOfMeasure.getRate(), BigDecimal.ONE),
				cpInstance.getSku());

		int count =
			_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
				cpInstance.getCPInstanceId());

		if ((count > 1) && (skuUnitOfMeasure.getBasePrice() == null)) {
			throw new CommercePriceEntryPriceException();
		}

		if (skuUnitOfMeasure.getBasePrice() != null) {
			_updateCommercePriceEntry(
				cpInstance, cpInstanceUnitOfMeasure,
				skuUnitOfMeasure.getBasePrice(),
				CommercePriceListConstants.TYPE_PRICE_LIST);
		}

		if (skuUnitOfMeasure.getPromoPrice() != null) {
			_updateCommercePriceEntry(
				cpInstance, cpInstanceUnitOfMeasure,
				skuUnitOfMeasure.getPromoPrice(),
				CommercePriceListConstants.TYPE_PROMOTION);
		}

		return cpInstanceUnitOfMeasure;
	}

	private Map<String, Map<String, String>> _getActions(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId(),
				"deleteSkuUnitOfMeasure",
				_cpInstanceUnitOfMeasureModelResourcePermission)
		).put(
			"get",
			addAction(
				"VIEW", cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId(),
				"getSkuUnitOfMeasure",
				_cpInstanceUnitOfMeasureModelResourcePermission)
		).put(
			"update",
			addAction(
				"UPDATE",
				cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId(),
				"patchSkuUnitOfMeasure",
				_cpInstanceUnitOfMeasureModelResourcePermission)
		).build();
	}

	private boolean _isDefaultPrimary(long cpInstanceId) throws Exception {
		int count =
			_cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
				cpInstanceId);

		if (count > 0) {
			return false;
		}

		return true;
	}

	private SkuUnitOfMeasure _toSkuUnitOfMeasure(
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws Exception {

		return _skuUnitOfMeasureDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpInstanceUnitOfMeasure), null,
				cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private void _updateCommercePriceEntry(
			CPInstance cpInstance,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, BigDecimal price,
			String type)
		throws Exception {

		if (price == null) {
			return;
		}

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.getInstanceBaseCommercePriceEntry(
				cpInstance.getCPInstanceUuid(), type,
				cpInstanceUnitOfMeasure.getKey());

		if (commercePriceEntry != null) {
			_commercePriceEntryService.updatePricingInfo(
				commercePriceEntry.getCommercePriceEntryId(),
				commercePriceEntry.isBulkPricing(), price,
				commercePriceEntry.isPriceOnApplication(),
				commercePriceEntry.getPromoPrice(),
				cpInstanceUnitOfMeasure.getKey(),
				_serviceContextHelper.getServiceContext());
		}
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPInstanceUnitOfMeasure)"
	)
	private ModelResourcePermission<CPInstanceUnitOfMeasure>
		_cpInstanceUnitOfMeasureModelResourcePermission;

	@Reference
	private CPInstanceUnitOfMeasureService _cpInstanceUnitOfMeasureService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.SkuUnitOfMeasureDTOConverter)"
	)
	private DTOConverter<CPInstanceUnitOfMeasure, SkuUnitOfMeasure>
		_skuUnitOfMeasureDTOConverter;

}