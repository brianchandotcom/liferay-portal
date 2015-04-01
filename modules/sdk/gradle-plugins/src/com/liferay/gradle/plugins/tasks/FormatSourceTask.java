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

package com.liferay.gradle.plugins.tasks;

import java.util.Collections;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class FormatSourceTask extends BasePortalToolTask {

	public FormatSourceTask() {
		setMaxHeapSize("512m");
	}

	@Override
	public void exec() {
		super.exec();

		project.delete("ServiceBuilder.temp");
	}

	@Override
	public List<String> getArgs() {
		return Collections.emptyList();
	}

	@Override
	public String getMain() {
		return _MAIN;
	}

	@Override
	protected void addDependencies() {
		super.addDependencies();

		addDependency("com.thoughtworks.qdox", "qdox", "1.12.1");
		addDependency("dom4j", "dom4j", "1.6.1");
		addDependency("org.apache.ant", "ant", "1.8.2");
		addDependency("xerces", "xercesImpl", "2.11.0");
	}

	@Override
	protected String getToolName() {
		return _TOOL_NAME;
	}

	private static final String _MAIN =
		"com.liferay.portal.tools.sourceformatter.SourceFormatter";

	private static final String _TOOL_NAME = "SourceFormatter";

}