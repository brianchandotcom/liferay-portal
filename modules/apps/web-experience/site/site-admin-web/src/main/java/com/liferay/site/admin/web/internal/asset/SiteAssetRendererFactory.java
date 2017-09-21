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

package com.liferay.site.admin.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ricardo Couso
 */
@Component(immediate = true, service = AssetRendererFactory.class)
public class SiteAssetRendererFactory extends BaseAssetRendererFactory<Group> {

	public static final String TYPE = "site";

	public SiteAssetRendererFactory() {
		setSearchable(false);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<Group> getAssetRenderer(long classPK, int type)
		throws PortalException {

		Group group = _groupLocalService.getGroup(classPK);

		SiteAssetRenderer siteAssetRenderer = new SiteAssetRenderer(group);

		if (siteAssetRenderer != null) {
			siteAssetRenderer.setAssetRendererType(type);

			return siteAssetRenderer;
		}
		else {
			return null;
		}
	}

	@Override
	public AssetRenderer<Group> getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		return new SiteAssetRenderer(group);
	}

	@Override
	public String getClassName() {
		return Group.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "site";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private GroupLocalService _groupLocalService;

}