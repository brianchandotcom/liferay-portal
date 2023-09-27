/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.prose;

import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Yi-Chen Tsai
 */
public abstract class BasePoshiProse {

	public String filterCommentLines(String content) {
		return content.replaceAll(commentLinePattern.pattern(), "");
	}

	public abstract Element toElement();

	protected final Pattern commentLinePattern = Pattern.compile("\\s*#.*");
	protected final Pattern tagPattern = Pattern.compile(
		"\\@\\s*(?<tagName>.*?)\\s*\\=\\s*\"(?<tagValue>.*)\"");

}