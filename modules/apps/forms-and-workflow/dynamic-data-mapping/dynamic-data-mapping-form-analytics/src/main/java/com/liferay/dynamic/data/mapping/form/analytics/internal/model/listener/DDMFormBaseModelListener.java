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

package com.liferay.dynamic.data.mapping.form.analytics.internal.model.listener;

import com.liferay.analytics.client.AnalyticsClient;
import com.liferay.analytics.model.AnalyticsEventsMessage;
import com.liferay.analytics.model.AnalyticsEventsMessage.Builder;
import com.liferay.analytics.model.AnalyticsEventsMessage.Event;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
public abstract class DDMFormBaseModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	protected void sendAnalytics(
			long companyId, String eventId, Map<String, String> properties)
		throws Exception {

		String analyticsKey = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_ANALYTICS_KEY);

		if (Validator.isNull(analyticsKey)) {
			return;
		}

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder(_APPLICATION_ID, eventId);

		eventBuilder.properties(properties);

		Event event = eventBuilder.build();

		Builder builder = AnalyticsEventsMessage.builder(analyticsKey, null);

		builder = builder.event(event);

		analyticsClient.sendAnalytics(builder.build());
	}

	@Reference
	protected AnalyticsClient analyticsClient;

	private static final String _APPLICATION_ID = "Forms";

}