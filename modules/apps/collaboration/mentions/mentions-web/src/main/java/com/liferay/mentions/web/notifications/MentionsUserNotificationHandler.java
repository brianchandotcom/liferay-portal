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

package com.liferay.mentions.web.notifications;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.mentions.web.constants.MentionsPortletKeys;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + MentionsPortletKeys.MENTIONS},
	service = UserNotificationHandler.class
)
public class MentionsUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public MentionsUserNotificationHandler() {
		setPortletId(MentionsPortletKeys.MENTIONS);
	}

	@Override
	protected AssetRenderer<?> getAssetRenderer(JSONObject jsonObject) {
		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(
			jsonObject.getLong("classPK"));

		if ((mbMessage != null) && mbMessage.isDiscussion()) {
			return getAssetRenderer(
				mbMessage.getClassName(), mbMessage.getClassPK());
		}
		else {
			return getAssetRenderer(
				jsonObject.getString("className"),
				jsonObject.getLong("classPK"));
		}
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected String getTitle(
		JSONObject jsonObject, AssetRenderer<?> assetRenderer,
		ServiceContext serviceContext) {

		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(
			jsonObject.getLong("classPK"));

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetRenderer.getClassName());

		String typeName = assetRendererFactory.getTypeName(
			serviceContext.getLocale());

		String message = StringPool.BLANK;
		String[] arguments = null;

		if ((mbMessage != null) && mbMessage.isDiscussion()) {
			message = "x-mentioned-you-in-a-comment-in-a-x";
			arguments = new String[] {
				HtmlUtil.escape(assetRenderer.getUserName()),
				StringUtil.toLowerCase(HtmlUtil.escape(typeName))
			};
		}
		else {
			message = "x-mentioned-you-in-a-x";
			arguments = new String[] {
				HtmlUtil.escape(assetRenderer.getUserName()),
				StringUtil.toLowerCase(HtmlUtil.escape(typeName))
			};
		}

		ResourceBundle resourceBundle = getResourceBundle(
			serviceContext.getLocale());

		return ResourceBundleUtil.getString(resourceBundle, message, arguments);
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.mentions.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader, LanguageResources.RESOURCE_BUNDLE_LOADER);
	}

	private MBMessageLocalService _mbMessageLocalService;
	private ResourceBundleLoader _resourceBundleLoader;

}