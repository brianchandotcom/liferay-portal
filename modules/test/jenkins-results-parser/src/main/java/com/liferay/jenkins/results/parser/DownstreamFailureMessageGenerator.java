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

package com.liferay.jenkins.results.parser;

import java.util.Hashtable;

import org.dom4j.Element;

/**
 * @author Kevin Yen
 */
public class DownstreamFailureMessageGenerator
	extends BaseFailureMessageGenerator {

	@Override
	public Element getMessage(Build build) {
		String consoleOutput = build.getConsoleText();

		if (consoleOutput.contains("Downstream jobs FAILED.")) {
			return Dom4JUtil.toCodeSnippetElement("Downstream jobs FAILED.");
		}

		return null;
	}

	@Override
	public String getMessage(
		String buildURL, String consoleOutput, Hashtable<?, ?> properties) {

		if (consoleOutput.contains("Downstream jobs FAILED.")) {
			return "<pre><code>Downstream jobs FAILED</code></pre>";
		}

		return null;
	}

}