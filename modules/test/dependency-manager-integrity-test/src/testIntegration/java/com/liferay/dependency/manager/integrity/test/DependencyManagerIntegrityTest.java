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

package com.liferay.dependency.manager.integrity.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.ComponentDeclaration;
import org.apache.felix.dm.ComponentDependencyDeclaration;
import org.apache.felix.dm.DependencyManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class DependencyManagerIntegrityTest {

	@Test
	public void testDependencyManagerIntegrity() throws IOException {
		List<ComponentDependencyDeclaration>
			missingComponentDependencyDeclarations = new ArrayList<>();

		for (DependencyManager dependencyManager :
				DependencyManager.getDependencyManagers()) {

			for (Component component : dependencyManager.getComponents()) {
				ComponentDeclaration componentDeclaration =
					(ComponentDeclaration)component;

				if (componentDeclaration.getState() !=
						ComponentDeclaration.STATE_UNREGISTERED) {

					continue;
				}

				for (ComponentDependencyDeclaration
						componentDependencyDeclaration :
							componentDeclaration.getComponentDependencies()) {

					if (componentDependencyDeclaration.getState() ==
							ComponentDependencyDeclaration.
								STATE_UNAVAILABLE_REQUIRED) {

						missingComponentDependencyDeclarations.add(
							componentDependencyDeclaration);
					}
				}
			}
		}

		Assert.assertTrue(
			"Missing dependencies " + missingComponentDependencyDeclarations,
			missingComponentDependencyDeclarations.isEmpty());
	}

}