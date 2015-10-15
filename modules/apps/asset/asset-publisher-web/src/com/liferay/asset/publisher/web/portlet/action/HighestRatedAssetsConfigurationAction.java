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
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS
	},
	service = ConfigurationAction.class
)
public class HighestRatedAssetsConfigurationAction
	extends AssetPublisherConfigurationAction {

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		this.assetTagLocalService = assetTagLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

}