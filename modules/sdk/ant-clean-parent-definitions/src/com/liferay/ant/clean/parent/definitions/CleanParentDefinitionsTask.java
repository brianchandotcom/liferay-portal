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

package com.liferay.ant.clean.parent.definitions;

import java.util.Hashtable;

import org.apache.tools.ant.AntTypeDefinition;
import org.apache.tools.ant.ComponentHelper;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Tina Tian
 */
public class CleanParentDefinitionsTask extends Task {

	@Override
	public void execute() {
		Project currentProject = getProject();

		if (!Boolean.valueOf(
				currentProject.getProperty("clean.parent.definitions"))) {

			return;
		}

		ComponentHelper componentHelper = ComponentHelper.getComponentHelper(
			currentProject);

		Hashtable<String, AntTypeDefinition> antTypeTable =
			componentHelper.getAntTypeTable();

		antTypeTable.clear();

		componentHelper.initDefaultDefinitions();
	}

}