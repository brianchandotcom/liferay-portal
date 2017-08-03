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
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 * @author Matthew Tambara
 */
@RunWith(PowerMockRunner.class)
public class CompanyBuilderTest extends BaseVLDAPTestCase {

	@Test
	public void testBuildDirectoriesNoWebId() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("ou", null);

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _companyBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesNullFilterConstraints() throws Exception {
		List<Directory> directories = _companyBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesValidWebId() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("ou", "liferay.com");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _companyBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithInvalidFilterConstraints()
		throws Exception {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _companyBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_companyBuilder.isValidAttribute("ou", "test"));
		Assert.assertTrue(
			_companyBuilder.isValidAttribute(
				"objectclass", "organizationalUnit"));
		Assert.assertTrue(
			_companyBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(_companyBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "organizationalUnit"));
		Assert.assertTrue(directory.hasAttribute("objectclass", "top"));
		Assert.assertTrue(directory.hasAttribute("ou", "liferay.com"));
	}

	private final CompanyBuilder _companyBuilder = new CompanyBuilder();

}