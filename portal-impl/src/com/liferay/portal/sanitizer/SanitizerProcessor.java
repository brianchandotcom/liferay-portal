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
public interface SanitizerProcessor {

	public byte[] process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException;

	public void process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, InputStream inputStream,
			OutputStream outputStream, Map<String, Object> options)
		throws SanitizerException;

	public String process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException;

	public void registerSanitizer(String key, Sanitizer sanitizer);

	public void unregisterSanitizer(String key, Sanitizer sanitizer);

}