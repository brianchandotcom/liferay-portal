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

package com.liferay.ant.beanshell;

import bsh.EvalError;
import bsh.Interpreter;

import java.util.Map;
import java.util.HashMap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Peter Yoo
 */
public class BeanShellTask extends Task {

	public void addText(String text) {
		_text = text;
	}

	public void setMapid(String mapId) {
		_mapId = mapId;
	}

	@Override
	public void execute() throws BuildException {
		Interpreter interpreter = new Interpreter();

		Class<?> clazz = getClass();

		interpreter.setClassLoader(clazz.getClassLoader());

		try {
			if (_mapId != null) {
				interpreter.set("map", getMap());
			}
			interpreter.set("project", getProject());

			interpreter.eval(_text);
		}
		catch (EvalError ee) {
			throw new BuildException(ee);
		}
	}

	private Map<String, Object>getMap() {
		Map<String, Object> map = _MAPS.get(_mapId);

		if (map == null) {
			map = new HashMap<String, Object>();
			_MAPS.put(_mapId, map);
		}

		return map;
	}

	private static final Map<String, Map<String, Object>> _MAPS = new HashMap<String, Map<String, Object>>();

	private String _mapId;
	private String _text;

}