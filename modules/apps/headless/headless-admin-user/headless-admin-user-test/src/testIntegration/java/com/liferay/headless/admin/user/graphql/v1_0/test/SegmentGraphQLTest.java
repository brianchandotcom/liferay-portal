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

package com.liferay.headless.admin.user.graphql.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Segment;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class SegmentGraphQLTest extends BaseSegmentGraphQLTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_adminUser = UserTestUtil.addGroupAdminUser(testGroup);
		_user = UserTestUtil.addGroupUser(testGroup, RoleConstants.POWER_USER);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "criteria", "name", "source"};
	}

	@Override
	protected Segment randomSegment() throws Exception {
		Segment segment = super.randomSegment();

		segment.setActive(true);
		segment.setCriteria(
			JSONUtil.put(
				"criteria",
				JSONUtil.put(
					"user",
					JSONUtil.put(
						"conjunction", "and"
					).put(
						"filterString",
						String.format(
							"(firstName eq '%s')", _user.getFirstName())
					).put(
						"typeValue", "model"
					))
			).toString());
		segment.setSource(SegmentsEntryConstants.SOURCE_DEFAULT);

		return segment;
	}

	@Override
	protected Segment testSegment_addSegment() throws Exception {
		Segment segment = randomSegment();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), _adminUser.getUserId());

		return _toSegment(
			SegmentsTestUtil.addSegmentsEntry(
				segment.getName(), segment.getName(), null,
				segment.getCriteria(), segment.getSource(),
				User.class.getName(), serviceContext));
	}

	private Segment _toSegment(SegmentsEntry segmentsEntry) {
		return new Segment() {
			{
				active = segmentsEntry.isActive();
				criteria = segmentsEntry.getCriteria();
				dateCreated = segmentsEntry.getCreateDate();
				dateModified = segmentsEntry.getModifiedDate();
				id = segmentsEntry.getSegmentsEntryId();
				name = segmentsEntry.getName(
					segmentsEntry.getDefaultLanguageId());
				siteId = segmentsEntry.getGroupId();
				source = segmentsEntry.getSource();
			}
		};
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@DeleteAfterTestRun
	private User _user;

}