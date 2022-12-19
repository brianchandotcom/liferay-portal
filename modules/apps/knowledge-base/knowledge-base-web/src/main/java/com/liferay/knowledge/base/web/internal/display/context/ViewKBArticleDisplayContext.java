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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;

import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;

/**
 * @author Ambrín Chaudhary
 */
public class ViewKBArticleDisplayContext {

	public ViewKBArticleDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_currentURL = String.valueOf(
			PortletURLUtil.getCurrent(
				liferayPortletRequest, liferayPortletResponse));

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getEditArticleURL(KBArticle kbArticle) {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/admin/common/edit_kb_article.jsp"
		).setRedirect(
			_currentURL
		).setParameter(
			"resourcePrimKey", kbArticle.getResourcePrimKey()
		).buildString();
	}

	public String getSubscriptionLabel(KBArticle kbArticle) {
		if (_hasSubscription(kbArticle)) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "unsubscribe");
		}

		return LanguageUtil.get(
			_liferayPortletRequest.getHttpServletRequest(), "subscribe");
	}

	public ActionURL getSubscriptionURL(KBArticle kbArticle) {
		String actionName = "/knowledge_base/subscribe_kb_article";

		if (_hasSubscription(kbArticle)) {
			actionName = "/knowledge_base/unsubscribe_kb_article";
		}

		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			actionName
		).setRedirect(
			_currentURL
		).setParameter(
			"resourcePrimKey", kbArticle.getResourcePrimKey()
		).buildActionURL();
	}

	public boolean isSubscriptionEnabled(KBArticle kbArticle) throws Exception {
		if (_isSubscriptionEnabled() && _hasSubscriptionPermission(kbArticle)) {
			return true;
		}

		return false;
	}

	private boolean _hasSubscription(KBArticle kbArticle) {
		if (SubscriptionLocalServiceUtil.isSubscribed(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId(),
				KBArticle.class.getName(), kbArticle.getResourcePrimKey())) {

			return true;
		}

		return false;
	}

	private boolean _hasSubscriptionPermission(KBArticle kbArticle)
		throws Exception {

		if ((kbArticle.isApproved() || !kbArticle.isFirstVersion()) &&
			KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.SUBSCRIBE)) {

			return true;
		}

		return false;
	}

	private Boolean _isSubscriptionEnabled() {
		return GetterUtil.getBoolean(
			_liferayPortletRequest.getAttribute(
				"init.jsp-enableKBArticleSubscriptions"),
			true);
	}

	private final String _currentURL;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}