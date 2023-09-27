/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPInstance",
	service = DTOConverter.class
)
public class SkuDTOConverter implements DTOConverter<CPInstance, Sku> {

	@Override
	public String getContentType() {
		return Sku.class.getSimpleName();
	}

	@Override
	public Sku toDTO(DTOConverterContext dtoConverterContext) throws Exception {
		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();
		CPInstance replacementCPInstance =
			_cpInstanceService.fetchCProductInstance(
				cpInstance.getReplacementCProductId(),
				cpInstance.getReplacementCPInstanceUuid());

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			(CPInstanceUnitOfMeasure)dtoConverterContext.getAttribute(
				"cpInstanceUnitOfMeasure");

		return new Sku() {
			{
				cost = cpInstance.getCost();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					CPInstance.class.getName(), cpInstance.getCPInstanceId(),
					cpInstance.getCompanyId(), dtoConverterContext.getLocale());
				depth = cpInstance.getDepth();
				discontinued = cpInstance.isDiscontinued();
				discontinuedDate = cpInstance.getDiscontinuedDate();
				displayDate = cpInstance.getDisplayDate();
				expirationDate = cpInstance.getExpirationDate();
				externalReferenceCode = cpInstance.getExternalReferenceCode();
				gtin = cpInstance.getGtin();
				height = cpInstance.getHeight();
				id = cpInstance.getCPInstanceId();
				manufacturerPartNumber = cpInstance.getManufacturerPartNumber();
				price = cpInstance.getPrice();
				productId = cpDefinition.getCProductId();
				productName = LanguageUtils.getLanguageIdMap(
					cpDefinition.getNameMap());
				promoPrice = cpInstance.getPromoPrice();
				published = cpInstance.isPublished();
				purchasable = cpInstance.isPurchasable();
				sku = cpInstance.getSku();
				unspsc = cpInstance.getUnspsc();
				weight = cpInstance.getWeight();
				width = cpInstance.getWidth();

				setReplacementSkuExternalReferenceCode(
					() -> {
						if (replacementCPInstance != null) {
							return replacementCPInstance.
								getExternalReferenceCode();
						}

						return null;
					});
				setReplacementSkuId(
					() -> {
						if (replacementCPInstance != null) {
							return replacementCPInstance.getCPInstanceId();
						}

						return null;
					});
				setSkuOptions(
					() -> {
						List<SkuOption> skuOptions = new ArrayList<>();

						List<CPInstanceOptionValueRel>
							cpInstanceOptionValueRels =
								_cpInstanceHelper.
									getCPInstanceCPInstanceOptionValueRels(
										cpInstance.getCPInstanceId());

						for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
								cpInstanceOptionValueRels) {

							CPDefinitionOptionRel cpDefinitionOptionRel =
								_cpDefinitionOptionRelLocalService.
									fetchCPDefinitionOptionRel(
										cpInstanceOptionValueRel.
											getCPDefinitionOptionRelId());

							if (cpDefinitionOptionRel == null) {
								continue;
							}

							CPDefinitionOptionValueRel
								cpDefinitionOptionValueRel =
									_cpDefinitionOptionValueRelLocalService.
										fetchCPDefinitionOptionValueRel(
											cpInstanceOptionValueRel.
												getCPDefinitionOptionValueRelId());

							if (cpDefinitionOptionValueRel == null) {
								continue;
							}

							SkuOption skuOption = new SkuOption() {
								{
									key = cpDefinitionOptionRel.getKey();
									optionId =
										cpDefinitionOptionRel.
											getCPDefinitionOptionRelId();
									optionValueId =
										cpDefinitionOptionValueRel.
											getCPDefinitionOptionValueRelId();
									value = cpDefinitionOptionValueRel.getKey();
								}
							};

							skuOptions.add(skuOption);
						}

						return skuOptions.toArray(new SkuOption[0]);
					});
				setUnitOfMeasureKey(
					() -> {
						if (cpInstanceUnitOfMeasure != null) {
							return cpInstanceUnitOfMeasure.getKey();
						}

						return null;
					});
				setUnitOfMeasureName(
					() -> {
						if (cpInstanceUnitOfMeasure != null) {
							return LanguageUtils.getLanguageIdMap(
								cpInstanceUnitOfMeasure.getNameMap());
						}

						return null;
					});
				setUnitOfMeasureSkuId(
					() -> {
						if (cpInstanceUnitOfMeasure != null) {
							return StringBundler.concat(
								cpInstance.getCPInstanceId(), StringPool.DASH,
								cpInstanceUnitOfMeasure.
									getCPInstanceUnitOfMeasureId());
						}

						return String.valueOf(cpInstance.getCPInstanceId());
					});
			}
		};
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceService _cpInstanceService;

}