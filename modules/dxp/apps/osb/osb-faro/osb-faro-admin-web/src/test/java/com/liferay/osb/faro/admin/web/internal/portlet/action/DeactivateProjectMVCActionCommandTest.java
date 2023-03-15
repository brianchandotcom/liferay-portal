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

package com.liferay.osb.faro.admin.web.internal.portlet.action;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Inácio Nery Kong
 */
public class DeactivateProjectMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtils.setField(
			_deactivateProjectMVCActionCommand, "contactsEngineClient",
			_contactsEngineClient);

		Mockito.when(
			_faroProjectLocalService.getFaroProject(Mockito.eq(1L))
		).thenReturn(
			_faroProject
		);

		ReflectionTestUtils.setField(
			_deactivateProjectMVCActionCommand, "_faroProjectLocalService",
			_faroProjectLocalService);
	}

	@Test
	public void test() throws Exception {
		ActionRequest actionRequest = Mockito.mock(ActionRequest.class);

		Mockito.when(
			actionRequest.getParameter(Mockito.eq("faroProjectId"))
		).thenReturn(
			"1"
		);

		ActionResponse actionResponse = Mockito.mock(ActionResponse.class);

		_deactivateProjectMVCActionCommand.doProcessAction(
			actionRequest, actionResponse);

		Mockito.verify(
			_contactsEngineClient, Mockito.atLeast(1)
		).deleteProject(
			Mockito.eq(_faroProject)
		);

		Mockito.verify(
			_faroProjectLocalService, Mockito.atLeast(1)
		).updateFaroProject(
			Mockito.eq(_faroProject)
		);
	}

	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final DeactivateProjectMVCActionCommand
		_deactivateProjectMVCActionCommand =
			new DeactivateProjectMVCActionCommand();
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);

}