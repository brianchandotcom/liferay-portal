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

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;

import javax.servlet.ServletContext;

/**
 * @author Peter Fellwock
 */
public class ContextRegisterPortalLifecycle implements PortalLifecycle {

	public ContextRegisterPortalLifecycle(ServletContext _servletContext) {
		this._servletContext = _servletContext;
	}

	@Override
	public void portalDestroy() {
		_servletContext = null;
	}

	@Override
	public void portalInit() {
		ModuleFrameworkUtilAdapter.registerContext(_servletContext);
	}

	private ServletContext _servletContext;

}