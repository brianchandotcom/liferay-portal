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

package com.liferay.gradle.plugins.internal.extensions;

import com.liferay.gradle.plugins.extensions.AppServer;
import com.liferay.gradle.plugins.extensions.JOnASAppServer;
import com.liferay.gradle.plugins.extensions.TomcatAppServer;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class AppServerFactory implements NamedDomainObjectFactory<AppServer> {

	public AppServerFactory(Project project) {
		_project = project;
	}

	@Override
	public AppServer create(String name) {
		if (name.equals("jonas")) {
			return new JOnASAppServer(_project);
		}
		else if (name.equals("tomcat")) {
			return new TomcatAppServer(_project);
		}

		return new AppServer(name, _project);
	}

	private final Project _project;

}