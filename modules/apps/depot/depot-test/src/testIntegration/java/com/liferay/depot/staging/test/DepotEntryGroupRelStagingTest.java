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

package com.liferay.depot.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.test.util.DepotStagingTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia Garcia
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotEntryGroupRelStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_liveDepotEntry = _addDepotEntry();

		_liveGroup = GroupTestUtil.addGroup();
	}

	@Test
	public void testDepotEntryGroupRelAfterConnectingStagedDepotAndStagedSite()
		throws Exception {

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);
		_stagingGroup = DepotStagingTestUtil.enableLocalStaging(_liveGroup);

		DepotEntryGroupRel stagingDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_stagingDepotEntry.getDepotEntryId(),
				_stagingGroup.getGroupId());

		Assert.assertEquals(
			_stagingDepotEntry.getDepotEntryId(),
			stagingDepotEntryGroupRel.getDepotEntryId());

		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_stagingDepotEntry.getDepotEntryId(),
					_liveGroup.getGroupId()));
		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_liveDepotEntry.getDepotEntryId(),
					_stagingGroup.getGroupId()));
	}

	@Test
	public void testDepotEntryGroupRelAfterPublishingConnectedSite()
		throws Exception {

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);
		_stagingGroup = DepotStagingTestUtil.enableLocalStaging(_liveGroup);

		DepotEntryGroupRel stagingDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_stagingDepotEntry.getDepotEntryId(),
				_stagingGroup.getGroupId());

		DepotStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		DepotEntryGroupRel liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					stagingDepotEntryGroupRel.getUuid(),
					_liveGroup.getGroupId());

		stagingDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					stagingDepotEntryGroupRel.getUuid(),
					_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveDepotEntryGroupRel.isSearchable(),
			stagingDepotEntryGroupRel.isSearchable());
		Assert.assertEquals(
			liveDepotEntryGroupRel.isDdmStructuresAvailable(),
			stagingDepotEntryGroupRel.isDdmStructuresAvailable());

		Assert.assertEquals(
			_liveDepotEntry.getDepotEntryId(),
			liveDepotEntryGroupRel.getDepotEntryId());

		Assert.assertEquals(
			_liveGroup.getGroupId(), liveDepotEntryGroupRel.getToGroupId());

		Assert.assertEquals(
			_stagingDepotEntry.getDepotEntryId(),
			stagingDepotEntryGroupRel.getDepotEntryId());

		Assert.assertEquals(
			_stagingGroup.getGroupId(),
			stagingDepotEntryGroupRel.getToGroupId());

		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_stagingDepotEntry.getDepotEntryId(),
					_liveGroup.getGroupId()));
		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_liveDepotEntry.getDepotEntryId(),
					_stagingGroup.getGroupId()));
	}

	@Test
	public void testDepotEntryGroupRelAfterPublishingDeletedConnection()
		throws Exception {

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);
		_stagingGroup = DepotStagingTestUtil.enableLocalStaging(_liveGroup);

		DepotEntryGroupRel stagingDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_stagingDepotEntry.getDepotEntryId(),
				_stagingGroup.getGroupId());

		DepotStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		_depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			stagingDepotEntryGroupRel);

		DepotEntryGroupRel liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					stagingDepotEntryGroupRel.getUuid(),
					_liveGroup.getGroupId());

		Assert.assertNotNull(liveDepotEntryGroupRel);

		DepotStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByUuidAndGroupId(
					stagingDepotEntryGroupRel.getUuid(),
					_liveGroup.getGroupId());

		Assert.assertNull(liveDepotEntryGroupRel);
	}

	@Test
	public void testDepotEntryGroupRelAfterPublishingModifiedConnection()
		throws Exception {

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);
		_stagingGroup = DepotStagingTestUtil.enableLocalStaging(_liveGroup);

		DepotEntryGroupRel stagingDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_stagingDepotEntry.getDepotEntryId(),
				_stagingGroup.getGroupId());

		DepotStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		_depotEntryGroupRelLocalService.updateSearchable(
			stagingDepotEntryGroupRel.getDepotEntryGroupRelId(), false);

		DepotEntryGroupRel liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					stagingDepotEntryGroupRel.getUuid(),
					_liveGroup.getGroupId());

		Assert.assertEquals(true, liveDepotEntryGroupRel.isSearchable());

		DepotStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					stagingDepotEntryGroupRel.getUuid(),
					_liveGroup.getGroupId());

		Assert.assertEquals(false, liveDepotEntryGroupRel.isSearchable());
	}

	@Test
	public void testDepotEntryGroupRelAfterStagingDepot() throws Exception {
		DepotEntryGroupRel liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_liveDepotEntry.getDepotEntryId(), _liveGroup.getGroupId());

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);

		Assert.assertEquals(
			liveDepotEntryGroupRel,
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					liveDepotEntryGroupRel.getUuid(), _liveGroup.getGroupId()));

		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_stagingDepotEntry.getDepotEntryId(),
					_liveGroup.getGroupId()));
	}

	@Test
	public void testDepotEntryGroupRelAfterStagingDepotAndSite()
		throws Exception {

		DepotEntryGroupRel liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_liveDepotEntry.getDepotEntryId(), _liveGroup.getGroupId(),
				false);

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);

		_stagingGroup = DepotStagingTestUtil.enableLocalStaging(_liveGroup);

		liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					liveDepotEntryGroupRel.getUuid(), _liveGroup.getGroupId());

		DepotEntryGroupRel stagingDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					liveDepotEntryGroupRel.getUuid(),
					_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveDepotEntryGroupRel.isSearchable(),
			stagingDepotEntryGroupRel.isSearchable());
		Assert.assertEquals(
			liveDepotEntryGroupRel.isDdmStructuresAvailable(),
			stagingDepotEntryGroupRel.isDdmStructuresAvailable());

		Assert.assertEquals(
			_liveDepotEntry.getDepotEntryId(),
			liveDepotEntryGroupRel.getDepotEntryId());

		Assert.assertEquals(
			_liveGroup.getGroupId(), liveDepotEntryGroupRel.getToGroupId());

		Assert.assertEquals(
			_stagingDepotEntry.getDepotEntryId(),
			stagingDepotEntryGroupRel.getDepotEntryId());

		Assert.assertEquals(
			_stagingGroup.getGroupId(),
			stagingDepotEntryGroupRel.getToGroupId());

		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_stagingDepotEntry.getDepotEntryId(),
					_liveGroup.getGroupId()));
		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_liveDepotEntry.getDepotEntryId(),
					_stagingGroup.getGroupId()));
	}

	@Test
	public void testDepotEntryGroupRelAfterStagingSiteAndDepot()
		throws Exception {

		DepotEntryGroupRel liveDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				_liveDepotEntry.getDepotEntryId(), _liveGroup.getGroupId());

		_stagingGroup = DepotStagingTestUtil.enableLocalStaging(_liveGroup);

		_stagingDepotEntry = DepotStagingTestUtil.enableLocalStaging(
			_liveDepotEntry);

		Assert.assertEquals(
			liveDepotEntryGroupRel,
			_depotEntryGroupRelLocalService.
				getDepotEntryGroupRelByUuidAndGroupId(
					liveDepotEntryGroupRel.getUuid(), _liveGroup.getGroupId()));

		Assert.assertNull(
			_depotEntryGroupRelLocalService.
				fetchDepotEntryGroupRelByDepotEntryIdToGroupId(
					_stagingDepotEntry.getDepotEntryId(),
					_stagingGroup.getGroupId()));
	}

	private DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@DeleteAfterTestRun
	private DepotEntry _liveDepotEntry;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private DepotEntry _stagingDepotEntry;
	private Group _stagingGroup;

}