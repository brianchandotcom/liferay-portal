/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.lang;

import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;

/**
 * @author Raymond Augé
 */
public class DoPrivilegedUtil {

	public static <T> T wrap(PrivilegedAction<T> privilegedAction) {
		return _pacl.wrap(privilegedAction);
	}

	public static <T> T wrap(
			PrivilegedExceptionAction<T> privilegedExceptionAction)
		throws Exception {

		return _pacl.wrap(privilegedExceptionAction);
	}

	public static <T> T wrap(T t) {
		return _pacl.wrap(t);
	}

	public static <T> T wrap(T t, boolean checkActive) {
		return _pacl.wrap(t, checkActive);
	}

	/**
	 * This pattern allows the portal to provide an implementation which has
	 * lowest possible cost when PACL is not enabled and still provide an
	 * injection point for inserting a PACL aware implementation when it is
	 * enabled. This will also allow complete decoupling of local code from the
	 * PACL implementation.
	 */

	/**
	 * This private static variable provides the injection point for inserting
	 * a PACL aware implementation.
	 */
	private static PACL _pacl = new NoPACL();

	/**
	 * This interface defines the local operations where PACL is required.
	 */
	public static interface PACL {

		public <T> T wrap(PrivilegedAction<T> privilegedAction);

		public <T> T wrap(
				PrivilegedExceptionAction<T> privilegedExceptionAction)
			throws Exception;

		public <T> T wrap(T t);

		public <T> T wrap(T t, boolean checkActive);

	}

	/**
	 * This implementation is the minimal cost operation required for the class
	 * to operate properly. The PACL aware implementation will implement the
	 * interface be injected in place of the NoPACL implementation.
	 */
	private static class NoPACL implements PACL {

		public <T> T wrap(PrivilegedAction<T> privilegedAction) {
			return privilegedAction.run();
		}

		public <T> T wrap(
				PrivilegedExceptionAction<T> privilegedExceptionAction)
			throws Exception {

			return privilegedExceptionAction.run();
		}

		public <T> T wrap(T t) {
			return t;
		}

		public <T> T wrap(T t, boolean checkActive) {
			return t;
		}

	}

}