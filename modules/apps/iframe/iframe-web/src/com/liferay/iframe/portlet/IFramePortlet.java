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

package com.liferay.iframe.portlet;

import com.liferay.iframe.upgrade.IFrameUpgrade;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.bridges.mvc.MVCPortlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Peter Fellwock
*/

@Component(
immediate = true,
property = {
		
	"com.liferay.portlet.configuration-action-class=com.liferay.portlet.iframe.action.ConfigurationActionImpl",
	"com.liferay.portlet.css-class-wrapper=portlet-iframe",
	"com.liferay.portlet.display-category=category.sample", 
	"com.liferay.portlet.header-portlet-css=/css/main.css",
	"com.liferay.portlet.icon=/default.png",
	"com.liferay.portlet.instanceable=true",
	"com.liferay.portlet.preferences-owned-by-group=true",
	"com.liferay.portlet.private-request-attributes=false",
	"com.liferay.portlet.private-session-attributes=false",
	"com.liferay.portlet.render-weight=50",
	"com.liferay.portlet.struts-path=iframe",
	"com.liferay.portlet.use-default-template=false",
	"javax.portlet.portlet.display-name=IFrame",
	"javax.portlet.portlet.expiration-cache=0",
	"javax.portlet.init-param.config-template=/configuration.jsp",
	"javax.portlet.init-param.template-path=/",
	"javax.portlet.init-param.view-template=/view.jsp",
	"javax.portlet.portlet.portlet-class=com.liferay.portlet.iframe.IFramePortlet",
	"javax.portlet.portlet.resource-bundle=com.liferay.portlet.StrutsResourceBundle",
	"javax.portlet.security-role-ref=power-user,user",
	"javax.portlet.supports.mime-type=text/html"
	
},
service = Portlet.class
)

public class IFramePortlet extends MVCPortlet {
	/**
	* Force upgrades to register before the portlet is registered to prevent
	* race conditions.
	*/
	@Reference(unbind = "-")
	protected void setIFrameUpgrade(
			IFrameUpgrade iFrameUpgrade) {
	}

	public static final String DEFAULT_EDIT_ACTION = "/iframe/edit";

	public static final String DEFAULT_VIEW_ACTION = "/iframe/view";

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		if (Validator.isNull(editAction)) {
			editAction = DEFAULT_EDIT_ACTION;
		}

		if (Validator.isNull(viewAction)) {
			viewAction = DEFAULT_VIEW_ACTION;
		}
	}

}

/**


<!-- IFrame -->

		<action path="/iframe/proxy" forward="/portlet/iframe/proxy.jsp" />

		<action path="/iframe/view" type="com.liferay.portlet.iframe.action.ViewAction">
			<forward name="portlet.iframe.view" path="portlet.iframe.view" />
		</action>
		
		
		
			<!-- IFrame -->

	<definition name="portlet.iframe" extends="portlet" />

	<definition name="portlet.iframe.view" extends="portlet.iframe">
		<put name="portlet_content" value="/portlet/iframe/view.jsp" />
	</definition>














**/