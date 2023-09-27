/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.notifications.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.notifications.test.util.BaseUserNotificationTestCase;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class SharingUserNotificationTest extends BaseUserNotificationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_fromUser = UserTestUtil.addUser();
	}

	@Test
	public void testNotificationMessage() throws Exception {
		subscribeToContainer();

		User toUser = UserTestUtil.addUser();

		try {
			_share(group, _fromUser, toUser);
			_share(group, user, toUser);

			List<JSONObject> userNotificationEventsJSONObjects =
				getUserNotificationEventsJSONObjects(toUser.getUserId());

			Assert.assertEquals(
				userNotificationEventsJSONObjects.toString(), 2,
				userNotificationEventsJSONObjects.size());

			JSONObject userNotificationEventsJSONObject =
				userNotificationEventsJSONObjects.get(0);

			Assert.assertEquals(
				_getExpectedNotificationMessage(user),
				userNotificationEventsJSONObject.getString("message"));

			userNotificationEventsJSONObject =
				userNotificationEventsJSONObjects.get(1);

			Assert.assertEquals(
				_getExpectedNotificationMessage(_fromUser),
				userNotificationEventsJSONObject.getString("message"));
		}
		finally {
			_userLocalService.deleteUser(toUser);

			_userNotificationEventLocalService.deleteUserNotificationEvents(
				toUser.getUserId());
		}
	}

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		return _share(group, _fromUser, user);
	}

	@Override
	protected String getPortletId() {
		return SharingPortletKeys.SHARING;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		MailServiceTestUtil.clearMessages();

		_userNotificationEventLocalService.deleteUserNotificationEvents(
			user.getUserId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		SharingEntry sharingEntry = (SharingEntry)baseModel;

		return _sharingEntryLocalService.updateSharingEntry(
			user.getUserId(), sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.VIEW), false, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private String _getExpectedNotificationMessage(User fromUser) {
		return StringBundler.concat(
			fromUser.getFullName(), " has shared ",
			group.getName(LocaleUtil.getDefault()), " with you for viewing.");
	}

	private SharingEntry _share(BaseModel<?> model, User fromUser, User toUser)
		throws Exception {

		long classPK = (Long)model.getPrimaryKeyObj();

		return _sharingEntryLocalService.addOrUpdateSharingEntry(
			fromUser.getUserId(), toUser.getUserId(),
			_classNameLocalService.getClassNameId(model.getModelClassName()),
			classPK, group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), fromUser.getUserId()));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private User _fromUser;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}