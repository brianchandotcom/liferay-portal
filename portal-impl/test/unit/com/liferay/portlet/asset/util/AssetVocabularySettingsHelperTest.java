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

package com.liferay.portlet.asset.util;

import com.liferay.portlet.asset.model.AssetCategoryConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsHelperTest {

	@Test
	public void testGetClassNameId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classNameIds = vocabularySettingsHelper.getClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);
	}

	@Test
	public void testGetClassTypeId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classTypeIds = vocabularySettingsHelper.getClassTypeIds();

		Assert.assertNotNull(classTypeIds);
		Assert.assertEquals(1, classTypeIds.length);
		Assert.assertEquals(2, classTypeIds[0]);
	}

	@Test
	public void testGetRequiredClassNameId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classNameIds =
			vocabularySettingsHelper.getRequiredClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 2, false);

		classNameIds = vocabularySettingsHelper.getRequiredClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(0, classNameIds.length);

		vocabularySettingsHelper = getVocabularySettingsHelper(
				false, new long[] {1, 2}, new long[] {1, 2},
			new boolean[] {true, false});

		classNameIds = vocabularySettingsHelper.getRequiredClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);
	}

	@Test
	public void testGetRequiredClassTypeId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classTypeIds = vocabularySettingsHelper.getClassTypeIds();

		Assert.assertNotNull(classTypeIds);
		Assert.assertEquals(1, classTypeIds.length);
		Assert.assertEquals(2, classTypeIds[0]);
	}

	@Test
	public void testHasClassNameAndTypeId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				AssetCategoryConstants.ALL_CLASS_NAMES_ID, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameAndTypeId(1, 1));
		Assert.assertTrue(vocabularySettingsHelper.hasClassNameAndTypeId(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			1, AssetCategoryConstants.ALL_CLASS_TYPES_ID, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameAndTypeId(1, 0));
		Assert.assertTrue(vocabularySettingsHelper.hasClassNameAndTypeId(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.hasClassNameAndTypeId(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 1, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameAndTypeId(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.hasClassNameAndTypeId(2, 2));
	}

	@Test
	public void testHasClassNameId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameId(1));
		Assert.assertTrue(vocabularySettingsHelper.hasClassNameId(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameId(1));
		Assert.assertFalse(vocabularySettingsHelper.hasClassNameId(2));
	}

	@Test
	public void testIsClassNameAndTypeIdRequired() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				AssetCategoryConstants.ALL_CLASS_NAMES_ID, false);

		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			AssetCategoryConstants.ALL_CLASS_NAMES_ID, true);

		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(1, 1));
		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 1, false);

		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 1, true);

		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameAndTypeIdRequired(2, 2));
	}

	@Test
	public void testIsClassNameIdRequired() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				AssetCategoryConstants.ALL_CLASS_NAME_IDS, false);

		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertTrue(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, false);

		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, true);

		Assert.assertTrue(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(2));
	}

	@Test
	public void testIsMultiValued() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				false, new long[] {1},
				new long[] {AssetCategoryConstants.ALL_CLASS_TYPES_ID},
				new boolean[] {true});

		Assert.assertFalse(vocabularySettingsHelper.isMultiValued());

		vocabularySettingsHelper = getVocabularySettingsHelper(1, true);

		Assert.assertTrue(vocabularySettingsHelper.isMultiValued());
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		boolean multiValued, long[] classNameIds, long[] classTypeIds,
		boolean[] requireds) {

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameAndTypeIds(
			classNameIds, classTypeIds, requireds);
		vocabularySettingsHelper.setMultiValued(multiValued);

		return vocabularySettingsHelper;
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		long classNameId, boolean required) {

		return getVocabularySettingsHelper(
			classNameId, AssetCategoryConstants.ALL_CLASS_TYPES_ID, required);
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		long classNameId, long classTypeId, boolean required) {

		long[] classNameIds = {classNameId};
		long[] classTypeIds = {classTypeId};
		boolean[] requireds = {required};

		return getVocabularySettingsHelper(
			true, classNameIds, classTypeIds, requireds);
	}

}