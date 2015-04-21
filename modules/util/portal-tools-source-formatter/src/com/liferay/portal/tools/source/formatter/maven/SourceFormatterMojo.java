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

package com.liferay.portal.tools.source.formatter.maven;

import com.liferay.portal.tools.source.formatter.SourceFormatter;
import com.liferay.portal.tools.source.formatter.SourceFormatterBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;


/**
 * @author Raymond Augé
 */
public class SourceFormatterMojo extends AbstractMojo {

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			SourceFormatter sourceFormatter = new SourceFormatter(
				_sourceFormatterBean);

			sourceFormatter.format();

			List<String> processedFiles = sourceFormatter.getProcessedFiles();

			Map pluginContext = getPluginContext();

			pluginContext.put(
				SourceFormatter.PROCESSED_FILES_ATTRIBUTE, processedFiles);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	public void setAutoFix(boolean autoFix) {
		_sourceFormatterBean.setAutoFix(autoFix);
	}

	public void setBaseDir(String baseDir) {
		_sourceFormatterBean.setBaseDir(baseDir);
	}

	public void setCopyright(String copyright) {
		_sourceFormatterBean.setCopyright(copyright);
	}

	public void setFileNames(String[] fileNames) {
		_sourceFormatterBean.setFileNames(Arrays.asList(fileNames));
	}

	public void setPrintErrors(boolean printErrors) {
		_sourceFormatterBean.setPrintErrors(printErrors);
	}

	public void setThrowException(boolean throwException) {
		_sourceFormatterBean.setThrowException(throwException);
	}

	public void setUseProperties(boolean useProperties) {
		_sourceFormatterBean.setUseProperties(useProperties);
	}

	private final SourceFormatterBean _sourceFormatterBean =
		new SourceFormatterBean();

}