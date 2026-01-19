/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetCategoriesDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_assetCategoriesDisplayContext = new AssetCategoriesDisplayContext(
			new MockHttpServletRequest(), null, null);
	}

	@Test
	public void testIsVisibilityTypeDisabled() {
		Assert.assertFalse(
			_assetCategoriesDisplayContext.isVisibilityTypeDisabled(null));
		Assert.assertFalse(
			_isVisibilityTypeDisabled(
				AssetVocabularyConstants.VISIBILITY_TYPE_EMPTY));
		Assert.assertTrue(
			_isVisibilityTypeDisabled(
				AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL));
		Assert.assertTrue(
			_isVisibilityTypeDisabled(
				AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC));
	}

	@Test
	public void testIsVisibilityTypeInternalChecked() {
		Assert.assertFalse(
			_assetCategoriesDisplayContext.isVisibilityTypeInternalChecked(
				null));

		AssetVocabulary vocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			vocabulary.getVisibilityType()
		).thenReturn(
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC
		);

		Assert.assertFalse(
			_assetCategoriesDisplayContext.isVisibilityTypeInternalChecked(
				vocabulary));

		Mockito.when(
			vocabulary.getVisibilityType()
		).thenReturn(
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL
		);

		Assert.assertTrue(
			_assetCategoriesDisplayContext.isVisibilityTypeInternalChecked(
				vocabulary));
	}

	@Test
	public void testIsVisibilityTypePublicChecked() {
		Assert.assertTrue(
			_assetCategoriesDisplayContext.isVisibilityTypePublicChecked(null));

		AssetVocabulary vocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			vocabulary.getVisibilityType()
		).thenReturn(
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL
		);

		Assert.assertFalse(
			_assetCategoriesDisplayContext.isVisibilityTypePublicChecked(
				vocabulary));

		Mockito.when(
			vocabulary.getVisibilityType()
		).thenReturn(
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC
		);

		Assert.assertTrue(
			_assetCategoriesDisplayContext.isVisibilityTypePublicChecked(
				vocabulary));
	}

	private boolean _isVisibilityTypeDisabled(int visibilityType) {
		AssetVocabulary vocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			vocabulary.getVisibilityType()
		).thenReturn(
			visibilityType
		);

		return _assetCategoriesDisplayContext.isVisibilityTypeDisabled(
			vocabulary);
	}

	private AssetCategoriesDisplayContext _assetCategoriesDisplayContext;

}