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

package com.liferay.layout.admin.web.internal.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class LayoutRevisionAssetRenderer
	extends BaseJSPAssetRenderer<LayoutRevision> {

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
	public LayoutRevision getAssetObject() {
		return _layoutRevision;
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
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _layoutRevision.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Locale locale = getLocale(portletRequest);

		StringBundler sb = new StringBundler(15);

		sb.append(LanguageUtil.get(locale, "page"));
		sb.append(": ");
		sb.append(_layoutRevision.getHTMLTitle(locale));
		sb.append("\n");
		sb.append(LanguageUtil.get(locale, "site-pages-variation"));
		sb.append(": ");
		sb.append(LanguageUtil.get(locale, _layoutSetBranch.getName()));
		sb.append("\n");
		sb.append(LanguageUtil.get(locale, "page-variation"));
		sb.append(": ");
		sb.append(LanguageUtil.get(locale, _layoutBranch.getName()));
		sb.append("\n");
		sb.append(LanguageUtil.get(locale, "revision-id"));
		sb.append(": ");
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
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.LAYOUT_REVISION, _layoutRevision);

		return super.include(request, response, template);
	}

	@Override
	public boolean isPreviewInContext() {
		return true;
	}

	private final LayoutBranch _layoutBranch;
	private final LayoutRevision _layoutRevision;
	private final LayoutSetBranch _layoutSetBranch;

}