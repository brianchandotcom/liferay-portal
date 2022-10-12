/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	service = {DTOConverter.class, SkuDTOConverter.class}
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
				cost = cpInstance.getCost();
				discontinued = cpInstance.isDiscontinued();
				displayDate = cpInstance.getDisplayDate();
				expirationDate = cpInstance.getExpirationDate();
				externalReferenceCode = cpInstance.getExternalReferenceCode();
				gtin = cpInstance.getGtin();
				id = cpInstance.getCPInstanceId();
				manufacturerPartNumber = cpInstance.getManufacturerPartNumber();
				published = cpInstance.isPublished();
				purchasable = cpInstance.isPurchasable();
				sku = cpInstance.getSku();
			}
		};
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}