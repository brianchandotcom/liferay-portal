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
package com.liferay.activities.portlet;

import com.liferay.activities.upgrade.ActivitiesUpgrade;
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
		"com.liferay.portlet.configuration-action-class=com.liferay.portal.kernel.portlet.DefaultConfigurationAction",
		"com.liferay.portlet.css-class-wrapper=portlet-activities",
		"com.liferay.portlet.display-category=category.social",		
		"com.liferay.portlet.friendly-url-mapper-class=com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper",
		"com.liferay.portlet.friendly-url-mapping=activities",
		"com.liferay.portlet.friendly-url-routes=com/liferay/portlet/activities/activities-friendly-url-routes.xml",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Activities",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=com.liferay.portlet.StrutsResourceBundle",
		"javax.portlet.security-role-ref=guest,power-user,user", 
		"javax.portlet.supports.mime-type=text/html",
	}, 
	service = Portlet.class
)
public class ActivitiesPortlet extends MVCPortlet {
	
	/**
	 * Force upgrades to register before the portlet is registered to prevent
	 * race conditions.
	 */
	@Reference(unbind = "-")
	protected void setActivitiesUpgrade(ActivitiesUpgrade activitiesUpgrade) {
	}
}