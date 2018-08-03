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

package com.liferay.mentions.web.internal.notifications;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + MentionsPortletKeys.MENTIONS,
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

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				serviceContext.getLocale());

		if ((mbMessage != null) && mbMessage.isDiscussion()) {
			return LanguageUtil.format(
				resourceBundle, "x-mentioned-you-in-a-comment-in-a-x",
				new String[] {
					HtmlUtil.escape(
						_portal.getUserName(
							jsonObject.getLong("userId"), StringPool.BLANK)),
					StringUtil.toLowerCase(HtmlUtil.escape(typeName))
				},
				false);
		}
		else {
			return LanguageUtil.format(
				resourceBundle, "x-mentioned-you-in-a-x",
				new String[] {
					HtmlUtil.escape(
						_portal.getUserName(
							jsonObject.getLong("userId"), StringPool.BLANK)),
					StringUtil.toLowerCase(HtmlUtil.escape(typeName))
				},
				false);
		}
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.mentions.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}