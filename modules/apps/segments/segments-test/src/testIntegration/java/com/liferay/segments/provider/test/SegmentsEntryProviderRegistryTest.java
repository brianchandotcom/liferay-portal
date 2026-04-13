/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.context.Context;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class SegmentsEntryProviderRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetSegmentsEntryIds() throws Exception {
		SegmentsEntry segmentsEntry1 = _addSegmentsEntry(
			RandomTestUtil.randomString());

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry2 = _addSegmentsEntry(user.getFirstName());

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				_group.getGroupId(), User.class.getName(),
				TestPropsValues.getUserId(), new Context());

		Assert.assertEquals(
			Arrays.toString(segmentsEntryIds), 1, segmentsEntryIds.length);
		Assert.assertFalse(
			ArrayUtil.contains(
				segmentsEntryIds, segmentsEntry1.getSegmentsEntryId()));
		Assert.assertTrue(
			ArrayUtil.contains(
				segmentsEntryIds, segmentsEntry2.getSegmentsEntryId()));

		segmentsEntryIds = _segmentsEntryProviderRegistry.getSegmentsEntryIds(
			_group.getGroupId(), User.class.getName(),
			TestPropsValues.getUserId(), new Context());

		Assert.assertEquals(
			Arrays.toString(segmentsEntryIds), 1, segmentsEntryIds.length);
		Assert.assertFalse(
			ArrayUtil.contains(
				segmentsEntryIds, segmentsEntry1.getSegmentsEntryId()));
		Assert.assertTrue(
			ArrayUtil.contains(
				segmentsEntryIds, segmentsEntry2.getSegmentsEntryId()));
	}

	private SegmentsEntry _addSegmentsEntry(String firstName) throws Exception {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", firstName),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}