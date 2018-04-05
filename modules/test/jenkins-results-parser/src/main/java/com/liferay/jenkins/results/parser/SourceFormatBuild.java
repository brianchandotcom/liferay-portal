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

import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.SourceFormatFailureMessageGenerator;

import org.dom4j.Element;

/**
 * @author Cesar Polanco
 */
public class SourceFormatBuild extends TopLevelBuild {

	@Override
	public Element[] getBuildFailureElements() {
		Element failureMessageElement = getFailureMessageElement();

		if (failureMessageElement != null) {
			return {failureMessageElement};
		}
	}

	protected SourceFormatBuild(String url) {
		this(url, null);
	}

	protected SourceFormatBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return new FailureMessageGenerator[] {
			new SourceFormatFailureMessageGenerator(),
			new GenericFailureMessageGenerator()
		};
	}

}