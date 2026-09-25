/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.exportimport.data.handler;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.test.util.LazyReferencingTestUtil;
import com.liferay.exportimport.test.util.lar.BasePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.staging.StagingGroupHelper;

import java.io.File;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Alberto Javier Moreno Lage
 */
public abstract class BaseBatchEnginePortletDataHandlerTestCase
	extends BasePortletDataHandlerTestCase {

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_targetCompany != null) {
			_companyLocalService.deleteCompany(_targetCompany);

			_companyGroup = null;
			_targetCompany = null;
			_targetCompanyGroup = null;
			_targetUser = null;
		}

		if (_depotEntry != null) {
			_depotEntryLocalService.deleteDepotEntry(_depotEntry);
			_depotEntryLocalService.deleteDepotEntry(_targetDepotEntry);

			_depotEntry = null;
			_depotLayout = null;
			_targetDepotEntry = null;
			_targetDepotLayout = null;
		}

		if (_group != null) {
			_groupLocalService.deleteGroup(_group);
			_groupLocalService.deleteGroup(_targetGroup);

			_group = null;
			_siteLayout = null;
			_targetGroup = null;
			_targetSiteLayout = null;
		}
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Scope scope = getScope();

		if ((scope == Scope.COMPANY) && (_targetCompany == null)) {
			_companyGroup = _stagingGroupHelper.fetchCompanyGroup(
				TestPropsValues.getCompanyId());

			_targetCompany = CompanyTestUtil.addCompany();

			_targetCompanyGroup = _stagingGroupHelper.fetchCompanyGroup(
				_targetCompany.getCompanyId());

			_targetUser = UserTestUtil.addCompanyAdminUser(_targetCompany);
		}
		else if ((scope == Scope.DEPOT) && (_depotEntry == null)) {
			_depotEntry = _addDepotEntry();

			_depotLayout = LayoutTestUtil.addTypePortletLayout(
				_depotEntry.getGroupId());

			_targetDepotEntry = _addDepotEntry();

			_targetDepotLayout = LayoutTestUtil.addTypePortletLayout(
				_targetDepotEntry.getGroupId());
		}
		else if ((scope == Scope.SITE) && (_group == null)) {
			_group = GroupTestUtil.addGroup();

			_siteLayout = LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId());

			_targetGroup = GroupTestUtil.addGroup();

			_targetSiteLayout = LayoutTestUtil.addTypePortletLayout(
				_targetGroup.getGroupId());
		}
	}

	@Test
	public void testExportImportComments() throws Exception {
		if (!supportsComments()) {
			return;
		}

		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		String body = RandomTestUtil.randomString();

		_commentManager.addComment(
			TestPropsValues.getUserId(), groupId,
			exportImportDescriptor.getModelClassName(),
			getPrimaryKey(groupId, externalReferenceCode), body,
			className -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);

				return serviceContext;
			});

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.COMMENTS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		List<String> comments = getComments(
			_getTargetGroupId(scope), externalReferenceCode);

		Assert.assertTrue(
			comments.toString(),
			ListUtil.exists(comments, comment -> comment.contains(body)));
	}

	@Override
	@Test
	public void testExportImportData() throws Exception {
		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode1 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());
		String externalReferenceCode2 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		_exportImport(scope, Collections.emptyMap(), null, null);

		List<String> externalReferenceCodes = _getTargetExternalReferenceCodes(
			scope);

		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.containsAll(
				Arrays.asList(externalReferenceCode1, externalReferenceCode2)));

		updateEntry(groupId, externalReferenceCode1);

		_exportImport(scope, Collections.emptyMap(), null, null);

		Assert.assertEquals(
			getEntryValue(groupId, externalReferenceCode1),
			getEntryValue(_getTargetGroupId(scope), externalReferenceCode1));
	}

	@Test
	public void testExportImportDeletions() throws Exception {
		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode1 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());
		String externalReferenceCode2 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		_exportImport(scope, Collections.emptyMap(), null, null);

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		try {
			deleteEntry(groupId, externalReferenceCode1);
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.DELETIONS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		List<String> externalReferenceCodes = _getTargetExternalReferenceCodes(
			scope);

		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode1));
		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode2));
	}

	@Test
	public void testExportImportDescriptor() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		Assert.assertNotNull(exportImportDescriptor.getKey());
		Assert.assertNotNull(exportImportDescriptor.getModelClassName());
		Assert.assertNotNull(exportImportDescriptor.getPortletId());
		Assert.assertNotNull(exportImportDescriptor.getScope());
	}

	@Test
	public void testExportImportFromLastPublishDate() throws Exception {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		if (!exportImportDescriptor.isStagingSupported()) {
			return;
		}

		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode1 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());
		String externalReferenceCode2 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.addChangesetCollection(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(), StringPool.BLANK);

		_changesetEntryLocalService.addChangesetEntry(
			TestPropsValues.getUserId(),
			changesetCollection.getChangesetCollectionId(),
			externalReferenceCode1,
			_classNameLocalService.getClassNameId(
				exportImportDescriptor.getModelClassName()),
			0);

		_exportImport(
			scope,
			HashMapBuilder.put(
				ExportImportDateUtil.RANGE,
				new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE}
			).put(
				"changesetCollectionId",
				new String[] {
					String.valueOf(
						changesetCollection.getChangesetCollectionId())
				}
			).build(),
			null, null);

		List<String> externalReferenceCodes = _getTargetExternalReferenceCodes(
			scope);

		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode1));
		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode2));
	}

	@Test
	public void testExportImportKeepCreatorData() throws Exception {
		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		_creatorUser = UserTestUtil.addUser();

		if (scope == Scope.COMPANY) {
			_creatorUser.setExternalReferenceCode(
				RandomTestUtil.randomString());

			_creatorUser = _userLocalService.updateUser(_creatorUser);

			User targetCreatorUser = UserTestUtil.addUser(_targetCompany);

			targetCreatorUser.setExternalReferenceCode(
				_creatorUser.getExternalReferenceCode());

			_userLocalService.updateUser(targetCreatorUser);
		}

		String externalReferenceCode = addEntry(
			groupId, _creatorUser.getUserId(), new Date());

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.CURRENT_USER_ID}
			).build(),
			null, null);

		User targetCreatorUser = _userLocalService.getUser(
			getCreatorUserId(_getTargetGroupId(scope), externalReferenceCode));

		Assert.assertEquals(
			_creatorUser.getExternalReferenceCode(),
			targetCreatorUser.getExternalReferenceCode());
	}

	@Test
	public void testExportImportPermissions() throws Exception {
		if (!supportsPermissions()) {
			return;
		}

		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		if (scope == Scope.COMPANY) {
			_targetRole = _roleLocalService.addRole(
				_role.getExternalReferenceCode(), _targetUser.getUserId(), null,
				0, _role.getName(), null, null, RoleConstants.TYPE_REGULAR,
				null, null);
		}

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			exportImportDescriptor.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(getPrimaryKey(groupId, externalReferenceCode)),
			_role.getRoleId(), new String[] {getPermissionsActionKey()});

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_getTargetCompanyId(scope), getTargetModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					getPrimaryKey(
						_getTargetGroupId(scope), externalReferenceCode)),
				_getTargetRoleId(scope), getPermissionsActionKey()));
	}

	@Test
	public void testExportImportWithDateRange() throws Exception {
		Scope scope = getScope();

		long groupId = _getGroupId(scope);

		long time = System.currentTimeMillis();

		String beforeExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(),
			new Date(time - (4 * Time.DAY)));
		String withinExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(),
			new Date(time - (2 * Time.DAY)));
		String afterExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date(time));

		Date startDate = new Date(time - (3 * Time.DAY));
		Date endDate = new Date(time - Time.DAY);

		_exportImport(scope, Collections.emptyMap(), startDate, endDate);

		List<String> externalReferenceCodes = _getTargetExternalReferenceCodes(
			scope);

		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(beforeExternalReferenceCode));
		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(withinExternalReferenceCode));
		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(afterExternalReferenceCode));
	}

	@Override
	@Test
	public void testPrepareManifestSummary() throws Exception {
		Group originalStagingGroup = stagingGroup;
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

			stagingGroup = _getGroup(getScope());

			super.testPrepareManifestSummary();
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			stagingGroup = originalStagingGroup;
		}
	}

	@Test
	public void testUpdateResolvesEmptyEntry() throws Exception {
		if (!supportsEmptyEntries()) {
			return;
		}

		long groupId = _getGroupId(getScope());

		String externalReferenceCode = null;

		try (SafeCloseable safeCloseable =
				LazyReferencingTestUtil.setLazyReferencingWithSafeCloseable(
					true)) {

			ExportImportThreadLocal.setPortletImportInProcess(true);

			try {
				externalReferenceCode = addEmptyEntry(
					groupId, TestPropsValues.getUserId());
			}
			finally {
				ExportImportThreadLocal.setPortletImportInProcess(false);
			}
		}

		Assert.assertEquals(
			WorkflowConstants.STATUS_EMPTY,
			getStatus(groupId, externalReferenceCode));

		ExportImportReportEntry exportImportReportEntry =
			_getEmptyExportImportReportEntry(externalReferenceCode);

		Assert.assertEquals(
			ExportImportReportEntryConstants.STATUS_UNRESOLVED,
			exportImportReportEntry.getStatus());

		updateEntry(groupId, externalReferenceCode);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			getStatus(groupId, externalReferenceCode));

		exportImportReportEntry = _getEmptyExportImportReportEntry(
			externalReferenceCode);

		Assert.assertEquals(
			ExportImportReportEntryConstants.STATUS_RESOLVED,
			exportImportReportEntry.getStatus());
	}

	protected static User getTargetUser() {
		return _targetUser;
	}

	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		throw new UnsupportedOperationException();
	}

	protected abstract String addEntry(
			long groupId, long userId, Date dateModified)
		throws Exception;

	protected abstract void deleteEntry(
			long groupId, String externalReferenceCode)
		throws Exception;

	protected List<String> getComments(
			long groupId, String externalReferenceCode)
		throws Exception {

		return TransformUtil.transform(
			_commentManager.getComments(
				getTargetModelClassName(),
				getPrimaryKey(groupId, externalReferenceCode),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			comment -> {
				if (comment.isRoot()) {
					return null;
				}

				return comment.getBody();
			});
	}

	protected abstract long getCreatorUserId(
			long groupId, String externalReferenceCode)
		throws Exception;

	@Override
	protected DataLevel getDataLevel() {
		Scope scope = getScope();

		if (scope == Scope.COMPANY) {
			return DataLevel.PORTAL;
		}

		if (scope == Scope.DEPOT) {
			return DataLevel.DEPOT;
		}

		if (scope == Scope.SITE) {
			return DataLevel.SITE;
		}

		return DataLevel.PORTLET_INSTANCE;
	}

	protected abstract Object getEntryValue(
			long groupId, String externalReferenceCode)
		throws Exception;

	protected abstract ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate();

	protected <T> ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate(Class<T> clazz) {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			clazz,
			"(export.import.vulcan.batch.engine.task.item.delegate=true)");
	}

	protected <T> ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate(
			Class<T> clazz, String filterString) {

		try {
			Bundle bundle = FrameworkUtil.getBundle(getClass());

			BundleContext bundleContext = bundle.getBundleContext();

			Collection<ServiceReference<T>> serviceReferences =
				bundleContext.getServiceReferences(clazz, filterString);

			Iterator<ServiceReference<T>> iterator =
				serviceReferences.iterator();

			return (ExportImportVulcanBatchEngineTaskItemDelegate<?>)
				bundleContext.getService(iterator.next());
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	protected abstract List<String> getExternalReferenceCodes(long groupId)
		throws Exception;

	protected String getPermissionsActionKey() {
		return ActionKeys.VIEW;
	}

	@Override
	protected String getPortletId() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		return exportImportDescriptor.getPortletId();
	}

	protected abstract long getPrimaryKey(
			long groupId, String externalReferenceCode)
		throws Exception;

	protected abstract Scope getScope();

	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected String getTargetModelClassName() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		return exportImportDescriptor.getModelClassName();
	}

	protected abstract boolean supportsComments();

	protected abstract boolean supportsEmptyEntries();

	protected abstract boolean supportsPermissions();

	protected abstract void updateEntry(
			long groupId, String externalReferenceCode)
		throws Exception;

	private DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _exportImport(
			Scope scope, Map<String, String[]> parameterMap, Date startDate,
			Date endDate)
		throws Exception {

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		parameterMap = HashMapBuilder.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_ALL}
		).put(
			PortletDataHandlerControl.getNamespacedName(
				portletId, exportImportDescriptor.getKey()),
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				portletId,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).putAll(
			parameterMap
		).build();

		if (scope == Scope.COMPANY) {
			_exportImportLayouts(parameterMap, startDate, endDate);
		}
		else {
			_exportImportPortletInfo(scope, parameterMap, startDate, endDate);
		}
	}

	private void _exportImportLayouts(
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		parameterMap = HashMapBuilder.putAll(
			parameterMap
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()}
		).build();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = TestPropsValues.getUser();

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportLayoutSettingsMap(
						user, _companyGroup.getGroupId(), false, new long[0],
						parameterMap);

			_setDateRange(settingsMap, startDate, endDate);

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						settingsMap);

			File larFile = ExportImportLocalServiceUtil.exportLayoutsAsFile(
				exportImportConfiguration);

			try {
				PermissionThreadLocal.setPermissionChecker(
					PermissionCheckerFactoryUtil.create(_targetUser));

				exportImportConfiguration = _updateImportConfiguration(
					exportImportConfiguration, _targetUser,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							_targetUser, _targetCompanyGroup.getGroupId(),
							false, null, parameterMap),
					_targetCompanyGroup.getGroupId());

				ExportImportLocalServiceUtil.importLayoutsDataDeletions(
					exportImportConfiguration, larFile);

				ExportImportLocalServiceUtil.importLayouts(
					exportImportConfiguration, larFile);
			}
			finally {
				FileUtil.delete(larFile);
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private void _exportImportPortletInfo(
			Scope scope, Map<String, String[]> parameterMap, Date startDate,
			Date endDate)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = TestPropsValues.getUser();

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			Layout layout = _siteLayout;

			if (scope == Scope.DEPOT) {
				layout = _depotLayout;
			}

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportPortletSettingsMap(
						user, layout.getPlid(), layout.getGroupId(), portletId,
						parameterMap, StringPool.BLANK);

			_setDateRange(settingsMap, startDate, endDate);

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_PORTLET_LOCAL,
						settingsMap);

			File larFile = ExportImportLocalServiceUtil.exportPortletInfoAsFile(
				exportImportConfiguration);

			try {
				Layout targetLayout = _targetSiteLayout;

				if (scope == Scope.DEPOT) {
					targetLayout = _targetDepotLayout;
				}

				exportImportConfiguration = _updateImportConfiguration(
					exportImportConfiguration, user,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportPortletSettingsMap(
							user, targetLayout.getPlid(),
							targetLayout.getGroupId(), portletId, parameterMap),
					targetLayout.getGroupId());

				ExportImportLocalServiceUtil.importPortletDataDeletions(
					exportImportConfiguration, larFile);

				ExportImportLocalServiceUtil.importPortletInfo(
					exportImportConfiguration, larFile);
			}
			finally {
				FileUtil.delete(larFile);
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private ExportImportReportEntry _getEmptyExportImportReportEntry(
			String classExternalReferenceCode)
		throws Exception {

		for (ExportImportReportEntry exportImportReportEntry :
				_exportImportReportEntryLocalService.
					getExportImportReportEntries(
						TestPropsValues.getCompanyId(), 0)) {

			if (Objects.equals(
					classExternalReferenceCode,
					exportImportReportEntry.getClassExternalReferenceCode()) &&
				(exportImportReportEntry.getType() ==
					ExportImportReportEntryConstants.TYPE_EMPTY)) {

				return exportImportReportEntry;
			}
		}

		return null;
	}

	private ExportImportDescriptor<?> _getExportImportDescriptor() {
		ExportImportVulcanBatchEngineTaskItemDelegate<?>
			exportImportVulcanBatchEngineTaskItemDelegate =
				getExportImportVulcanBatchEngineTaskItemDelegate();

		return exportImportVulcanBatchEngineTaskItemDelegate.
			getExportImportDescriptor();
	}

	private Group _getGroup(Scope scope) throws Exception {
		if (scope == Scope.COMPANY) {
			return _companyGroup;
		}

		if (scope == Scope.DEPOT) {
			return _groupLocalService.getGroup(_depotEntry.getGroupId());
		}

		return _group;
	}

	private long _getGroupId(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _companyGroup.getGroupId();
		}

		if (scope == Scope.DEPOT) {
			return _depotEntry.getGroupId();
		}

		return _group.getGroupId();
	}

	private long _getTargetCompanyId(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _targetCompanyGroup.getCompanyId();
		}

		if (scope == Scope.DEPOT) {
			return _targetDepotEntry.getCompanyId();
		}

		return _targetGroup.getCompanyId();
	}

	private List<String> _getTargetExternalReferenceCodes(Scope scope)
		throws Exception {

		return getExternalReferenceCodes(_getTargetGroupId(scope));
	}

	private long _getTargetGroupId(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _targetCompanyGroup.getGroupId();
		}

		if (scope == Scope.DEPOT) {
			return _targetDepotEntry.getGroupId();
		}

		return _targetGroup.getGroupId();
	}

	private long _getTargetRoleId(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _targetRole.getRoleId();
		}

		return _role.getRoleId();
	}

	private void _setDateRange(
		Map<String, Serializable> settingsMap, Date startDate, Date endDate) {

		if ((endDate != null) && (startDate != null)) {
			settingsMap.put("endDate", endDate);
			settingsMap.put("startDate", startDate);
		}
	}

	private ExportImportConfiguration _updateImportConfiguration(
			ExportImportConfiguration exportImportConfiguration, User user,
			Map<String, Serializable> settingsMap, long targetGroupId)
		throws Exception {

		exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				updateExportImportConfiguration(
					user.getUserId(),
					exportImportConfiguration.getExportImportConfigurationId(),
					StringPool.BLANK, StringPool.BLANK, settingsMap,
					new ServiceContext());

		exportImportConfiguration.setGroupId(targetGroupId);

		return ExportImportConfigurationLocalServiceUtil.
			updateExportImportConfiguration(exportImportConfiguration);
	}

	private static Group _companyGroup;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static DepotEntry _depotEntry;

	@Inject
	private static DepotEntryLocalService _depotEntryLocalService;

	private static Layout _depotLayout;
	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

	private static Layout _siteLayout;
	private static Company _targetCompany;
	private static Group _targetCompanyGroup;
	private static DepotEntry _targetDepotEntry;
	private static Layout _targetDepotLayout;
	private static Group _targetGroup;
	private static Layout _targetSiteLayout;
	private static User _targetUser;

	@Inject
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CommentManager _commentManager;

	@DeleteAfterTestRun
	private User _creatorUser;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

	private Role _targetRole;

	@Inject
	private UserLocalService _userLocalService;

}