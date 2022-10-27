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

package com.liferay.portal.workflow.task.web.internal.portlet.action;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionResponse;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletResponse;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CompleteTaskMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_completeTaskMVCActionCommand = new CompleteTaskMVCActionCommand();
	}

	@Test
	public void testGetHttpServletRequest() {
		ActionRequest mockActionRequest = new MockActionRequest();

		HttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, new MockPortletResponse());

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getHttpServletRequest(mockActionRequest)
		).thenReturn(
			mockHttpServletRequest
		);

		ReflectionTestUtil.setFieldValue(
			_completeTaskMVCActionCommand, "_portal", portal);

		HttpServletRequest httpServletRequest = ReflectionTestUtil.invoke(
			_completeTaskMVCActionCommand, "_getHttpServletRequest",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, new MockActionResponse());

		Assert.assertNotNull(httpServletRequest);

		Assert.assertNotNull(
			httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE));
	}

	@Test
	public void testGetHttpServletRequestWithNoPortletResponse() {
		ActionRequest mockActionRequest = new MockActionRequest();
		ActionResponse mockActionResponse = new MockActionResponse();

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getHttpServletRequest(mockActionRequest)
		).thenReturn(
			new MockHttpServletRequest()
		);

		Mockito.when(
			portal.getLiferayPortletResponse(mockActionResponse)
		).thenReturn(
			new MockLiferayPortletActionResponse()
		);

		ReflectionTestUtil.setFieldValue(
			_completeTaskMVCActionCommand, "_portal", portal);

		HttpServletRequest httpServletRequest = ReflectionTestUtil.invoke(
			_completeTaskMVCActionCommand, "_getHttpServletRequest",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, mockActionResponse);

		Assert.assertNotNull(httpServletRequest);

		Assert.assertNotNull(
			httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE));
	}

	private CompleteTaskMVCActionCommand _completeTaskMVCActionCommand;

}