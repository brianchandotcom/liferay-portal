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

package com.liferay.portal.kernel.security.pacl.permission;

import java.security.BasicPermission;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class PortalMessageBusPermission extends BasicPermission {

	public static void checkListen(String destinationName) {
		_pacl.checkListen(destinationName);
	}

	public static void checkSend(String destinationName) {
		_pacl.checkSend(destinationName);
	}

	public PortalMessageBusPermission(String name, String destinationName) {
		super(name);

		_destinationName = destinationName;
	}

	@Override
	public String getActions() {
		return _destinationName;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public interface PACL {

		public void checkListen(String destinationName);

		public void checkSend(String destinationName);

	}

	private static final PACL _pacl = new NoPACL();

	private final String _destinationName;

	private static class NoPACL implements PACL {

		@Override
		public void checkListen(String destinationName) {
		}

		@Override
		public void checkSend(String destinationName) {
		}

	}

}