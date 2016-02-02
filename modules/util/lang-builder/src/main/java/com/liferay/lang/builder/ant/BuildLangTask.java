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
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setLangDirName(String langDirName) {
		_langBuilderArgs.setLangDirName(langDirName);
	}

	public void setLangFileName(String langFileName) {
		_langBuilderArgs.setLangFileName(langFileName);
	}

	public void setPlugin(boolean plugin) {
		_langBuilderArgs.setPlugin(plugin);
	}

	public void setPortalLanguagePropertiesFileName(
		String portalLanguagePropertiesFileName) {

		_langBuilderArgs.setPortalLanguagePropertiesFileName(
			portalLanguagePropertiesFileName);
	}

	public void setTranslate(boolean translate) {
		_langBuilderArgs.setTranslate(translate);
	}

	public void setTranslateClientId(String translateClientId) {
		_langBuilderArgs.setTranslateClientId(translateClientId);
	}

	public void setTranslateClientSecret(String translateClientSecret) {
		_langBuilderArgs.setTranslateClientSecret(translateClientSecret);
	}

	private final LangBuilderArgs _langBuilderArgs = new LangBuilderArgs();

}