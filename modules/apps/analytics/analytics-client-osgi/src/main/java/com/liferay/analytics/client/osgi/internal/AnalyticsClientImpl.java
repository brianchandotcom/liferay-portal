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

package com.liferay.analytics.client.osgi.internal;

import com.liferay.analytics.client.AnalyticsClient;
import com.liferay.analytics.client.IdentityClient;
import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.AnalyticsEventsMessage;
import com.liferay.analytics.model.IdentityContextMessage;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = AnalyticsClient.class)
public class AnalyticsClientImpl implements AnalyticsClient {

	public String sendAnalytics(AnalyticsEventsMessage analyticsEventsMessage)
		throws Exception {

		if (analyticsEventsMessage.getUserId() == null) {
			String userId = getUserId(analyticsEventsMessage.getAnalyticsKey());

			AnalyticsEventsMessage.Builder builder =
				AnalyticsEventsMessage.builder(analyticsEventsMessage);

			analyticsEventsMessage = builder.userId(userId).build();
		}

		String jsonAnalyticsEventsMessage = _jsonObjectMapper.map(
			analyticsEventsMessage);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Sending analytics message %s to destination %s//%s:%s%s",
					jsonAnalyticsEventsMessage,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PROTOCOL,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_HOST,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PORT,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PATH));
		}

		return _jsonWebServiceClient.doPostAsJSON(
			_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PATH,
			jsonAnalyticsEventsMessage);
	}

	@Activate
	protected void activate() {
		Properties properties = new Properties();

		properties.setProperty(
			"hostName", _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_HOST);
		properties.setProperty(
			"hostPort", _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PORT);
		properties.setProperty(
			"protocol", _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PROTOCOL);

		ComponentInstance componentInstance = _componentFactory.newInstance(
			(Dictionary)properties);

		componentInstance.getInstance();

		_jsonWebServiceClient =
			(JSONWebServiceClient)componentInstance.getInstance();
	}

	@Deactivate
	protected void deactivate() {
		_jsonWebServiceClient.destroy();
	}

	protected String getUserId(String analyticsKey) throws Exception {
		HttpSession session = PortalSessionThreadLocal.getHttpSession();

		if ((session != null) &&
			(session.getAttribute(_REQUEST_ATTRIBUTE_NAME_ANALYTICS_USER_ID) !=
				null)) {

			return (String)session.getAttribute(
				_REQUEST_ATTRIBUTE_NAME_ANALYTICS_USER_ID);
		}

		IdentityContextMessage.Builder identityContextMessageBuilder =
			IdentityContextMessage.builder(analyticsKey);

		identityContextMessageBuilder.protocolVersion("1.0");

		// User profile

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		if (user != null) {
			identityContextMessageBuilder.dataSourceIndividualIdentifier(
				GetterUtil.getString(user.getUserId()));

			identityContextMessageBuilder.identityFieldsProperty(
				"email", user.getEmailAddress());
			identityContextMessageBuilder.identityFieldsProperty(
				"name", user.getFullName());
		}

		// User session info

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		if (locale != null) {
			identityContextMessageBuilder.language(locale.getLanguage());
		}

		TimeZone timeZone = TimeZoneThreadLocal.getThemeDisplayTimeZone();

		if (timeZone != null) {
			identityContextMessageBuilder.timezone(timeZone.getDisplayName());
		}

		String userId = _identityClient.getUserId(
			identityContextMessageBuilder.build());

		if (session != null) {
			session.setAttribute(
				_REQUEST_ATTRIBUTE_NAME_ANALYTICS_USER_ID, userId);
		}

		return userId;
	}

	@Reference(
		target = "(component.factory=JSONWebServiceClient)", unbind = "-"
	)
	protected void setComponentFactory(ComponentFactory componentFactory) {
		_componentFactory = componentFactory;
	}

	@Reference(unbind = "-")
	protected void setIdentityClient(IdentityClient identityClient) {
		_identityClient = identityClient;
	}

	@Reference(
		target = "(model=com.liferay.analytics.model.AnalyticsEventsMessage)",
		unbind = "-"
	)
	protected void setJSONObjectMapper(
		JSONObjectMapper<AnalyticsEventsMessage> jsonObjectMapper) {

		_jsonObjectMapper = jsonObjectMapper;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final String _REQUEST_ATTRIBUTE_NAME_ANALYTICS_USER_ID =
		"ANALYTICS_USER_ID";

	private static final String _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_HOST =
		System.getProperty("analytics.gateway.host", "ec-dev.liferay.com");

	private static final String _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PATH =
		System.getProperty(
			"analytics.gateway.path",
			"/api/analyticsgateway/send-analytics-events");

	private static final String _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PORT =
		System.getProperty("analytics.gateway.port", "8095");

	private static final String
		_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PROTOCOL = System.getProperty(
			"analytics.gateway.protocol", "https");

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsClientImpl.class);

	private ComponentFactory _componentFactory;
	private IdentityClient _identityClient;
	private JSONObjectMapper<AnalyticsEventsMessage> _jsonObjectMapper;
	private JSONWebServiceClient _jsonWebServiceClient;
	private UserLocalService _userLocalService;

}