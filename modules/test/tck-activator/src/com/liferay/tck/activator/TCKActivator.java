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

package com.liferay.tck.activator;

import com.liferay.portal.struts.StrutsActionRegistryUtil;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component
public class TCKActivator {

	@Activate
	protected void activate() {
		StrutsActionRegistryUtil.register(_TCK_PATH, _tckStrutsAction);
	}

	@Deactivate
	protected void deactivate() {
		StrutsActionRegistryUtil.unregister(_TCK_PATH);
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private static final String _TCK_PATH = "/portal/tck";

	private static final TCKStrutsAction _tckStrutsAction =
		new TCKStrutsAction();

}