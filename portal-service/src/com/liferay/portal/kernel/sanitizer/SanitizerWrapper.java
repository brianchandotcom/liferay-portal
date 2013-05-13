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

package com.liferay.portal.kernel.sanitizer;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class SanitizerWrapper implements Sanitizer {

	public SanitizerWrapper(Sanitizer sanitizer) {
		_originalSanitizer = sanitizer;
		_sanitizer = sanitizer;
	}

	@Override
	public byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		return _sanitizer.sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			bytes, options);
	}

	@Override
	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {

		_sanitizer.sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			inputStream, outputStream, options);
	}

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		return _sanitizer.sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			s, options);
	}

	public void setSanitizer(Sanitizer sanitizer) {
		if (sanitizer == null) {
			_sanitizer = _originalSanitizer;
		}
		else {
			_sanitizer = sanitizer;
		}
	}

	private Sanitizer _originalSanitizer;
	private Sanitizer _sanitizer;

}