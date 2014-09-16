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
package com.liferay.hello.velocity.web.portlet;

import com.liferay.hello.velocity.web.upgrade.HelloVelocityUpgrade;
import com.liferay.util.bridges.mvc.MVCPortlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;

/**
* @author Peter Fellwock
*/
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-hello-velocity",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true", "com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.struts-path=hello_velocity",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Hello Velocity", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.vm",
		"javax.portlet.portlet.info.keywords=Hello Velocity",
		"javax.portlet.portlet.info.short-title=Hello Velocity",
		"javax.portlet.portlet.info.title=Hello Velocity",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class HelloVelocityPortlet extends MVCPortlet {

	@Reference(unbind = "-")
	protected void setHelloVelocityUpgrade(
		HelloVelocityUpgrade helloVelocityUpgrade) {
	}
	
}