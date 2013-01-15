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

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;

/**
 * @author Douglas Wong
 */
public class SanitizerProcessorUtil {

	public static byte[] process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		return _instance.process(
			key, sanitizerClassNames, companyId, groupId, userId, className,
			classPK, contentType, modes, bytes, options);
	}

	public static void process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, InputStream inputStream,
			OutputStream outputStream, Map<String, Object> options)
		throws SanitizerException {

		_instance.process(
			key, sanitizerClassNames, companyId, groupId, userId, className,
			classPK, contentType, modes, inputStream, outputStream, options);
	}

	public static String process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		return _instance.process(
			key, sanitizerClassNames, companyId, groupId, userId, className,
			classPK, contentType, modes, s, options);
	}

	public static void registerSanitizer(String key, Sanitizer sanitizer) {
		_instance.registerSanitizer(key, sanitizer);
	}

	public static void setSanitizerProcessor(
		SanitizerProcessor sanitizerProcessor) {

		_instance = sanitizerProcessor;
	}

	public static void unregisterSanitizer(String key, Sanitizer sanitizer) {
		_instance.unregisterSanitizer(key, sanitizer);
	}

	private static SanitizerProcessor _instance = new SanitizerProcessorImpl();

}