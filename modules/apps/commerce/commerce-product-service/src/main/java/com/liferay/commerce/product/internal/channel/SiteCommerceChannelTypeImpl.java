/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.channel;

import com.liferay.commerce.product.channel.CommerceChannelType;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
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
		return _language.get(
			locale, CommerceChannelConstants.CHANNEL_TYPE_SITE);
	}

	@Override
	public UnicodeProperties getTypeSettingsUnicodeProperties(
		Map<String, String[]> parameterMap) {

		return UnicodePropertiesBuilder.create(
			true
		).put(
			"groupIds",
			StringUtil.merge(
				GetterUtil.getLongValues(
					parameterMap.get(
						"CommerceChannelSitesSearchContainerPrimaryKeys")))
		).build();
	}

	@Reference
	private Language _language;

}