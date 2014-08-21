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

package com.liferay.portal.asset;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Raymond Augé
 */
public class LayoutRevisionAssetRenderer extends BaseAssetRenderer {

	public LayoutRevisionAssetRenderer(LayoutRevision layoutRevision) {
		_layoutRevision = layoutRevision;

		try {
			_layoutBranch = layoutRevision.getLayoutBranch();

			_layoutSetBranch =
				LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
					_layoutRevision.getLayoutSetBranchId());
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String getClassName() {
		return LayoutRevision.class.getName();
	}

	@Override
	public long getClassPK() {
		return _layoutRevision.getLayoutRevisionId();
	}

	@Override
	public long getGroupId() {
		return _layoutRevision.getGroupId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Locale locale = getLocale(portletRequest);

		StringBundler sb = new StringBundler(16);

		sb.append("<strong>");
		sb.append(LanguageUtil.get(locale, "page"));
		sb.append(":</strong> ");
		sb.append(_layoutRevision.getHTMLTitle(locale));
		sb.append("<br /><strong>");
		sb.append(LanguageUtil.get(locale, "site-pages-variation"));
		sb.append(":</strong> ");
		sb.append(LanguageUtil.get(locale, _layoutSetBranch.getName()));
		sb.append("<br /><strong>");
		sb.append(LanguageUtil.get(locale, "page-variation"));
		sb.append(":</strong> ");
		sb.append(LanguageUtil.get(locale, _layoutBranch.getName()));
		sb.append("<br /><strong>");
		sb.append(LanguageUtil.get(locale, "revision-id"));
		sb.append(":</strong> ");
		sb.append(_layoutRevision.getLayoutRevisionId());

		return sb.toString();
	}

	@Override
	public String getTitle(Locale locale) {
		return _layoutRevision.getHTMLTitle(locale);
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = LayoutLocalServiceUtil.getLayout(
				_layoutRevision.getPlid());

			String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);

			layoutURL = HttpUtil.addParameter(
				layoutURL, "layoutSetBranchId",
				_layoutRevision.getLayoutSetBranchId());
			layoutURL = HttpUtil.addParameter(
				layoutURL, "layoutRevisionId",
				_layoutRevision.getLayoutRevisionId());

			return layoutURL;
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	@Override
	public long getUserId() {
		return _layoutRevision.getUserId();
	}

	@Override
	public String getUserName() {
		return _layoutRevision.getUserName();
	}

	@Override
	public String getUuid() {
		return null;
	}

	@Override
	public boolean isPreviewInContext() {
		return true;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			renderRequest.setAttribute(
				WebKeys.LAYOUT_REVISION, _layoutRevision);

			return "/html/portlet/layouts_admin/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private final LayoutBranch _layoutBranch;
	private final LayoutRevision _layoutRevision;
	private final LayoutSetBranch _layoutSetBranch;

}