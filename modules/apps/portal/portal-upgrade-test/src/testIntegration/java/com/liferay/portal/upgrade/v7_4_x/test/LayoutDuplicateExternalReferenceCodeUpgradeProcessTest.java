/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.upgrade.v7_4_x.LayoutDuplicateExternalReferenceCodeUpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class LayoutDuplicateExternalReferenceCodeUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();
		_group = GroupTestUtil.addGroup();

		_dbInspector = new DBInspector(_connection);
	}

	@After
	public void tearDown() throws Exception {
		try {
			for (IndexMetadata indexMetadata : _indexMetadatas) {
				if (!_dbInspector.hasIndex(
						"Layout", indexMetadata.getIndexName())) {

					IndexUpdaterUtil.updatePortalIndexes();

					break;
				}
			}
		}
		finally {
			DataAccess.cleanUp(_connection);
		}
	}

	@Override
	@Test
	public void testMissingCtCollectionId() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				IndexUpdaterUtil.class.getName(), LoggerTestUtil.OFF)) {

			super.testMissingCtCollectionId();
		}
	}

	@Test
	@TestInfo("LPD-99950")
	public void testUpgrade() throws Exception {
		Layout[] duplicateExternalReferenceCodeLayouts1 =
			_addDuplicateExternalReferenceCodeLayouts();
		Layout[] duplicateExternalReferenceCodeLayouts2 =
			_addDuplicateExternalReferenceCodeLayouts();

		Layout fallbackExternalReferenceCodeLayout =
			LayoutTestUtil.addTypePortletLayout(_group, false);
		Layout uniqueExternalReferenceCodeLayout =
			LayoutTestUtil.addTypePortletLayout(_group, false);

		Group group2 = GroupTestUtil.addGroup();

		Layout group2Layout = LayoutTestUtil.addTypePortletLayout(
			group2, false);

		long plid1 = duplicateExternalReferenceCodeLayouts1[1].getPlid();
		long plid2 = duplicateExternalReferenceCodeLayouts2[1].getPlid();

		long renamedPlid1 = duplicateExternalReferenceCodeLayouts1[0].getPlid();
		long renamedPlid2 = duplicateExternalReferenceCodeLayouts2[0].getPlid();

		String externalReferenceCode1 = _getExternalReferenceCode(plid1);
		String externalReferenceCode2 = _getExternalReferenceCode(plid2);

		_updateExternalReferenceCode(
			fallbackExternalReferenceCodeLayout.getPlid(),
			String.valueOf(renamedPlid2));

		String uniqueExternalReferenceCode = PortalUUIDUtil.generate();

		_updateExternalReferenceCode(
			uniqueExternalReferenceCodeLayout.getPlid(),
			uniqueExternalReferenceCode);

		_updateExternalReferenceCode(
			group2Layout.getPlid(), externalReferenceCode1);

		runUpgrade();

		Assert.assertEquals(
			String.valueOf(renamedPlid2),
			_getExternalReferenceCode(
				fallbackExternalReferenceCodeLayout.getPlid()));
		Assert.assertEquals(
			externalReferenceCode1,
			_getExternalReferenceCode(group2Layout.getPlid()));
		Assert.assertEquals(
			externalReferenceCode1, _getExternalReferenceCode(plid1));
		Assert.assertEquals(
			externalReferenceCode2, _getExternalReferenceCode(plid2));
		Assert.assertEquals(
			String.valueOf(renamedPlid1),
			_getExternalReferenceCode(renamedPlid1));
		Assert.assertNotEquals(
			externalReferenceCode2, _getExternalReferenceCode(renamedPlid2));
		Assert.assertNotEquals(
			String.valueOf(renamedPlid2),
			_getExternalReferenceCode(renamedPlid2));
		Assert.assertEquals(
			uniqueExternalReferenceCode,
			_getExternalReferenceCode(
				uniqueExternalReferenceCodeLayout.getPlid()));

		IndexUpdaterUtil.updatePortalIndexes();

		for (IndexMetadata indexMetadata : _indexMetadatas) {
			Assert.assertTrue(
				_dbInspector.hasIndex("Layout", indexMetadata.getIndexName()));
		}

		Assert.assertNotNull(
			_layoutLocalService.fetchLayout(
				fallbackExternalReferenceCodeLayout.getPlid()));
		Assert.assertNotNull(
			_layoutLocalService.fetchLayout(group2Layout.getPlid()));
		Assert.assertNotNull(_layoutLocalService.fetchLayout(plid1));
		Assert.assertNotNull(_layoutLocalService.fetchLayout(plid2));
		Assert.assertNotNull(_layoutLocalService.fetchLayout(renamedPlid1));
		Assert.assertNotNull(_layoutLocalService.fetchLayout(renamedPlid2));
		Assert.assertNotNull(
			_layoutLocalService.fetchLayout(
				uniqueExternalReferenceCodeLayout.getPlid()));

		String fallbackExternalReferenceCode = _getExternalReferenceCode(
			renamedPlid2);

		runUpgrade();

		Assert.assertEquals(
			externalReferenceCode1, _getExternalReferenceCode(plid1));
		Assert.assertEquals(
			String.valueOf(renamedPlid1),
			_getExternalReferenceCode(renamedPlid1));
		Assert.assertEquals(
			fallbackExternalReferenceCode,
			_getExternalReferenceCode(renamedPlid2));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Layout[] layouts = _addDuplicateExternalReferenceCodeLayouts();

		return _layoutLocalService.getLayout(layouts[0].getPlid());
	}

	@Override
	protected void deleteCTModel(long primaryKey) throws Exception {
		_layoutLocalService.deleteLayout(
			_layoutLocalService.getLayout(primaryKey));
	}

	@Override
	protected CTService<?> getCTService() {
		return _layoutLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess =
			new LayoutDuplicateExternalReferenceCodeUpgradeProcess();

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		Layout layout = (Layout)ctModel;

		layout.setPriority(RandomTestUtil.randomInt());

		return _layoutLocalService.updateLayout(layout);
	}

	private Layout[] _addDuplicateExternalReferenceCodeLayouts()
		throws Exception {

		if (_indexMetadatas.isEmpty()) {
			_indexMetadatas = UpgradeTestUtil.dropUniqueIndexes(
				_connection, "Layout", "externalReferenceCode");
		}

		Layout publicLayout = LayoutTestUtil.addTypePortletLayout(
			_group, false);
		Layout privateLayout = LayoutTestUtil.addTypePortletLayout(
			_group, true);

		String externalReferenceCode = PortalUUIDUtil.generate();

		_updateExternalReferenceCode(
			publicLayout.getPlid(), externalReferenceCode);
		_updateExternalReferenceCode(
			privateLayout.getPlid(), externalReferenceCode);

		_entityCache.clearCache();
		_multiVMPool.clear();

		return new Layout[] {publicLayout, privateLayout};
	}

	private String _getExternalReferenceCode(long plid) throws Exception {
		Layout layout = _layoutLocalService.getLayout(plid);

		return layout.getExternalReferenceCode();
	}

	private void _updateExternalReferenceCode(
			long plid, String externalReferenceCode)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"update Layout set externalReferenceCode = ? where plid = ?")) {

			preparedStatement.setString(1, externalReferenceCode);
			preparedStatement.setLong(2, plid);

			preparedStatement.executeUpdate();
		}
	}

	private Connection _connection;
	private DBInspector _dbInspector;

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group;

	private List<IndexMetadata> _indexMetadatas = Collections.emptyList();

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}