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

package com.liferay.asset.publisher.web.portlet.action;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portlet.asset.service.AssetTagLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.RECENT_CONTENT
	},
	service = ConfigurationAction.class
)
public class RecentContentConfigurationAction
	extends AssetPublisherConfigurationAction {

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.publisher.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		this.assetTagLocalService = assetTagLocalService;
	}

	@Override
	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	@Override
	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	@Override
	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

}