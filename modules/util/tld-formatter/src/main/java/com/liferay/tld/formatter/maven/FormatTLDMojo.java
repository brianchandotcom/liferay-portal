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

package com.liferay.tld.formatter.maven;

import com.liferay.tld.formatter.TLDFormatter;
import com.liferay.tld.formatter.TLDFormatterArgs;
import com.liferay.tld.formatter.TLDFormatterInvoker;

import java.io.File;

import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Invoke Liferay TLD Formatter to format files.
 *
 * @author Andrea Di Giorgi
 * @goal format-tld
 */
public class FormatTLDMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			@SuppressWarnings("rawtypes")
			Map pluginContext = getPluginContext();

			TLDFormatter tldFormatter = TLDFormatterInvoker.invoke(
				baseDir, _tldFormatterArgs);

			Set<String> modifiedFileNames = tldFormatter.getModifiedFileNames();

			pluginContext.put(
				TLDFormatterArgs.OUTPUT_KEY_MODIFIED_FILES, modifiedFileNames);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setBaseDirName(String baseDirName) {
		_tldFormatterArgs.setBaseDirName(baseDirName);
	}

	/**
	 * @parameter
	 */
	public void setPlugin(boolean plugin) {
		_tldFormatterArgs.setPlugin(plugin);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final TLDFormatterArgs _tldFormatterArgs = new TLDFormatterArgs();

}