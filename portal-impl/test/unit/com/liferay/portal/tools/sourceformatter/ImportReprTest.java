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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Carlos Sierra Andrés
 *
 */
public class ImportReprTest {

	@Test
	public void testContains() {
		ImportRepr importRepr = ImportRepr.create("import java.util.Arrays");
		ImportRepr importRepr2 = ImportRepr.create("import java.util.Arrays");

		ArrayList<ImportRepr> arrayList = new ArrayList<ImportRepr>();

		arrayList.add(importRepr);

		if (!arrayList.contains(importRepr)) {
			arrayList.add(importRepr2);
		}

		Assert.assertEquals(1, arrayList.size());
	}

	@Test
	public void testEquals() {
		ImportRepr importRepr = ImportRepr.create("import java.util.Arrays");
		ImportRepr importRepr2 = ImportRepr.create("import java.util.Arrays");
		Assert.assertEquals(importRepr, importRepr2);
	}

	@Test
	public void testImportSorting() {
		ArrayList<ImportRepr> importsList = new ArrayList<ImportRepr>();

		ImportRepr mapEntry = ImportRepr.create("import java.util.Map.Entry;");
		ImportRepr mapImport = ImportRepr.create("import java.util.Map;");
		ImportRepr graphics = ImportRepr.create("import java.awt.Graphics;");
		ImportRepr graph2d = ImportRepr.create("import java.awt.Graphics2D;");

		importsList.add(mapEntry);
		importsList.add(mapImport);
		importsList.add(graphics);
		importsList.add(graph2d);

		ListUtil.sort(importsList);

		Assert.assertEquals(0, importsList.indexOf(graphics));
		Assert.assertEquals(1, importsList.indexOf(graph2d));
		Assert.assertEquals(2, importsList.indexOf(mapImport));
		Assert.assertEquals(3, importsList.indexOf(mapEntry));
	}

}