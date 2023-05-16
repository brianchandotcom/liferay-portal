/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.soy.task;

import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.portal.tools.soy.builder.commands.WrapSoyAlloyTemplateCommand;

import java.io.File;
import java.io.IOException;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@CacheableTask
@Deprecated
public class WrapSoyAlloyTemplateTask extends SourceTask {

	@Input
	public String getModuleName() {
		return GradleUtil.toString(_moduleName);
	}

	@Input
	public String getNamespace() {
		return GradleUtil.toString(_namespace);
	}

	public void setModuleName(Object moduleName) {
		_moduleName = moduleName;
	}

	public void setNamespace(Object namespace) {
		_namespace = namespace;
	}

	@TaskAction
	public void wrapAlloyTemplate() throws IOException {
		WrapSoyAlloyTemplateCommand wrapSoyAlloyTemplateCommand =
			new WrapSoyAlloyTemplateCommand();

		wrapSoyAlloyTemplateCommand.setModuleName(getModuleName());
		wrapSoyAlloyTemplateCommand.setNamespace(getNamespace());

		for (File file : getSource()) {
			wrapSoyAlloyTemplateCommand.execute(file.toPath());
		}
	}

	private Object _moduleName;
	private Object _namespace;

}