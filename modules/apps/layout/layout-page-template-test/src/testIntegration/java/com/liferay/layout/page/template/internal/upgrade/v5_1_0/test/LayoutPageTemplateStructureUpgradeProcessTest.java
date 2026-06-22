/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.sql.Connection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_db = DBManagerUtil.getDB();

		_connection = DataAccess.getConnection();

		_db.alterTableAddColumn(
			_connection, "LayoutPageTemplateStructure", "classPK", "LONG");

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_db.alterTableDropColumn(
			_connection, "LayoutPageTemplateStructure", "classPK");

		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-95515")
	public void testUpgradeAssignsOwnExperienceToEachLayoutFragments()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentPublishedLayout(
			_group, RandomTestUtil.randomString(),
			WorkflowConstants.STATUS_APPROVED);

		long publishedPlid = layout.getPlid();

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			publishedPlid);

		long draftPlid = draftLayout.getPlid();

		Assert.assertNotNull(
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), publishedPlid));
		Assert.assertNotNull(
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), draftPlid));

		FragmentEntryLink publishedFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				StringPool.BLANK, layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid()));
		FragmentEntryLink draftFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				StringPool.BLANK, draftLayout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(draftLayout.getPlid()));

		_simulatePreUpgradeState(draftPlid, publishedPlid);

		_runUpgrade();

		_multiVMPool.clear();

		long publishedSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				publishedPlid);
		long draftSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftPlid);

		Assert.assertTrue(publishedSegmentsExperienceId > 0);
		Assert.assertTrue(draftSegmentsExperienceId > 0);
		Assert.assertNotEquals(
			publishedSegmentsExperienceId, draftSegmentsExperienceId);

		publishedFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				publishedFragmentEntryLink.getFragmentEntryLinkId());
		draftFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				draftFragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			publishedSegmentsExperienceId,
			publishedFragmentEntryLink.getSegmentsExperienceId());
		Assert.assertEquals(
			draftSegmentsExperienceId,
			draftFragmentEntryLink.getSegmentsExperienceId());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private void _simulatePreUpgradeState(long draftPlid, long publishedPlid)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"update FragmentEntryLink set segmentsExperienceId = 0 where ",
				"plid in (", draftPlid, ", ", publishedPlid, ")"));

		_db.runSQL("update LayoutPageTemplateStructure set classPK = plid");
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.page.template.internal.upgrade.v5_1_0." +
			"LayoutPageTemplateStructureUpgradeProcess";

	private Connection _connection;
	private DB _db;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.layout.page.template.internal.upgrade.registry.LayoutPageTemplateServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}