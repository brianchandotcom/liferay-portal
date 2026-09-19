/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.group;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.comparator.GroupDescriptiveNameComparator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class ExportImportGroupHelperImplTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_exportImportGroupHelperImpl = new ExportImportGroupHelperImpl();

		_groupService = Mockito.mock(GroupService.class);
		_portal = Mockito.mock(Portal.class);

		ReflectionTestUtil.setFieldValue(
			_exportImportGroupHelperImpl, "_groupService", _groupService);
		ReflectionTestUtil.setFieldValue(
			_exportImportGroupHelperImpl, "_portal", _portal);

		Mockito.when(
			_portal.getClassNameId(Company.class.getName())
		).thenReturn(
			_COMPANY_CLASS_NAME_ID
		);

		Mockito.when(
			_portal.getClassNameId(Group.class.getName())
		).thenReturn(
			_GROUP_CLASS_NAME_ID
		);
	}

	@Test
	public void testGetChildGroupCountLeavesOutUnsupportedChildGroups()
		throws Exception {

		Group group = _mockGroup();

		Group childGroup = _mockGroup();

		Group inactiveChildGroup = _mockGroup();

		Mockito.when(
			inactiveChildGroup.isActive()
		).thenReturn(
			false
		);

		Mockito.when(
			_groupService.getGroups(
				group.getCompanyId(), group.getGroupId(), true)
		).thenReturn(
			Arrays.asList(childGroup, inactiveChildGroup)
		);

		Assert.assertEquals(
			1, _exportImportGroupHelperImpl.getChildGroupCount(group));
	}

	@Test
	public void testGetGroupPathWhenGroupIsGlobal() throws Exception {
		Group group = _mockNamedGroup("Global");

		Mockito.when(
			group.isCompany()
		).thenReturn(
			true
		);

		Assert.assertEquals(
			"Global",
			_exportImportGroupHelperImpl.getGroupPath(group, LocaleUtil.US));
	}

	@Test
	public void testGetGroupPathWhenGroupIsRoot() throws Exception {
		Assert.assertEquals(
			"EMEA",
			_exportImportGroupHelperImpl.getGroupPath(
				_mockNamedGroup("EMEA"), LocaleUtil.US));
	}

	@Test
	public void testGetGroupPathWhenGroupSitsBelowAnother() throws Exception {
		Group parentGroup = _mockNamedGroup("EMEA");

		Group group = _mockNamedGroup("News");

		Mockito.when(
			group.getAncestors()
		).thenReturn(
			ListUtil.fromArray(parentGroup)
		);

		Assert.assertEquals(
			"EMEA / News",
			_exportImportGroupHelperImpl.getGroupPath(group, LocaleUtil.US));
	}

	@Test
	public void testGetGroupPathWhenGroupSitsBelowSeveral() throws Exception {
		Group parentGroup = _mockNamedGroup("EMEA");

		Group grandparentGroup = _mockNamedGroup("Europe");

		Group group = _mockNamedGroup("News");

		Mockito.when(
			group.getAncestors()
		).thenReturn(
			ListUtil.fromArray(parentGroup, grandparentGroup)
		);

		Assert.assertEquals(
			"Europe / EMEA / News",
			_exportImportGroupHelperImpl.getGroupPath(group, LocaleUtil.US));
	}

	@Test
	public void testGetSupportedGroupsLeavesOutUnsupportedGroups()
		throws Exception {

		Group group = _mockNamedGroup("EMEA");

		Group stagedGroup = _mockNamedGroup("Support");

		Mockito.when(
			stagedGroup.isStaged()
		).thenReturn(
			true
		);

		_setUpSearch(group, stagedGroup);

		Assert.assertEquals(
			Arrays.asList(group),
			_exportImportGroupHelperImpl.getSupportedGroups(
				_COMPANY_ID, null, null));
	}

	@Test
	public void testGetSupportedGroupsRestrictsTheQueryToSites()
		throws Exception {

		_setUpSearch(_mockNamedGroup("EMEA"));

		_exportImportGroupHelperImpl.getSupportedGroups(
			_COMPANY_ID, null, null);

		ArgumentCaptor<long[]> classNameIdsArgumentCaptor =
			ArgumentCaptor.captor();
		ArgumentCaptor<LinkedHashMap<String, Object>> paramsArgumentCaptor =
			ArgumentCaptor.captor();

		Mockito.verify(
			_groupService
		).search(
			Mockito.anyLong(), classNameIdsArgumentCaptor.capture(),
			Mockito.isNull(), paramsArgumentCaptor.capture(), Mockito.anyInt(),
			Mockito.anyInt(), Mockito.isNull()
		);

		Assert.assertArrayEquals(
			new long[] {_COMPANY_CLASS_NAME_ID, _GROUP_CLASS_NAME_ID},
			classNameIdsArgumentCaptor.getValue());

		LinkedHashMap<String, Object> params = paramsArgumentCaptor.getValue();

		Assert.assertEquals(Boolean.TRUE, params.get("active"));
		Assert.assertEquals(Boolean.TRUE, params.get("site"));
	}

	@Test
	public void testGetSupportedGroupsWithOrderByComparator() throws Exception {
		Group group = _mockNamedGroup("EMEA");

		Group otherGroup = _mockNamedGroup("Support");

		_setUpSearch(group, otherGroup);

		OrderByComparator<Group> orderByComparator =
			new GroupDescriptiveNameComparator(false, LocaleUtil.US);

		Assert.assertEquals(
			Arrays.asList(group, otherGroup),
			_exportImportGroupHelperImpl.getSupportedGroups(
				_COMPANY_ID, null, orderByComparator));

		Mockito.verify(
			_groupService
		).search(
			Mockito.anyLong(), Mockito.any(long[].class), Mockito.isNull(),
			Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.same(orderByComparator)
		);
	}

	@Test
	public void testGetSupportedGroupsWithSearch() throws Exception {
		Group group = _mockNamedGroup("EMEA");

		Mockito.when(
			_groupService.search(
				Mockito.anyLong(), Mockito.any(long[].class),
				Mockito.eq("EMEA"), Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.isNull())
		).thenReturn(
			ListUtil.fromArray(group)
		);

		Assert.assertEquals(
			Arrays.asList(group),
			_exportImportGroupHelperImpl.getSupportedGroups(
				_COMPANY_ID, "EMEA", null));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsCMS() {
		Group group = _mockGroup();

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsCompanyGroup() {
		Group group = _mockGroup();

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			false
		);

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsDepot() {
		Group group = _mockGroup();

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			false
		);

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsGlobal() {
		Group group = _mockGroup();

		Mockito.when(
			group.isCompany()
		).thenReturn(
			true
		);

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			false
		);

		Assert.assertTrue(_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsInactive() {
		Group group = _mockGroup();

		Mockito.when(
			group.isActive()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsLayoutPrototype() {
		Group group = _mockGroup();

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsLayoutSetPrototype() {
		Group group = _mockGroup();

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsNotASite() {
		Group group = _mockGroup();

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsOrganizationSite() {
		Group group = _mockGroup();

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsSite() {
		Assert.assertTrue(
			_exportImportGroupHelperImpl.isGroupSupported(_mockGroup()));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsStaged() {
		Group group = _mockGroup();

		Mockito.when(
			group.isStaged()
		).thenReturn(
			true
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	@Test
	public void testIsGroupSupportedWhenGroupIsStagingGroup() {
		Group group = _mockGroup();

		Mockito.when(
			group.isStagingGroup()
		).thenReturn(
			true
		);

		Assert.assertFalse(
			_exportImportGroupHelperImpl.isGroupSupported(group));
	}

	private Group _mockGroup() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isActive()
		).thenReturn(
			true
		);

		Mockito.when(
			group.isRegularSite()
		).thenReturn(
			true
		);

		Mockito.when(
			group.isSite()
		).thenReturn(
			true
		);

		return group;
	}

	private Group _mockNamedGroup(String descriptiveName) throws Exception {
		Group group = _mockGroup();

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			_groupId++
		);

		Mockito.when(
			group.getDescriptiveName(LocaleUtil.US)
		).thenReturn(
			descriptiveName
		);

		return group;
	}

	private void _setUpSearch(Group... groups) throws Exception {
		Mockito.when(
			_groupService.search(
				Mockito.anyLong(), Mockito.any(long[].class), Mockito.isNull(),
				Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any())
		).thenReturn(
			ListUtil.fromArray(groups)
		);
	}

	private static final long _COMPANY_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	private ExportImportGroupHelperImpl _exportImportGroupHelperImpl;
	private long _groupId = 1;
	private GroupService _groupService;
	private Portal _portal;

}