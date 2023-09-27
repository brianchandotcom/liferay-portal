/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.sanitizer;

import java.util.Map;

/**
 * Provides an interface and constants for sanitizer component implementations.
 * Commonly, sanitizers are implemented for stripping offensive vocabulary from
 * content or for removing malicious HTML content, such as cross-site scripting
 * (CSS). Multiple implementations can be deployed in a hook plugin and
 * specified in a comma separated list of values for the
 * <code>sanitizer.impl</code> portal property (see <a
 * href="http://docs.liferay.com/portal/7.0/propertiesdoc/portal.properties.html#Sanitizer">Sanitizer</a>).
 * All installed sanitizers are chained.
 *
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public interface Sanitizer {

	public static final String MODE_ALL = "ALL";

	public static final String MODE_BAD_WORDS = "BAD_WORDS";

	public static final String MODE_XSS = "XSS";

	/**
	 * Returns the sanitized content as a string.
	 *
	 * @param  companyId the primary key of the portal instance
	 * @param  groupId the primary key of the site's group
	 * @param  userId the user who changed the content
	 * @param  className the class name of the content model implementation
	 * @param  classPK the primary key of the content to sanitize,
	 *         <code>0</code> if not available
	 * @param  contentType the content type. For more information, see {@link
	 *         com.liferay.portal.kernel.util.ContentTypes}.
	 * @param  modes ways in which to run the sanitizer, such as {@link
	 *         #MODE_ALL}, {@link #MODE_BAD_WORDS}, and/or {@link #MODE_XSS}
	 * @param  content the content to sanitize
	 * @param  options the options map
	 * @return the sanitized content
	 * @throws SanitizerException if a sanitizer exception occurred
	 */
	public String sanitize(
			long companyId, long groupId, long userId, String className,
			long classPK, String contentType, String[] modes, String content,
			Map<String, Object> options)
		throws SanitizerException;

}