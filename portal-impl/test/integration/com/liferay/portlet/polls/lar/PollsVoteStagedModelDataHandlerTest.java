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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.util.test.PollsTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PollsVoteStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		PollsQuestion question = PollsTestUtil.addQuestion(group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, PollsQuestion.class, question);

		PollsChoice choice = PollsTestUtil.addChoice(
			group.getGroupId(), question.getQuestionId());

		PollsChoiceUtil.update(choice);

		addDependentStagedModel(
			dependentStagedModelsMap, PollsChoice.class, choice);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> questionDependentStagedModels =
			dependentStagedModelsMap.get(PollsQuestion.class.getSimpleName());

		PollsQuestion question =
			(PollsQuestion)questionDependentStagedModels.get(0);

		List<StagedModel>  choiceDependentStagedModels =
			dependentStagedModelsMap.get(PollsChoice.class.getSimpleName());

		PollsChoice choice = (PollsChoice)choiceDependentStagedModels.get(0);

		return PollsTestUtil.addVote(
			group.getGroupId(), question.getQuestionId(), choice.getChoiceId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return PollsVoteLocalServiceUtil.getPollsVoteByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return PollsVote.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> choiceDependentStagedModels =
			dependentStagedModelsMap.get(PollsChoice.class.getSimpleName());

		Assert.assertEquals(1, choiceDependentStagedModels.size());

		PollsChoice choice = (PollsChoice)choiceDependentStagedModels.get(0);

		PollsChoiceLocalServiceUtil.getPollsChoiceByUuidAndGroupId(
			choice.getUuid(), group.getGroupId());

		List<StagedModel> questionDependentStagedModels =
			dependentStagedModelsMap.get(PollsQuestion.class.getSimpleName());

		Assert.assertEquals(1, questionDependentStagedModels.size());

		PollsQuestion question =
			(PollsQuestion)questionDependentStagedModels.get(0);

		PollsQuestionLocalServiceUtil.getPollsQuestionByUuidAndGroupId(
			question.getUuid(), group.getGroupId());
	}

}