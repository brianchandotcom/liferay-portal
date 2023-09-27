/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 */
public class ImportPackageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContains() {
		ImportPackage importPackage1 =
			_javaImportsFormatter.createImportPackage(_ARRAYS_IMPORT_STATEMENT);

		List<ImportPackage> importPackages = new ArrayList<>();

		importPackages.add(importPackage1);

		if (!importPackages.contains(importPackage1)) {
			ImportPackage importPackage2 =
				_javaImportsFormatter.createImportPackage(
					_ARRAYS_IMPORT_STATEMENT);

			importPackages.add(importPackage2);
		}

		Assert.assertEquals(
			importPackages.toString(), 1, importPackages.size());
	}

	@Test
	public void testEquals() {
		ImportPackage importPackage1 =
			_javaImportsFormatter.createImportPackage(_ARRAYS_IMPORT_STATEMENT);
		ImportPackage importPackage2 =
			_javaImportsFormatter.createImportPackage(_ARRAYS_IMPORT_STATEMENT);

		Assert.assertEquals(importPackage1, importPackage2);
	}

	private static final String _ARRAYS_IMPORT_STATEMENT =
		"import java.util.Arrays";

	private final JavaImportsFormatter _javaImportsFormatter =
		new JavaImportsFormatter();

}