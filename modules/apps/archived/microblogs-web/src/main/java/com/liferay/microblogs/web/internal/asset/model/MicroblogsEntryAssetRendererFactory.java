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

package com.liferay.microblogs.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + MicroblogsPortletKeys.MICROBLOGS,
	service = AssetRendererFactory.class
)
public class MicroblogsEntryAssetRendererFactory
	extends BaseAssetRendererFactory<MicroblogsEntry> {

	public static final String CLASS_NAME = MicroblogsEntry.class.getName();

	public static final String TYPE = "microblogs";

	public MicroblogsEntryAssetRendererFactory() {
		setClassName(CLASS_NAME);
		setPortletId(MicroblogsPortletKeys.MICROBLOGS);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<MicroblogsEntry> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		MicroblogsEntryAssetRenderer microblogsEntryAssetRenderer =
			new MicroblogsEntryAssetRenderer(
				_microblogsEntryLocalService.getMicroblogsEntry(classPK),
				_microblogsEntryModelResourcePermission);

		microblogsEntryAssetRenderer.setServletContext(_servletContext);

		return microblogsEntryAssetRenderer;
	}

	@Override
	public String getIconCssClass() {
		return "comments";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _microblogsEntryModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.microblogs.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setMicroblogsEntryLocalService(
		MicroblogsEntryLocalService microblogsEntryLocalService) {

		_microblogsEntryLocalService = microblogsEntryLocalService;
	}

	private MicroblogsEntryLocalService _microblogsEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.microblogs.model.MicroblogsEntry)"
	)
	private ModelResourcePermission<MicroblogsEntry>
		_microblogsEntryModelResourcePermission;

	private ServletContext _servletContext;

}