/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kevin Lee
 */
public class SourceProcessorTestParameters {

	public static SourceProcessorTestParameters create(String fileName) {
		return new SourceProcessorTestParameters(fileName);
	}

	public SourceProcessorTestParameters addDependentFileName(
		String dependentFileName) {

		_dependentFileNames.add(dependentFileName);

		return this;
	}

	public SourceProcessorTestParameters addExpectedMessage(String message) {
		return addExpectedMessage(message, -1);
	}

	public SourceProcessorTestParameters addExpectedMessage(
		String message, int lineNumber) {

		_expectedMessages.add(message);
		_lineNumbers.add(lineNumber);

		return this;
	}

	public SourceProcessorTestParameters addExpectedMessages(
		String[] messages) {

		for (String message : messages) {
			addExpectedMessage(message, -1);
		}

		return this;
	}

	public Set<String> getDependentFileNames() {
		return _dependentFileNames;
	}

	public String getExpectedFileName() {
		return _expectedFileName;
	}

	public List<String> getExpectedMessages() {
		return _expectedMessages;
	}

	public String getFileName() {
		return _fileName;
	}

	public List<Integer> getLineNumbers() {
		return _lineNumbers;
	}

	public SourceProcessorTestParameters setExpectedFileName(
		String expectedFileName) {

		_expectedFileName = expectedFileName;

		return this;
	}

	private SourceProcessorTestParameters(String fileName) {
		_fileName = fileName;
	}

	private final Set<String> _dependentFileNames = new HashSet<>();
	private String _expectedFileName;
	private final List<String> _expectedMessages = new ArrayList<>();
	private final String _fileName;
	private final List<Integer> _lineNumbers = new ArrayList<>();

}