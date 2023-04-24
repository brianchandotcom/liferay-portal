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

package com.liferay.commerce.channel.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.channel.web.internal.constants.CommerceChannelFDSNames;
import com.liferay.commerce.channel.web.internal.model.Channel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"fds.data.provider.key=" + CommerceChannelFDSNames.CHANNEL,
		"frontend.data.set.name=" + CommerceChannelFDSNames.CHANNEL
	},
	service = {FDSDataProvider.class, FDSView.class}
)
public class CommerceChannelTableFDSView
	extends BaseTableFDSView implements FDSDataProvider<Channel> {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "name",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"type", "type"
		).build();
	}

	@Override
	public List<Channel> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<Channel> channels = new ArrayList<>();

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			_portal.getCompanyId(httpServletRequest), fdsKeywords.getKeywords(),
			fdsPagination.getStartPosition(), fdsPagination.getEndPosition(),
			sort);

		for (CommerceChannel commerceChannel : commerceChannels) {
			channels.add(
				new Channel(
					commerceChannel.getCommerceChannelId(),
					commerceChannel.getName(),
					_language.get(
						httpServletRequest, commerceChannel.getType())));
		}

		return channels;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		return _commerceChannelService.searchCommerceChannelsCount(
			_portal.getCompanyId(httpServletRequest),
			fdsKeywords.getKeywords());
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}