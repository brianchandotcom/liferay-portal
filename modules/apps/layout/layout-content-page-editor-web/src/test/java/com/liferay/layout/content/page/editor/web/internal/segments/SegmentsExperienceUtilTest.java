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

package com.liferay.layout.content.page.editor.web.internal.segments;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.model.SegmentsExperience;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author David Arques
 */
public class SegmentsExperienceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Test
	public void testGetSegmentsExperienceJSONObject() {
		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.when(
			segmentsExperience.isActive()
		).thenReturn(
			RandomTestUtil.randomBoolean()
		);

		Mockito.when(
			segmentsExperience.getNameCurrentValue()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			segmentsExperience.getPriority()
		).thenReturn(
			RandomTestUtil.randomInt()
		);

		Mockito.when(
			segmentsExperience.getSegmentsEntryId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			segmentsExperience.getSegmentsExperienceId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			segmentsExperience.getTypeSettingsUnicodeProperties()
		).thenReturn(
			new UnicodeProperties(true)
		);

		Assert.assertEquals(
			JSONUtil.put(
				"active", segmentsExperience.isActive()
			).put(
				"languageIds",
				LocaleUtil.toLanguageIds(LanguageUtil.getAvailableLocales())
			).put(
				"name", segmentsExperience.getNameCurrentValue()
			).put(
				"priority", segmentsExperience.getPriority()
			).put(
				"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
			).put(
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId()
			).toString(),
			String.valueOf(
				SegmentsExperienceUtil.getSegmentsExperienceJSONObject(
					segmentsExperience)));
	}

}