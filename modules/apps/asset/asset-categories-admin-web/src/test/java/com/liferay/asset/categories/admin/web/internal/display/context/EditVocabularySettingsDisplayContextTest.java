/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class EditVocabularySettingsDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_classNameLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testGetSelectedClassNameIds() {
		long existingClassNameId = RandomTestUtil.randomLong();
		long nonexistingClassNameId = RandomTestUtil.randomLong();

		_classNameLocalServiceUtilMockedStatic.when(
			() -> ClassNameLocalServiceUtil.fetchClassName(existingClassNameId)
		).thenReturn(
			Mockito.mock(ClassName.class)
		);

		_classNameLocalServiceUtilMockedStatic.when(
			() -> ClassNameLocalServiceUtil.fetchClassName(
				nonexistingClassNameId)
		).thenReturn(
			null
		);

		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			assetVocabulary.getSelectedClassNameIds()
		).thenReturn(
			new long[] {existingClassNameId, nonexistingClassNameId}
		);

		EditVocabularySettingsDisplayContext
			editVocabularySettingsDisplayContext =
				new EditVocabularySettingsDisplayContext(
					new MockHttpServletRequest(), assetVocabulary);

		long[] selectedClassNameIds =
			editVocabularySettingsDisplayContext.getSelectedClassNameIds();

		Assert.assertEquals(
			Arrays.toString(selectedClassNameIds), 1,
			selectedClassNameIds.length);

		Assert.assertEquals(existingClassNameId, selectedClassNameIds[0]);
	}

	private static final MockedStatic<ClassNameLocalServiceUtil>
		_classNameLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ClassNameLocalServiceUtil.class);

}