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

package com.liferay.portal.kernel.sanitizer;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class SanitizerUtil {

	public static Sanitizer getSanitizer() {
		PortalRuntimePermission.checkGetBeanProperty(SanitizerUtil.class);

		return _sanitizer;
	}

	/**
	 * @deprecated As of 7.0.0 please use {@link
	 *             #sanitize(long, long, long, String, long, String, String)}
	 */
	@Deprecated
	public static byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, byte[] bytes)
		throws SanitizerException {

		return sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			Sanitizer.MODE_ALL, bytes, null);
	}

	/**
	 * @deprecated As of 7.0.0 please use {@link
	 *             #sanitize(long, long, long, String, long, String, String)}
	 */
	@Deprecated
	public static void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, InputStream inputStream,
			OutputStream outputStream)
		throws SanitizerException {

		sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			Sanitizer.MODE_ALL, inputStream, outputStream, null);
	}

	public static String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String s)
		throws SanitizerException {

		return sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			Sanitizer.MODE_ALL, s, null);
	}

	/**
	 * @deprecated As of 7.0.0 please use {@link
	 *             #sanitize(long, long, long, String, long, String, String, String, Map)}
	 */
	@Deprecated
	public static byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String mode, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		return sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			new String[] {mode}, bytes, options);
	}

	/**
	 * @deprecated As of 7.0.0 please use {@link
	 *             #sanitize(long, long, long, String, long, String, String, String, Map)}
	 */
	@Deprecated
	public static void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String mode,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {

		sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			new String[] {mode}, inputStream, outputStream, options);
	}

	public static String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String mode, String s,
			Map<String, Object> options)
		throws SanitizerException {

		return sanitize(
			companyId, groupId, userId, className, classPK, contentType,
			new String[] {mode}, s, options);
	}

	/**
	 * @deprecated As of 7.0.0 please use {@link
	 *             #sanitize(long, long, long, String, long, String, String[], String, Map)}
	 */
	@Deprecated
	public static byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		return getSanitizer().sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			bytes, options);
	}

	/**
	 * @deprecated As of 7.0.0 please use {@link
	 *             #sanitize(long, long, long, String, long, String, String[], String, Map)}
	 */
	@Deprecated
	public static void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {

		getSanitizer().sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			inputStream, outputStream, options);
	}

	public static String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		return getSanitizer().sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			s, options);
	}

	public void setSanitizer(Sanitizer sanitizer) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_sanitizer = sanitizer;
	}

	private static Sanitizer _sanitizer;

}