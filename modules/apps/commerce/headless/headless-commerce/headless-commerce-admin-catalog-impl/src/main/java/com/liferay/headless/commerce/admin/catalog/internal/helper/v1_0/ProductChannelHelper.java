/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductChannel;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = ProductChannelHelper.class)
public class ProductChannelHelper {

	public Page<ProductChannel> getProductChannelsPage(
			long id, Pagination pagination)
		throws Exception {

		int commerceChannelRelsCount =
			_commerceChannelRelService.getCommerceChannelRelsCount(
				CPDefinition.class.getName(), id);

		return Page.of(
			TransformUtil.transform(
				_commerceChannelRelService.getCommerceChannelRels(
					CPDefinition.class.getName(), id, null,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::toProductChannel),
			pagination, commerceChannelRelsCount);
	}

	public ProductChannel toProductChannel(
			CommerceChannelRel commerceChannelRel)
		throws Exception {

		CommerceChannel commerceChannel =
			commerceChannelRel.getCommerceChannel();

		return new ProductChannel() {
			{
				channelId = commerceChannel.getCommerceChannelId();
				currencyCode = commerceChannel.getCommerceCurrencyCode();
				externalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				id = commerceChannelRel.getCommerceChannelRelId();
				name = commerceChannel.getName();
				type = commerceChannel.getType();
			}
		};
	}

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

}