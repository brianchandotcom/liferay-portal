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

package com.liferay.portal.kernel.portlet.bundle.resourcebundletracker;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TestPortlet.PORTLET_NAME,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class TestPortlet extends GenericPortlet {

	public static final String PORTLET_NAME = "TEST_PORTLET_NAME";

}