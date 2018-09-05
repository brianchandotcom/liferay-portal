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

package com.liferay.blogs.web.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.security.permission.resource.BlogsEntryPermission;
import com.liferay.blogs.web.internal.util.BlogsEntryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Sergio González
 * @author Zsolt Berentey
 */
public class BlogsEntryAssetRenderer
	extends BaseJSPAssetRenderer<BlogsEntry> implements TrashRenderer {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #BlogsEntryAssetRenderer(BlogsEntry, ResourceBundleLoader)}
	 */
	@Deprecated
	public BlogsEntryAssetRenderer(BlogsEntry entry) {
		this(
			entry,
			new AggregateResourceBundleLoader(
				new ClassResourceBundleLoader(
					"content.Language", BlogsEntryAssetRenderer.class)));
	}

	public BlogsEntryAssetRenderer(
		BlogsEntry entry, ResourceBundleLoader resourceBundleLoader) {

		_entry = entry;
		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	public BlogsEntry getAssetObject() {
		return _entry;
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _entry.getEntryId();
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			return "edit_entry_discussion";
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Date getDisplayDate() {
		return _entry.getDisplayDate();
	}

	@Override
	public long getGroupId() {
		return _entry.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/blogs/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<BlogsEntry> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public int getStatus() {
		return _entry.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		int abstractLength = AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH;

		if (portletRequest != null) {
			abstractLength = GetterUtil.getInteger(
				portletRequest.getAttribute(
					WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH),
				AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);
		}

		String summary = HtmlUtil.escape(_entry.getDescription());

		if (Validator.isNull(summary)) {
			summary = HtmlUtil.stripHtml(
				StringUtil.shorten(_entry.getContent(), abstractLength));
		}

		return summary;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return BlogsEntryUtil.getDisplayTitle(resourceBundle, _entry);
	}

	@Override
	public String getType() {
		return BlogsEntryAssetRendererFactory.TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_entry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, BlogsPortletKeys.BLOGS, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");
		portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));

		return portletURL;
	}

	@Override
	public String getUrlTitle() {
		return _entry.getUrlTitle();
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<BlogsEntry> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry");
		portletURL.setParameter("entryId", String.valueOf(_entry.getEntryId()));
		portletURL.setWindowState(windowState);

		return portletURL.toString();
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect, "/blogs/find_entry",
			"entryId", _entry.getEntryId());
	}

	@Override
	public long getUserId() {
		return _entry.getUserId();
	}

	@Override
	public String getUserName() {
		return _entry.getUserName();
	}

	@Override
	public String getUuid() {
		return _entry.getUuid();
	}

	public boolean hasDeletePermission(PermissionChecker permissionChecker)
		throws PortalException {

		return BlogsEntryPermission.contains(
			permissionChecker, _entry, ActionKeys.DELETE);
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return BlogsEntryPermission.contains(
			permissionChecker, _entry, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return BlogsEntryPermission.contains(
			permissionChecker, _entry, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.BLOGS_ENTRY, _entry);

		return super.include(request, response, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final BlogsEntry _entry;
	private final ResourceBundleLoader _resourceBundleLoader;

}