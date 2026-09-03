/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.site;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.staging.StagingGroupHelper;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class ExportImportSiteProviderImplTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_exportImportSiteProviderImpl = new ExportImportSiteProviderImpl();

		_groupLocalService = Mockito.mock(GroupLocalService.class);
		_groupService = Mockito.mock(GroupService.class);
		_portal = Mockito.mock(Portal.class);
		_stagingGroupHelper = Mockito.mock(StagingGroupHelper.class);

		ReflectionTestUtil.setFieldValue(
			_exportImportSiteProviderImpl, "_groupLocalService",
			_groupLocalService);
		ReflectionTestUtil.setFieldValue(
			_exportImportSiteProviderImpl, "_groupService", _groupService);
		ReflectionTestUtil.setFieldValue(
			_exportImportSiteProviderImpl, "_portal", _portal);
		ReflectionTestUtil.setFieldValue(
			_exportImportSiteProviderImpl, "_stagingGroupHelper",
			_stagingGroupHelper);

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
	public void testGetChildSiteCountLeavesOutUnsupportedChildSites() {
		Group group = _mockSite();

		Group childGroup = _mockSite();

		Group inactiveChildGroup = _mockSite();

		Mockito.when(
			inactiveChildGroup.isActive()
		).thenReturn(
			false
		);

		Mockito.when(
			_groupLocalService.getGroups(
				group.getCompanyId(), group.getGroupId(), true)
		).thenReturn(
			Arrays.asList(childGroup, inactiveChildGroup)
		);

		Assert.assertEquals(
			1, _exportImportSiteProviderImpl.getChildSiteCount(group));
	}

	@Test
	public void testGetChildSiteCountWhenGroupIsNull() {
		Assert.assertEquals(
			0, _exportImportSiteProviderImpl.getChildSiteCount(null));
	}

	@Test
	public void testGetDescriptiveNameWhenDescriptiveNameIsUnavailable()
		throws Exception {

		Group group = _mockSite();

		Mockito.when(
			group.getDescriptiveName(LocaleUtil.US)
		).thenThrow(
			new PortalException()
		);

		Mockito.when(
			group.getName(LocaleUtil.US)
		).thenReturn(
			"Guest"
		);

		Assert.assertEquals(
			"Guest",
			_exportImportSiteProviderImpl.getDescriptiveName(
				group, LocaleUtil.US));
	}

	@Test
	public void testGetPathWhenGroupIsGlobal() throws Exception {
		Group companyGroup = _mockGlobalSite();

		Assert.assertEquals(
			"Global",
			_exportImportSiteProviderImpl.getPath(companyGroup, LocaleUtil.US));
	}

	@Test
	public void testGetPathWhenGroupIsNull() {
		Assert.assertEquals(
			StringPool.BLANK,
			_exportImportSiteProviderImpl.getPath(null, LocaleUtil.US));
	}

	@Test
	public void testGetPathWhenGroupSitsBelowAnother() throws Exception {
		_mockGlobalSite();

		Group parentGroup = _mockNamedSite("EMEA");

		Group group = _mockNamedSite("News");

		Mockito.when(
			group.getAncestors()
		).thenReturn(
			ListUtil.fromArray(parentGroup)
		);

		Assert.assertEquals(
			"Global / EMEA / News",
			_exportImportSiteProviderImpl.getPath(group, LocaleUtil.US));
	}

	@Test
	public void testGetSupportedSitesLeavesOutUnsupportedSites()
		throws Exception {

		Group group = _mockNamedSite("EMEA");

		Group stagedGroup = _mockNamedSite("Support");

		Mockito.when(
			stagedGroup.isStaged()
		).thenReturn(
			true
		);

		_setUpSearch(group, stagedGroup);

		Assert.assertEquals(
			Arrays.asList(group),
			_exportImportSiteProviderImpl.getSupportedSites(
				_COMPANY_ID, null, null));
	}

	@Test
	public void testGetSupportedSitesWithComparator() throws Exception {
		Group group = _mockNamedSite("EMEA");

		Group otherGroup = _mockNamedSite("Support");

		_setUpSearch(group, otherGroup);

		Assert.assertEquals(
			Arrays.asList(otherGroup, group),
			_exportImportSiteProviderImpl.getSupportedSites(
				_COMPANY_ID, null,
				Comparator.comparingLong(
					Group::getGroupId
				).reversed()));
	}

	@Test
	public void testGetSupportedSitesWithSearch() throws Exception {
		Group group = _mockNamedSite("EMEA");

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
			_exportImportSiteProviderImpl.getSupportedSites(
				_COMPANY_ID, "EMEA", null));
	}

	@Test
	public void testIsSupportedWhenGroupIsCMS() {
		Group group = _mockSite();

		Mockito.when(
			group.isCMS()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsCompanyGroup() {
		Group group = _mockSite();

		Mockito.when(
			_stagingGroupHelper.isCompanyGroup(group)
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsDepot() {
		Group group = _mockSite();

		Mockito.when(
			group.isDepot()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsGlobal() {
		Group group = _mockSite();

		Mockito.when(
			group.getClassNameId()
		).thenReturn(
			_COMPANY_CLASS_NAME_ID
		);

		Mockito.when(
			group.isCompany()
		).thenReturn(
			true
		);

		Assert.assertTrue(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsInactive() {
		Group group = _mockSite();

		Mockito.when(
			group.isActive()
		).thenReturn(
			false
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsLayoutPrototype() {
		Group group = _mockSite();

		Mockito.when(
			group.isLayoutPrototype()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsLayoutSetPrototype() {
		Group group = _mockSite();

		Mockito.when(
			group.isLayoutSetPrototype()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsNotASite() {
		Group group = _mockSite();

		Mockito.when(
			group.isSite()
		).thenReturn(
			false
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsNull() {
		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(null));
	}

	@Test
	public void testIsSupportedWhenGroupIsOrganizationSite() {
		Group group = _mockSite();

		Mockito.when(
			group.getClassNameId()
		).thenReturn(
			_ORGANIZATION_CLASS_NAME_ID
		);

		Mockito.when(
			group.isOrganization()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsSite() {
		Assert.assertTrue(
			_exportImportSiteProviderImpl.isSupported(_mockSite()));
	}

	@Test
	public void testIsSupportedWhenGroupIsStaged() {
		Group group = _mockSite();

		Mockito.when(
			group.isStaged()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	@Test
	public void testIsSupportedWhenGroupIsStagingGroup() {
		Group group = _mockSite();

		Mockito.when(
			group.isStagingGroup()
		).thenReturn(
			true
		);

		Assert.assertFalse(_exportImportSiteProviderImpl.isSupported(group));
	}

	private Group _mockGlobalSite() throws Exception {
		Group companyGroup = _mockNamedSite("Global");

		Mockito.when(
			companyGroup.isCompany()
		).thenReturn(
			true
		);

		Mockito.when(
			_groupLocalService.fetchCompanyGroup(companyGroup.getCompanyId())
		).thenReturn(
			companyGroup
		);

		return companyGroup;
	}

	private Group _mockNamedSite(String descriptiveName) throws Exception {
		Group group = _mockSite();

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

	private Group _mockSite() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getClassNameId()
		).thenReturn(
			_GROUP_CLASS_NAME_ID
		);

		Mockito.when(
			group.isActive()
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

	private void _setUpSearch(Group... groups) throws Exception {
		Mockito.when(
			_groupService.search(
				Mockito.anyLong(), Mockito.any(long[].class), Mockito.isNull(),
				Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.isNull())
		).thenReturn(
			ListUtil.fromArray(groups)
		);
	}

	private static final long _COMPANY_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	private static final long _ORGANIZATION_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	private ExportImportSiteProviderImpl _exportImportSiteProviderImpl;
	private long _groupId = 1;
	private GroupLocalService _groupLocalService;
	private GroupService _groupService;
	private Portal _portal;
	private StagingGroupHelper _stagingGroupHelper;

}