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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Daniel Kocsis
 */
public class MBThreadFlagStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		MBMessage message = MBTestUtil.addMessage(group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, MBMessage.class, message);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group, Map<String,
			List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		return MBTestUtil.addThreadFlag(
			group.getGroupId(), message.getThread());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return null;
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return MBThreadFlag.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		MBMessageLocalServiceUtil.getMBMessageByUuidAndGroupId(
			message.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		validateImport(dependentStagedModelsMap, group);

		MBThreadFlag threadFlag = (MBThreadFlag)stagedModel;

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		Assert.assertTrue(
			MBThreadFlagLocalServiceUtil.hasThreadFlag(
				threadFlag.getUserId(), message.getThread()));
	}

}