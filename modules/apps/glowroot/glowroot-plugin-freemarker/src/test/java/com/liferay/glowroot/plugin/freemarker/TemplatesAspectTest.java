/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.glowroot.plugin.freemarker;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gergely Szalay
 */
public class TemplatesAspectTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildMessage() {
		_testBuildMessageWithAllNull();
		_testBuildMessageWithNullThemeDisplay();
		_testBuildMessageWithThemeDisplay();
		_testBuildMessageWithThemeDisplayAndTemplate();
	}

	private String _invokeBuildMessage(
		TemplatesAspect.DDMTemplateShim dDMTemplateShim,
		TemplatesAspect.JournalArticleShim journalArticleShim,
		TemplatesAspect.ThemeDisplayShim themeDisplayShim) {

		return ReflectionTestUtil.invoke(
			TemplatesAspect.class, "_buildMessage",
			new Class<?>[] {
				TemplatesAspect.DDMTemplateShim.class,
				TemplatesAspect.JournalArticleShim.class,
				TemplatesAspect.ThemeDisplayShim.class
			},
			dDMTemplateShim, journalArticleShim, themeDisplayShim);
	}

	private void _testBuildMessageWithAllNull() {
		Assert.assertEquals(
			"Journal Article FreeMarker Template (Company ID ?, Site Group " +
				"ID ?)",
			_invokeBuildMessage(null, null, null));
	}

	private void _testBuildMessageWithNullThemeDisplay() {
		long companyId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();

		TemplatesAspect.JournalArticleShim journalArticleShim = Mockito.mock(
			TemplatesAspect.JournalArticleShim.class);

		Mockito.when(
			journalArticleShim.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			journalArticleShim.getGroupId()
		).thenReturn(
			groupId
		);

		Assert.assertEquals(
			StringBundler.concat(
				"Journal Article FreeMarker Template (Company ID ", companyId,
				", Site Group ID ", groupId, ")"),
			_invokeBuildMessage(null, journalArticleShim, null));
	}

	private void _testBuildMessageWithThemeDisplay() {
		long companyId = RandomTestUtil.randomLong();
		long siteGroupId = RandomTestUtil.randomLong();

		TemplatesAspect.ThemeDisplayShim themeDisplayShim = Mockito.mock(
			TemplatesAspect.ThemeDisplayShim.class);

		Mockito.when(
			themeDisplayShim.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			themeDisplayShim.getSiteGroupId()
		).thenReturn(
			siteGroupId
		);

		Assert.assertEquals(
			StringBundler.concat(
				"Journal Article FreeMarker Template (Company ID ", companyId,
				", Site Group ID ", siteGroupId, ")"),
			_invokeBuildMessage(null, null, themeDisplayShim));
	}

	private void _testBuildMessageWithThemeDisplayAndTemplate() {
		long companyId = RandomTestUtil.randomLong();
		long siteGroupId = RandomTestUtil.randomLong();
		long templateId = RandomTestUtil.randomLong();

		TemplatesAspect.DDMTemplateShim dDMTemplateShim = Mockito.mock(
			TemplatesAspect.DDMTemplateShim.class);

		Mockito.when(
			dDMTemplateShim.getTemplateId()
		).thenReturn(
			templateId
		);

		TemplatesAspect.ThemeDisplayShim themeDisplayShim = Mockito.mock(
			TemplatesAspect.ThemeDisplayShim.class);

		Mockito.when(
			themeDisplayShim.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			themeDisplayShim.getSiteGroupId()
		).thenReturn(
			siteGroupId
		);

		Assert.assertEquals(
			StringBundler.concat(
				"Journal Article FreeMarker Template (Company ID ", companyId,
				", Site Group ID ", siteGroupId,
				", and Dynamic Data Mapping Template ID ", templateId, ")"),
			_invokeBuildMessage(dDMTemplateShim, null, themeDisplayShim));
	}

}