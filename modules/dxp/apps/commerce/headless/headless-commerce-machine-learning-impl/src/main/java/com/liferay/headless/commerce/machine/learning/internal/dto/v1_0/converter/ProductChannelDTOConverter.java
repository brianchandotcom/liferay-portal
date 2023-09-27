/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductChannel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CommerceChannel",
	service = DTOConverter.class
)
public class ProductChannelDTOConverter
	implements DTOConverter<CommerceChannel, ProductChannel> {

	@Override
	public String getContentType() {
		return ProductChannel.class.getSimpleName();
	}

	@Override
	public CommerceChannel getObject(String externalReferenceCode)
		throws Exception {

		return _commerceChannelLocalService.fetchCommerceChannel(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public ProductChannel toDTO(
			DTOConverterContext dtoConverterContext,
			CommerceChannel commerceChannel)
		throws Exception {

		if (commerceChannel == null) {
			return null;
		}

		return new ProductChannel() {
			{
				currencyCode = commerceChannel.getCommerceCurrencyCode();
				externalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				id = commerceChannel.getCommerceChannelId();
				name = commerceChannel.getName();
				type = commerceChannel.getType();
			}
		};
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

}