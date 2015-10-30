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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrew Betts
 */
public class XugglerInstallException extends PortalException {

	public static class MuseBeURLClassLoader extends XugglerInstallException {

		public MuseBeURLClassLoader() {
			super(
				"Unable to install JAR because the portal class loader is " +
					"not an instance of URLClassLoader");
		}

	}

	public static class MustInstallJar extends XugglerInstallException {

		public MustInstallJar(String name, Throwable cause) {
			super("Unable to install jar " + name, cause);
		}

	}

	private XugglerInstallException(String message) {
		super(message);
	}

	private XugglerInstallException(String message, Throwable cause) {
		super(message, cause);
	}

}