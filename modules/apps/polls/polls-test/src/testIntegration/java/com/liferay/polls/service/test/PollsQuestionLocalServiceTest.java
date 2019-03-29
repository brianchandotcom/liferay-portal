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

package com.liferay.polls.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.polls.exception.QuestionChoiceException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class PollsQuestionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = QuestionChoiceException.class)
	public void testAddQuestion() throws Exception {
		Map<Locale, String> titleMap = createLocalizedMap(
			RandomTestUtil.randomString());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			RandomTestUtil.randomString());
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		boolean neverExpire = true;

		List<PollsChoice> pollChoices = new ArrayList<>();

		pollChoices.add(createPollsChoice("A"));
		pollChoices.add(createPollsChoice("A"));

		ServiceContext serviceContext = new ServiceContext();

		PollsQuestionLocalServiceUtil.addQuestion(
			TestPropsValues.getUserId(), titleMap, descriptionMap,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, pollChoices,
			serviceContext);
	}

	@Test(expected = QuestionChoiceException.class)
	public void testUpdateQuestion() throws Exception {
		Map<Locale, String> titleMap = createLocalizedMap(
			RandomTestUtil.randomString());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			RandomTestUtil.randomString());
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		boolean neverExpire = true;

		List<PollsChoice> pollChoices = new ArrayList<>();

		pollChoices.add(createPollsChoice("A"));
		pollChoices.add(createPollsChoice("B"));

		ServiceContext serviceContext = new ServiceContext();

		PollsQuestion pollsQuestion = PollsQuestionLocalServiceUtil.addQuestion(
			TestPropsValues.getUserId(), titleMap, descriptionMap,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, pollChoices,
			serviceContext);

		Assert.assertNotNull(pollsQuestion);

		pollChoices = new ArrayList<>();

		pollChoices.add(createPollsChoice("B"));
		pollChoices.add(createPollsChoice("B"));

		PollsQuestionLocalServiceUtil.updateQuestion(
			TestPropsValues.getUserId(), pollsQuestion.getQuestionId(),
			titleMap, descriptionMap, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, pollChoices, serviceContext);
	}

	protected Map<Locale, String> createLocalizedMap(String localizedValue) {
		Map<Locale, String> localizedMap = new HashMap<>();

		localizedMap.put(LocaleUtil.US, localizedValue);

		return localizedMap;
	}

	protected PollsChoice createPollsChoice(String value) {
		PollsChoice choice = PollsChoiceUtil.create(0);

		choice.setName(value);
		choice.setDescriptionMap(createLocalizedMap(value));

		return choice;
	}

}