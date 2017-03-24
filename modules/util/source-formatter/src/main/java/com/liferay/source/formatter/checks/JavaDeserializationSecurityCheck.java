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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaDeserializationSecurityCheck extends BaseFileCheck {

	public JavaDeserializationSecurityCheck(
		List<String> secureDeserializationExcludes,
		List<String> runOutsidePortalExcludes) {

		_secureDeserializationExcludes = secureDeserializationExcludes;
		_runOutsidePortalExcludes = runOutsidePortalExcludes;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.contains("/test/") ||
			fileName.contains("/testIntegration/") ||
			isExcludedPath(_secureDeserializationExcludes, absolutePath)) {

			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkDeserializationSecurity(
			sourceFormatterMessages, fileName, content, absolutePath);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkDeserializationSecurity(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content, String absolutePath) {

		for (Pattern vulnerabilityPattern :
				_javaSerializationVulnerabilityPatterns) {

			Matcher matcher = vulnerabilityPattern.matcher(content);

			if (!matcher.matches()) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

			if (isExcludedPath(_runOutsidePortalExcludes, absolutePath)) {
				sb.append("Possible Java Serialization Remote Code Execution ");
				sb.append("vulnerability using ");
			}
			else {
				sb.append("Use ProtectedObjectInputStream instead of ");
			}

			sb.append(matcher.group(1));

			addMessage(sourceFormatterMessages, fileName, sb.toString());
		}
	}

	private final Pattern[] _javaSerializationVulnerabilityPatterns =
		new Pattern[] {
			Pattern.compile(
				".*(new [a-z\\.\\s]*ObjectInputStream).*", Pattern.DOTALL),
			Pattern.compile(
				".*(extends [a-z\\.\\s]*ObjectInputStream).*", Pattern.DOTALL)
		};
	private final List<String> _runOutsidePortalExcludes;
	private final List<String> _secureDeserializationExcludes;

}