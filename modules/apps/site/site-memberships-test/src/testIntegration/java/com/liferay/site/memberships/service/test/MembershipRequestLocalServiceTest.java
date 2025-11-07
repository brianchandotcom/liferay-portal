/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.MembershipRequestLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balazs Breier
 */
@RunWith(Arquillian.class)
public class MembershipRequestLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddMembershipRequest() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Group group = _groupLocalService.addGroup(
			StringPool.BLANK, TestPropsValues.getUserId(), 0, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			GroupConstants.TYPE_SITE_RESTRICTED, null, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			FriendlyURLNormalizerUtil.normalize(RandomTestUtil.randomString()),
			true, false, true, serviceContext);

		User user = UserTestUtil.addUser(null, group.getGroupId());

		MembershipRequest membershipRequest =
			_membershipRequestLocalService.addMembershipRequest(
				user.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		try {
			_membershipRequestLocalService.addMembershipRequest(
				user.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

			Assert.fail();
		}
		catch (PortalException portalException) {
			String message = portalException.getMessage();

			Assert.assertTrue(
				message.contains("Pending membership request already exists"));
		}

		membershipRequest.setStatusId(MembershipRequestConstants.STATUS_DENIED);

		_membershipRequestLocalService.updateMembershipRequest(
			membershipRequest);

		String key =
			user.getUserId() + StringPool.UNDERLINE + group.getGroupId();

		LockManagerUtil.lock(
			MembershipRequest.class.getName(), key, PortalUUIDUtil.generate());

		try {
			_membershipRequestLocalService.addMembershipRequest(
				user.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

			Assert.fail();
		}
		catch (PortalException portalException) {
			String message = portalException.getMessage();

			Assert.assertTrue(
				message.contains("Pending membership request already exists"));
		}
		finally {
			LockManagerUtil.unlock(MembershipRequest.class.getName(), key);
		}
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private MembershipRequestLocalService _membershipRequestLocalService;

}