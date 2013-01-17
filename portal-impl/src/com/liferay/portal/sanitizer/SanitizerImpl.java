/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sanitizer;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class SanitizerImpl implements Sanitizer {

	public SanitizerImpl() {
		for (String className : PropsValues.SANITIZER_IMPL) {
			if (Validator.isNull(className)) {
				continue;
			}

			Sanitizer sanitizer = (Sanitizer)InstancePool.get(className);

			registerSanitizer(sanitizer);
		}
	}

	public void registerSanitizer(Sanitizer sanitizer) {
		_sanitizers.add(sanitizer);
	}

	public byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		byte[] output = bytes;

		for (Sanitizer sanitizer : _sanitizers) {
			output = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, output, options);
		}

		return output;
	}

	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {

		for (Sanitizer sanitizer : _sanitizers) {
			sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, inputStream, outputStream, options);
		}
	}

	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		String output = s;

		for (Sanitizer sanitizer : _sanitizers) {
			output = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, output, options);
		}

		return output;
	}

	public void unregisterSanitizer(Sanitizer sanitizer) {
		_sanitizers.remove(sanitizer);
	}

	private List<Sanitizer> _sanitizers = new ArrayList<Sanitizer>();

}