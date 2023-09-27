/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.internal.constants.SegmentsDestinationNames;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class SegmentsEntryReindexMessageListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, String.format("(roleIds eq '%s')", _role.getRoleId()),
			Criteria.Conjunction.AND);

		_segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId(),
			CriteriaSerializer.serialize(criteria), User.class.getName());

		_user1 = UserTestUtil.addUser();
		_user2 = UserTestUtil.addUser();
	}

	@Test
	public void testSegmentsEntryRelAdded() throws Exception {
		_roleLocalService.addUserRole(_user1.getUserId(), _role);

		_invokeMessageListener();

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				_segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			segmentsEntryRels.toString(), 1, segmentsEntryRels.size());

		SegmentsEntryRel segmentsEntryRel1 = segmentsEntryRels.get(0);

		Assert.assertEquals(_user1.getUserId(), segmentsEntryRel1.getClassPK());

		_roleLocalService.addUserRole(_user2.getUserId(), _role);

		_invokeMessageListener();

		segmentsEntryRels = _segmentsEntryRelLocalService.getSegmentsEntryRels(
			_segmentsEntry.getSegmentsEntryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			OrderByComparatorFactoryUtil.create(
				"SegmentsEntryRel", "classPK", true));

		Assert.assertEquals(
			segmentsEntryRels.toString(), 2, segmentsEntryRels.size());

		SegmentsEntryRel segmentsEntryRel2 = segmentsEntryRels.get(0);
		SegmentsEntryRel segmentsEntryRel3 = segmentsEntryRels.get(1);

		Assert.assertEquals(_user1.getUserId(), segmentsEntryRel2.getClassPK());
		Assert.assertEquals(_user2.getUserId(), segmentsEntryRel3.getClassPK());
	}

	@Test
	public void testSegmentsEntryRelRemoved() throws Exception {
		_roleLocalService.addUserRole(_user1.getUserId(), _role);
		_roleLocalService.addUserRole(_user2.getUserId(), _role);

		_invokeMessageListener();

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				_segmentsEntry.getSegmentsEntryId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				OrderByComparatorFactoryUtil.create(
					"SegmentsEntryRel", "classPK", true));

		Assert.assertEquals(
			segmentsEntryRels.toString(), 2, segmentsEntryRels.size());

		SegmentsEntryRel segmentsEntryRel1 = segmentsEntryRels.get(0);
		SegmentsEntryRel segmentsEntryRel2 = segmentsEntryRels.get(1);

		Assert.assertEquals(_user1.getUserId(), segmentsEntryRel1.getClassPK());
		Assert.assertEquals(_user2.getUserId(), segmentsEntryRel2.getClassPK());

		_roleLocalService.deleteUserRole(_user2.getUserId(), _role);

		_invokeMessageListener();

		segmentsEntryRels = _segmentsEntryRelLocalService.getSegmentsEntryRels(
			_segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			segmentsEntryRels.toString(), 1, segmentsEntryRels.size());

		SegmentsEntryRel segmentsEntryRel3 = segmentsEntryRels.get(0);

		Assert.assertEquals(_user1.getUserId(), segmentsEntryRel3.getClassPK());
	}

	private void _invokeMessageListener() throws Exception {
		Message message = new Message();

		message.put("companyId", _segmentsEntry.getCompanyId());
		message.put("segmentsEntryId", _segmentsEntry.getSegmentsEntryId());
		message.put("type", _segmentsEntry.getType());

		_messageListener.receive(message);
	}

	@Inject(
		filter = "destination.name=" + SegmentsDestinationNames.SEGMENTS_ENTRY_REINDEX
	)
	private MessageListener _messageListener;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

}