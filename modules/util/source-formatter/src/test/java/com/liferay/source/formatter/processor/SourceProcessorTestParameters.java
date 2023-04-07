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