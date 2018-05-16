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

package com.liferay.commerce.user.segment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.user.segment.exception.CommerceUserSegmentEntrySystemException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryConstants;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentEntryTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceUserSegmentEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = CommerceUserSegmentEntrySystemException.class)
	public void testDeleteSystemCommerceUserSegmentEntry() throws Exception {
		frutillaRule.scenario(
			"Delete system user segment entry"
		).given(
			"A system user segment entry"
		).when(
			"I delete the user segment entry"
		).then(
			"A CommerceUserSegmentEntrySystemException should be thrown"
		);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentEntryTestUtil.addCommerceUserSegmentEntry(
				_group.getGroupId(), true);

		_commerceUserSegmentEntryLocalService.deleteCommerceUserSegmentEntry(
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
	}

	@Test
	public void testGetGuestUserCommerceUserSegmentEntry() throws Exception {
		frutillaRule.scenario(
			"Retrieve user segments for default user (guest)"
		).given(
			"The guest user"
		).when(
			"I try to retrieve available user segments"
		).then(
			"The result should be the default user segment"
		);

		User guestUser = _userLocalService.getDefaultUser(
			_group.getCompanyId());

		long[] commerceUserSegmentEntryIDs =
			_commerceUserSegmentEntryLocalService.
				getCommerceUserSegmentEntryIds(
					_group.getGroupId(), 0, guestUser.getUserId());

		Assert.assertEquals(
			commerceUserSegmentEntryIDs.toString(), 1,
			commerceUserSegmentEntryIDs.length);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			_commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntry(
				commerceUserSegmentEntryIDs[0]);

		Assert.assertEquals(
			commerceUserSegmentEntry.toString(), true,
			commerceUserSegmentEntry.isSystem());

		Assert.assertEquals(
			CommerceUserSegmentEntryConstants.KEY_GUEST,
			commerceUserSegmentEntry.getName(Locale.US));
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

}