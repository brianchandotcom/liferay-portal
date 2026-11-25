/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.upgrade.v2_3_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.DisplayPageTemplateTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wulbern
 */
@RunWith(Arquillian.class)
public class AssetDisplayPageEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_dlFileEntryClassNameId = _portal.getClassNameId(
			DLFileEntry.class.getName());

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		_dlFileEntry = DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

		_fileEntryClassNameId = _portal.getClassNameId(
			FileEntry.class.getName());
	}

	@Test
	@TestInfo("LPS-123590")
	public void testUpgradeProcess() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addDisplayPageTemplate();

		_addAssetDisplayPageEntry(
			_dlFileEntryClassNameId, layoutPageTemplateEntry);

		Assert.assertNull(
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				_group.getGroupId(), _fileEntryClassNameId,
				_dlFileEntry.getFileEntryId()));

		_upgrade();

		_assertAssetDisplayPageEntry(layoutPageTemplateEntry);
	}

	@Test
	@TestInfo("LPS-123590")
	public void testUpgradeProcessWithFileEntryAssetDisplayPageEntry()
		throws Exception {

		_addAssetDisplayPageEntry(
			_dlFileEntryClassNameId, _addDisplayPageTemplate());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_addDisplayPageTemplate();

		_addAssetDisplayPageEntry(
			_fileEntryClassNameId, layoutPageTemplateEntry);

		_upgrade();

		_assertAssetDisplayPageEntry(layoutPageTemplateEntry);
	}

	private void _addAssetDisplayPageEntry(
		long classNameId, LayoutPageTemplateEntry layoutPageTemplateEntry) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.createAssetDisplayPageEntry(
				_counterLocalService.increment());

		assetDisplayPageEntry.setUuid(String.valueOf(UUID.randomUUID()));
		assetDisplayPageEntry.setGroupId(_group.getGroupId());
		assetDisplayPageEntry.setCompanyId(_group.getCompanyId());
		assetDisplayPageEntry.setClassNameId(classNameId);
		assetDisplayPageEntry.setClassPK(_dlFileEntry.getFileEntryId());
		assetDisplayPageEntry.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		assetDisplayPageEntry.setType(AssetDisplayPageConstants.TYPE_SPECIFIC);
		assetDisplayPageEntry.setPlid(layoutPageTemplateEntry.getPlid());

		_assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntry);
	}

	private LayoutPageTemplateEntry _addDisplayPageTemplate() throws Exception {
		return DisplayPageTemplateTestUtil.addDisplayPageTemplate(
			_group.getGroupId(), _fileEntryClassNameId, null, false,
			WorkflowConstants.STATUS_APPROVED);
	}

	private void _assertAssetDisplayPageEntry(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		Assert.assertNull(
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				_group.getGroupId(), _dlFileEntryClassNameId,
				_dlFileEntry.getFileEntryId()));

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				_group.getGroupId(), _fileEntryClassNameId,
				_dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			assetDisplayPageEntry.getLayoutPageTemplateEntryId());
		Assert.assertEquals(
			layoutPageTemplateEntry.getPlid(), assetDisplayPageEntry.getPlid());
		Assert.assertEquals(
			AssetDisplayPageConstants.TYPE_SPECIFIC,
			assetDisplayPageEntry.getType());
	}

	private void _upgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.asset.display.page.internal.upgrade.v2_3_2." +
				"AssetDisplayPageEntryUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	private DLFileEntry _dlFileEntry;
	private long _dlFileEntryClassNameId;
	private long _fileEntryClassNameId;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "(&(component.name=com.liferay.asset.display.page.internal.upgrade.registry.AssetDisplayPageServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}