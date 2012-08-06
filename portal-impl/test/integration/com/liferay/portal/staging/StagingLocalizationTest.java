/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.staging;

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Julio Camarero
 */
@ExecutionTestListeners(listeners = {
	TransactionalExecutionTestListener.class,
	MainServletExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class StagingLocalizationTest extends BaseStagingTestCase {

	@Test
	public void testChangeDefaultLocale() throws Exception {
		enableLocalStagingChangingLocales(
			"es_ES", "en_US,es_ES,de_DE", "en_US");
	}

	@Test
	public void testChangeDefaultLocaleAndDefaultContentLocale()
		throws Exception {

		enableLocalStagingChangingLocales(
			"en_US", "en_US,es_ES,de_DE", "es_ES");
	}

	@Test
	public void testChangeDefaultLocaleAndDefaultContentLocaleToEmptyLocale()
		throws Exception {

		enableLocalStagingChangingLocales(
			"fr_FR", "fr_FR,en_US,es_ES,de_DE", "en_US");
	}

	@Test
	public void testRemoveDefaultLocale() throws Exception {
		enableLocalStagingChangingLocales("es_ES", "es_ES,de_DE", "en_US");
	}

	@Test
	public void testRemoveDefaultLocaleAndChangeContentDefaultLocale()
		throws Exception {

		enableLocalStagingChangingLocales("de_DE", "de_DE,es_ES", "en_US");
	}

	@Test
	public void testRemoveDefaultLocaleAndDefaultContentLocaleToEmptyLocale()
		throws Exception {

		enableLocalStagingChangingLocales("de_DE", "de_DE", "es_ES");
	}

	@Test
	public void testRemoveDefaultLocaleToEmptyLocale() throws Exception {
		enableLocalStagingChangingLocales("de_DE", "de_DE", "en_US");
	}

	@Test
	public void testSameLocales() throws Exception {
		enableLocalStagingChangingLocales(
			"en_US", "en_US,es_ES,de_DE", "en_US");
	}

	protected void enableLocalStagingChangingLocales(
		String defaultLanguageId, String languageIds,
		String contentDefaultLanguageId)
		throws Exception {

		// initial status

		updateLocales("en_US", "en_US,es_ES,de_DE,fr_FR");

		Group group = ServiceTestUtil.addGroup(ServiceTestUtil.randomString());

		ServiceTestUtil.addLayout(group.getGroupId(), "Page1");

		// Create Content

		JournalArticle article = addJournalArticle(
			group.getGroupId(), "Title", "content", contentDefaultLanguageId);

		PollsQuestion question = addPollsQuestion(
			group.getGroupId(), "Question", "Description");

		updateLocales(defaultLanguageId, languageIds);

		// Enable Staging

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(group.getGroupId());

		Map<String, String[]> parameters = StagingUtil.getStagingParameters();

		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {String.valueOf(false)});

		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" + PortletKeys.JOURNAL,
			new String[] {String.valueOf(true)});

		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" + PortletKeys.POLLS,
			new String[] {String.valueOf(true)});

		for (String parameterName : parameters.keySet()) {
			serviceContext.setAttribute(
				parameterName, parameters.get(parameterName)[0]);
		}

		serviceContext.setAttribute(
			StagingConstants.STAGED_PORTLET + PortletKeys.JOURNAL, true);

		serviceContext.setAttribute(
			StagingConstants.STAGED_PORTLET + PortletKeys.POLLS, true);

		StagingUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, group, false, false,
			serviceContext);

		Group stagingGroup = group.getStagingGroup();

		// Update Content in Staging

		JournalArticle stagingArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				stagingGroup.getGroupId(), article.getUrlTitle());

		PollsQuestion stagingQuestion =
			PollsQuestionLocalServiceUtil.getPollsQuestionByUuidAndGroupId(
				question.getUuid(), stagingGroup.getGroupId());

		if (languageIds.contains(contentDefaultLanguageId)) {
			Assert.assertEquals(
				article.getDefaultLocale(), stagingArticle.getDefaultLocale());
		}
		else {
			Assert.assertEquals(
				defaultLanguageId, stagingArticle.getDefaultLocale());
		}

		Assert.assertEquals(
			defaultLanguageId, stagingQuestion.getDefaultLocale());

		for (Locale locale : _portal_locales) {
			if (languageIds.contains(LocaleUtil.toLanguageId(locale))) {
				Assert.assertEquals(
					article.getTitle(locale), stagingArticle.getTitle(locale));

				Assert.assertEquals(
					question.getTitle(locale),
					stagingQuestion.getTitle(locale));
			}
			else {
				if (languageIds.contains(contentDefaultLanguageId)) {
					Assert.assertEquals(
						article.getTitle(locale),
						stagingArticle.getTitle(locale));
				}
				else {
					Assert.assertEquals(
						article.getTitle(defaultLanguageId),
						stagingArticle.getTitle(locale));
				}

				Assert.assertEquals(
					question.getTitle(defaultLanguageId),
					stagingQuestion.getTitle(locale));
			}
		}
	}

	private static Locale[] _portal_locales = {
		new Locale("en", "US"), new Locale("es", "ES"), new Locale("de", "DE"),
		new Locale("fr", "FR")
	};

}