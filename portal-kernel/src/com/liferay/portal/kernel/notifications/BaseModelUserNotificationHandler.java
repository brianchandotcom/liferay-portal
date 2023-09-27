/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.notifications;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.annotation.versioning.ProviderType;

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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
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
				getTitle(userNotificationEvent, serviceContext)
			});
	}

	protected String getBodyContent(JSONObject jsonObject) {
		return jsonObject.getString("entryTitle");
	}

	protected String getFormattedMessage(
		JSONObject jsonObject, ServiceContext serviceContext, String message,
		String typeName) {

		return LanguageUtil.format(
			serviceContext.getLocale(), message,
			new String[] {
				HtmlUtil.escape(_getUserFullName(jsonObject)),
				StringUtil.toLowerCase(HtmlUtil.escape(typeName))
			},
			false);
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		String entryURL = jsonObject.getString("entryURL");

		if (Validator.isNull(entryURL)) {
			return StringPool.BLANK;
		}

		String entryURLDomain = HttpComponentsUtil.getDomain(entryURL);

		String portalURLDomain = HttpComponentsUtil.getDomain(
			serviceContext.getPortalURL());

		if (!entryURLDomain.equals(portalURLDomain)) {
			entryURL = StringUtil.replaceFirst(
				entryURL, entryURLDomain, portalURLDomain);
		}

		return entryURL;
	}

	protected String getTitle(
			JSONObject jsonObject, AssetRenderer<?> assetRenderer,
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

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
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY) {

			message = "x-updated-a-x";
		}

		return getFormattedMessage(
			jsonObject, serviceContext, message, typeName);
	}

	@Override
	protected String getTitle(
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

		return getTitle(
			jsonObject, assetRenderer, userNotificationEvent, serviceContext);
	}

	private String _getUserFullName(JSONObject jsonObject) {
		String fullName = jsonObject.getString("fullName");

		if (Validator.isNotNull(fullName)) {
			return fullName;
		}

		return PortalUtil.getUserName(
			jsonObject.getLong("userId"), StringPool.BLANK);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseModelUserNotificationHandler.class);

}