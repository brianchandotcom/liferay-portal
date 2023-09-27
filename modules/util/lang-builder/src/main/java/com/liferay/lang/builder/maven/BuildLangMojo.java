/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @goal build
 */
public class BuildLangMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			LangBuilderInvoker.invoke(baseDir, _langBuilderArgs);
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
		}
	}

	/**
	 * @parameter
	 */
	public void setExcludedLanguageIds(String[] excludedLanguageIds) {
		_langBuilderArgs.setExcludedLanguageIds(excludedLanguageIds);
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
	public void setTitleCapitalization(boolean titleCapitalization) {
		_langBuilderArgs.setTitleCapitalization(titleCapitalization);
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
	public void setTranslateSubscriptionKey(String translateSubscriptionKey) {
		_langBuilderArgs.setTranslateSubscriptionKey(translateSubscriptionKey);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final LangBuilderArgs _langBuilderArgs = new LangBuilderArgs();

}