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

package com.liferay.wiki.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.web.internal.security.permission.resource.WikiPagePermission;

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

	/**
	 * @deprecated As of 1.6.0, replaced by {@link
	 *             #WikiPageAssetRenderer(WikiPage, WikiEngineRenderer,
	 *             TrashHelper)}
	 */
	@Deprecated
	public WikiPageAssetRenderer(
		WikiPage page, WikiEngineRenderer wikiEngineRenderer) {

		this(page, wikiEngineRenderer, null);
	}

	public WikiPageAssetRenderer(
		WikiPage page, WikiEngineRenderer wikiEngineRenderer,
		TrashHelper trashHelper) {

		_page = page;
		_wikiEngineRenderer = wikiEngineRenderer;
		_trashHelper = trashHelper;
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
		if (_wikiGroupServiceOverriddenConfiguration == null) {
			try {
				_wikiGroupServiceOverriddenConfiguration =
					ConfigurationProviderUtil.getConfiguration(
						WikiGroupServiceOverriddenConfiguration.class,
						new GroupServiceSettingsLocator(
							_page.getGroupId(), WikiConstants.SERVICE_NAME));
			}
			catch (Exception e) {
				_log.error(e, e);

				return null;
			}
		}

		if (_wikiGroupServiceOverriddenConfiguration.pageCommentsEnabled()) {
			return "edit_page_discussion";
		}
		else {
			return null;
		}
	}

	/**
	 * @deprecated As of 1.2.0, with no direct replacement
	 */
	@Deprecated
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
				_wikiEngineRenderer.convert(_page, null, null, null));
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

		if (_trashHelper == null) {
			return _page.getTitle();
		}

		return _trashHelper.getOriginalTitle(_page.getTitle());
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

		Group group = GroupLocalServiceUtil.fetchGroup(_page.getGroupId());

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, WikiPortletKeys.WIKI, 0, 0,
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

	public boolean hasDeletePermission(PermissionChecker permissionChecker)
		throws PortalException {

		return WikiPagePermission.contains(
			permissionChecker, _page, ActionKeys.DELETE);
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return WikiPagePermission.contains(
			permissionChecker, _page, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return WikiPagePermission.contains(
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

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageAssetRenderer.class);

	private final WikiPage _page;
	private final TrashHelper _trashHelper;
	private final WikiEngineRenderer _wikiEngineRenderer;
	private WikiGroupServiceOverriddenConfiguration
		_wikiGroupServiceOverriddenConfiguration;

}