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

package com.liferay.segments.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class LayoutModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
	}

	@Test
	public void testDefaultSegmentsExperienceIsAddedWhenAddingNewLayoutWithTypeContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_CONTENT);

		SegmentsExperience defaultSegmentsExperience =
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

		Assert.assertNotNull(defaultSegmentsExperience);
	}

	@Test
	public void testDefaultSegmentsExperienceIsAddedWhenUpdatingNewLayoutWithTypeContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_PORTLET);

		SegmentsExperience defaultSegmentsExperience =
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

		Assert.assertNull(defaultSegmentsExperience);

		layout.setType(LayoutConstants.TYPE_CONTENT);

		_layoutLocalService.updateLayout(layout);

		defaultSegmentsExperience =
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

		Assert.assertNotNull(defaultSegmentsExperience);
	}

	@Test
	public void testDefaultSegmentsExperienceIsNotAddedWhenAddingNewLayoutWithTypeDifferentToContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_PORTLET);

		SegmentsExperience defaultSegmentsExperience =
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

		Assert.assertNull(defaultSegmentsExperience);
	}

	@Test
	public void testDefaultSegmentsExperienceIsNotAddedWhenUpdatingNewLayoutWithTypeDifferentToContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_PORTLET);

		SegmentsExperience defaultSegmentsExperience =
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

		Assert.assertNull(defaultSegmentsExperience);

		layout.setType(LayoutConstants.TYPE_CONTENT);

		_layoutLocalService.updateLayout(layout);

		defaultSegmentsExperience =
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

		Assert.assertNull(defaultSegmentsExperience);
	}

	private Layout _addLayout(long groupId, String type) throws Exception {
		String name = RandomTestUtil.randomString();

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		Layout layout = null;

		try {
			layout = _layoutLocalService.getFriendlyURLLayout(
				groupId, false, friendlyURL);

			return layout;
		}
		catch (NoSuchLayoutException nsle) {
		}

		String description = "This is a test page.";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null, description,
			type, false, friendlyURL, serviceContext);
	}

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}