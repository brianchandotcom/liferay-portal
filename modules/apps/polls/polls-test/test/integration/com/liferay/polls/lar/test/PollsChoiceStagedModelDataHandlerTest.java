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

import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.util.test.PollsTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRunTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class PollsChoiceStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new DeleteAfterTestRunTestRule(), TransactionalTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		PollsQuestion question = PollsTestUtil.addQuestion(group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, PollsQuestion.class, question);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			PollsQuestion.class.getSimpleName());

		PollsQuestion question = (PollsQuestion)dependentStagedModels.get(0);

		return PollsTestUtil.addChoice(
			group.getGroupId(), question.getQuestionId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return PollsChoiceLocalServiceUtil.getPollsChoiceByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return PollsChoice.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			PollsQuestion.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		PollsQuestion question = (PollsQuestion)dependentStagedModels.get(0);

		PollsQuestionLocalServiceUtil.getPollsQuestionByUuidAndGroupId(
			question.getUuid(), group.getGroupId());
	}

}