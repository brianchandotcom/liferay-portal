/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class CommerceChannelDTOConverterContext
	extends DefaultDTOConverterContext {

	public CommerceChannelDTOConverterContext(
		Object id, Locale locale, Map<Long, String> channelNames) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_channelNames = channelNames;
	}

	public String getChannelName(Long analyticsChannelId) {
		if (analyticsChannelId == null) {
			return null;
		}

		return _channelNames.getOrDefault(analyticsChannelId, null);
	}

	private final Map<Long, String> _channelNames;

}