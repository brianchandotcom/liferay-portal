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

package com.liferay.portal.kernel.notifications;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
@ProviderType
public abstract class BaseModelUserNotificationHandler
	extends BaseUserNotificationHandler {

	protected AssetRenderer<?> getAssetRenderer(JSONObject jsonObject) {
		String className = jsonObject.getString("className");
		long classPK = jsonObject.getLong("classPK");

		return getAssetRenderer(className, classPK);
	}

	protected AssetRenderer<?> getAssetRenderer(
		String className, long classPK) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return null;
		}

		AssetRenderer<?> assetRenderer = null;

		try {
			assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
		}
		catch (Exception e) {
		}

		return assetRenderer;
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		AssetRenderer<?> assetRenderer = getAssetRenderer(jsonObject);

		if (assetRenderer == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					StringUtil.shorten(getBodyContent(jsonObject), 70)),
				getTitle(jsonObject, assetRenderer, serviceContext)
			});
	}

	protected String getBodyContent(JSONObject jsonObject) {
		return jsonObject.getString("entryTitle");
	}

	protected String getFormattedMessage(
		JSONObject jsonObject, ServiceContext serviceContext, String message,
		String typeName) {

		ResourceBundle resourceBundle = getResourceBundle(
			serviceContext.getLocale());

		return ResourceBundleUtil.getString(
			resourceBundle, message,
			new String[] {
				HtmlUtil.escape(
					PortalUtil.getUserName(
						jsonObject.getLong("userId"), StringPool.BLANK)),
				StringUtil.toLowerCase(HtmlUtil.escape(typeName))
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		return jsonObject.getString("entryURL");
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		return resourceBundleLoader.loadResourceBundle(
			LocaleUtil.toLanguageId(locale));
	}

	protected ResourceBundleLoader getResourceBundleLoader() {
		return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
	}

	protected String getTitle(
		JSONObject jsonObject, AssetRenderer<?> assetRenderer,
		ServiceContext serviceContext) {

		String message = StringPool.BLANK;

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetRenderer.getClassName());

		String typeName = assetRendererFactory.getTypeName(
			serviceContext.getLocale());

		int notificationType = jsonObject.getInt("notificationType");

		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			message = "x-added-a-new-x";
		}
		else if (notificationType ==
					UserNotificationDefinition.
						NOTIFICATION_TYPE_UPDATE_ENTRY) {

			message = "x-updated-a-x";
		}

		return getFormattedMessage(
			jsonObject, serviceContext, message, typeName);
	}

}