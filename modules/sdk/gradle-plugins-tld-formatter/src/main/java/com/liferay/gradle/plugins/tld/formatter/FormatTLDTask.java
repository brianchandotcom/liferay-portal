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

package com.liferay.gradle.plugins.tld.formatter;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.JavaExec;

/**
 * @author Andrea Di Giorgi
 */
public class FormatTLDTask extends JavaExec {

	public FormatTLDTask() {
		setMain("com.liferay.tld.formatter.TLDFormatter");
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@Input
	public boolean isPlugin() {
		return _plugin;
	}

	public void setPlugin(boolean plugin) {
		_plugin = plugin;
	}

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("tld.plugin=" + isPlugin());

		return args;
	}

	private boolean _plugin = true;

}