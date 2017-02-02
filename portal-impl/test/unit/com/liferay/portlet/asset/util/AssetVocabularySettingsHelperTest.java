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

import com.liferay.asset.kernel.model.AssetCategoryConstants;

import java.util.Arrays;

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
		Assert.assertEquals(
			Arrays.toString(classNameIds), 1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);

		vocabularySettingsHelper = new AssetVocabularySettingsHelper(
			"multiValued=false\nselectedClassNameIds=1\n");

		classNameIds = vocabularySettingsHelper.getClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(
			Arrays.toString(classNameIds), 1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);
	}

	@Test
	public void testGetClassTypePKs() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classTypePKs = vocabularySettingsHelper.getClassTypePKs();

		Assert.assertNotNull(classTypePKs);
		Assert.assertEquals(
			Arrays.toString(classTypePKs), 1, classTypePKs.length);
		Assert.assertEquals(2, classTypePKs[0]);

		vocabularySettingsHelper = new AssetVocabularySettingsHelper(
			"multiValued=false\nselectedClassNameIds=1\n");

		classTypePKs = vocabularySettingsHelper.getClassTypePKs();

		Assert.assertNotNull(classTypePKs);
		Assert.assertEquals(
			Arrays.toString(classTypePKs), 1, classTypePKs.length);
		Assert.assertEquals(
			AssetCategoryConstants.ALL_CLASS_TYPE_PK, classTypePKs[0]);
	}

	@Test
	public void testGetRequiredClassNameIds() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classNameIds =
			vocabularySettingsHelper.getRequiredClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(
			Arrays.toString(classNameIds), 1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 2, false);

		classNameIds = vocabularySettingsHelper.getRequiredClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(
			Arrays.toString(classNameIds), 0, classNameIds.length);

		vocabularySettingsHelper = getVocabularySettingsHelper(
			false, new long[] {1, 2}, new long[] {1, 2},
			new boolean[] {true, false});

		classNameIds = vocabularySettingsHelper.getRequiredClassNameIds();

		Assert.assertNotNull(classNameIds);
		Assert.assertEquals(
			Arrays.toString(classNameIds), 1, classNameIds.length);
		Assert.assertEquals(1, classNameIds[0]);
	}

	@Test
	public void testGetRequiredClassTypePKs() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(1, 2, true);

		long[] classTypePKs =
			vocabularySettingsHelper.getRequiredClassTypePKs();

		Assert.assertNotNull(classTypePKs);
		Assert.assertEquals(
			Arrays.toString(classTypePKs), 1, classTypePKs.length);
		Assert.assertEquals(2, classTypePKs[0]);
	}

	@Test
	public void testHasClassNameIdAndClassTypePK() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				AssetCategoryConstants.ALL_CLASS_NAME_ID, true);

		Assert.assertTrue(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(1, 1));
		Assert.assertTrue(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			1, AssetCategoryConstants.ALL_CLASS_TYPE_PK, true);

		Assert.assertTrue(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(1, 0));
		Assert.assertTrue(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 1, true);

		Assert.assertTrue(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.hasClassNameIdAndClassTypePK(2, 2));
	}

	@Test
	public void testIsClassNameIdAndClassTypePKRequired() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				AssetCategoryConstants.ALL_CLASS_NAME_ID, false);

		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			AssetCategoryConstants.ALL_CLASS_NAME_ID, true);

		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(1, 1));
		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 1, false);

		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(2, 2));

		vocabularySettingsHelper = getVocabularySettingsHelper(1, 1, true);

		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(1, 1));
		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(2, 2));
	}

	@Test
	public void testIsMultiValued() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				false, new long[] {1},
				new long[] {AssetCategoryConstants.ALL_CLASS_TYPE_PK},
				new boolean[] {true});

		Assert.assertFalse(vocabularySettingsHelper.isMultiValued());

		vocabularySettingsHelper = getVocabularySettingsHelper(1, true);

		Assert.assertTrue(vocabularySettingsHelper.isMultiValued());
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		boolean multiValued, long[] classNameIds, long[] classTypePKs,
		boolean[] requireds) {

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requireds);
		vocabularySettingsHelper.setMultiValued(multiValued);

		return vocabularySettingsHelper;
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		long classNameId, boolean required) {

		return getVocabularySettingsHelper(
			classNameId, AssetCategoryConstants.ALL_CLASS_TYPE_PK, required);
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		long classNameId, long classTypePK, boolean required) {

		long[] classNameIds = {classNameId};
		long[] classTypePKs = {classTypePK};
		boolean[] requireds = {required};

		return getVocabularySettingsHelper(
			true, classNameIds, classTypePKs, requireds);
	}

}