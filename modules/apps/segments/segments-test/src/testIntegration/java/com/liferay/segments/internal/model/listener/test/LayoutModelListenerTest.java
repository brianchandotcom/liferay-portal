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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

		Assert.assertTrue(_hasDefaultSegmentsExperienceLayout(layout));
	}

	@Test
	public void testDefaultSegmentsExperienceIsAddedWhenUpdatingNewLayoutWithTypeContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_PORTLET);

		Assert.assertFalse(_hasDefaultSegmentsExperienceLayout(layout));

		layout.setType(LayoutConstants.TYPE_CONTENT);

		_layoutLocalService.updateLayout(layout);

		Assert.assertTrue(_hasDefaultSegmentsExperienceLayout(layout));
	}

	@Test
	public void testDefaultSegmentsExperienceIsNotAddedWhenAddingNewDraftLayout()
		throws Exception {

		Layout draftLayout = LayoutTestUtil.addLayout(_group);

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_CONTENT, _classNameId,
			draftLayout.getPlid());

		Assert.assertFalse(_hasDefaultSegmentsExperienceLayout(layout));
	}

	@Test
	public void testDefaultSegmentsExperienceIsNotAddedWhenAddingNewLayoutWithTypeDifferentToContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_PORTLET);

		Assert.assertFalse(_hasDefaultSegmentsExperienceLayout(layout));
	}

	@Test
	public void testDefaultSegmentsExperienceIsNotAddedWhenUpdatingNewLayoutWithTypeDifferentToContent()
		throws Exception {

		Layout layout = _addLayout(
			_group.getGroupId(), LayoutConstants.TYPE_PORTLET);

		Assert.assertFalse(_hasDefaultSegmentsExperienceLayout(layout));

		layout.setType(LayoutConstants.TYPE_PORTLET);

		_layoutLocalService.updateLayout(layout);

		Assert.assertFalse(_hasDefaultSegmentsExperienceLayout(layout));
	}

	private Layout _addLayout(long groupId, String type) throws Exception {
		return _addLayout(groupId, type, 0, 0);
	}

	private Layout _addLayout(
			long groupId, String type, long classNameId, long classPK)
		throws Exception {

		String friendlyURL =
			StringPool.SLASH +
				FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString());

		try {
			Layout layout = _layoutLocalService.getFriendlyURLLayout(
				groupId, false, friendlyURL);

			return layout;
		}
		catch (NoSuchLayoutException nsle) {
		}

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.getSiteDefault(), friendlyURL);

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, classNameId, classPK,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), type, null, false, false,
			friendlyURLMap, ServiceContextTestUtil.getServiceContext());
	}

	private boolean _hasDefaultSegmentsExperienceLayout(Layout layout) {
		try {
			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, layout.getPlid());

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
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