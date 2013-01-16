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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Douglas Wong
 */
public class SanitizerProcessorImpl implements SanitizerProcessor {

	public byte[] process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		byte[] output = bytes;

		for (String sanitizerClassName : sanitizerClassNames) {
			if (Validator.isNull(sanitizerClassName)) {
				return output;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Process sanitizer " + sanitizerClassName);
			}

			Sanitizer sanitizer = (Sanitizer)InstancePool.get(
				sanitizerClassName);

			output = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, output, options);
		}

		if (Validator.isNull(key)) {
			return output;
		}

		List<Sanitizer> sanitizers = _getSanitizers(key);

		for (Sanitizer sanitizer : sanitizers) {
			output = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, output, options);
		}

		return output;
	}

	public void process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, InputStream inputStream,
			OutputStream outputStream, Map<String, Object> options)
		throws SanitizerException {

		for (String sanitizerClassName : sanitizerClassNames) {
			if (Validator.isNull(sanitizerClassName)) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Process sanitizer " + sanitizerClassName);
			}

			Sanitizer sanitizer = (Sanitizer)InstancePool.get(
				sanitizerClassName);

			sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, inputStream, outputStream, options);
		}

		if (Validator.isNull(key)) {
			return;
		}

		List<Sanitizer> sanitizers = _getSanitizers(key);

		for (Sanitizer sanitizer : sanitizers) {
			sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, inputStream, outputStream, options);
		}
	}

	public String process(
			String key, String[] sanitizerClassNames, long companyId,
			long groupId, long userId, String className, long classPK,
			String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		String output = s;

		for (String sanitizerClassName : sanitizerClassNames) {
			if (Validator.isNull(sanitizerClassName)) {
				return output;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Process sanitizer " + sanitizerClassName);
			}

			Sanitizer sanitizer = (Sanitizer)InstancePool.get(
				sanitizerClassName);

			output = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, output, options);
		}

		if (Validator.isNull(key)) {
			return output;
		}

		List<Sanitizer> sanitizers = _getSanitizers(key);

		for (Sanitizer sanitizer : sanitizers) {
			output = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, output, options);
		}

		return output;
	}

	public void registerSanitizer(String key, Sanitizer sanitizer) {
		List<Sanitizer> sanitizers = _getSanitizers(key);

		sanitizers.add(sanitizer);
	}

	public void unregisterSanitizer(String key, Sanitizer sanitizer) {
		List<Sanitizer> sanitizers = _getSanitizers(key);

		sanitizers.remove(sanitizer);
	}

	private List<Sanitizer> _getSanitizers(String key) {
		List<Sanitizer> sanitizers = _sanitizerMap.get(key);

		if (sanitizers == null) {
			sanitizers = new ArrayList<Sanitizer>();

			_sanitizerMap.put(key, sanitizers);
		}

		return sanitizers;
	}

	private static Log _log = LogFactoryUtil.getLog(
		SanitizerProcessorImpl.class);

	private Map<String, List<Sanitizer>> _sanitizerMap =
		new HashMap<String, List<Sanitizer>>();

}