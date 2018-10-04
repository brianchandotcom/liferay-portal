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

package com.liferay.source.formatter.checks.configuration;

import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.SourceCheck;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class SourceChecksResult {

	public SourceChecksResult(String content) {
		_content = content;
	}

	public void addSourceFormatterMessage(
		SourceFormatterMessage sourceFormatterMessage) {

		_sourceFormatterMessages.add(sourceFormatterMessage);
	}

	public String getContent() {
		return _content;
	}

	public SourceCheck getMostRecentProcessedSourceCheck() {
		return _mostRecentProcessedSourceCheck;
	}

	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		return _sourceFormatterMessages;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setMostRecentProcessedSourceCheck(
		SourceCheck mostRecentProcessedSourceCheck) {

		_mostRecentProcessedSourceCheck = mostRecentProcessedSourceCheck;
	}

	private String _content;
	private SourceCheck _mostRecentProcessedSourceCheck;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new HashSet<>();

}