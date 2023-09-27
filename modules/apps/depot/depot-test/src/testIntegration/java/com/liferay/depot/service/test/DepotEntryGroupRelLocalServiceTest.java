/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.exception.NoSuchEntryGroupRelException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DepotEntryGroupRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddDepotEntryGroupRel() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		Assert.assertNotNull(depotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			depotEntry.getDepotEntryId(), depotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			_group.getGroupId(), depotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testAddDepotEntryGroupRelWithARepeatedDepotEntryGroupRel()
		throws Exception {

		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel originalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		DepotEntryGroupRel finalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryGroupRelId(),
			finalDepotEntryGroupRel.getDepotEntryGroupRelId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryId(),
			finalDepotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getToGroupId(),
			finalDepotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testDeleteDepotEntryGroupRel() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		_depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			depotEntryGroupRel.getDepotEntryGroupRelId());

		try {
			_depotEntryGroupRelLocalService.getDepotEntryGroupRel(
				depotEntryGroupRel.getDepotEntryGroupRelId());

			Assert.fail();
		}
		catch (NoSuchEntryGroupRelException noSuchEntryGroupRelException) {
		}
	}

	@Test
	public void testDeleteGroup() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		Group group = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			null, 0, 0,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, true, true,
			ServiceContextTestUtil.getServiceContext());

		int depotEntryGroupRelsCount =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), group.getGroupId());

		Assert.assertEquals(
			depotEntryGroupRelsCount + 1,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount());

		_groupLocalService.deleteGroup(group.getGroupId());

		Assert.assertEquals(
			depotEntryGroupRelsCount,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount());
	}

	@Test
	public void testGetDepotEntryGroupRel() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel originalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		DepotEntryGroupRel finalDepotEntryGroupRel =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRel(
				originalDepotEntryGroupRel.getDepotEntryGroupRelId());

		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryGroupRelId(),
			finalDepotEntryGroupRel.getDepotEntryGroupRelId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getDepotEntryId(),
			finalDepotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			originalDepotEntryGroupRel.getToGroupId(),
			finalDepotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testGetDepotEntryGroupRels() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), _group.getGroupId());

		List<DepotEntryGroupRel> depotEntryGroupRels =
			_depotEntryGroupRelLocalService.getDepotEntryGroupRels(
				_group.getGroupId(), 0, 20);

		Assert.assertEquals(
			depotEntryGroupRels.toString(), depotEntryGroupRels.size(), 1);

		DepotEntryGroupRel depotEntryGroupRel = depotEntryGroupRels.get(0);

		Assert.assertEquals(
			depotEntry.getDepotEntryId(), depotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			_group.getGroupId(), depotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testGetDepotEntryGroupRelsCount() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry1.getDepotEntryId(), _group.getGroupId());

		DepotEntry depotEntry2 = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry2.getDepotEntryId(), _group.getGroupId());

		Assert.assertEquals(
			2,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount(
				_group.getGroupId()));
		Assert.assertEquals(
			0,
			_depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount(
				RandomTestUtil.randomInt()));
	}

	@Test
	public void testGetSearchableDepotEntryGroupRels() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), _group.getGroupId());

		int searchableDepotEntryGroupRelsCount =
			_depotEntryGroupRelLocalService.
				getSearchableDepotEntryGroupRelsCount(_group.getGroupId());

		Assert.assertEquals(1, searchableDepotEntryGroupRelsCount);

		List<DepotEntryGroupRel> searchableDepotEntryGroupRels =
			_depotEntryGroupRelLocalService.getSearchableDepotEntryGroupRels(
				_group.getGroupId(), 0, searchableDepotEntryGroupRelsCount);

		Assert.assertEquals(
			searchableDepotEntryGroupRels.toString(), 1,
			searchableDepotEntryGroupRels.size());
		Assert.assertEquals(
			depotEntryGroupRel, searchableDepotEntryGroupRels.get(0));
	}

	@Test
	public void testGetSearchableDepotEntryGroupRelsWithAnUnsearchableDepotEntryGroupRel()
		throws Exception {

		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), _group.getGroupId(), false);

		int searchableDepotEntryGroupRelsCount =
			_depotEntryGroupRelLocalService.
				getSearchableDepotEntryGroupRelsCount(_group.getGroupId());

		Assert.assertEquals(0, searchableDepotEntryGroupRelsCount);

		List<DepotEntryGroupRel> searchableDepotEntryGroupRels =
			_depotEntryGroupRelLocalService.getSearchableDepotEntryGroupRels(
				_group.getGroupId(), 0, searchableDepotEntryGroupRelsCount);

		Assert.assertTrue(
			searchableDepotEntryGroupRels.toString(),
			searchableDepotEntryGroupRels.isEmpty());
	}

	@Test
	public void testUpdateSearchable() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry();

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry1.getDepotEntryId(), _group.getGroupId());

		Assert.assertTrue(depotEntryGroupRel.isSearchable());

		depotEntryGroupRel = _depotEntryGroupRelLocalService.updateSearchable(
			depotEntryGroupRel.getDepotEntryGroupRelId(), false);

		Assert.assertFalse(depotEntryGroupRel.isSearchable());

		depotEntryGroupRel = _depotEntryGroupRelLocalService.updateSearchable(
			depotEntryGroupRel.getDepotEntryGroupRelId(), true);

		Assert.assertTrue(depotEntryGroupRel.isSearchable());
	}

	private DepotEntry _addDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}