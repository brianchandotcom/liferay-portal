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

package com.liferay.portal.portlet.tracker.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Fellwock
 **/

public class PortletPropertyValidator {
	
	
	public static boolean findSingle(String str){
		
		for(String temp : startsWith){
			if(str.startsWith(temp)){
				return true;
			}
		}
		
		for(String temp : singleValue){
			if(str.equals(temp)){
				return true;
			}
		}
		
		return false;
	}
	
	public static String[] validatePropertiesKeys(String[] propertyKeys) {
		
		List<String> notFound = new ArrayList<String>();
		
		for(String str : propertyKeys){
			if(!findSingle(str)){
				notFound.add(str);
			}
		}
		
		return notFound.toArray(new String[notFound.size()]);
	}
	
	public static String [] startsWith = { 
		"component.",
		"javax.portlet.init-param",
		"service."
	};

	public static String [] singleValue = {
		"com.liferay.portlet.action-timeout",
		"com.liferay.portlet.active",
		"com.liferay.portlet.add-default-resource",
		"com.liferay.portlet.ajaxable",
		"com.liferay.portlet.autopropagated-parameters",
		"com.liferay.portlet.autopropagated-parameters",
		"com.liferay.portlet.configuration-path",
		"com.liferay.portlet.control-panel-entry-category",
		"com.liferay.portlet.control-panel-entry-weight",
		"com.liferay.portlet.css-class-wrapper",
		"com.liferay.portlet.display-category",
		"com.liferay.portlet.facebook-integration",
		"com.liferay.portlet.footer-portal-css",
		"com.liferay.portlet.footer-portal-javascript",
		"com.liferay.portlet.footer-portlet-css",
		"com.liferay.portlet.footer-portlet-javascript",
		"com.liferay.portlet.friendly-url-mapping",
		"com.liferay.portlet.friendly-url-routes",
		"com.liferay.portlet.header-portal-css",
		"com.liferay.portlet.header-portal-javascript",
		"com.liferay.portlet.header-portlet-css",
		"com.liferay.portlet.header-portlet-javascript",
		"com.liferay.portlet.icon",
		"com.liferay.portlet.instanceable",
		"com.liferay.portlet.layout-cacheable",
		"com.liferay.portlet.maximize-edit",
		"com.liferay.portlet.maximize-help",
		"com.liferay.portlet.parent-struts-path",
		"com.liferay.portlet.pop-up-print",
		"com.liferay.portlet.preferences-company-wide",
		"com.liferay.portlet.preferences-owned-by-group",
		"com.liferay.portlet.preferences-unique-per-layout",
		"com.liferay.portlet.private-request-attributes",
		"com.liferay.portlet.private-session-attributes",
		"com.liferay.portlet.remoteable",
		"com.liferay.portlet.render-timeout",
		"com.liferay.portlet.render-weight",
		"com.liferay.portlet.requires-namespaced-parameters",
		"com.liferay.portlet.restore-current-view",
		"com.liferay.portlet.scopeable",
		"com.liferay.portlet.show-portlet-access-denied",
		"com.liferay.portlet.show-portlet-inactive",
		"com.liferay.portlet.show-portlet-inactive",
		"com.liferay.portlet.single-page-application",
		"com.liferay.portlet.struts-path",
		"com.liferay.portlet.system",
		"com.liferay.portlet.use-default-template",
		"com.liferay.portlet.user-principal-strategy",
		"com.liferay.portlet.virtual-path",
		"javax.portlet.description",
		"javax.portlet.display-name",
		"javax.portlet.expiration-cache",
		"javax.portlet.info.keywords",
		"javax.portlet.info.short-title",
		"javax.portlet.info.title",
		"javax.portlet.portlet-mode",
		"javax.portlet.portlet-name",
		"javax.portlet.preferences",
		"javax.portlet.resource-bundle",
		"javax.portlet.security-role-ref",
		"javax.portlet.supported-processing-event",
		"javax.portlet.supported-public-render-parameter",
		"javax.portlet.supported-publishing-event",
		"javax.portlet.window-state",
		"objectClass"
	};

}
