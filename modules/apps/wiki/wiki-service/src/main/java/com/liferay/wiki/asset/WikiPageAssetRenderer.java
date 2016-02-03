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

package com.liferay.wiki.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseJSPAssetRenderer;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.util.WikiUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 * @author Sergio González
 */
public class WikiPageAssetRenderer
	extends BaseJSPAssetRenderer<WikiPage> implements TrashRenderer {

	public static final String TYPE = "wiki_page";

	public static long getClassPK(WikiPage page) {
		if (!page.isApproved() && !page.isDraft() && !page.isPending() &&
			!page.isInTrash() &&
			(page.getVersion() != WikiPageConstants.VERSION_DEFAULT)) {

			return page.getPageId();
		}
		else {
			return page.getResourcePrimKey();
		}
	}

	public WikiPageAssetRenderer(WikiPage page) throws PortalException {
		_page = page;

		_wikiGroupServiceOverriddenConfiguration =
			ConfigurationFactoryUtil.getConfiguration(
				WikiGroupServiceOverriddenConfiguration.class,
				new GroupServiceSettingsLocator(
					page.getGroupId(), WikiConstants.SERVICE_NAME));
	}

	@Override
	public WikiPage getAssetObject() {
		return _page;
	}

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public long getClassPK() {
		return getClassPK(_page);
	}

	@Override
	public String getDiscussionPath() {
		if (_wikiGroupServiceOverriddenConfiguration.pageCommentsEnabled()) {
			return "edit_page_discussion";
		}
		else {
			return null;
		}
	}

	@Override
	public Date getDisplayDate() {
		return _page.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _page.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/wiki/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletId() {
		return WikiPortletKeys.WIKI;
	}

	@Override
	public int getStatus() {
		return _page.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String content = _page.getContent();

		try {
			content = HtmlUtil.extractText(
				WikiUtil.convert(_page, null, null, null));
		}
		catch (Exception e) {
		}

		return content;
	}

	@Override
	public String getTitle(Locale locale) {
		if (!_page.isInTrash()) {
			return _page.getTitle();
		}

		return TrashUtil.getOriginalTitle(_page.getTitle());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, WikiPortletKeys.WIKI,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, WikiPortletKeys.WIKI,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/wiki/export_page");
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());

		return portletURL;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<WikiPage> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter("mvcRenderCommandName", "/wiki/view");
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());
		portletURL.setWindowState(windowState);

		return portletURL.toString();
	}

	@Override
	public PortletURL getURLViewDiffs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, WikiPortletKeys.WIKI,
			PortletRequest.RENDER_PHASE);

		WikiPage previousVersionPage =
			WikiPageLocalServiceUtil.getPreviousVersionPage(_page);

		if (previousVersionPage.getVersion() == _page.getVersion()) {
			return null;
		}

		portletURL.setParameter(
			"mvcRenderCommandName", "/wiki/compare_versions");
		portletURL.setParameter("groupId", String.valueOf(_page.getGroupId()));
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());
		portletURL.setParameter(
			"sourceVersion", String.valueOf(previousVersionPage.getVersion()));
		portletURL.setParameter(
			"targetVersion", String.valueOf(_page.getVersion()));

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect, "/wiki/find_page",
			"pageResourcePrimKey", _page.getResourcePrimKey());
	}

	@Override
	public long getUserId() {
		return _page.getUserId();
	}

	@Override
	public String getUserName() {
		return _page.getUserName();
	}

	@Override
	public String getUuid() {
		return _page.getUuid();
	}

	public boolean hasDeletePermission(PermissionChecker permissionChecker) {
		return WikiPagePermissionChecker.contains(
			permissionChecker, _page, ActionKeys.DELETE);
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return WikiPagePermissionChecker.contains(
			permissionChecker, _page, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return WikiPagePermissionChecker.contains(
			permissionChecker, _page, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WikiWebKeys.WIKI_PAGE, _page);

		return super.include(request, response, template);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final WikiPage _page;
	private final WikiGroupServiceOverriddenConfiguration
		_wikiGroupServiceOverriddenConfiguration;

}