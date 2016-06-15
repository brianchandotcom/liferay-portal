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

package com.liferay.knowledge.base.web.portlet.configuration.icon;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"path=/admin/view_article.jsp", "path=/admin/view_articles.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class KBArticleSubscriptionPortletConfigurationIcon
	extends BaseGetKBArticlePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		String key = "subscribe";

		if (isSubscribed(portletRequest)) {
			key = "unsubscribe";
		}

		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), key);
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
			PortletRequest.ACTION_PHASE);

		if (isSubscribed(portletRequest)) {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "unsubscribeKBArticle");
		}
		else {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "subscribeKBArticle");
		}

		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));

		KBArticle kbArticle = getKBArticle(portletRequest);

		portletURL.setParameter(
			"resourceClassNameId", String.valueOf(kbArticle.getClassNameId()));
		portletURL.setParameter(
			"resourcePrimKey", String.valueOf(kbArticle.getResourcePrimKey()));

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 110;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		KBArticle kbArticle = getKBArticle(portletRequest);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if ((kbArticle.isApproved() || !kbArticle.isFirstVersion()) &&
			KBArticlePermission.contains(
				permissionChecker, kbArticle, KBActionKeys.SUBSCRIBE)) {

			return true;
		}

		return false;
	}

	protected boolean isSubscribed(PortletRequest portletRequest) {
		KBArticle kbArticle = getKBArticle(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _subscriptionLocalService.isSubscribed(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			KBArticle.class.getName(), kbArticle.getResourcePrimKey());
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private SubscriptionLocalService _subscriptionLocalService;

}