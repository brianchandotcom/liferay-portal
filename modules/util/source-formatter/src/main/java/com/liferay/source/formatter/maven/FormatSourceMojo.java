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

package com.liferay.source.formatter.maven;

import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Formats Liferay sources.
 *
 * @author Raymond Augé
 * @goal format-source
 */
public class FormatSourceMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			@SuppressWarnings("rawtypes")
			Map pluginContext = getPluginContext();

			SourceFormatter sourceFormatter = new SourceFormatter(
				_sourceFormatterArgs);

			sourceFormatter.format();

			List<String> modifiedFileNames =
				sourceFormatter.getModifiedFileNames();

			pluginContext.put(
				SourceFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				modifiedFileNames);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setAutoFix(boolean autoFix) {
		_sourceFormatterArgs.setAutoFix(autoFix);
	}

	/**
	 * @parameter
	 */
	public void setBaseDir(String baseDir) {
		_sourceFormatterArgs.setBaseDirName(baseDir);
	}

	/**
	 * @parameter
	 */
	public void setCopyright(String copyright) {
		_sourceFormatterArgs.setCopyrightFileName(copyright);
	}

	/**
	 * @parameter
	 */
	public void setFileNames(String[] fileNames) {
		_sourceFormatterArgs.setFileNames(Arrays.asList(fileNames));
	}

	/**
	 * @parameter
	 */
	public void setFormatCurrentBranch(boolean formatCurrentBranch) {
		_sourceFormatterArgs.setFormatCurrentBranch(formatCurrentBranch);
	}

	/**
	 * @parameter
	 */
	public void setFormatLatestAuthor(boolean formatLatestAuthor) {
		_sourceFormatterArgs.setFormatLatestAuthor(formatLatestAuthor);
	}

	/**
	 * @parameter
	 */
	public void setFormatLocalChanges(boolean formatLocalChanges) {
		_sourceFormatterArgs.setFormatLocalChanges(formatLocalChanges);
	}

	/**
	 * @parameter
	 */
	public void setPrintErrors(boolean printErrors) {
		_sourceFormatterArgs.setPrintErrors(printErrors);
	}

	/**
	 * @parameter
	 */
	public void setThrowException(boolean throwException) {
		_sourceFormatterArgs.setThrowException(throwException);
	}

	/**
	 * @parameter
	 */
	public void setUseProperties(boolean useProperties) {
		_sourceFormatterArgs.setUseProperties(useProperties);
	}

	private final SourceFormatterArgs _sourceFormatterArgs =
		new SourceFormatterArgs();

}