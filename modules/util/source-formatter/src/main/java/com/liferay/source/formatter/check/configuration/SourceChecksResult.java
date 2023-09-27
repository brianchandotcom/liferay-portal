/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.configuration;

import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.check.SourceCheck;

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