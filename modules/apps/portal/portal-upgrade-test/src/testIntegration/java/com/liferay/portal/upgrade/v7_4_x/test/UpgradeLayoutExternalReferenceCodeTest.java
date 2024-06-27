/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeLayoutExternalReferenceCode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class UpgradeLayoutExternalReferenceCodeTest {

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

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), _group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			LayoutConstants.TYPE_CONTENT, false, false, null, _serviceContext);

		String externalReferenceCode = layout.getExternalReferenceCode();

		_setExternalReferenceCodeToBlank(layout.getPlid());

		layout = _layoutLocalService.fetchLayout(layout.getPlid());

		Assert.assertEquals(
			StringPool.BLANK, layout.getExternalReferenceCode());

		Assert.assertNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				externalReferenceCode, _group.getGroupId()));

		_runUpgrade();

		Layout updatedLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotEquals(
			StringPool.BLANK, updatedLayout.getExternalReferenceCode());
		Assert.assertEquals(
			updatedLayout.getUuid(), updatedLayout.getExternalReferenceCode());

		Layout draftLayout = layout.fetchDraftLayout();

		String draftExternalReferenceCode =
			draftLayout.getExternalReferenceCode();

		_setExternalReferenceCodeToBlank(draftLayout.getPlid());

		draftLayout = _layoutLocalService.fetchLayout(draftLayout.getPlid());

		Assert.assertEquals(
			StringPool.BLANK, draftLayout.getExternalReferenceCode());

		Assert.assertNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				draftExternalReferenceCode, _group.getGroupId()));

		_runUpgrade();

		Layout updatedDraftLayout = _layoutLocalService.fetchLayout(
			draftLayout.getPlid());

		Assert.assertNotEquals(
			StringPool.BLANK, updatedLayout.getExternalReferenceCode());
		Assert.assertEquals(
			updatedDraftLayout.getUuid(),
			updatedDraftLayout.getExternalReferenceCode());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess =
			new UpgradeLayoutExternalReferenceCode();

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	private void _setExternalReferenceCodeToBlank(long plid) throws Exception {
		_db.runSQL(
			"update Layout set externalReferenceCode = '' where plid = " +
				plid);

		_multiVMPool.clear();
	}

	private static DB _db;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	private ServiceContext _serviceContext;

}