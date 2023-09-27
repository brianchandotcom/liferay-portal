/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class WorkflowDefinitionLinkUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<WorkflowDefinitionLink> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected WorkflowDefinitionLink addBaseModel(long userId)
		throws Exception {

		return addBaseModel(userId, true);
	}

	@Override
	protected WorkflowDefinitionLink addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
				userId, TestPropsValues.getCompanyId(),
				TestPropsValues.getGroupId(), null, 0, 0,
				RandomTestUtil.randomString(), 0);

		if (deleteAfterTestRun) {
			_workflowDefinitionLinks.add(workflowDefinitionLink);
		}

		return workflowDefinitionLink;
	}

	@Override
	protected UADAnonymizer<WorkflowDefinitionLink> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
				baseModelPK);

		String userName = workflowDefinitionLink.getUserName();

		if ((workflowDefinitionLink.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				baseModelPK);

		if (workflowDefinitionLink == null) {
			return true;
		}

		return false;
	}

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.uad.anonymizer.WorkflowDefinitionLinkUADAnonymizer"
	)
	private UADAnonymizer<WorkflowDefinitionLink> _uadAnonymizer;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@DeleteAfterTestRun
	private final List<WorkflowDefinitionLink> _workflowDefinitionLinks =
		new ArrayList<>();

}