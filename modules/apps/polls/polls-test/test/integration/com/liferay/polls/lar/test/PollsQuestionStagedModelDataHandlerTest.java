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

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.util.test.PollsTestUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;

import java.util.List;
import java.util.Map;

import org.arquillian.liferay.deploymentscenario.annotations.BndFile;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@BndFile("bnd-test.bnd")
@RunWith(Arquillian.class)
public class PollsQuestionStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return PollsTestUtil.addQuestion(group.getGroupId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return PollsQuestionLocalServiceUtil.
				getPollsQuestionByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return PollsQuestion.class;
	}

}