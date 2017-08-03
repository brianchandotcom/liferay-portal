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

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public abstract class BaseDirectoryBuilderTestCase extends BaseVLDAPTestCase {

	protected void doTestBuildDirectories() throws Exception {
		doTestBuildDirectoriesWithDefaultFilterConstraints();
		doTestBuildDirectoriesWithInvalidFilterConstraints();
		doTestBuildDirectoriesWithNullFilterConstraints();
	}

	protected void doTestBuildDirectoriesWithDefaultFilterConstraints()
		throws Exception {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		filterConstraints.add(new FilterConstraint());

		List<Directory> directories = directoryBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		Assert.assertNotNull(directory);
	}

	protected void doTestBuildDirectoriesWithInvalidFilterConstraints()
		throws Exception {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = directoryBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	protected void doTestBuildDirectoriesWithNullFilterConstraints()
		throws Exception {

		List<Directory> directories = directoryBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

		Directory directory = directories.get(0);

		Assert.assertNotNull(directory);
	}

	protected void doTestValidAttributes(String name, String... values) {
		for (String value : values) {
			Assert.assertTrue(directoryBuilder.isValidAttribute(name, value));
		}
	}

	protected DirectoryBuilder directoryBuilder;

}