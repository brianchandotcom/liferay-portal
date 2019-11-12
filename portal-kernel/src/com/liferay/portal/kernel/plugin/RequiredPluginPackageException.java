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

package com.liferay.portal.kernel.plugin;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class RequiredPluginPackageException extends PortalException {

	public RequiredPluginPackageException() {
	}

	public RequiredPluginPackageException(String msg) {
		super(msg);
	}

	public RequiredPluginPackageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RequiredPluginPackageException(Throwable cause) {
		super(cause);
	}

	public String getContext() {
		return _context;
	}

	public void setContext(String context) {
		_context = context;
	}

	private String _context;

}