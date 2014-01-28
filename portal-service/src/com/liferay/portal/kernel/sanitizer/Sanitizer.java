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
 * Provides constants and methods for sanitizing offensive vocabulary or
 * malicious HTML content. This interface is implemented by dynamically
 * configurable sanitizer components that can be deployed as a hook, customizing
 * the portal property <code>sanitizer.impl</code>. All installed sanitizers are
 * chained.
 *
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public interface Sanitizer {

	public static final String MODE_ALL = "ALL";

	public static final String MODE_BAD_WORDS = "BAD_WORDS";

	public static final String MODE_XSS = "XSS";

	/**
	 * Returns the sanitized content as a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the site's group
	 * @param  userId the user who changed the content
	 * @param  className the class name of the content model implementation
	 * @param  classPK the primary key of the content to sanitize,
	 *         <code>0</code> if not available
	 * @param  contentType the content type. For more information, see {@link
	 *         com.liferay.portal.kernel.util.ContentTypes}.
	 * @param  modes the sanitizer modes
	 * @param  bytes the content to be sanitized
	 * @param  options the options map
	 * @return the sanitized content
	 * @throws SanitizerException if a sanitizer exception occurred
	 */
	public byte[] sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, byte[] bytes,
			Map<String, Object> options)
		throws SanitizerException;

	/**
	 * Sanitizes the input stream content and streams the result as the output
	 * stream.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the site's group
	 * @param  userId the user who changed the content
	 * @param  className the class name of the content model implementation
	 * @param  classPK the primary key of the content to sanitize,
	 *         <code>0</code> if not available
	 * @param  contentType the content type. For more information, see {@link
	 *         com.liferay.portal.kernel.util.ContentTypes}.
	 * @param  modes the sanitizer modes
	 * @param  inputStream the content to be sanitized
	 * @param  outputStream the result of the sanitizing process
	 * @param  options the options map
	 * @throws SanitizerException if a sanitizer exception occurred
	 */
	public void sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes,
			InputStream inputStream, OutputStream outputStream,
			Map<String, Object> options)
		throws SanitizerException;

	/**
	 * Returns the sanitized content as a string.
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the site's group
	 * @param  userId the user who changed the content
	 * @param  className the class name of the content model implementation
	 * @param  classPK the primary key of the content to sanitize,
	 *         <code>0</code> if not available
	 * @param  contentType the content type. For more information, see {@link
	 *         com.liferay.portal.kernel.util.ContentTypes}.
	 * @param  modes the sanitizer modes
	 * @param  s the content to sanitize
	 * @param  options the options map
	 * @return the sanitized content
	 * @throws SanitizerException if a sanitizer exception occurred
	 */
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String s,
			Map<String, Object> options)
		throws SanitizerException;

}