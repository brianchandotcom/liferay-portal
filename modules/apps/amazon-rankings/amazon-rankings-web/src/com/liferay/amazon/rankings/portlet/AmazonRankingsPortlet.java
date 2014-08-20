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

package com.liferay.amazon.rankings.portlet;

import com.liferay.amazon.rankings.upgrade.AmazonRankingsUpgrade;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
* @author Raymond Augé
* @author Peter Fellwock
*/
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-amazon-rankings",
		"com.liferay.portlet.display-category=category.shopping",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=0",
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.struts-path=amazon_rankings",
		"javax.portlet.display-name=Amazon Rankings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html",
	},
	service = Portlet.class
)
public class AmazonRankingsPortlet extends MVCPortlet {

	/**
	* Force upgrades to register before the portlet is registered to prevent
	* race conditions.
	*/
	@Reference(unbind = "-")
	protected void setAmazonRankingsUpgrade(
		AmazonRankingsUpgrade amazonRankingsUpgrade) {
	}

}