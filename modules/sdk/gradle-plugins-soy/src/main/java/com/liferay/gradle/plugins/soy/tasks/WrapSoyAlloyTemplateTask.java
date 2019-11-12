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

package com.liferay.gradle.plugins.soy.tasks;

import com.liferay.gradle.plugins.soy.internal.util.GradleUtil;
import com.liferay.portal.tools.soy.builder.commands.WrapSoyAlloyTemplateCommand;

import java.io.File;
import java.io.IOException;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
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
		_wrapSoyAlloyTemplateCommand.setModuleName(getModuleName());
		_wrapSoyAlloyTemplateCommand.setNamespace(getNamespace());

		for (File file : getSource()) {
			_wrapSoyAlloyTemplateCommand.execute(file.toPath());
		}
	}

	private Object _moduleName;
	private Object _namespace;
	private final WrapSoyAlloyTemplateCommand _wrapSoyAlloyTemplateCommand =
		new WrapSoyAlloyTemplateCommand();

}