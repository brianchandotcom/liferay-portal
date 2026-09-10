/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portlet.test.MockLiferayPortletContext;

import jakarta.portlet.Portlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class KaleoDesignerPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_kaleoDefinition = _addKaleoDefinition();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testRender() throws Exception {
		KaleoDefinition kaleoDefinition = _addKaleoDefinition();

		_kaleoDefinitionLocalService.deleteKaleoDefinition(kaleoDefinition);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest(
				kaleoDefinition.getName(), "/designer/error.jsp");

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		_assertSessionError(
			NoSuchDefinitionException.class, mockLiferayPortletRenderRequest);

		mockLiferayPortletRenderRequest = _getMockLiferayPortletRenderRequest(
			_kaleoDefinition.getName(), "/designer/error.jsp");

		mockLiferayPortletRenderRequest.setParameter(
			"draftVersion", RandomTestUtil.randomString());

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		_assertSessionError(
			NoSuchDefinitionVersionException.class,
			mockLiferayPortletRenderRequest);

		mockLiferayPortletRenderRequest = _getMockLiferayPortletRenderRequest(
			RandomTestUtil.randomString(), "/designer/error.jsp");

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		_assertSessionError(
			NoSuchDefinitionVersionException.class,
			mockLiferayPortletRenderRequest);

		mockLiferayPortletRenderRequest = _getMockLiferayPortletRenderRequest(
			_kaleoDefinition.getName(), "/designer/error.jsp");

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		_assertSessionError(
			PrincipalException.MustBeCompanyAdmin.class,
			mockLiferayPortletRenderRequest);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		mockLiferayPortletRenderRequest = _getMockLiferayPortletRenderRequest(
			_kaleoDefinition.getName(),
			"/designer/edit_workflow_definition.jsp");

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		Assert.assertEquals(
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				_kaleoDefinition.getCompanyId(), _kaleoDefinition.getName()),
			mockLiferayPortletRenderRequest.getAttribute(
				"KALEO_DRAFT_DEFINITION"));
		Assert.assertTrue(
			SessionErrors.isEmpty(mockLiferayPortletRenderRequest));
	}

	private KaleoDefinition _addKaleoDefinition() throws Exception {
		return _kaleoDefinitionLocalService.addKaleoDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocalizationUtil.getXml(
				new LocalizedValuesMap(RandomTestUtil.randomString()), "title"),
			RandomTestUtil.randomString(),
			StringUtil.read(
				getClass(), "dependencies/workflow-definition.json"),
			WorkflowDefinitionConstants.SCOPE_AI,
			RandomTestUtil.randomBoolean(), 1,
			ServiceContextTestUtil.getServiceContext(
				_accountEntry.getAccountEntryGroupId(),
				TestPropsValues.getUserId()));
	}

	private void _assertSessionError(
		Class<?> clazz,
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest) {

		Assert.assertNull(
			mockLiferayPortletRenderRequest.getAttribute(
				"KALEO_DRAFT_DEFINITION"));
		Assert.assertTrue(
			SessionErrors.contains(mockLiferayPortletRenderRequest, clazz));
	}

	private MockLiferayPortletRenderRequest _getMockLiferayPortletRenderRequest(
			String name, String path)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			new MockLiferayPortletContext(path));
		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletRenderRequest.setParameter(
			"mvcPath", "/designer/edit_workflow_definition.jsp");
		mockLiferayPortletRenderRequest.setParameter("name", name);

		return mockLiferayPortletRenderRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_user.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		return themeDisplay;
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private KaleoDefinition _kaleoDefinition;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.KaleoDesignerPortlet"
	)
	private Portlet _portlet;

	@DeleteAfterTestRun
	private User _user;

}