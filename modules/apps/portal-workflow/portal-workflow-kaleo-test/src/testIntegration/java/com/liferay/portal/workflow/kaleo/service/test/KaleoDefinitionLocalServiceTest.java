/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class KaleoDefinitionLocalServiceTest
	extends BaseKaleoLocalServiceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_classNameLocalService.invalidate();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAddKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		Assert.assertEquals(1, kaleoDefinition.getVersion());
	}

	@Test
	public void testDeactivateKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		Assert.assertFalse(kaleoDefinition.isActive());
	}

	@Test
	public void testDeactivateKaleoDefinitionWithSystem()
		throws PortalException {

		_testDeactivateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_CHANGE_TONE);
		_testDeactivateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR);
		_testDeactivateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING);
		_testDeactivateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_LIFERAY_SEARCH);
		_testDeactivateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_MAKE_LONGER);
		_testDeactivateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER);
	}

	@Test(expected = WorkflowException.class)
	public void testDeleteKaleoDefinition1() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deleteKaleoDefinition(kaleoDefinition);
	}

	@Test(expected = NoSuchDefinitionException.class)
	public void testDeleteKaleoDefinition2() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		deleteKaleoDefinition(kaleoDefinition);

		_kaleoDefinitionLocalService.getKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Test
	public void testDeleteKaleoDefinition3() throws Exception {
		_testDeleteKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_CHANGE_TONE);
		_testDeleteKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR);
		_testDeleteKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING);
		_testDeleteKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_LIFERAY_SEARCH);
		_testDeleteKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_MAKE_LONGER);
		_testDeleteKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER);
	}

	@Test
	public void testUpdateKaleoDefinitionShouldIncrementVersion1()
		throws Exception {

		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		kaleoDefinition = updateKaleoDefinition(kaleoDefinition);

		Assert.assertEquals(2, kaleoDefinition.getVersion());
	}

	@Test
	public void testUpdateKaleoDefinitionWithSystem() throws Exception {
		_testUpdateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_CHANGE_TONE);
		_testUpdateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR);
		_testUpdateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING);
		_testUpdateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_LIFERAY_SEARCH);
		_testUpdateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_MAKE_LONGER);
		_testUpdateKaleoDefinitionWithSystem(
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER);
	}

	private void _testDeactivateKaleoDefinitionWithSystem(String name)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.getKaleoDefinition(
				name, serviceContext);

		AssertUtils.assertFailure(
			RequiredWorkflowDefinitionException.
				MustNotDeactivateSystemWorkflowDefinition.class,
			StringBundler.concat(
				"Workflow definition ", kaleoDefinition.getKaleoDefinitionId(),
				" cannot be deactivated because it is a system workflow ",
				"definition"),
			() -> _kaleoDefinitionLocalService.deactivateKaleoDefinition(
				name, kaleoDefinition.getVersion(), serviceContext));
	}

	private void _testDeleteKaleoDefinitionWithSystem(String name)
		throws Exception {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.getKaleoDefinition(
				name, serviceContext);

		AssertUtils.assertFailure(
			RequiredWorkflowDefinitionException.
				MustNotDeleteSystemWorkflowDefinition.class,
			StringBundler.concat(
				"Workflow definition ", kaleoDefinition.getKaleoDefinitionId(),
				" cannot be deleted because it is a system workflow ",
				"definition"),
			() -> _kaleoDefinitionLocalService.deleteKaleoDefinition(
				name, serviceContext));
	}

	private void _testUpdateKaleoDefinitionWithSystem(String name)
		throws Exception {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.getKaleoDefinition(
				name, serviceContext);

		AssertUtils.assertFailure(
			RequiredWorkflowDefinitionException.
				MustNotUpdateSystemWorkflowDefinition.class,
			StringBundler.concat(
				"Workflow definition ", kaleoDefinition.getKaleoDefinitionId(),
				" cannot be updated because it is a system workflow ",
				"definition"),
			() -> _kaleoDefinitionLocalService.updatedKaleoDefinition(
				kaleoDefinition.getExternalReferenceCode(),
				kaleoDefinition.getKaleoDefinitionId(),
				kaleoDefinition.getTitle(), kaleoDefinition.getDescription(),
				kaleoDefinition.getContent(), serviceContext));
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

}