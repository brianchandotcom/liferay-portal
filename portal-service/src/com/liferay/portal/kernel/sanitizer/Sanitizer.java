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

import com.liferay.portal.kernel.util.ContentTypes;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;

/**
 * Content can be stripped of offensive vocabulary or malicious HTML content,
 * e.g. XSS. Sanitizers are dynamically configurable components that implement
 * this interface. New implementations can be deployed as a hook, customizing the
 * portal.property entry {@code sanitizer.impl=(name of implementing class)}.
 * All installed Sanitizers are chained
 * 
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public interface Sanitizer {

	public static final String MODE_ALL = "ALL";

	public static final String MODE_BAD_WORDS = "BAD_WORDS";

	public static final String MODE_XSS = "XSS";

	/**
	 * Sanitize the content given as bytearray, return the result as
	 * bytearray as well. This method may on the parameter bytearray,
	 * don't rely on it to be unchanged.
	 *  
	 * @param companyId   the instance in which the given content is contained
	 * @param groupId     the site in which the given content is contained
	 * @param userId      the user who has changed the given content
	 * @param className   classname of the given content model implementation
	 * @param classPK     primary key of the content to sanitize, 0 if not available
	 * @param contentType {@link ContentTypes}
	 * @param modes       any of this class's constants MODE_ALL, MODE_BAD_WORDS, MODE_XSS
	 * @param bytes       the content to be sanitized. The sanitizer may change this array
	 * @param options
	 * @return            the sanitized content
	 * @throws SanitizerException
	 */
	public byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException;

	/**
	 * Sanitize the given InputStream, stream the result into the 
	 * given OutputStream

	 * @param companyId   the instance in which the given content is contained
	 * @param groupId     the site in which the given content is contained
	 * @param userId      the user who has changed the given content
	 * @param className   classname of the given content model implementation
	 * @param classPK     primary key of the content to sanitize, 0 if not available
	 * @param contentType {@link ContentTypes}
	 * @param modes       any of this class's constants MODE_ALL, MODE_BAD_WORDS, MODE_XSS
	 * @param inputStream the content to be sanitized
	 * @param outputStream the result of the sanitization process
	 * @param options
	 * @throws SanitizerException
	 */
	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException;

	/**
	 * Sanitize the given String, return the sanitized String.
	 * 
	 * @param companyId   the instance in which the given content is contained
	 * @param groupId     the site in which the given content is contained
	 * @param userId      the user who has changed the given content
	 * @param className   classname of the given content model implementation
	 * @param classPK     primary key of the content to sanitize, 0 if not available
	 * @param contentType {@link ContentTypes}
	 * @param modes       any of this class's constants MODE_ALL, MODE_BAD_WORDS, MODE_XSS
	 * @param s           the content to sanitize
	 * @param options
	 * @return            the sanitized content
	 * @throws SanitizerException
	 */
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException;

}