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
import com.liferay.portal.kernel.json.JSONUtil;
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
		ReflectionTestUtil.setFieldValue(
			_journalArticleDDMFormFieldTemplateContextContributor,
			"_journalArticleLocalService", _journalArticleLocalService);

		ReflectionTestUtil.setFieldValue(
			_journalArticleDDMFormFieldTemplateContextContributor,
			"_jsonFactory", _jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_journalArticleDDMFormFieldTemplateContextContributor, "_portal",
			_portal);
	}

	@Test
	public void testGetValue() throws Exception {
		_testGetValueFetchesLatestArticle();
		_testGetValueWhenArticleIsDeleted();
		_testGetValueWithNullValue();
	}

	private String _createInputJSONString(long classPK, String title) {
		return JSONUtil.put(
			"classNameId", RandomTestUtil.randomLong()
		).put(
			"classPK", classPK
		).put(
			"title", title
		).toString();
	}

	private String _invokeGetValue(String value) throws Exception {
		return ReflectionTestUtil.invoke(
			_journalArticleDDMFormFieldTemplateContextContributor, "_getValue",
			new Class<?>[] {String.class}, value);
	}

	private void _testGetValueFetchesLatestArticle() throws Exception {
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

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_invokeGetValue(
				_createInputJSONString(
					classPK, RandomTestUtil.randomString())));

		Assert.assertEquals(latestTitle, jsonObject.getString("title"));
	}

	private void _testGetValueWhenArticleIsDeleted() throws Exception {
		long classPK = RandomTestUtil.randomLong();

		Mockito.when(
			_journalArticleLocalService.fetchLatestArticle(classPK)
		).thenReturn(
			null
		);

		String title = RandomTestUtil.randomString();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_invokeGetValue(_createInputJSONString(classPK, title)));

		Assert.assertEquals(classPK, jsonObject.getLong("classPK"));
		Assert.assertEquals(title, jsonObject.getString("title"));
	}

	private void _testGetValueWithNullValue() throws Exception {
		Assert.assertEquals(StringPool.BLANK, _invokeGetValue(null));
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