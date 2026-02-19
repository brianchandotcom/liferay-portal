/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentStagingTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_liveGroup = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_liveGroup);
	}

	@Test
	public void testFragmentEntryLinkCopiedWhenLocalStagingActivated()
		throws Exception {

		FragmentEntryLink liveFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				null, _layout,
				SegmentsExperienceLocalServiceUtil.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()));

		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
			liveFragmentEntryLink.getUuid(), _stagingGroup.getGroupId());
	}

	@Test
	public void testPublishFragmentEntryDeletionWithPreviousFragmentEntryName()
		throws PortalException {

		FragmentCollection liveFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_liveGroup.getGroupId());

		FragmentEntry liveFragmentEntry =
			FragmentEntryTestUtil.addFragmentEntry(
				liveFragmentCollection.getFragmentCollectionId());

		FragmentEntryLink liveFragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_liveGroup.getGroupId(), liveFragmentEntry.getFragmentEntryId(),
				_layout.getPlid());

		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLinks(
			_stagingGroup.getGroupId());

		FragmentEntry stagingFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByUuidAndGroupId(
				liveFragmentEntry.getUuid(), _stagingGroup.getGroupId());

		_fragmentEntryLocalService.deleteFragmentEntry(stagingFragmentEntry);

		FragmentCollection stagingFragmentCollection =
			FragmentCollectionLocalServiceUtil.
				getFragmentCollectionByUuidAndGroupId(
					liveFragmentCollection.getUuid(),
					_stagingGroup.getGroupId());

		FragmentEntry newStagingFragmentEntry =
			FragmentEntryTestUtil.addFragmentEntry(
				stagingFragmentCollection.getFragmentCollectionId(),
				liveFragmentEntry.getName());

		Layout stagingLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			_layout.getUuid(), _stagingGroup.getGroupId(), false);

		FragmentTestUtil.addFragmentEntryLink(
			_stagingGroup.getGroupId(),
			newStagingFragmentEntry.getFragmentEntryId(),
			stagingLayout.getPlid());

		FragmentStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				liveFragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(liveFragmentEntryLink);

		liveFragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
			liveFragmentEntry.getFragmentEntryId());

		Assert.assertNull(liveFragmentEntry);
	}

	@Test
	public void testPublishFragmentEntryLink() throws Exception {
		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		Layout stagingLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			_layout.getUuid(), _stagingGroup.getGroupId(), false);

		Layout draftStagingLayout = stagingLayout.fetchDraftLayout();

		FragmentEntryLink stagingFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				null, draftStagingLayout,
				SegmentsExperienceLocalServiceUtil.
					fetchDefaultSegmentsExperienceId(
						draftStagingLayout.getPlid()));

		ContentLayoutTestUtil.publishLayout(draftStagingLayout, stagingLayout);

		FragmentStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
			stagingFragmentEntryLink.getUuid(), _liveGroup.getGroupId());
	}

	@Test
	public void testValidateFragmentEntryAfterDeactivateStaging()
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_liveGroup.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		FragmentEntryLink liveFragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_liveGroup.getGroupId(), fragmentEntry.getFragmentEntryId(),
				_layout.getPlid());

		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		FragmentStagingTestUtil.publishLayoutsRangeFromLastPublishedDate(
			_stagingGroup, _liveGroup);

		liveFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				liveFragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		Long groupId1 = ScopeUtil.getItemGroupId(
			liveFragmentEntryLink.getCompanyId(),
			liveFragmentEntryLink.getFragmentEntryScopeERC(),
			liveFragmentEntryLink.getGroupId());

		FragmentEntry liveFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				liveFragmentEntryLink.getFragmentEntryERC(), groupId1);

		Assert.assertEquals(
			liveFragmentEntryLink.getGroupId(), liveFragmentEntry.getGroupId());

		StagingLocalServiceUtil.disableStaging(
			_liveGroup, new ServiceContext());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				liveFragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		Assert.assertNotNull(
			_fragmentEntryLocalService.getFragmentEntryByExternalReferenceCode(
				fragmentEntryLink.getFragmentEntryERC(),
				ScopeUtil.getItemGroupId(
					fragmentEntryLink.getCompanyId(),
					fragmentEntryLink.getFragmentEntryScopeERC(),
					fragmentEntryLink.getGroupId())));
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	private Layout _layout;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Group _stagingGroup;

}