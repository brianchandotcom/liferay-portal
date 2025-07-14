/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;

import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_addClassPKColumn();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_dropClassPKColumn();
	}

	@Test
	public void testUpgradePortletLayoutWithoutUser() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				0, null, 0, 0, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED, new ServiceContext());

		_db.runSQLTemplate(
			"update LayoutPageTemplateStructure set classPK = plid;", true);

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		layout.setStatus(WorkflowConstants.STATUS_DRAFT);

		layout.setType(LayoutConstants.TYPE_PORTLET);

		layout.setUserId(0);

		layout = _layoutLocalService.updateLayout(layout);

		_runUpgrade();

		layout = _layoutLocalService.fetchLayout(layout.getPlid());

		Assert.assertEquals(
			layout.getStatusByUserId(),
			_userLocalService.getGuestUserId(layout.getCompanyId()));
	}

	private static void _addClassPKColumn() throws Exception {
		_classPKColumnsAdded = false;
		_indexMetadataList = Collections.emptyList();

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (!dbInspector.hasColumn(
					"LayoutPageTemplateStructure", "classNameId") &&
				!dbInspector.hasColumn(
					"LayoutPageTemplateStructure", "classPK")) {

				_db.runSQLTemplate(
					"alter table LayoutPageTemplateStructure add classNameId " +
						"LONG;",
					true);
				_db.runSQLTemplate(
					"alter table LayoutPageTemplateStructure add classPK LONG;",
					true);
				_db.runSQLTemplate(
					"update LayoutPageTemplateStructure set classPK = plid;",
					true);

				_classPKColumnsAdded = true;

				_indexMetadataList = _db.dropIndexes(
					connection, "LayoutPageTemplateStructure", "plid");
			}
		}
	}

	private static void _dropClassPKColumn() throws Exception {
		if (!_classPKColumnsAdded) {
			return;
		}

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (dbInspector.hasColumn(
					"LayoutPageTemplateStructure", "classNameId") &&
				dbInspector.hasColumn(
					"LayoutPageTemplateStructure", "classPK")) {

				_db.runSQLTemplate(
					"alter table LayoutPageTemplateStructure drop column " +
						"classNameId;",
					true);
				_db.runSQLTemplate(
					"alter table LayoutPageTemplateStructure drop column " +
						"classPK;",
					true);
			}

			if (ListUtil.isNotEmpty(_indexMetadataList)) {
				_db.addIndexes(connection, _indexMetadataList);
			}
		}
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.page.template.internal.upgrade.v5_1_1." +
			"LayoutPageTemplateStructureUpgradeProcess";

	private static boolean _classPKColumnsAdded;
	private static DB _db;
	private static List<IndexMetadata> _indexMetadataList;

	@Inject(
		filter = "(&(component.name=com.liferay.layout.page.template.internal.upgrade.registry.LayoutPageTemplateServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}