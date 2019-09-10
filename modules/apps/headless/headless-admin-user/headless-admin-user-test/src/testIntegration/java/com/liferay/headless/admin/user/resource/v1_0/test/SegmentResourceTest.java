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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Segment;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.SegmentResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class SegmentResourceTest extends BaseSegmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_adminUser = UserTestUtil.addGroupAdminUser(testGroup);
		_user = UserTestUtil.addGroupUser(testGroup, RoleConstants.POWER_USER);
	}

	@Ignore
	@Test
	public void testGetSiteSegmentsPageWithDefaultPermissions()
		throws Exception {

		SegmentResource.Builder builder = SegmentResource.builder();

		segmentResource = builder.authentication(
			_user.getEmailAddress(), _user.getPasswordUnencrypted()
		).build();

		Long siteId = testGetSiteSegmentsPage_getSiteId();

		Segment segment1 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Segment segment2 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Page<Segment> page = segmentResource.getSiteSegmentsPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2), (List<Segment>)page.getItems());
		assertValid(page);
	}

	@Ignore
	@Test
	public void testGetSiteSegmentsPageWithoutViewPermissions()
		throws Exception {

		SegmentResource.Builder builder = SegmentResource.builder();

		segmentResource = builder.authentication(
			_user.getEmailAddress(), _user.getPasswordUnencrypted()
		).build();

		Long siteId = testGetSiteSegmentsPage_getSiteId();

		Segment segment = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		testGetSiteSegmentsPage_addSegment(siteId, randomSegment());

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			testGroup.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				testGroup.getCompanyId(),
				"com.liferay.segments.model.SegmentsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segment.getId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		Page<Segment> page = segmentResource.getSiteSegmentsPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
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
	protected Segment testGetSiteSegmentsPage_addSegment(
			Long siteId, Segment segment)
		throws Exception {

		return _addSegment(siteId, segment);
	}

	@Override
	protected Segment testGetSiteUserAccountSegmentsPage_addSegment(
			Long siteId, Long userAccountId, Segment segment)
		throws Exception {

		return _addSegment(siteId, segment);
	}

	@Override
	protected Long testGetSiteUserAccountSegmentsPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected Segment testGraphQLSegment_addSegment() throws Exception {
		return testGetSiteSegmentsPage_addSegment(
			testGroup.getGroupId(), randomSegment());
	}

	private Segment _addSegment(Long siteId, Segment segment)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				siteId, _adminUser.getUserId());

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