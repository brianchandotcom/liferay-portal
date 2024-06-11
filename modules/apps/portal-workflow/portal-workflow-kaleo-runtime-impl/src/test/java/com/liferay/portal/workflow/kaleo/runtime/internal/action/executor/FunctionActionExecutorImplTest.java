/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.action.executor;

import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.runtime.internal.configuration.FunctionActionExecutorImplConfiguration;

import java.util.Collections;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Paulo Albuquerque
 */
public class FunctionActionExecutorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testLaunch() throws Exception {
		FunctionActionExecutorImpl functionActionExecutorImpl = Mockito.spy(
			new FunctionActionExecutorImpl());

		long companyId = RandomTestUtil.randomLong();

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_companyId", companyId);

		long userId = RandomTestUtil.randomLong();

		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		Mockito.when(
			userLocalService.getUserIdByScreenName(
				companyId, "default-service-account")
		).thenReturn(
			userId
		);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_userLocalService", userLocalService);

		FunctionActionExecutorImplConfiguration
			functionActionExecutorImplConfiguration = Mockito.mock(
				FunctionActionExecutorImplConfiguration.class);

		Mockito.when(
			functionActionExecutorImplConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);
		Mockito.when(
			functionActionExecutorImplConfiguration.resourcePath()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl,
			"_functionActionExecutorImplConfiguration",
			functionActionExecutorImplConfiguration);

		PortalCatapult portalCatapult = Mockito.mock(PortalCatapult.class);

		ReflectionTestUtil.setFieldValue(
			functionActionExecutorImpl, "_portalCatapult", portalCatapult);

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			Collections.singletonList(
				new WorkflowTaskAssignee(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomLong()));

		functionActionExecutorImpl.launch(
			new JSONObjectImpl(), workflowTaskAssignees);

		Mockito.verify(
			portalCatapult, Mockito.times(1)
		).launch(
			Mockito.anyLong(), Mockito.eq(Http.Method.POST),
			Mockito.anyString(), Mockito.any(), Mockito.anyString(),
			Mockito.eq(userId)
		);

		userId = RandomTestUtil.randomLong();

		workflowTaskAssignees = Collections.singletonList(
			new WorkflowTaskAssignee(User.class.getName(), userId));

		functionActionExecutorImpl.launch(
			new JSONObjectImpl(), workflowTaskAssignees);

		Mockito.verify(
			portalCatapult, Mockito.times(1)
		).launch(
			Mockito.anyLong(), Mockito.eq(Http.Method.POST),
			Mockito.anyString(), Mockito.any(), Mockito.anyString(),
			Mockito.eq(userId)
		);
	}

}