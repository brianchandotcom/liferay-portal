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

package com.liferay.commerce.product.internal.channel;

import com.liferay.commerce.product.channel.CommerceChannelType;
import com.liferay.commerce.product.model.CommerceChannelConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alec Sloan
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.channel.type.key=" + CommerceChannelConstants.CHANNEL_TYPE_SITE,
		"commerce.product.channel.type.order:Integer=10"
	},
	service = CommerceChannelType.class
)
public class SiteCommerceChannelTypeImpl implements CommerceChannelType {

	@Override
	public String getKey() {
		return CommerceChannelConstants.CHANNEL_TYPE_SITE;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale, CommerceChannelConstants.CHANNEL_TYPE_SITE);
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties(
		Map<String, String[]> parameterMap) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		long[] groupIds = GetterUtil.getLongValues(
			parameterMap.get("CommerceChannelSitesSearchContainerPrimaryKeys"));

		typeSettingsProperties.put("groupIds", StringUtil.merge(groupIds));

		return typeSettingsProperties;
	}

}