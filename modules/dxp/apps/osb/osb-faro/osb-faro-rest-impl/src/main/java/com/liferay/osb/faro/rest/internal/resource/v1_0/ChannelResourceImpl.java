/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.osb.faro.rest.dto.v1_0.Channel;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.resource.v1_0.ChannelResource;
import com.liferay.osb.faro.service.FaroChannelLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

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
			_faroChannelLocalService.getFaroChannel(channelId, groupId));
	}

	@Override
	public Page<Channel> getWorkspaceGroupChannelsPage(Long groupId)
		throws Exception {

		return Page.of(
			transform(
				_faroChannelLocalService.search(
					groupId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				faroChannel -> _channelDTOConverter.toDTO(
					new FaroDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(),
						faroChannel.getChannelId(),
						contextAcceptLanguage.getPreferredLocale()),
					faroChannel)));
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.ChannelDTOConverter)"
	)
	private DTOConverter<FaroChannel, Channel> _channelDTOConverter;

	@Reference
	private FaroChannelLocalService _faroChannelLocalService;

}