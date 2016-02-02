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

package com.liferay.lang.builder.maven;

import com.liferay.lang.builder.LangBuilderArgs;
import com.liferay.lang.builder.LangBuilderInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Builds language property files.
 *
 * @author Andrea Di Giorgi
 * @goal build-lang
 */
public class BuildLangMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			LangBuilderInvoker.invoke(baseDir, _langBuilderArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setLangDirName(String langDirName) {
		_langBuilderArgs.setLangDirName(langDirName);
	}

	/**
	 * @parameter
	 */
	public void setLangFileName(String langFileName) {
		_langBuilderArgs.setLangFileName(langFileName);
	}

	/**
	 * @parameter
	 */
	public void setPlugin(boolean plugin) {
		_langBuilderArgs.setPlugin(plugin);
	}

	/**
	 * @parameter
	 */
	public void setPortalLanguagePropertiesFileName(
		String portalLanguagePropertiesFileName) {

		_langBuilderArgs.setPortalLanguagePropertiesFileName(
			portalLanguagePropertiesFileName);
	}

	/**
	 * @parameter
	 */
	public void setTranslate(boolean translate) {
		_langBuilderArgs.setTranslate(translate);
	}

	/**
	 * @parameter
	 */
	public void setTranslateClientId(String translateClientId) {
		_langBuilderArgs.setTranslateClientId(translateClientId);
	}

	/**
	 * @parameter
	 */
	public void setTranslateClientSecret(String translateClientSecret) {
		_langBuilderArgs.setTranslateClientSecret(translateClientSecret);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final LangBuilderArgs _langBuilderArgs = new LangBuilderArgs();

}