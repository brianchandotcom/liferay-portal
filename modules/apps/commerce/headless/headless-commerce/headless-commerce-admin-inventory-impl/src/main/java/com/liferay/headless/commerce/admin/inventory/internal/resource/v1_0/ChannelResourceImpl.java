/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseChannel;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.ChannelResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Crescenzo Rega
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/channel.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ChannelResource.class
)
public class ChannelResourceImpl extends BaseChannelResourceImpl {

	@NestedField(parentClass = WarehouseChannel.class, value = "channel")
	@Override
	public Channel getWarehouseChannelChannel(Long id) throws Exception {
		CommerceChannelRel commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(id);

		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceChannelRel.getCommerceChannelId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter.ChannelDTOConverter)"
	)
	private DTOConverter<CommerceChannel, Channel> _channelDTOConverter;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

}