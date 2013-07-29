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
public class ImportPackageModelTest {

	@Test
	public void testContains() {
		ImportPackageModel importPackageModel = ImportPackageModel.create(
			_ARRAYS_IMPORT_STATEMENT);

		ImportPackageModel importPackageModel2 = ImportPackageModel.create(
			_ARRAYS_IMPORT_STATEMENT);

		List<ImportPackageModel> importPackageModels =
			new ArrayList<ImportPackageModel>();

		importPackageModels.add(importPackageModel);

		if (!importPackageModels.contains(importPackageModel)) {
			importPackageModels.add(importPackageModel2);
		}

		Assert.assertEquals(1, importPackageModels.size());
	}

	@Test
	public void testEquals() {
		ImportPackageModel importPackageModel = ImportPackageModel.create(
			_ARRAYS_IMPORT_STATEMENT);

		ImportPackageModel importPackageModel2 = ImportPackageModel.create(
			_ARRAYS_IMPORT_STATEMENT);

		Assert.assertEquals(importPackageModel, importPackageModel2);
	}

	@Test
	public void testImportSorting() {
		List<ImportPackageModel> importPackageModels =
			new ArrayList<ImportPackageModel>();

		ImportPackageModel mapEntryImportPackageModel =
			ImportPackageModel.create("import java.util.Map.Entry;");

		ImportPackageModel mapImportPackageModel = ImportPackageModel.create(
			"import java.util.Map;");

		ImportPackageModel graphicsImportPackageModel =
			ImportPackageModel.create("import java.awt.Graphics;");

		ImportPackageModel graphics2dImportPackageModel =
			ImportPackageModel.create("import java.awt.Graphics2D;");

		importPackageModels.add(graphicsImportPackageModel);
		importPackageModels.add(graphics2dImportPackageModel);
		importPackageModels.add(mapEntryImportPackageModel);
		importPackageModels.add(mapImportPackageModel);

		ListUtil.sort(importPackageModels);

		Assert.assertEquals(
			0, importPackageModels.indexOf(graphicsImportPackageModel));

		Assert.assertEquals(
			1, importPackageModels.indexOf(graphics2dImportPackageModel));

		Assert.assertEquals(
			2, importPackageModels.indexOf(mapImportPackageModel));

		Assert.assertEquals(
			3, importPackageModels.indexOf(mapEntryImportPackageModel));
	}

	private static final String _ARRAYS_IMPORT_STATEMENT =
		"import java.util.Arrays";

}