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

package com.liferay.portlet.mobiledevicerules.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.util.MDRTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MDRActionStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModels(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> stagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		List<StagedModel> stagedModels = new ArrayList<StagedModel>();

		MDRRuleGroup ruleGroup = MDRTestUtil.addRuleGroup(group.getGroupId());
		MDRRuleGroupInstance ruleGroupInstance =
			MDRTestUtil.addRuleGroupInstance(
				group.getGroupId(), ruleGroup.getRuleGroupId());

		stagedModels.add(ruleGroup);

		stagedModelsMap.put(MDRRuleGroup.class.getName(), stagedModels);

		stagedModels = new ArrayList<StagedModel>();

		stagedModels.add(ruleGroupInstance);

		stagedModelsMap.put(MDRRuleGroupInstance.class.getName(), stagedModels);

		return stagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group, Map<String, List<StagedModel>> dependentStagedModels)
		throws Exception {

		List<StagedModel> ruleGroupInstances = dependentStagedModels.get(
			MDRRuleGroupInstance.class.getName());

		MDRRuleGroupInstance ruleGroupInstance =
			(MDRRuleGroupInstance)ruleGroupInstances.get(0);

		return MDRTestUtil.addAction(
			ruleGroupInstance.getRuleGroupInstanceId());
	}

	@Override
	protected String getElementName() {
		return "action";
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return MDRActionLocalServiceUtil.getMDRActionByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected String getStagedModelClassName() {
		return MDRAction.class.getName();
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> stagedModelsMap, Group group)
		throws Exception {

		List<StagedModel> stagedModels = stagedModelsMap.get(
			MDRRuleGroupInstance.class.getName());

		Assert.assertEquals(1, stagedModels.size());

		MDRRuleGroupInstance ruleGroupInstance =
			(MDRRuleGroupInstance)stagedModels.get(0);

		MDRRuleGroupInstanceLocalServiceUtil.
			getMDRRuleGroupInstanceByUuidAndGroupId(
				ruleGroupInstance.getUuid(), ruleGroupInstance.getGroupId());

		stagedModels = stagedModelsMap.get(MDRRuleGroup.class.getName());

		Assert.assertEquals(1, stagedModels.size());

		MDRRuleGroup ruleGroup = (MDRRuleGroup)stagedModels.get(0);

		MDRRuleGroupLocalServiceUtil.getMDRRuleGroupByUuidAndGroupId(
			ruleGroup.getUuid(), group.getGroupId());
	}

}