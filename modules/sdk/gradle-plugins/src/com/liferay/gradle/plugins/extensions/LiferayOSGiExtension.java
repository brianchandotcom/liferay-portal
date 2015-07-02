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

package com.liferay.gradle.plugins.extensions;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.compile.CompileOptions;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiExtension extends LiferayExtension {

	public LiferayOSGiExtension(Project project) {
		super(project);
	}

	public Map<String, String> getBundleDefaultInstructions() {
		Map<String, String> map = new HashMap<>();

		map.put(Constants.BUNDLE_SYMBOLICNAME, project.getName());
		map.put(Constants.BUNDLE_VENDOR, "Liferay, Inc.");

		map.put(
			"Git-Descriptor",
			"${system-allow-fail;git describe --dirty --always}");
		map.put("Git-SHA", "${system-allow-fail;git rev-list -1 HEAD}");

		JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
			project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

		CompileOptions compileOptions = javaCompile.getOptions();

		map.put("Javac-Debug", _getOnOffValue(compileOptions.isDebug()));
		map.put(
			"Javac-Deprecation",
			_getOnOffValue(compileOptions.isDeprecation()));

		String encoding = compileOptions.getEncoding();

		if (Validator.isNull(encoding)) {
			encoding = System.getProperty("file.encoding");
		}

		map.put("Javac-Encoding", encoding);

		map.put(Constants.DONOTCOPY, "(.touch)");
		map.put(Constants.DSANNOTATIONS, "*");
		map.put(Constants.SOURCES, "false");

		return map;
	}

	public boolean isAutoUpdateXml() {
		return _autoUpdateXml;
	}

	public void setAutoUpdateXml(boolean autoUpdateXml) {
		_autoUpdateXml = autoUpdateXml;
	}

	private String _getOnOffValue(boolean b) {
		if (b) {
			return "on";
		}

		return "off";
	}

	private boolean _autoUpdateXml = true;

}