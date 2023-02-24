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

package com.liferay.analytics.settings.rest.internal.dto.v1_0.converter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Riccardo Ferrari
 */
public class ChannelDTOConverterContext extends DefaultDTOConverterContext {

	public ChannelDTOConverterContext(
		String[] analyticsChannelIds, String analyticsDataSourceId, Object id,
		Locale locale) {

		super(false, new HashMap<>(), null, id, locale, null, null);

		_analyticsChannelIds = analyticsChannelIds;

		_analyticsDataSourceId = GetterUtil.getLong(analyticsDataSourceId);
	}

	public boolean isCommerceSyncEnabled(String analyticsChannelId) {
		return ArrayUtil.contains(_analyticsChannelIds, analyticsChannelId);
	}

	public boolean isLocalAnalyticsDataSource(Long analyticsDataSourceId) {
		return Objects.equals(analyticsDataSourceId, _analyticsDataSourceId);
	}

	private final String[] _analyticsChannelIds;
	private final Long _analyticsDataSourceId;

}