/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.rest.dto.v1_0.Channel;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.internal.dto.v1_0.util.FaroPaginationUtil;
import com.liferay.osb.faro.rest.resource.v1_0.ChannelResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Leslie Wong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/channel.properties",
	scope = ServiceScope.PROTOTYPE, service = ChannelResource.class
)
public class ChannelResourceImpl extends BaseChannelResourceImpl {

	@Override
	public Channel getWorkspaceGroupChannel(Long groupId, String channelId)
		throws Exception {

		return _channelDTOConverter.toDTO(
			new FaroDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), channelId,
				contextAcceptLanguage.getPreferredLocale()),
			_contactsEngineClient.getChannel(
				_faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId));
	}

	@Override
	public Page<Channel> getWorkspaceGroupChannelsPage(
			Long groupId, Pagination pagination)
		throws Exception {

		Results<com.liferay.osb.faro.engine.client.model.Channel> results =
			_contactsEngineClient.getChannels(
				_faroProjectLocalService.getFaroProjectByGroupId(groupId),
				FaroPaginationUtil.getCur(pagination),
				FaroPaginationUtil.getDelta(pagination), null, null);

		return Page.of(
			transform(
				results.getItems(),
				engineChannel -> _channelDTOConverter.toDTO(
					new FaroDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(),
						engineChannel.getId(),
						contextAcceptLanguage.getPreferredLocale()),
					engineChannel)),
			pagination, results.getTotal());
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.ChannelDTOConverter)"
	)
	private DTOConverter
		<com.liferay.osb.faro.engine.client.model.Channel, Channel>
			_channelDTOConverter;

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}