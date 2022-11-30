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

package com.liferay.lang.sanitizer.util;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

/**
 * @author Seiphon Wang
 */
public class AntiSamyUtil {

	public static String sanitize(Policy policy, String content)
		throws PolicyException, ScanException {

		if (policy == null) {
			policy = Policy.getInstance();
		}

		AntiSamy antiSamy = new AntiSamy();

		CleanResults cleanResults = antiSamy.scan(content, policy);

		return cleanResults.getCleanHTML();
	}

}