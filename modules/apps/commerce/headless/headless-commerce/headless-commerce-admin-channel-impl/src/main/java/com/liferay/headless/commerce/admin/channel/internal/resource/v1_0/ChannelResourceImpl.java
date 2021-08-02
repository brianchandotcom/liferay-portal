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

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.ChannelDTOConverter;
import com.liferay.headless.commerce.admin.channel.internal.odata.entity.v1_0.ChannelEntityModel;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/channel.properties",
	scope = ServiceScope.PROTOTYPE, service = ChannelResource.class
)
public class ChannelResourceImpl
	extends BaseChannelResourceImpl implements EntityModelResource {

	@Override
	public void deleteChannel(Long channelId) throws Exception {
		_commerceChannelService.deleteCommerceChannel(channelId);
	}

	@Override
	public Channel getChannel(Long channelId) throws Exception {
		CommerceChannel commerceChannel =
			_commerceChannelService.fetchCommerceChannel(channelId);

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		return _toChannel(commerceChannel);
	}

	@Override
	public Page<Channel> getChannelsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommerceChannel.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toChannel(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Channel patchChannel(Long channelId, Channel channel)
		throws Exception {

		Channel existingChannel = getChannel(channelId);

		if (channel.getCurrencyCode() != null) {
			existingChannel.setCurrencyCode(channel.getCurrencyCode());
		}

		if (channel.getExternalReferenceCode() != null) {
			existingChannel.setExternalReferenceCode(
				channel.getExternalReferenceCode());
		}

		if (channel.getName() != null) {
			existingChannel.setName(channel.getName());
		}

		if (channel.getType() != null) {
			existingChannel.setType(channel.getType());
		}

		return putChannel(channelId, existingChannel);
	}

	@Override
	public Channel postChannel(Channel channel) throws Exception {
		return _toChannel(
			_commerceChannelService.addCommerceChannel(
				channel.getExternalReferenceCode(),
				GetterUtil.get(channel.getSiteGroupId(), 0), channel.getName(),
				channel.getType(), null, channel.getCurrencyCode(),
				_serviceContextHelper.getServiceContext(contextUser)));
	}

	@Override
	public Channel putChannel(Long channelId, Channel channel)
		throws Exception {

		return _toChannel(
			_commerceChannelService.updateCommerceChannel(
				channelId, channel.getSiteGroupId(), channel.getName(),
				channel.getType(), null, channel.getCurrencyCode()));
	}

	private Channel _toChannel(CommerceChannel commerceChannel)
		throws Exception {

		return _toChannel(commerceChannel.getCommerceChannelId());
	}

	private Channel _toChannel(Long commerceChannelId) throws Exception {
		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceChannelId, contextAcceptLanguage.getPreferredLocale()));
	}

	private static final EntityModel _entityModel = new ChannelEntityModel();

	@Reference
	private ChannelDTOConverter _channelDTOConverter;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}