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

package com.liferay.asset.tags.compiler.web.portlet;

import com.liferay.asset.tags.compiler.web.upgrade.AssetTagsCompilerWebUpgrade;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.ajaxable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=1",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"com.liferay.portlet.show-portlet-inactive=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Tags Compiler",
		"javax.portlet.info.short-title=Tags Compiler",
		"javax.portlet.info.title=Tags Compiler",
		"javax.portlet.supported-public-render-parameter=tags",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AssetTagsCompilerPortlet extends MVCPortlet {

	@Reference(unbind = "-")
	protected void setAssetTagsCompilerWebUpgrade(
		AssetTagsCompilerWebUpgrade assetTagsCompilerWebUpgrade) {
	}

}