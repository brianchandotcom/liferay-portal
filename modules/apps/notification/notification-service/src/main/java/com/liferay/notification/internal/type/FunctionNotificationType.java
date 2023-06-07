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

package com.liferay.notification.internal.type;

import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.internal.configuration.FunctionNotificationTypeConfiguration;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.type.BaseNotificationType;
import com.liferay.notification.type.NotificationType;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	configurationPid = "com.liferay.notification.internal.configuration.FunctionNotificationTypeConfiguration",
	factory = "com.liferay.notification.internal.type.FunctionNotificationType",
	service = NotificationType.class
)
public class FunctionNotificationType extends BaseNotificationType {

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		notificationContext.setNotificationRecipient(notificationRecipient);
		notificationContext.setNotificationRecipientSettings(
			notificationRecipient.getNotificationRecipientSettings());

		_portalCatapult.launch(
			_companyId, Http.Method.POST,
			_functionNotificationTypeConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			_jsonFactory.createJSONObject(
				_jsonFactory.looseSerialize(
					notificationContext, "notificationRecipientSettings",
					"termValues")),
			_functionNotificationTypeConfiguration.resourcePath(),
			notificationContext.getUserId());
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_companyId = ConfigurationFactoryUtil.getCompanyId(
			_companyLocalService, properties);
		_functionNotificationTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionNotificationTypeConfiguration.class, properties);
		_type = GetterUtil.getString(properties.get("notification.type"));
	}

	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	private FunctionNotificationTypeConfiguration
		_functionNotificationTypeConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortalCatapult _portalCatapult;

	private String _type;

}