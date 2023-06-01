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

package com.liferay.commerce.order.content.web.internal.info.item.provider;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = InfoItemCapabilitiesProvider.class)
public class CommerceOrderInfoItemCapabilitiesProvider
	implements InfoItemCapabilitiesProvider<CommerceOrder> {

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		if (FeatureFlagManagerUtil.isEnabled("COMMERCE-9410")) {
			return ListUtil.fromArray(
				_displayPageInfoItemCapability, _templateInfoItemCapability);
		}

		return ListUtil.fromArray();
	}

	@Reference(
		target = "(info.item.capability.key=" + DisplayPageInfoItemCapability.KEY + ")"
	)
	private InfoItemCapability _displayPageInfoItemCapability;

	@Reference(
		target = "(info.item.capability.key=" + TemplateInfoItemCapability.KEY + ")"
	)
	private InfoItemCapability _templateInfoItemCapability;

}