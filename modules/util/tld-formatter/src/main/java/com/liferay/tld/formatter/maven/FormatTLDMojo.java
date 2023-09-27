/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.tld.formatter.maven;

import com.liferay.tld.formatter.TLDFormatter;
import com.liferay.tld.formatter.TLDFormatterArgs;
import com.liferay.tld.formatter.TLDFormatterInvoker;

import java.io.File;

import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Invoke Liferay TLD Formatter to format files.
 *
 * @author Andrea Di Giorgi
 * @goal format
 */
public class FormatTLDMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			@SuppressWarnings("rawtypes")
			Map pluginContext = getPluginContext();

			TLDFormatter tldFormatter = TLDFormatterInvoker.invoke(
				baseDir, _tldFormatterArgs);

			pluginContext.put(
				TLDFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				tldFormatter.getModifiedFileNames());
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
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