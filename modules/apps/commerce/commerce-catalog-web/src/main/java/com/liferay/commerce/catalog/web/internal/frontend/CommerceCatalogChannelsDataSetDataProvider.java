/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.catalog.web.internal.frontend;

import com.liferay.commerce.catalog.web.internal.model.Channel;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceCatalogDataSetConstants.COMMERCE_DATA_SET_KEY_CATALOG_CHANNELS,
	service = ClayDataSetDataProvider.class
)
public class CommerceCatalogChannelsDataSetDataProvider
	implements ClayDataSetDataProvider<Channel> {

	@Override
	public List<Channel> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Channel> channels = new ArrayList<>();

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		List<CommerceChannelRel> commerceChannels =
			_commerceChannelRelService.getCommerceChannelRels(
				CommerceCatalog.class.getName(), commerceCatalogId, null,
				pagination.getStartPosition(), pagination.getEndPosition());

		for (CommerceChannelRel commerceChannelRel : commerceChannels) {
			CommerceChannel commerceChannel =
				commerceChannelRel.getCommerceChannel();

			channels.add(
				new Channel(
					commerceChannel.getCommerceChannelId(),
					commerceChannel.getName(), commerceChannel.getType()));
		}

		return channels;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			CommerceCatalog.class.getName(), commerceCatalogId);
	}

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

}