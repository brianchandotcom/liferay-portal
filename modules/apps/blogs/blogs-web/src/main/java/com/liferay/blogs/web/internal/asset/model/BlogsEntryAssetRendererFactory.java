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

package com.liferay.blogs.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + BlogsPortletKeys.BLOGS,
	service = AssetRendererFactory.class
)
public class BlogsEntryAssetRendererFactory
	extends BaseAssetRendererFactory<BlogsEntry> {

	public static final String TYPE = "blog";

	public BlogsEntryAssetRendererFactory() {
		setClassName(BlogsEntry.class.getName());
		setLinkable(true);
		setPortletId(BlogsPortletKeys.BLOGS);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<BlogsEntry> getAssetRenderer(long classPK, int type)
		throws PortalException {

		BlogsEntryAssetRenderer blogsEntryAssetRenderer =
			new BlogsEntryAssetRenderer(
				_blogsEntryLocalService.getEntry(classPK),
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						"com.liferay.blogs.web"));

		blogsEntryAssetRenderer.setAssetDisplayPageFriendlyURLProvider(
			_assetDisplayPageFriendlyURLProvider);
		blogsEntryAssetRenderer.setAssetRendererType(type);
		blogsEntryAssetRenderer.setServletContext(_servletContext);

		return blogsEntryAssetRenderer;
	}

	@Override
	public AssetRenderer<BlogsEntry> getAssetRenderer(
			long groupId, String urlTitle)
		throws PortalException {

		BlogsEntry entry = _blogsEntryLocalService.getEntry(groupId, urlTitle);

		BlogsEntryAssetRenderer blogsEntryAssetRenderer =
			new BlogsEntryAssetRenderer(
				entry,
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						"com.liferay.blogs.web"));

		blogsEntryAssetRenderer.setServletContext(_servletContext);

		return blogsEntryAssetRenderer;
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "blogs";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classTypeId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			liferayPortletRequest, getGroup(liferayPortletRequest),
			BlogsPortletKeys.BLOGS, 0, 0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				BlogsPortletKeys.BLOGS, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return _portletResourcePermission.contains(
			permissionChecker, groupId, ActionKeys.ADD_ENTRY);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _blogsEntryModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private ModelResourcePermission<BlogsEntry>
		_blogsEntryModelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.blogs.web)")
	private ServletContext _servletContext;

}