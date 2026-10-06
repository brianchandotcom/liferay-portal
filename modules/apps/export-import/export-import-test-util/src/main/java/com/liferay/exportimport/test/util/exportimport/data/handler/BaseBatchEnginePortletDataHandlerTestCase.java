/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.exportimport.data.handler;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
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
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.test.rule.ExportImportScopeClassTestRule;
import com.liferay.exportimport.test.util.LazyReferencingTestUtil;
import com.liferay.exportimport.test.util.lar.BasePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
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

	@Before
	@Override
	public void setUp() throws Exception {
		ExportImportVulcanBatchEngineTaskItemDelegate<?>
			exportImportVulcanBatchEngineTaskItemDelegate =
				getExportImportVulcanBatchEngineTaskItemDelegate();

		_exportImportDescriptor =
			exportImportVulcanBatchEngineTaskItemDelegate.
				getExportImportDescriptor();

		super.setUp();

		ExportImportScopeClassTestRule exportImportScopeClassTestRule =
			getExportImportScopeClassTestRule();

		_group = exportImportScopeClassTestRule.getGroup();
		_layout = exportImportScopeClassTestRule.getLayout();
		_targetGroup = exportImportScopeClassTestRule.getTargetGroup();
		_targetLayout = exportImportScopeClassTestRule.getTargetLayout();
		_targetUser = exportImportScopeClassTestRule.getTargetUser();
	}

	@Test
	public void testExportImportComments() throws Exception {
		if (!supportsComments()) {
			return;
		}

		long groupId = _group.getGroupId();

		String externalReferenceCode = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());

		String body = RandomTestUtil.randomString();

		_commentManager.addComment(
			TestPropsValues.getUserId(), groupId,
			_exportImportDescriptor.getModelClassName(),
			getPrimaryKey(externalReferenceCode, groupId), body,
			className -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);

				return serviceContext;
			});

		_exportImport(
			HashMapBuilder.put(
				PortletDataHandlerKeys.COMMENTS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		List<String> comments = _getComments(
			externalReferenceCode, _targetGroup.getGroupId());

		Assert.assertTrue(
			comments.toString(),
			ListUtil.exists(comments, comment -> comment.contains(body)));
	}

	@Override
	@Test
	public void testExportImportData() throws Exception {
		long groupId = _group.getGroupId();

		String externalReferenceCode1 = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());
		String externalReferenceCode2 = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());

		_exportImport(Collections.emptyMap(), null, null);

		long targetGroupId = _targetGroup.getGroupId();

		Assert.assertEquals(
			getEntryValue(externalReferenceCode1, groupId),
			getEntryValue(externalReferenceCode1, targetGroupId));
		Assert.assertEquals(
			getEntryValue(externalReferenceCode2, groupId),
			getEntryValue(externalReferenceCode2, targetGroupId));

		updateEntry(externalReferenceCode1, groupId);

		_exportImport(Collections.emptyMap(), null, null);

		Assert.assertEquals(
			getEntryValue(externalReferenceCode1, groupId),
			getEntryValue(externalReferenceCode1, targetGroupId));
	}

	@Test
	public void testExportImportDeletions() throws Exception {
		long groupId = _group.getGroupId();

		String externalReferenceCode1 = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());
		String externalReferenceCode2 = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());

		_exportImport(Collections.emptyMap(), null, null);

		long targetGroupId = _targetGroup.getGroupId();

		Assert.assertNotNull(fetchEntry(externalReferenceCode1, targetGroupId));
		Assert.assertNotNull(fetchEntry(externalReferenceCode2, targetGroupId));

		deleteEntry(externalReferenceCode1, groupId);

		_exportImport(
			HashMapBuilder.put(
				PortletDataHandlerKeys.DELETIONS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		Assert.assertNull(fetchEntry(externalReferenceCode1, targetGroupId));
		Assert.assertNotNull(fetchEntry(externalReferenceCode2, targetGroupId));
	}

	@Test
	public void testExportImportFromLastPublishDate() throws Exception {
		if (!_exportImportDescriptor.isStagingSupported()) {
			return;
		}

		long groupId = _group.getGroupId();

		String externalReferenceCode1 = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());
		String externalReferenceCode2 = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.fetchOrAddChangesetCollection(
				groupId,
				StagingConstants.RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);

		_changesetEntryLocalService.fetchOrAddChangesetEntry(
			changesetCollection.getChangesetCollectionId(),
			externalReferenceCode1,
			_classNameLocalService.getClassNameId(
				_exportImportDescriptor.getModelClassName()),
			getPrimaryKey(externalReferenceCode1, groupId));

		_exportImport(
			HashMapBuilder.put(
				ExportImportDateUtil.RANGE,
				new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE}
			).build(),
			null, null);

		long targetGroupId = _targetGroup.getGroupId();

		Assert.assertNotNull(fetchEntry(externalReferenceCode1, targetGroupId));
		Assert.assertNull(fetchEntry(externalReferenceCode2, targetGroupId));
	}

	@Test
	public void testExportImportKeepCreatorData() throws Exception {
		User user = _addUser();

		String externalReferenceCode = addEntry(
			_group.getGroupId(), new Date(), user.getUserId());

		_exportImport(
			HashMapBuilder.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.CURRENT_USER_ID}
			).build(),
			null, null);

		User targetUser = _userLocalService.getUser(
			getCreatorUserId(externalReferenceCode, _targetGroup.getGroupId()));

		Assert.assertEquals(
			user.getExternalReferenceCode(),
			targetUser.getExternalReferenceCode());
	}

	@Test
	public void testExportImportOverrideCreatorData() throws Exception {
		User user = _addUser();

		String externalReferenceCode = addEntry(
			_group.getGroupId(), new Date(), user.getUserId());

		_exportImport(
			HashMapBuilder.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.ALWAYS_CURRENT_USER_ID}
			).build(),
			null, null);

		Assert.assertEquals(
			_targetUser.getUserId(),
			getCreatorUserId(externalReferenceCode, _targetGroup.getGroupId()));
	}

	@Test
	public void testExportImportPermissions() throws Exception {
		if (!supportsPermissions()) {
			return;
		}

		long groupId = _group.getGroupId();

		String externalReferenceCode = addEntry(
			groupId, new Date(), TestPropsValues.getUserId());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		if (getScope() == Scope.COMPANY) {
			_roleLocalService.addRole(
				role.getExternalReferenceCode(), _targetUser.getUserId(), null,
				0, role.getName(), null, null, RoleConstants.TYPE_REGULAR, null,
				null);
		}

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_exportImportDescriptor.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(getPrimaryKey(externalReferenceCode, groupId)),
			role.getRoleId(), new String[] {ActionKeys.VIEW});

		_exportImport(
			HashMapBuilder.put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		Role targetRole = _roleLocalService.getRoleByExternalReferenceCode(
			role.getExternalReferenceCode(), _targetGroup.getCompanyId());

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_targetGroup.getCompanyId(), getTargetModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					getPrimaryKey(
						externalReferenceCode, _targetGroup.getGroupId())),
				targetRole.getRoleId(), ActionKeys.VIEW));
	}

	@Test
	public void testExportImportWithDateRange() throws Exception {
		long groupId = _group.getGroupId();

		long time = System.currentTimeMillis();

		String externalReferenceCode1 = addEntry(
			groupId, new Date(time - (4 * Time.DAY)),
			TestPropsValues.getUserId());
		String externalReferenceCode2 = addEntry(
			groupId, new Date(time - (2 * Time.DAY)),
			TestPropsValues.getUserId());
		String externalReferenceCode3 = addEntry(
			groupId, new Date(time), TestPropsValues.getUserId());

		Date startDate = new Date(time - (3 * Time.DAY));
		Date endDate = new Date(time - Time.DAY);

		_exportImport(Collections.emptyMap(), startDate, endDate);

		long targetGroupId = _targetGroup.getGroupId();

		Assert.assertNull(fetchEntry(externalReferenceCode1, targetGroupId));
		Assert.assertNotNull(fetchEntry(externalReferenceCode2, targetGroupId));
		Assert.assertNull(fetchEntry(externalReferenceCode3, targetGroupId));
	}

	@Test
	public void testUpdateResolvesEmptyEntry() throws Exception {
		if (!supportsEmptyEntries()) {
			return;
		}

		long groupId = _group.getGroupId();

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
			getStatus(externalReferenceCode, groupId));

		ExportImportReportEntry exportImportReportEntry =
			_getEmptyExportImportReportEntry(externalReferenceCode);

		Assert.assertEquals(
			ExportImportReportEntryConstants.STATUS_UNRESOLVED,
			exportImportReportEntry.getStatus());

		updateEntry(externalReferenceCode, groupId);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			getStatus(externalReferenceCode, groupId));

		exportImportReportEntry = _getEmptyExportImportReportEntry(
			externalReferenceCode);

		Assert.assertEquals(
			ExportImportReportEntryConstants.STATUS_RESOLVED,
			exportImportReportEntry.getStatus());
	}

	@Rule
	public final PermissionCheckerMethodTestRule
		permissionCheckerMethodTestRule =
			PermissionCheckerMethodTestRule.INSTANCE;

	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		throw new UnsupportedOperationException();
	}

	protected abstract String addEntry(
			long groupId, Date modifiedDate, long userId)
		throws Exception;

	protected abstract void deleteEntry(
			String externalReferenceCode, long groupId)
		throws Exception;

	protected abstract Object fetchEntry(
			String externalReferenceCode, long groupId)
		throws Exception;

	protected abstract long getCreatorUserId(
			String externalReferenceCode, long groupId)
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
			String externalReferenceCode, long groupId)
		throws Exception;

	protected abstract ExportImportScopeClassTestRule
		getExportImportScopeClassTestRule();

	protected abstract ExportImportVulcanBatchEngineTaskItemDelegate<?>
			getExportImportVulcanBatchEngineTaskItemDelegate()
		throws Exception;

	protected <T> ExportImportVulcanBatchEngineTaskItemDelegate<?>
			getExportImportVulcanBatchEngineTaskItemDelegate(
				Class<T> clazz, String filterString)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Collection<ServiceReference<T>> serviceReferences =
			bundleContext.getServiceReferences(clazz, filterString);

		Iterator<ServiceReference<T>> iterator = serviceReferences.iterator();

		return (ExportImportVulcanBatchEngineTaskItemDelegate<?>)
			bundleContext.getService(iterator.next());
	}

	@Override
	protected String getPortletId() {
		return _exportImportDescriptor.getPortletId();
	}

	protected abstract long getPrimaryKey(
			String externalReferenceCode, long groupId)
		throws Exception;

	protected Scope getScope() {
		ExportImportScopeClassTestRule exportImportScopeClassTestRule =
			getExportImportScopeClassTestRule();

		return exportImportScopeClassTestRule.getScope();
	}

	protected int getStatus(String externalReferenceCode, long groupId)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected String getTargetModelClassName() {
		return _exportImportDescriptor.getModelClassName();
	}

	protected User getTargetUser() {
		return _targetUser;
	}

	protected abstract boolean supportsComments();

	protected abstract boolean supportsEmptyEntries();

	protected abstract boolean supportsPermissions();

	protected abstract void updateEntry(
			String externalReferenceCode, long groupId)
		throws Exception;

	private User _addUser() throws Exception {
		User user = UserTestUtil.addUser();

		if (getScope() != Scope.COMPANY) {
			return user;
		}

		user = _userLocalService.updateExternalReferenceCode(
			user, RandomTestUtil.randomString());

		_userLocalService.updateExternalReferenceCode(
			UserTestUtil.addUser(
				_companyLocalService.getCompany(_targetGroup.getCompanyId())),
			user.getExternalReferenceCode());

		return user;
	}

	private void _exportImport(
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		Scope scope = getScope();

		parameterMap = HashMapBuilder.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_ALL}
		).put(
			PortletDataHandlerControl.getNamespacedName(
				portletId, _exportImportDescriptor.getKey()),
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
			new String[] {String.valueOf(scope != Scope.COMPANY)}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).putAll(
			parameterMap
		).build();

		User user = TestPropsValues.getUser();

		if (scope == Scope.COMPANY) {
			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportLayoutSettingsMap(
						user, _group.getGroupId(), false, new long[0],
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

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			try {
				PermissionThreadLocal.setPermissionChecker(
					PermissionCheckerFactoryUtil.create(_targetUser));

				exportImportConfiguration = _updateImportConfiguration(
					exportImportConfiguration, _targetUser,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							_targetUser, _targetGroup.getGroupId(), false, null,
							parameterMap),
					_targetGroup.getGroupId());

				ExportImportLocalServiceUtil.importLayoutsDataDeletions(
					exportImportConfiguration, larFile);

				ExportImportLocalServiceUtil.importLayouts(
					exportImportConfiguration, larFile);
			}
			finally {
				PermissionThreadLocal.setPermissionChecker(permissionChecker);

				FileUtil.delete(larFile);
			}
		}
		else {
			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportPortletSettingsMap(
						user, _layout.getPlid(), _layout.getGroupId(),
						portletId, parameterMap, StringPool.BLANK);

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
				exportImportConfiguration = _updateImportConfiguration(
					exportImportConfiguration, user,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportPortletSettingsMap(
							user, _targetLayout.getPlid(),
							_targetLayout.getGroupId(), portletId,
							parameterMap),
					_targetLayout.getGroupId());

				ExportImportLocalServiceUtil.importPortletDataDeletions(
					exportImportConfiguration, larFile);

				ExportImportLocalServiceUtil.importPortletInfo(
					exportImportConfiguration, larFile);
			}
			finally {
				FileUtil.delete(larFile);
			}
		}
	}

	private List<String> _getComments(
			String externalReferenceCode, long groupId)
		throws Exception {

		return TransformUtil.transform(
			_commentManager.getComments(
				getTargetModelClassName(),
				getPrimaryKey(externalReferenceCode, groupId),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			comment -> {
				if (comment.isRoot()) {
					return null;
				}

				return comment.getBody();
			});
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

	@Inject
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CommentManager _commentManager;

	@Inject
	private CompanyLocalService _companyLocalService;

	private ExportImportDescriptor<?> _exportImportDescriptor;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	private Group _group;
	private Layout _layout;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private Group _targetGroup;
	private Layout _targetLayout;
	private User _targetUser;

	@Inject
	private UserLocalService _userLocalService;

}