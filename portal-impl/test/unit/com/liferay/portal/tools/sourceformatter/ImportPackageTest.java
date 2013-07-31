/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 */
public class ImportPackageTest {

	@Test
	public void testContains() {
		ImportPackage importPackage = ImportPackageFactoryUtil.create(
			_ARRAYS_IMPORT_STATEMENT);
		ImportPackage importPackage2 = ImportPackageFactoryUtil.create(
			_ARRAYS_IMPORT_STATEMENT);

		List<ImportPackage> importPackages = new ArrayList<ImportPackage>();

		importPackages.add(importPackage);

		if (!importPackages.contains(importPackage)) {
			importPackages.add(importPackage2);
		}

		Assert.assertEquals(1, importPackages.size());
	}

	@Test
	public void testEquals() {
		ImportPackage importPackage = ImportPackageFactoryUtil.create(
			_ARRAYS_IMPORT_STATEMENT);

		ImportPackage importPackage2 = ImportPackageFactoryUtil.create(
			_ARRAYS_IMPORT_STATEMENT);

		Assert.assertEquals(importPackage, importPackage2);
	}

	@Test
	public void testImportSorting() {
		List<ImportPackage> importPackages = new ArrayList<ImportPackage>();

		ImportPackage graphicsImportPackage = ImportPackageFactoryUtil.create(
			"import java.awt.Graphics;");
		ImportPackage graphics2dImportPackage = ImportPackageFactoryUtil.create(
			"import java.awt.Graphics2D;");
		ImportPackage mapEntryImportPackage = ImportPackageFactoryUtil.create(
			"import java.util.Map.Entry;");
		ImportPackage mapImportPackage = ImportPackageFactoryUtil.create(
			"import java.util.Map;");

		importPackages.add(graphicsImportPackage);
		importPackages.add(graphics2dImportPackage);
		importPackages.add(mapEntryImportPackage);
		importPackages.add(mapImportPackage);

		ListUtil.sort(importPackages);

		Assert.assertEquals(0, importPackages.indexOf(graphicsImportPackage));
		Assert.assertEquals(1, importPackages.indexOf(graphics2dImportPackage));
		Assert.assertEquals(2, importPackages.indexOf(mapImportPackage));
		Assert.assertEquals(3, importPackages.indexOf(mapEntryImportPackage));
	}

	private static final String _ARRAYS_IMPORT_STATEMENT =
		"import java.util.Arrays";

}