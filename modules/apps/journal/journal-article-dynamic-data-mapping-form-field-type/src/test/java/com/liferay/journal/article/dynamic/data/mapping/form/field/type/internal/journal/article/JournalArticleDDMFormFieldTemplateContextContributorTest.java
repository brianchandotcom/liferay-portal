/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.article.dynamic.data.mapping.form.field.type.internal.journal.article;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Chaitanya Sammetla
 */
public class JournalArticleDDMFormFieldTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpJournalArticleLocalService();
		_setUpJSONFactory();
		_setUpPortal();
	}

	@Test
	public void testGetValueFetchesLatestArticle() throws Exception {
		long classPK = RandomTestUtil.randomLong();

		Mockito.when(
			_journalArticleLocalService.fetchLatestArticle(classPK)
		).thenReturn(
			_journalArticle
		);

		String latestTitle = RandomTestUtil.randomString();

		Mockito.when(
			_journalArticle.getTitle()
		).thenReturn(
			latestTitle
		);

		String oldTitle = RandomTestUtil.randomString();

		JSONObject inputJSONObject = _jsonFactory.createJSONObject();

		inputJSONObject.put(
			"classNameId", RandomTestUtil.randomLong()
		).put(
			"classPK", classPK
		).put(
			"title", oldTitle
		);

		String result = ReflectionTestUtil.invoke(
			_journalArticleDDMFormFieldTemplateContextContributor, "_getValue",
			new Class<?>[] {String.class}, inputJSONObject.toString());

		JSONObject resultJSONObject = _jsonFactory.createJSONObject(result);

		Assert.assertEquals(latestTitle, resultJSONObject.getString("title"));
		Assert.assertNotEquals(oldTitle, resultJSONObject.getString("title"));
	}

	@Test
	public void testGetValueWithNullValue() throws Exception {
		String result = ReflectionTestUtil.invoke(
			_journalArticleDDMFormFieldTemplateContextContributor, "_getValue",
			new Class<?>[] {String.class}, (Object)null);

		Assert.assertEquals(StringPool.BLANK, result);
	}

	private void _setUpJournalArticleLocalService() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_journalArticleDDMFormFieldTemplateContextContributor,
			"_journalArticleLocalService", _journalArticleLocalService);
	}

	private void _setUpJSONFactory() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_journalArticleDDMFormFieldTemplateContextContributor,
			"_jsonFactory", _jsonFactory);
	}

	private void _setUpPortal() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_journalArticleDDMFormFieldTemplateContextContributor, "_portal",
			_portal);
	}

	private final JournalArticle _journalArticle = Mockito.mock(
		JournalArticle.class);
	private final JournalArticleDDMFormFieldTemplateContextContributor
		_journalArticleDDMFormFieldTemplateContextContributor =
			new JournalArticleDDMFormFieldTemplateContextContributor();
	private final JournalArticleLocalService _journalArticleLocalService =
		Mockito.mock(JournalArticleLocalService.class);
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final Portal _portal = Mockito.mock(Portal.class);

}