/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v3_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SegmentsExperienceUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_publishedLayout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = _publishedLayout.fetchDraftLayout();

		try {
			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				"{}", _draftLayout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_draftLayout.getPlid()));

			SegmentsExperience segmentsExperience =
				SegmentsTestUtil.addSegmentsExperience(
					_group.getGroupId(), _draftLayout.getPlid());

			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				"{}", _draftLayout,
				segmentsExperience.getSegmentsExperienceId());

			ContentLayoutTestUtil.publishLayout(_draftLayout, _publishedLayout);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		_deleteSegmentsExperiences();

		runUpgrade();

		_assertSegmentsExperiences();
	}

	@Test
	public void testUpgradeWithClassPKColumns() throws Exception {
		_deleteSegmentsExperiences();

		List<IndexMetadata> indexMetadataList = new ArrayList<>();

		try {
			indexMetadataList.addAll(
				_renameColumn(
					"plid", "classPK", "LayoutPageTemplateStructure"));
			indexMetadataList.addAll(
				_renameColumn("plid", "plid2", "FragmentEntryLink"));

			runUpgrade();
		}
		finally {
			_renameColumn("classPK", "plid", "LayoutPageTemplateStructure");
			_renameColumn("plid2", "plid", "FragmentEntryLink");

			if (ListUtil.isNotEmpty(indexMetadataList)) {
				_db.addIndexes(DataAccess.getConnection(), indexMetadataList);
			}
		}

		_assertSegmentsExperiences();
	}

	@Test
	public void testUpgradeWithFragmentEntryLinkClassPKColumn()
		throws Exception {

		_deleteSegmentsExperiences();

		List<IndexMetadata> indexMetadataList = new ArrayList<>();

		try {
			indexMetadataList.addAll(
				_renameColumn("plid", "plid2", "FragmentEntryLink"));

			runUpgrade();
		}
		finally {
			_renameColumn("plid2", "plid", "FragmentEntryLink");

			if (ListUtil.isNotEmpty(indexMetadataList)) {
				_db.addIndexes(DataAccess.getConnection(), indexMetadataList);
			}
		}

		_assertSegmentsExperiences();
	}

	@Test
	public void testUpgradeWithLayoutPageTemplateStructureClassPKColumn()
		throws Exception {

		_deleteSegmentsExperiences();

		List<IndexMetadata> indexMetadataList = new ArrayList<>();

		try {
			indexMetadataList.addAll(
				_renameColumn(
					"plid", "classPK", "LayoutPageTemplateStructure"));

			runUpgrade();
		}
		finally {
			_renameColumn("classPK", "plid", "LayoutPageTemplateStructure");

			if (ListUtil.isNotEmpty(indexMetadataList)) {
				_db.addIndexes(DataAccess.getConnection(), indexMetadataList);
			}
		}

		_assertSegmentsExperiences();
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return SegmentsTestUtil.addSegmentsExperience(
			_group.getGroupId(), 0, _draftLayout.getPlid());
	}

	@Override
	protected void deleteCTModel(long primaryKey) throws Exception {
		_segmentsExperienceLocalService.deleteSegmentsExperience(primaryKey);
	}

	@Override
	protected CTService<?> getCTService() {
		return _segmentsExperienceLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		SegmentsExperience segmentsExperience = (SegmentsExperience)ctModel;

		segmentsExperience.setName(RandomTestUtil.randomString());

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperience);
	}

	private void _assertFragmentEntryLinks(
		int expectedCount, List<SegmentsExperience> segmentsExperiences) {

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			List<FragmentEntryLink> fragmentEntryLinks =
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						_group.getGroupId(),
						segmentsExperience.getSegmentsExperienceId(),
						_draftLayout.getPlid());

			Assert.assertEquals(
				fragmentEntryLinks.toString(), expectedCount,
				fragmentEntryLinks.size());
		}
	}

	private void _assertLayoutPageTemplateStructureRels(long expectedPlid) {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _draftLayout.getPlid());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				_layoutPageTemplateStructureRelLocalService.
					getLayoutPageTemplateStructureRels(
						layoutPageTemplateStructure.
							getLayoutPageTemplateStructureId())) {

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					layoutPageTemplateStructureRel.getSegmentsExperienceId());

			Assert.assertEquals(expectedPlid, segmentsExperience.getPlid());
		}
	}

	private void _assertSegmentsExperiences() {
		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceLocalService.getSegmentsExperiences(
				_group.getGroupId(), _draftLayout.getPlid());

		_assertFragmentEntryLinks(1, segmentsExperiences);

		_assertLayoutPageTemplateStructureRels(_draftLayout.getPlid());
		_assertSegmentsExperiences(2, segmentsExperiences);
	}

	private void _assertSegmentsExperiences(
		int expectedCount, List<SegmentsExperience> segmentsExperiences) {

		Assert.assertEquals(
			segmentsExperiences.toString(), expectedCount,
			segmentsExperiences.size());

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			Assert.assertNotNull(
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					_group.getGroupId(),
					segmentsExperience.getSegmentsExperienceKey(),
					_publishedLayout.getPlid()));
		}
	}

	private void _deleteSegmentsExperiences() throws Exception {
		_updateFragmentEntryLinks();
		_updateLayoutPageTemplateStructureRels();

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceLocalService.getSegmentsExperiences(
				_group.getGroupId(), _draftLayout.getPlid());

		DB db = DBManagerUtil.getDB();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			_resourceLocalService.deleteResource(
				segmentsExperience, ResourceConstants.SCOPE_INDIVIDUAL);

			db.runSQL(
				"delete from SegmentsExperience where segmentsExperienceId = " +
					segmentsExperience.getSegmentsExperienceId());
		}

		_multiVMPool.clear();

		_assertFragmentEntryLinks(0, segmentsExperiences);

		_assertLayoutPageTemplateStructureRels(_publishedLayout.getPlid());
		_assertSegmentsExperiences(0, Collections.emptyList());
	}

	private long _getPublishedSegmentsExperienceId(
		long plid, long segmentsExperienceId) {

		SegmentsExperience draftSegmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperienceId);

		SegmentsExperience publishedSegmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				_group.getGroupId(),
				draftSegmentsExperience.getSegmentsExperienceKey(), plid);

		return publishedSegmentsExperience.getSegmentsExperienceId();
	}

	private List<IndexMetadata> _renameColumn(
			String columnName, String newColumnName, String tableName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			List<IndexMetadata> indexMetadataList = new ArrayList<>();

			if (dbInspector.hasColumn(tableName, newColumnName) ||
				!dbInspector.hasColumn(tableName, columnName)) {

				return indexMetadataList;
			}

			indexMetadataList.addAll(
				_db.dropIndexes(connection, tableName, columnName));

			// Special alter for reserved words like SYSTEM in MySQL

			if (DBManagerUtil.getDBType() == DBType.MYSQL) {
				_db.runSQLTemplate(
					StringBundler.concat(
						"alter table ", tableName, " change `", columnName,
						"` ", newColumnName, " LONG"),
					true);

				return indexMetadataList;
			}

			_db.runSQLTemplate(
				StringBundler.concat(
					"alter table ", tableName, " rename column ", columnName,
					" to ", newColumnName),
				true);

			return indexMetadataList;
		}
	}

	private void _updateFragmentEntryLinks() {
		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
					_group.getGroupId(), _draftLayout.getPlid())) {

			fragmentEntryLink.setSegmentsExperienceId(
				_getPublishedSegmentsExperienceId(
					_publishedLayout.getPlid(),
					fragmentEntryLink.getSegmentsExperienceId()));

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink);
		}
	}

	private void _updateLayoutPageTemplateStructureRels() {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _draftLayout.getPlid());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				_layoutPageTemplateStructureRelLocalService.
					getLayoutPageTemplateStructureRels(
						layoutPageTemplateStructure.
							getLayoutPageTemplateStructureId())) {

			layoutPageTemplateStructureRel.setSegmentsExperienceId(
				_getPublishedSegmentsExperienceId(
					_publishedLayout.getPlid(),
					layoutPageTemplateStructureRel.getSegmentsExperienceId()));

			_layoutPageTemplateStructureRelLocalService.
				updateLayoutPageTemplateStructureRel(
					layoutPageTemplateStructureRel);
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.segments.internal.upgrade.v3_2_0." +
			"SegmentsExperienceUpgradeProcess";

	private static DB _db;

	@Inject(
		filter = "(&(component.name=com.liferay.segments.internal.upgrade.registry.SegmentsServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Layout _draftLayout;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	private Layout _publishedLayout;

	@Inject
	private ResourceLocalService _resourceLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}