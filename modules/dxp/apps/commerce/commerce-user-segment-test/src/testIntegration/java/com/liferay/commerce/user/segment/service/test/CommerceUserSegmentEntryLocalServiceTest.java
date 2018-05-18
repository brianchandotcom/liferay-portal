/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.segment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.user.segment.exception.CommerceUserSegmentEntrySystemException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryConstants;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentTestUtil;
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
			CommerceUserSegmentTestUtil.addCommerceUserSegmentEntry(
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