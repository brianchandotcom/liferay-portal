/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.context.Context;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
@Sync
public class ReferredSegmentsEntryProviderTest {

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
	public void testGetSegmentsEntryClassPKsWithAllReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') and (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		Assert.assertEquals(
			0,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntry3.getSegmentsEntryId()));
	}

	@Test
	public void testGetSegmentsEntryClassPKsWithAnyReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		Assert.assertEquals(
			2,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntry3.getSegmentsEntryId()));
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {_user1.getUserId(), _user2.getUserId()},
				_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
					segmentsEntry3.getSegmentsEntryId(), 0, 2)));
	}

	@Test
	public void testGetSegmentsEntryClassPKsWithCriteriaAndReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria,
			String.format("(firstName eq '%s')", _user1.getFirstName()),
			Criteria.Conjunction.AND);

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		Assert.assertEquals(
			1,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntry3.getSegmentsEntryId()));
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {_user1.getUserId()},
				_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
					segmentsEntry3.getSegmentsEntryId(), 0, 1)));
	}

	@Test
	public void testGetSegmentsEntryClassPKsWithCriteriaOrReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria,
			String.format("(firstName eq '%s')", _user1.getFirstName()),
			Criteria.Conjunction.OR);

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.OR);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		Assert.assertEquals(
			2,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntry3.getSegmentsEntryId()));
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {_user1.getUserId(), _user2.getUserId()},
				_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
					segmentsEntry3.getSegmentsEntryId(), 0, 2)));
	}

	@Test
	public void testGetSegmentsEntryClassPKsWithReferredSegmentsAfterDeletingSegment()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		_segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry1);

		Assert.assertEquals(
			1,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntry3.getSegmentsEntryId()));
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {_user2.getUserId()},
				_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
					segmentsEntry3.getSegmentsEntryId(), 0, 1)));
	}

	@Test
	public void testGetSegmentsEntryIdsWithAllReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') and (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				_group.getGroupId(), User.class.getName(), _user1.getUserId(),
				new Context());

		Assert.assertEquals(
			StringUtil.merge(segmentsEntryIds, StringPool.COMMA), 1,
			segmentsEntryIds.length);
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {segmentsEntry1.getSegmentsEntryId()},
				segmentsEntryIds));
	}

	@Test
	public void testGetSegmentsEntryIdsWithAnyReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				_group.getGroupId(), User.class.getName(), _user1.getUserId(),
				new Context());

		Assert.assertEquals(
			StringUtil.merge(segmentsEntryIds, StringPool.COMMA), 2,
			segmentsEntryIds.length);
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {
					segmentsEntry1.getSegmentsEntryId(),
					segmentsEntry3.getSegmentsEntryId()
				},
				segmentsEntryIds));
	}

	@Test
	public void testGetSegmentsEntryIdsWithCriteriaAndReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_contextSegmentsCriteriaContributor.contribute(
			criteria, "(languageId eq 'en')", Criteria.Conjunction.AND);

		_userSegmentsCriteriaContributor.contribute(
			criteria,
			String.format("(firstName eq '%s')", _user1.getFirstName()),
			Criteria.Conjunction.AND);

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.AND);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		Context context = new Context();

		context.put(Context.LANGUAGE_ID, "en");
		context.put(Context.SIGNED_IN, true);

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				_group.getGroupId(), User.class.getName(), _user1.getUserId(),
				context);

		Assert.assertEquals(
			StringUtil.merge(segmentsEntryIds, StringPool.COMMA), 2,
			segmentsEntryIds.length);
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {
					segmentsEntry1.getSegmentsEntryId(),
					segmentsEntry3.getSegmentsEntryId()
				},
				segmentsEntryIds));
	}

	@Test
	public void testGetSegmentsEntryIdsWithCriteriaOrReferredSegments()
		throws Exception {

		_user1 = UserTestUtil.addUser(_group.getGroupId());
		_user2 = UserTestUtil.addUser(_group.getGroupId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user1.getUserId());
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), User.class.getName(), _user2.getUserId());

		Criteria criteria = new Criteria();

		_contextSegmentsCriteriaContributor.contribute(
			criteria, "not (languageId eq 'en')", Criteria.Conjunction.OR);

		_userSegmentsCriteriaContributor.contribute(
			criteria,
			String.format("not (firstName eq '%s')", _user1.getFirstName()),
			Criteria.Conjunction.OR);

		_segmentsEntrySegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(segmentsEntryIds eq '%s') or (segmentsEntryIds eq '%s')",
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()),
			Criteria.Conjunction.OR);

		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());

		Context context = new Context();

		context.put("languageId", "en");

		long[] segmentsEntryIds =
			_segmentsEntryProviderRegistry.getSegmentsEntryIds(
				_group.getGroupId(), User.class.getName(), _user1.getUserId(),
				context);

		Assert.assertEquals(
			StringUtil.merge(segmentsEntryIds, StringPool.COMMA), 2,
			segmentsEntryIds.length);
		Assert.assertTrue(
			ArrayUtil.containsAll(
				new long[] {
					segmentsEntry1.getSegmentsEntryId(),
					segmentsEntry3.getSegmentsEntryId()
				},
				segmentsEntryIds));
	}

	@Inject(
		filter = "segments.criteria.contributor.key=context",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _contextSegmentsCriteriaContributor;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Inject(
		filter = "segments.criteria.contributor.key=segments",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor
		_segmentsEntrySegmentsCriteriaContributor;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

}