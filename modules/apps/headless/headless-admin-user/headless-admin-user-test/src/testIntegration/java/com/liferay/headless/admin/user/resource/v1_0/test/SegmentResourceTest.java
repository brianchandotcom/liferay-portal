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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;

import org.junit.Before;
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

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	protected Segment testGetSiteSegmentsPage_addSegment(
			Long siteId, Segment segment)
		throws Exception {

		return _addSegment(segment, siteId, null);
	}

	@Override
	protected Segment testGetSiteUserAccountSegmentsPage_addSegment(
			Long siteId, Long userAccountId, Segment segment)
		throws Exception {

		return _addSegment(segment, siteId, userAccountId);
	}

	@Override
	protected Long testGetSiteUserAccountSegmentsPage_getUserAccountId() {
		return _user.getUserId();
	}

	private Segment _addSegment(Segment segment, Long siteId, Long userAccountId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(siteId);

		if(userAccountId == null) {
			serviceContext.setUserId(_user.getUserId());
		} else {
			serviceContext.setUserId(userAccountId);
		}

		return _toSegment(
			SegmentsEntryLocalServiceUtil.addSegmentsEntry(
				segment.getName(), null, null, segment.getActive(),
				segment.getCriteria(), segment.getSource(), null,
				serviceContext));
	}

	@Override
	protected Segment randomSegment() {
		Segment segment = super.randomSegment();

		segment.setActive(true);

		segment.setCriteria("{'user':{'conjunction':'and','filterString':'(firstName eq 'test')','typeValue':'model'}}");

		segment.setSource(SegmentsConstants.SOURCE_DEFAULT);

		return segment;
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
	private User _user;

}