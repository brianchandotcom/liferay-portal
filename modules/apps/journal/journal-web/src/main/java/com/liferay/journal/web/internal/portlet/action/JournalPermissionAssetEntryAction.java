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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.asset.kernel.action.AssetEntryAction;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"model.class.name=com.liferay.journal.model.JournalArticle"
	},
	service = AssetEntryAction.class
)
public class JournalPermissionAssetEntryAction
	implements AssetEntryAction<JournalArticle> {

	@Override
	public String getDialogTitle(Locale locale) {
		return _language.get(locale, "permissions");
	}

	@Override
	public String getDialogURL(
			HttpServletRequest httpServletRequest,
			AssetRenderer<JournalArticle> assetRenderer)
		throws PortalException {

		JournalArticle article = (JournalArticle)assetRenderer.getAssetObject();

		String permissionURL = null;

		try {
			permissionURL = PermissionsURLTag.doTag(
				StringPool.BLANK, JournalArticle.class.getName(),
				article.getTitle(httpServletRequest.getLocale()), null,
				String.valueOf(article.getResourcePrimKey()),
				LiferayWindowState.POP_UP.toString(), null, httpServletRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return permissionURL;
	}

	@Override
	public String getIcon() {
		return "cog";
	}

	@Override
	public String getMessage(Locale locale) {
		return _language.get(locale, "permissions");
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			AssetRenderer<JournalArticle> assetRenderer)
		throws PortalException {

		JournalArticle article = (JournalArticle)assetRenderer.getAssetObject();

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, article, ActionKeys.PERMISSIONS);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalPermissionAssetEntryAction.class);

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	@Reference
	private Language _language;

}