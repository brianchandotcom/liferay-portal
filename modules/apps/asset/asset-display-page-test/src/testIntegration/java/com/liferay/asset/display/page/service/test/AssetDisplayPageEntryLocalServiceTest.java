/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.display.page.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.asset.display.page.test.util.AssetDisplayPageEntryTestUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetDisplayPageEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.asset.display.page.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_classNameId = _portal.getClassNameId(AssetEntry.class.getName());
	}

	@Test
	public void testAddAssetDisplayPageEntry() throws PortalException {
		long classPK = RandomTestUtil.randomLong();

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK, 0);

		Assert.assertNotNull(assetDisplayPageEntry);

		Assert.assertEquals(
			AssetDisplayPageConstants.TYPE_DEFAULT,
			assetDisplayPageEntry.getType());
	}

	@Test
	public void testAddAssetDisplayPageEntryUpdatesLayoutPageTemplate()
		throws PortalException {

		_layoutPageTemplateEntry = _getLayoutPageTemplateEntry();

		Date originalModifiedDate = _layoutPageTemplateEntry.getModifiedDate();

		long classPK = RandomTestUtil.randomLong();

		AssetDisplayPageEntryTestUtil.addAssetDisplayPageEntry(
			_group.getGroupId(), _classNameId, classPK,
			_layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertFalse(
			DateUtil.equals(
				originalModifiedDate,
				layoutPageTemplateEntry.getModifiedDate()));
	}

	@Test
	public void testAddAssetDisplayPageEntryWithType() throws PortalException {
		long classPK = RandomTestUtil.randomLong();

		long layoutPageTemplateEntryId = RandomTestUtil.randomLong();

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryTestUtil.addAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK,
				layoutPageTemplateEntryId,
				AssetDisplayPageConstants.TYPE_SPECIFIC);

		AssetDisplayPageEntry persistedAssetDisplayPageEntry =
			_assetDisplayPageEntryPersistence.findByPrimaryKey(
				assetDisplayPageEntry.getAssetDisplayPageEntryId());

		Assert.assertEquals(
			_classNameId, persistedAssetDisplayPageEntry.getClassNameId());

		Assert.assertEquals(
			classPK, persistedAssetDisplayPageEntry.getClassPK());

		Assert.assertEquals(
			layoutPageTemplateEntryId,
			persistedAssetDisplayPageEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			AssetDisplayPageConstants.TYPE_SPECIFIC,
			persistedAssetDisplayPageEntry.getType());
	}

	@Test
	public void testDeleteAssetDisplayPageEntry() throws PortalException {
		long classPK = RandomTestUtil.randomLong();

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK, 0);

		_assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(
			assetDisplayPageEntry.getAssetDisplayPageEntryId());

		Assert.assertNull(
			_assetDisplayPageEntryPersistence.fetchByPrimaryKey(
				assetDisplayPageEntry.getAssetDisplayPageEntryId()));
	}

	@Test
	public void testFetchAssetDisplayPageEntry() throws PortalException {
		long classPK = RandomTestUtil.randomLong();

		long layoutPageTemplateEntryId = RandomTestUtil.randomLong();

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK,
				layoutPageTemplateEntryId);

		AssetDisplayPageEntry persistedAssetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK);

		Assert.assertEquals(
			_classNameId, persistedAssetDisplayPageEntry.getClassNameId());

		Assert.assertEquals(
			assetDisplayPageEntry.getClassPK(),
			persistedAssetDisplayPageEntry.getClassPK());

		Assert.assertEquals(
			layoutPageTemplateEntryId,
			persistedAssetDisplayPageEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			assetDisplayPageEntry.getType(),
			persistedAssetDisplayPageEntry.getType());
	}

	@Test
	public void testGetAssetDisplayPageEntriesByLayoutPageTemplateEntryId()
		throws PortalException {

		long layoutPageTemplateEntryId = RandomTestUtil.randomLong();

		List<AssetDisplayPageEntry> originalAssetDisplayPageEntries =
			_assetDisplayPageEntryLocalService.
				getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
					layoutPageTemplateEntryId);

		AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
			_group.getGroupId(), _classNameId, RandomTestUtil.randomLong(),
			layoutPageTemplateEntryId);

		AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
			_group.getGroupId(), _classNameId, RandomTestUtil.randomLong(),
			layoutPageTemplateEntryId);

		List<AssetDisplayPageEntry> actualAssetDisplayPageEntries =
			_assetDisplayPageEntryLocalService.
				getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
					layoutPageTemplateEntryId);

		Assert.assertEquals(
			actualAssetDisplayPageEntries.toString(),
			originalAssetDisplayPageEntries.size() + 2,
			actualAssetDisplayPageEntries.size());
	}

	@Test
	public void testGetAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId()
		throws PortalException {

		long layoutPageTemplateEntryId = RandomTestUtil.randomLong();

		int originalAssetDisplayPageEntriesCount =
			_assetDisplayPageEntryLocalService.
				getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
					layoutPageTemplateEntryId);

		AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
			_group.getGroupId(), _classNameId, RandomTestUtil.randomLong(),
			layoutPageTemplateEntryId);

		AssetDisplayPageEntryTestUtil.addDefaultAssetDisplayPageEntry(
			_group.getGroupId(), _classNameId, RandomTestUtil.randomLong(),
			layoutPageTemplateEntryId);

		int actualAssetDisplayPageEntriesCount =
			_assetDisplayPageEntryLocalService.
				getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
					layoutPageTemplateEntryId);

		Assert.assertEquals(
			originalAssetDisplayPageEntriesCount + 2,
			actualAssetDisplayPageEntriesCount);
	}

	@Test
	public void testUpdateAssetDisplayPageEntry() throws PortalException {
		long classPK = RandomTestUtil.randomLong();

		long layoutPageTemplateEntryId = 0;

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryTestUtil.addAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK,
				layoutPageTemplateEntryId,
				AssetDisplayPageConstants.TYPE_DEFAULT);

		layoutPageTemplateEntryId = RandomTestUtil.randomLong();

		_assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntry.getAssetDisplayPageEntryId(),
			layoutPageTemplateEntryId, AssetDisplayPageConstants.TYPE_SPECIFIC);

		AssetDisplayPageEntry persistedAssetDisplayPageEntry =
			_assetDisplayPageEntryPersistence.findByPrimaryKey(
				assetDisplayPageEntry.getAssetDisplayPageEntryId());

		Assert.assertEquals(
			layoutPageTemplateEntryId,
			persistedAssetDisplayPageEntry.getLayoutPageTemplateEntryId());

		Assert.assertEquals(
			AssetDisplayPageConstants.TYPE_SPECIFIC,
			persistedAssetDisplayPageEntry.getType());
	}

	@Test
	public void testUpdateAssetDisplayPageEntryUpdatesLayoutPageTemplate()
		throws PortalException {

		_layoutPageTemplateEntry = _getLayoutPageTemplateEntry();

		long classPK = RandomTestUtil.randomLong();

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryTestUtil.addAssetDisplayPageEntry(
				_group.getGroupId(), _classNameId, classPK,
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				AssetDisplayPageConstants.TYPE_DEFAULT);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Date originalModifiedDate = layoutPageTemplateEntry.getModifiedDate();

		_assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntry.getAssetDisplayPageEntryId(),
			_layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC);

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		Assert.assertFalse(
			DateUtil.equals(
				originalModifiedDate,
				layoutPageTemplateEntry.getModifiedDate()));
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry()
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0, 0, 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED, new ServiceContext());

		layoutPageTemplateEntry.setModifiedDate(null);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetDisplayPageEntryPersistence _assetDisplayPageEntryPersistence;

	private long _classNameId;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}