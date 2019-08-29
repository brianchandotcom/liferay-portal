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

package com.liferay.fragment.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentStagingTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_liveGroup.getGroupId(), TestPropsValues.getUserId());

		_layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _liveGroup.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	@Test
	public void testFragmentEntryLinkCopiedWhenLocalStagingActivated()
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_liveGroup.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		FragmentEntryLink liveFragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_liveGroup.getGroupId(), fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid());

		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		FragmentEntryLink stagingFragmentEntryLink =
			_fragmentEntryLinkLocalService.
				fetchFragmentEntryLinkByUuidAndGroupId(
					liveFragmentEntryLink.getUuid(),
					_stagingGroup.getGroupId());

		Assert.assertNotNull(stagingFragmentEntryLink);
	}

	@Test
	public void testPublishFragmentEntryLink() throws PortalException {
		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_stagingGroup.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		Layout stagingLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			_layout.getUuid(), _stagingGroup.getGroupId(), false);

		FragmentEntryLink stagingFragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_stagingGroup.getGroupId(), fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class),
				stagingLayout.getPlid());

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
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid());

		_stagingGroup = FragmentStagingTestUtil.enableLocalStaging(_liveGroup);

		FragmentStagingTestUtil.publishLayoutsRangeFromLastPublishedDate(
			_stagingGroup, _liveGroup);

		liveFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				liveFragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		FragmentEntry liveFragmentEntry =
			_fragmentEntryLocalService.getFragmentEntry(
				liveFragmentEntryLink.getFragmentEntryId());

		Assert.assertEquals(
			liveFragmentEntryLink.getGroupId(), liveFragmentEntry.getGroupId());

		StagingLocalServiceUtil.disableStaging(
			_liveGroup, new ServiceContext());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				liveFragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		Assert.assertNotNull(
			_fragmentEntryLocalService.getFragmentEntry(
				fragmentEntryLink.getFragmentEntryId()));
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