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

/**
 * @author     Raymond Augé
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class PortalFilePermission {

	public static void checkCopy(String source, String destination) {
		_pacl.checkCopy(source, destination);
	}

	public static void checkDelete(String path) {
		_pacl.checkDelete(path);
	}

	public static void checkMove(String source, String destination) {
		_pacl.checkMove(source, destination);
	}

	public static void checkRead(String path) {
		_pacl.checkRead(path);
	}

	public static void checkWrite(String path) {
		_pacl.checkWrite(path);
	}

	public interface PACL {

		public void checkCopy(String source, String destination);

		public void checkDelete(String path);

		public void checkMove(String source, String destination);

		public void checkRead(String path);

		public void checkWrite(String path);

	}

	private static final PACL _pacl = new NoPACL();

	private static class NoPACL implements PACL {

		@Override
		public void checkCopy(String source, String destination) {
		}

		@Override
		public void checkDelete(String path) {
		}

		@Override
		public void checkMove(String source, String destination) {
		}

		@Override
		public void checkRead(String path) {
		}

		@Override
		public void checkWrite(String path) {
		}

	}

}