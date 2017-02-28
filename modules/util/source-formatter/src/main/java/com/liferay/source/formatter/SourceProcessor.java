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

package com.liferay.source.formatter;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public interface SourceProcessor {

	public void format() throws Exception;

	public SourceMismatchException getFirstSourceMismatchException();

	public String[] getIncludes();

	public List<String> getModifiedFileNames();

	public Set<SourceFormatterMessage> getSourceFormatterMessages();

	public void processMessage(String fileName, String message);

	public void processMessage(String fileName, String message, int lineCount);

	public void processMessage(
		String fileName, String message, String markdownFileName);

	public void processMessage(
		String fileName, String message, String markdownFileName,
		int lineCount);

	public void setProperties(Properties properties);

	public void setSourceFormatterArgs(SourceFormatterArgs sourceFormatterArgs);

}