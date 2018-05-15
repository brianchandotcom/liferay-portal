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

package com.liferay.portal.workflow.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.uad.test.WorkflowDefinitionLinkUADTestHelper;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class WorkflowDefinitionLinkUADExporterTest
	extends BaseUADExporterTestCase<WorkflowDefinitionLink> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_workflowDefinitionLinkUADTestHelper.cleanUpDependencies(
			_workflowDefinitionLinks);
	}

	@Override
	protected WorkflowDefinitionLink addBaseModel(long userId)
		throws Exception {

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkUADTestHelper.addWorkflowDefinitionLink(
				userId);

		_workflowDefinitionLinks.add(workflowDefinitionLink);

		return workflowDefinitionLink;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "workflowDefinitionLinkId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@Inject(filter = "component.name=*.WorkflowDefinitionLinkUADExporter")
	private UADExporter _uadExporter;

	@DeleteAfterTestRun
	private final List<WorkflowDefinitionLink> _workflowDefinitionLinks =
		new ArrayList<>();

	@Inject
	private WorkflowDefinitionLinkUADTestHelper
		_workflowDefinitionLinkUADTestHelper;

}