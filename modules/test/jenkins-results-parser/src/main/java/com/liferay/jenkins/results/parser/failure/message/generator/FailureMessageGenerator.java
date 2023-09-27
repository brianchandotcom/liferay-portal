/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public interface FailureMessageGenerator {

	public Element getMessageElement(Build build);

	public Element getMessageElement(String consoleText);

	public boolean isGenericCIFailure();

}