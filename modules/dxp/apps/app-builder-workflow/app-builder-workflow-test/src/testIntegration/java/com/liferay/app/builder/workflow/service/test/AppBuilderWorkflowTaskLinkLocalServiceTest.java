/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.service.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.app.builder.workflow.exception.DuplicateAppBuilderWorkflowTaskLinkException;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppBuilderWorkflowTaskLinkLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = DuplicateAppBuilderWorkflowTaskLinkException.class)
	public void testAddDuplicatedAppBuilderWorkflowTaskLink() throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.addAppBuilderApp(
				TestPropsValues.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), true, RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString());

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		_appBuilderWorkflowTaskLinkLocalService.addAppBuilderWorkflowTaskLink(
			TestPropsValues.getCompanyId(), appBuilderApp.getAppBuilderAppId(),
			latestAppBuilderAppVersion.getAppBuilderAppVersionId(),
			appBuilderApp.getDdmStructureLayoutId(), true, "A");

		_appBuilderWorkflowTaskLinkLocalService.addAppBuilderWorkflowTaskLink(
			TestPropsValues.getCompanyId(), appBuilderApp.getAppBuilderAppId(),
			latestAppBuilderAppVersion.getAppBuilderAppVersionId(),
			appBuilderApp.getDdmStructureLayoutId(), true, "A");
	}

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Inject
	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

}