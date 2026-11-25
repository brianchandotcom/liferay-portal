/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.details.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.launch.details.LaunchDetails;
import com.liferay.launch.details.provider.LaunchDetailsProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LaunchDetailsProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	@TestInfo("LPD-115787")
	public void testFetchLaunchDetails() throws Exception {
		Assert.assertNull(
			_launchDetailsProvider.fetchLaunchDetails(
				RandomTestUtil.randomLong()));

		String name = RandomTestUtil.randomString();

		ObjectEntry launchObjectEntry = _addLaunchObjectEntry(name);

		LaunchDetails launchDetails = _launchDetailsProvider.fetchLaunchDetails(
			launchObjectEntry.getObjectEntryId());

		Assert.assertEquals(
			launchObjectEntry.getObjectEntryId(), launchDetails.getLaunchId());
		Assert.assertEquals(name, launchDetails.getName());
	}

	private ObjectEntry _addLaunchObjectEntry(String name) throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				TestPropsValues.getCompanyId(), "Launch");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", name
			).build(),
			serviceContext);
	}

	@Inject
	private LaunchDetailsProvider _launchDetailsProvider;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}