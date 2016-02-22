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

package com.liferay.polls.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.util.test.PollsTestUtil;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class PollsPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@Rule
	public final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected void addStagedModels() throws Exception {
		PollsQuestion question = PollsTestUtil.addQuestion(
			stagingGroup.getGroupId());

		PollsChoice choice = PollsTestUtil.addChoice(
			stagingGroup.getGroupId(), question.getQuestionId());

		PollsTestUtil.addVote(
			stagingGroup.getGroupId(), question.getQuestionId(),
			choice.getChoiceId());
	}

	@Override
	protected String getPortletId() {
		return PollsPortletKeys.POLLS;
	}

}