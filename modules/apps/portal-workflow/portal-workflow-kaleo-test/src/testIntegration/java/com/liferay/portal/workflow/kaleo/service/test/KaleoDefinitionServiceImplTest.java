/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.workflow.configuration.WorkflowDefinitionConfiguration;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Feliphe Marinho
 * @author Nathaly Gomes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class KaleoDefinitionServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		PortalInstances.initCompany(_company);

		_companyAdminUser = UserTestUtil.addCompanyAdminUser(_company);

		_configuration = _configurationAdmin.getConfiguration(
			WorkflowDefinitionConfiguration.class.getName(),
			StringPool.QUESTION);
	}

	@Before
	public void setUp() throws Exception {
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_serviceContext = ServiceContextTestUtil.getServiceContext();
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);

		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"company.administrator.can.publish", false
			).build());
	}

	@Test
	public void testAddKaleoDefinition() throws Exception {

		// Administrator with "company.administrator.can.publish" disabled

		_setUpPermissionThreadLocal(_companyAdminUser);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _companyAdminUser.getUserId(), " must have ",
				WorkflowConstants.RESOURCE_NAME, ",ADD_DEFINITION permission ",
				"for null "),
			this::_addKaleoDefinition);

		// Administrator with "company.administrator.can.publish" enabled

		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"company.administrator.can.publish", true
			).build());

		Assert.assertNotNull(_addKaleoDefinition());
	}

	@Test
	public void testAddWorkflowDefinitionLink() throws Exception {
		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.addKaleoDefinition(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_read(), StringPool.BLANK, 1, _serviceContext);

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), _serviceContext);

		User user = _addUser();

		_setUpPermissionThreadLocal(user);

		AssertUtils.assertFailure(
			PrincipalException.MustBeCompanyAdmin.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must be the company ",
				"administrator to perform the action"),
			() -> _workflowDefinitionLinkService.addWorkflowDefinitionLink(
				user.getUserId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getGroupId(), BlogsEntry.class.getName(), 0, 0,
				kaleoDefinition.getName(), 1));

		_setUpPermissionThreadLocal(_companyAdminUser);

		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"company.administrator.can.publish", true
			).build());

		Assert.assertNotNull(
			_workflowDefinitionLinkService.addWorkflowDefinitionLink(
				_companyAdminUser.getUserId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getGroupId(), BlogsEntry.class.getName(), 0, 0,
				kaleoDefinition.getName(), 1));
	}

	@Test
	public void testGetKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = _addKaleoDefinition();

		User user = _addUser();

		_setUpPermissionThreadLocal(user);

		AssertUtils.assertFailure(
			PrincipalException.MustBeCompanyAdmin.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must be the company ",
				"administrator to perform the action"),
			() -> _kaleoDefinitionService.getKaleoDefinition(
				kaleoDefinition.getKaleoDefinitionId()));

		_setUpPermissionThreadLocal(_companyAdminUser);

		Assert.assertEquals(
			kaleoDefinition,
			_kaleoDefinitionService.getKaleoDefinition(
				kaleoDefinition.getKaleoDefinitionId()));
	}

	@Test
	public void testGetScopeKaleoDefinitions() throws Exception {
		User user = _addUser();

		_setUpPermissionThreadLocal(user);

		AssertUtils.assertFailure(
			PrincipalException.MustBeCompanyAdmin.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must be the company ",
				"administrator to perform the action"),
			() -> _kaleoDefinitionService.getScopeKaleoDefinitions(
				WorkflowDefinitionConstants.SCOPE_ALL, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, _serviceContext));
		AssertUtils.assertFailure(
			PrincipalException.MustBeCompanyAdmin.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must be the company ",
				"administrator to perform the action"),
			() -> _kaleoDefinitionService.getScopeKaleoDefinitions(
				WorkflowDefinitionConstants.SCOPE_ALL, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, _serviceContext));

		_setUpPermissionThreadLocal(_companyAdminUser);

		KaleoDefinition singleApprover =
			_kaleoDefinitionLocalService.getKaleoDefinition(
				"Single Approver", _serviceContext);

		Assert.assertEquals(
			Collections.singletonList(singleApprover),
			_kaleoDefinitionService.getScopeKaleoDefinitions(
				WorkflowDefinitionConstants.SCOPE_ALL, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, _serviceContext));
		Assert.assertEquals(
			Collections.singletonList(singleApprover),
			_kaleoDefinitionService.getScopeKaleoDefinitions(
				WorkflowDefinitionConstants.SCOPE_ALL, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null, _serviceContext));
	}

	@Test
	public void testGetWorkflowDefinitionLinks() throws Exception {
		_setUpPermissionThreadLocal(_companyAdminUser);

		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"company.administrator.can.publish", true
			).build());

		WorkflowDefinitionLink workflowDefinitionLink1 =
			_workflowDefinitionLinkService.addWorkflowDefinitionLink(
				_companyAdminUser.getUserId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getGroupId(), BlogsEntry.class.getName(), 0, 0,
				"Single Approver", 1);

		User user = _addUser();

		_setUpPermissionThreadLocal(user);

		AssertUtils.assertFailure(
			PrincipalException.MustBeCompanyAdmin.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must be the company ",
				"administrator to perform the action"),
			() -> _workflowDefinitionLinkService.getWorkflowDefinitionLinks(
				TestPropsValues.getCompanyId(), "Single Approver", 1));

		_setUpPermissionThreadLocal(_companyAdminUser);

		List<WorkflowDefinitionLink> workflowDefinitionLinks =
			_workflowDefinitionLinkService.getWorkflowDefinitionLinks(
				TestPropsValues.getCompanyId(), "Single Approver", 1);

		WorkflowDefinitionLink workflowDefinitionLink2 =
			workflowDefinitionLinks.get(0);

		Assert.assertEquals(
			workflowDefinitionLink1.getClassName(),
			workflowDefinitionLink2.getClassName());

		Assert.assertEquals(
			workflowDefinitionLink1.getWorkflowDefinitionName(),
			workflowDefinitionLink2.getWorkflowDefinitionName());
	}

	@Test
	public void testUpdateKaleoDefinition() throws Exception {

		// Administrator with "company.administrator.can.publish" disabled

		KaleoDefinition kaleoDefinition = _addKaleoDefinition();

		_setUpPermissionThreadLocal(_companyAdminUser);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _companyAdminUser.getUserId(), " must have ",
				WorkflowConstants.RESOURCE_NAME, ",ADD_DEFINITION permission ",
				"for null "),
			() -> _kaleoDefinitionService.updateKaleoDefinition(
				kaleoDefinition.getExternalReferenceCode(),
				kaleoDefinition.getKaleoDefinitionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				kaleoDefinition.getContent(), _serviceContext));

		// Administrator with "company.administrator.can.publish" enabled

		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"company.administrator.can.publish", true
			).build());

		Assert.assertNotNull(
			_kaleoDefinitionService.updateKaleoDefinition(
				kaleoDefinition.getExternalReferenceCode(),
				kaleoDefinition.getKaleoDefinitionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				kaleoDefinition.getContent(), _serviceContext));
	}

	private KaleoDefinition _addKaleoDefinition() throws Exception {
		return _kaleoDefinitionService.addKaleoDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read(), "company", 1, _serviceContext);
	}

	private User _addUser() throws Exception {
		return UserTestUtil.addUser(
			_company.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[0], _serviceContext);
	}

	private String _read() throws Exception {
		ClassLoader classLoader =
			BaseKaleoLocalServiceTestCase.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/workflow/kaleo/dependencies" +
					"/legal-marketing-workflow-definition.xml")) {

			return StringUtil.read(inputStream);
		}
	}

	private void _setUpPermissionThreadLocal(User user) {
		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
	}

	private static Company _company;
	private static User _companyAdminUser;
	private static Configuration _configuration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private KaleoDefinitionService _kaleoDefinitionService;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private ServiceContext _serviceContext;

	@Inject
	private WorkflowDefinitionLinkService _workflowDefinitionLinkService;

}