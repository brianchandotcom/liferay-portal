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

package com.liferay.portal.sanitizer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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

			try {
				Sanitizer sanitizer = (Sanitizer)InstanceFactory.newInstance(
					className);

				registerSanitizer(sanitizer);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void registerSanitizer(Sanitizer sanitizer) {
		_sanitizers.add(sanitizer);
	}

	@Override
	public byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException {

		for (Sanitizer sanitizer : _sanitizers) {
			bytes = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, bytes, options);
		}

		return bytes;
	}

	@Override
	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException {

		if (_sanitizers.isEmpty()) {
			return;
		}

		if (_sanitizers.size() == 1) {
			sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, inputStream, outputStream, options);

			return;
		}

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try {
			StreamUtil.transfer(inputStream, byteArrayOutputStream);
		}
		catch (IOException ioe) {
			throw new SanitizerException(ioe);
		}

		byte[] bytes = sanitize(
			companyId, groupId, userId, className, classPK, contentType, modes,
			byteArrayOutputStream.toByteArray(), options);

		try {
			outputStream.write(bytes);
		}
		catch (IOException ioe) {
			throw new SanitizerException(ioe);
		}
	}

	@Override
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException {

		for (Sanitizer sanitizer : _sanitizers) {
			s = sanitizer.sanitize(
				companyId, groupId, userId, className, classPK, contentType,
				modes, s, options);
		}

		return s;
	}

	public void unregisterSanitizer(Sanitizer sanitizer) {
		_sanitizers.remove(sanitizer);
	}

	private static Log _log = LogFactoryUtil.getLog(SanitizerImpl.class);

	private List<Sanitizer> _sanitizers = new CopyOnWriteArrayList<Sanitizer>();

}