/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@RunWith(Arquillian.class)
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

		vocabularySettingsHelper = getVocabularySettingsHelper(
			true, new long[] {AssetCategoryConstants.ALL_CLASS_NAME_ID, 1},
			new long[] {
				AssetCategoryConstants.ALL_CLASS_TYPE_PK,
				AssetCategoryConstants.ALL_CLASS_TYPE_PK
			},
			new boolean[] {false, true});

		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(
				AssetCategoryConstants.ALL_CLASS_NAME_ID,
				AssetCategoryConstants.ALL_CLASS_TYPE_PK));
		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(
				1, AssetCategoryConstants.ALL_CLASS_TYPE_PK));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			true, new long[] {1, 1},
			new long[] {AssetCategoryConstants.ALL_CLASS_TYPE_PK, 2},
			new boolean[] {false, true});

		Assert.assertFalse(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(
				1, AssetCategoryConstants.ALL_CLASS_TYPE_PK));
		Assert.assertTrue(
			vocabularySettingsHelper.isClassNameIdAndClassTypePKRequired(1, 2));
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

		return getVocabularySettingsHelper(
			true, new long[] {classNameId}, new long[] {classTypePK},
			new boolean[] {required});
	}

}