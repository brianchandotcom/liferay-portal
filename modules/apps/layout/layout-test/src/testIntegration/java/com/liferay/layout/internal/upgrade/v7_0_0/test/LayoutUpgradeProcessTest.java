/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v7_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
public class LayoutUpgradeProcessTest extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		DB db = DBManagerUtil.getDB();

		db.alterTableAddColumn(_connection, "Layout", "iconImageId", "LONG");

		_group = _groupLocalService.getGroup(TestPropsValues.getGroupId());
	}

	@After
	public void tearDown() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Test
	@TestInfo("LPD-76573")
	public void testUpgrade() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CTSettingsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", true
						).build())) {

			Image image0 = _addImage(_counterLocalService.increment());

			Image image1 = _addImage(_counterLocalService.increment());

			Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);

			_updateLayoutLegacyColumn(
				layout1.getPlid(), layout1.getCtCollectionId(),
				image1.getImageId());

			CTCollection ctCollection =
				_ctCollectionLocalService.addCTCollection(
					null, TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId(), 0,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

			Image image2;
			Image image3;
			Layout layout2;
			Layout layout3;
			Layout layout4;

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection.getCtCollectionId())) {

				image2 = _imageLocalService.getImage(image1.getImageId());

				image2.setExternalReferenceCode(RandomTestUtil.randomString());

				image2 = _imageLocalService.updateImage(image2);

				layout2 = _layoutLocalService.updateLayout(layout1);

				_updateLayoutLegacyColumn(
					layout2.getPlid(), ctCollection.getCtCollectionId(),
					image2.getImageId());

				layout3 = LayoutTestUtil.addTypePortletLayout(_group);
				image3 = _addImage(_counterLocalService.increment());

				_updateLayoutLegacyColumn(
					layout3.getPlid(), ctCollection.getCtCollectionId(),
					image3.getImageId());

				layout4 = LayoutTestUtil.addTypePortletLayout(_group);

				_updateLayoutLegacyColumn(
					layout4.getPlid(), ctCollection.getCtCollectionId(),
					image0.getImageId());
			}

			runUpgrade();

			_assertLayoutIconImage(true, image1, layout1.getPlid());

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection.getCtCollectionId())) {

				_assertLayoutIconImage(true, image2, layout1.getPlid());

				_assertLayoutIconImage(true, image3, layout3.getPlid());

				_assertLayoutIconImage(false, image0, layout4.getPlid());
			}

			Assert.assertFalse(_dbInspector.hasColumn("Layout", "iconImageId"));
			Assert.assertTrue(_dbInspector.hasColumn("Layout", "iconImageERC"));
		}
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);
		Image image = _addImage(_counterLocalService.increment());

		layout.setIconImageERC(image.getExternalReferenceCode());

		_updateLayoutLegacyColumn(
			layout.getPlid(), layout.getCtCollectionId(), image.getImageId());

		return layout;
	}

	@Override
	protected void deleteCTModel(long primaryKey) throws Exception {
		Layout layout = _layoutLocalService.fetchLayout(primaryKey);

		if (layout != null) {
			_layoutLocalService.deleteLayout(layout);
		}
	}

	@Override
	protected CTService<?> getCTService() {
		return _layoutLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(7, 0, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			Class<?> upgradeProcessClass = upgradeProcess.getClass();

			Method getPostUpgradeStepsMethod =
				upgradeProcessClass.getDeclaredMethod("getPostUpgradeSteps");

			getPostUpgradeStepsMethod.setAccessible(true);

			UpgradeStep[] postUpgradeSteps =
				(UpgradeStep[])getPostUpgradeStepsMethod.invoke(upgradeProcess);

			upgradeProcess.upgrade();

			for (UpgradeStep postUpgradeStep : postUpgradeSteps) {
				postUpgradeStep.upgrade();
			}
		}

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) {
		return _layoutLocalService.updateLayout((Layout)ctModel);
	}

	private Image _addImage(long imageId) throws Exception {
		return _imageLocalService.updateImage(
			TestPropsValues.getCompanyId(), imageId,
			FileUtil.getBytes(getClass(), "dependencies/liferay.png"));
	}

	private void _assertLayoutIconImage(
		boolean hasSameCtCollectionId, Image image, long plid) {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		Assert.assertEquals(image.getCompanyId(), layout.getCompanyId());

		if (hasSameCtCollectionId) {
			Assert.assertEquals(
				image.getCtCollectionId(), layout.getCtCollectionId());
		}
		else {
			Assert.assertNotEquals(
				image.getCtCollectionId(), layout.getCtCollectionId());
		}

		Assert.assertEquals(
			image.getExternalReferenceCode(), layout.getIconImageERC());
		Assert.assertEquals(image.getImageId(), layout.getIconImageId());
	}

	private void _updateLayoutLegacyColumn(
			long plid, long ctCollectionId, long iconImageId)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"UPDATE Layout SET iconImageId = ?, iconImageERC = null " +
					"WHERE ctCollectionId = ? AND plid = ?")) {

			preparedStatement.setLong(1, iconImageId);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setLong(3, plid);
			preparedStatement.executeUpdate();
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.internal.upgrade.registry.LayoutServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Connection _connection;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private DBInspector _dbInspector;

	@Inject
	private EntityCache _entityCache;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ImageLocalService _imageLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}