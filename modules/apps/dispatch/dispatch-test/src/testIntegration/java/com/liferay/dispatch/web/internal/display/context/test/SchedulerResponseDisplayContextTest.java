/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class SchedulerResponseDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		Company company = CompanyTestUtil.addCompany();

		_companyAdminPermissionChecker = PermissionCheckerFactoryUtil.create(
			UserTestUtil.getAdminUser(company.getCompanyId()));

		_omniadminPermissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PrincipalThreadLocal.setName(_originalName);
	}

	@After
	public void tearDown() throws Exception {
		_schedulerEngineHelper.delete(
			_JOB_NAME_SYSTEM, _JOB_NAME_SYSTEM, StorageType.MEMORY_CLUSTERED);
	}

	@Test
	public void testGetSchedulerResponsesWithCompanyAdmin() throws Exception {
		_scheduleSystemJob();

		Assert.assertFalse(
			_hasSchedulerResponse(
				_JOB_NAME_SYSTEM, _companyAdminPermissionChecker));
	}

	@Test
	public void testGetSchedulerResponsesWithOmniadmin() throws Exception {
		_scheduleSystemJob();

		Assert.assertTrue(
			_hasSchedulerResponse(
				_JOB_NAME_SYSTEM, _omniadminPermissionChecker));
	}

	private Object _getSchedulerResponseDisplayContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.US);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest(mockHttpServletRequest);

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		return mockLiferayPortletRenderRequest.getAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT);
	}

	private boolean _hasSchedulerResponse(
			String jobName, PermissionChecker permissionChecker)
		throws Exception {

		List<SchedulerResponse> schedulerResponses = ReflectionTestUtil.invoke(
			_getSchedulerResponseDisplayContext(), "_getSchedulerResponses",
			new Class<?>[] {int.class, PermissionChecker.class, int.class},
			Integer.MAX_VALUE, permissionChecker, 0);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			if (Objects.equals(jobName, schedulerResponse.getJobName())) {
				return true;
			}
		}

		return false;
	}

	private void _scheduleSystemJob() throws Exception {
		_schedulerEngineHelper.schedule(
			_triggerFactory.createTrigger(
				_JOB_NAME_SYSTEM, _JOB_NAME_SYSTEM, null, null, 60,
				TimeUnit.MINUTE),
			StorageType.MEMORY_CLUSTERED, null, RandomTestUtil.randomString(),
			new Message());
	}

	private static final String _JOB_NAME_SYSTEM =
		RandomTestUtil.randomString();

	private static PermissionChecker _companyAdminPermissionChecker;
	private static PermissionChecker _omniadminPermissionChecker;
	private static String _originalName;

	@Inject(
		filter = "component.name=com.liferay.dispatch.web.internal.portlet.action.EditSchedulerResponseMVCRenderCommand"
	)
	private MVCRenderCommand _mvcRenderCommand;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Inject
	private TriggerFactory _triggerFactory;

}