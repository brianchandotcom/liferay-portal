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
	service = {DTOConverter.class, ProductChannelDTOConverter.class}
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