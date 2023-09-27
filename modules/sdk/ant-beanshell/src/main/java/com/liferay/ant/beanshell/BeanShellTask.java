/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.beanshell;

import bsh.EvalError;
import bsh.Interpreter;

import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

/**
 * @author Peter Yoo
 */
public class BeanShellTask extends Task {

	public void addText(String text) {
		_text = text;
	}

	@Override
	public void execute() throws BuildException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		if (_classpathRef != null) {
			Path path = project.getReference(_classpathRef);

			if (path == null) {
				throw new BuildException(
					"Invalid BeanShell class path reference: " + _classpathRef);
			}

			classLoader = new AntClassLoader(getProject(), path);
		}

		Interpreter interpreter = new Interpreter();

		interpreter.setClassLoader(classLoader);

		try {
			if (_mapId != null) {
				interpreter.set("beanShellMap", _getMap());
			}

			interpreter.set("project", getProject());

			interpreter.eval(_text);
		}
		catch (EvalError ee) {
			throw new BuildException(ee);
		}
	}

	public void setClasspathRef(String classpathRef) {
		_classpathRef = classpathRef;
	}

	public void setMapId(String mapId) {
		_mapId = mapId;
	}

	private Map<String, Object> _getMap() {
		Map<String, Object> map = _maps.get(_mapId);

		if (map == null) {
			map = new HashMap<String, Object>();

			_maps.put(_mapId, map);
		}

		return map;
	}

	private static final Map<String, Map<String, Object>> _maps =
		new HashMap<String, Map<String, Object>>();

	private String _classpathRef;
	private String _mapId;
	private String _text;

}