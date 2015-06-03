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

package com.liferay.portal.sanitizer.bundle.sanitizerimpl;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.util.StackTraceUtil;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestSanitizer implements Sanitizer {

	@Override
	public byte[] sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes, byte[] bytes,
		Map<String, Object> options) {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return "bytes".getBytes();
	}

	@Override
	public void sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes,
		InputStream inputStream, OutputStream outputStream,
		Map<String, Object> options) {

		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Override
	public String sanitize(
		long companyId, long groupId, long userId, String className,
		long classPK, String contentType, String[] modes, String s,
		Map<String, Object> options) {

		return companyId + ":" + groupId;
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}