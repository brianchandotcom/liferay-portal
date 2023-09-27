/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.lang.builder.ant;

import com.liferay.lang.builder.LangBuilderArgs;
import com.liferay.lang.builder.LangBuilderInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class BuildLangTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			LangBuilderInvoker.invoke(project.getBaseDir(), _langBuilderArgs);
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setExcludedLanguageIds(String[] excludedLanguageIds) {
		_langBuilderArgs.setExcludedLanguageIds(excludedLanguageIds);
	}

	public void setLangDirName(String langDirName) {
		_langBuilderArgs.setLangDirName(langDirName);
	}

	public void setLangFileName(String langFileName) {
		_langBuilderArgs.setLangFileName(langFileName);
	}

	public void setTitleCapitalization(boolean titleCapitalization) {
		_langBuilderArgs.setTitleCapitalization(titleCapitalization);
	}

	public void setTranslate(boolean translate) {
		_langBuilderArgs.setTranslate(translate);
	}

	public void setTranslateSubscriptionKey(String translateSubscriptionKey) {
		_langBuilderArgs.setTranslateSubscriptionKey(translateSubscriptionKey);
	}

	private final LangBuilderArgs _langBuilderArgs = new LangBuilderArgs();

}