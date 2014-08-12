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

	public static <T> T wrapWhenActive(T t) {
		return _pacl.wrapWhenActive(t);
	}

	public interface PACL {

		public <T> T wrap(PrivilegedAction<T> privilegedAction);

		public <T> T wrap(
				PrivilegedExceptionAction<T> privilegedExceptionAction)
			throws Exception;

		public <T> T wrap(T t);

		public <T> T wrapWhenActive(T t);

	}

	private static final PACL _pacl = new NoPACL();

	private static class NoPACL implements PACL {

		@Override
		public <T> T wrap(PrivilegedAction<T> privilegedAction) {
			return privilegedAction.run();
		}

		@Override
		public <T> T wrap(
				PrivilegedExceptionAction<T> privilegedExceptionAction)
			throws Exception {

			return privilegedExceptionAction.run();
		}

		@Override
		public <T> T wrap(T t) {
			return t;
		}

		@Override
		public <T> T wrapWhenActive(T t) {
			return t;
		}

	}

}