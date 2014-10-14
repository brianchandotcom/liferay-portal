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

package com.liferay.portal.sanitizer;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

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
 * @author Peter Fellwock
 */
public class SanitizerImpl implements Sanitizer {

	public SanitizerImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			Sanitizer.class, new SanitizerServiceTrackerCustomizer());

		_serviceTracker.open();
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
			Sanitizer sanitizer = _sanitizers.get(0);

			sanitizer.sanitize(
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

	private final List<Sanitizer> _sanitizers =
		new CopyOnWriteArrayList<Sanitizer>();
	private final ServiceTracker<?, Sanitizer> _serviceTracker;

	private class SanitizerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Sanitizer, Sanitizer> {

		@Override
		public Sanitizer addingService(
			ServiceReference<Sanitizer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Sanitizer sanitizer = registry.getService(serviceReference);

			_sanitizers.add(sanitizer);

			return sanitizer;
		}

		@Override
		public void modifiedService(
			ServiceReference<Sanitizer> serviceReference, Sanitizer sanitizer) {
		}

		@Override
		public void removedService(
			ServiceReference<Sanitizer> serviceReference, Sanitizer sanitizer) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_sanitizers.remove(sanitizer);
		}

	}

}