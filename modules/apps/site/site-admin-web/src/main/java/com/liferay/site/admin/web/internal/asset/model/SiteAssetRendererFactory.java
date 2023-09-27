/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.asset.model;

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
@Component(service = AssetRendererFactory.class)
public class SiteAssetRendererFactory extends BaseAssetRendererFactory<Group> {

	public static final String TYPE = "site";

	public SiteAssetRendererFactory() {
		setSearchable(false);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<Group> getAssetRenderer(long classPK, int type)
		throws PortalException {

		SiteAssetRenderer siteAssetRenderer = new SiteAssetRenderer(
			_groupLocalService.getGroup(classPK));

		if (siteAssetRenderer != null) {
			siteAssetRenderer.setAssetRendererType(type);

			return siteAssetRenderer;
		}

		return null;
	}

	@Override
	public AssetRenderer<Group> getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		return new SiteAssetRenderer(_groupLocalService.getGroup(groupId));
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

	@Reference
	private GroupLocalService _groupLocalService;

}