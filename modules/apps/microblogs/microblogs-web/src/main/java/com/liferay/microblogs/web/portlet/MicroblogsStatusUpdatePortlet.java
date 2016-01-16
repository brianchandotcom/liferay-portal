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

package com.liferay.microblogs.web.portlet;

import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.microblogs.service.MicroblogsEntryService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=microblogs-portlet",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.footer-portlet-javascript=/microblogs/js/main.js",
		"com.liferay.portlet.header-portlet-css=/microblogs/css/main.css",
		"com.liferay.portlet.icon=/microblogs/icons/microblogs.png",
		"javax.portlet.display-name=Microblogs Status Update",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-jsp=/status_update/configuration.jsp",
		"javax.portlet.init-param.view-template=/status_update/view.jsp",
		"javax.portlet.name=" + MicroblogsPortletKeys.MICROBLOGS_STATUS_UPDATE,
		"javax.portlet.portlet-info.keyworkds=Microblogs Status Update",
		"javax.portlet.portlet-info.short-title=Microblogs Status Update",
		"javax.portlet.portlet-info.title=Microblogs Status Update",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MicroblogsStatusUpdatePortlet extends MicroblogsPortlet {

	@Override
	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		this.assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	@Reference(unbind = "-")
	protected void setMicroblogsEntryLocalService(
		MicroblogsEntryLocalService microblogsEntryLocalService) {

		this.microblogsEntryLocalService = microblogsEntryLocalService;
	}

	@Override
	@Reference(unbind = "-")
	protected void setMicroblogsEntryService(
		MicroblogsEntryService microblogsEntryService) {

		this.microblogsEntryService = microblogsEntryService;
	}

}