/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Sku;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
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
	public CPInstance getObject(String externalReferenceCode) throws Exception {
		return _cpInstanceLocalService.fetchCPInstance(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public Sku toDTO(
			DTOConverterContext dtoConverterContext, CPInstance cpInstance)
		throws Exception {

		if (cpInstance == null) {
			return null;
		}

		return new Sku() {
			{
				setCost(cpInstance::getCost);
				setDiscontinued(cpInstance::isDiscontinued);
				setDisplayDate(cpInstance::getDisplayDate);
				setExpirationDate(cpInstance::getExpirationDate);
				setExternalReferenceCode(cpInstance::getExternalReferenceCode);
				setGtin(cpInstance::getGtin);
				setId(cpInstance::getCPInstanceId);
				setManufacturerPartNumber(
					cpInstance::getManufacturerPartNumber);
				setPublished(cpInstance::isPublished);
				setPurchasable(cpInstance::isPurchasable);
				setSku(cpInstance::getSku);
			}
		};
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}