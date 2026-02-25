/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.dsr.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.dsr.client.dto.v1_0.UserAccount;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.dsr.site.initializer.constants.DSRTicketConstants;
import com.liferay.site.dsr.site.initializer.test.util.DSRTestUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class UserAccountResourceTest extends BaseUserAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		DSRTestUtil.getOrAddGroup(UserAccountResourceTest.class);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		_objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"name", RandomTestUtil.randomString()
			).build(),
			serviceContext);
	}

	@Override
	@Test
	public void testPatchRoomUserAccount() throws Exception {
		UserAccount postUserAccount = testPostRoomUserAccount_addUserAccount(
			randomUserAccount());

		UserAccount patchUserAccount = userAccountResource.patchRoomUserAccount(
			_objectEntry.getObjectEntryId(), postUserAccount.getId(),
			new UserAccount() {
				{
					roleKey = "Site Administrator";
				}
			});

		Assert.assertEquals(postUserAccount.getId(), patchUserAccount.getId());
		Assert.assertEquals(
			"Site Administrator", patchUserAccount.getRoleKey());
	}

	@Override
	@Test
	public void testPostRoomUserAccount() throws Exception {
		super.testPostRoomUserAccount();

		_testPostRoomUserAccount();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"emailAddress"};
	}

	@Override
	protected UserAccount randomUserAccount() throws Exception {
		User user = UserTestUtil.addUser();

		return new UserAccount() {
			{
				emailAddress = user.getEmailAddress();
			}
		};
	}

	@Override
	protected Long testDeleteRoomUserAccount_getRoomId() throws Exception {
		return _objectEntry.getObjectEntryId();
	}

	@Override
	protected Long testGetRoomUserAccountsPage_getRoomId() throws Exception {
		return _objectEntry.getObjectEntryId();
	}

	private long _getGroupId(ObjectEntry objectEntry) throws Exception {
		Map<String, Serializable> values = objectEntry.getValues();

		Group group = _groupLocalService.getGroup(
			GetterUtil.getLong(values.get("siteId")));

		return group.getGroupId();
	}

	private void _testPostRoomUserAccount() throws Exception {

		// A new get is required to retrieve updated values for the
		// object entry

		_objectEntry = _objectEntryLocalService.getObjectEntry(
			_objectEntry.getObjectEntryId());

		UserAccount randomUserAccount1 = randomUserAccount();

		UserAccount postUserAccount = testPostRoomUserAccount_addUserAccount(
			randomUserAccount1);

		assertEquals(randomUserAccount1, postUserAccount);
		assertValid(postUserAccount);

		List<Ticket> tickets = _ticketLocalService.getTickets(
			TestPropsValues.getCompanyId(), Group.class.getName(),
			_getGroupId(_objectEntry), DSRTicketConstants.TYPE_INVITE_MEMBER);

		Assert.assertEquals(tickets.toString(), 2, tickets.size());

		Assert.assertTrue(
			ListUtil.exists(
				tickets,
				ticket -> {
					try {
						JSONObject jsonObject = _jsonFactory.createJSONObject(
							ticket.getExtraInfo());

						return Objects.equals(
							randomUserAccount1.getEmailAddress(),
							jsonObject.get("emailAddress"));
					}
					catch (Exception exception) {
						return false;
					}
				}));

		List<NotificationQueueEntry> notificationQueueEntries =
			_notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertTrue(
			ListUtil.exists(
				notificationQueueEntries,
				notificationQueueEntry -> {
					Map<String, Object> notificationRecipientSettingsMap =
						NotificationRecipientSettingUtil.
							getNotificationRecipientSettingsMap(
								notificationQueueEntry);

					return Objects.equals(
						randomUserAccount1.getEmailAddress(),
						String.valueOf(
							notificationRecipientSettingsMap.get(
								NotificationRecipientSettingConstants.
									NAME_TO)));
				}));

		UserAccount randomUserAccount2 = randomUserAccount();

		randomUserAccount2.setEmailAddress(
			RandomTestUtil.randomString() + "@liferay.com");

		postUserAccount = testPostRoomUserAccount_addUserAccount(
			randomUserAccount2);

		assertEquals(randomUserAccount2, postUserAccount);
		assertValid(postUserAccount);

		tickets = _ticketLocalService.getTickets(
			TestPropsValues.getCompanyId(), Group.class.getName(),
			_getGroupId(_objectEntry), DSRTicketConstants.TYPE_INVITE_MEMBER);

		Assert.assertEquals(tickets.toString(), 3, tickets.size());
		Assert.assertTrue(
			ListUtil.exists(
				tickets,
				ticket -> {
					try {
						JSONObject jsonObject = _jsonFactory.createJSONObject(
							ticket.getExtraInfo());

						return Objects.equals(
							randomUserAccount2.getEmailAddress(),
							jsonObject.get("emailAddress"));
					}
					catch (Exception exception) {
						return false;
					}
				}));

		notificationQueueEntries =
			_notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertTrue(
			ListUtil.exists(
				notificationQueueEntries,
				notificationQueueEntry -> {
					Map<String, Object> notificationRecipientSettingsMap =
						NotificationRecipientSettingUtil.
							getNotificationRecipientSettingsMap(
								notificationQueueEntry);

					return Objects.equals(
						randomUserAccount2.getEmailAddress(),
						String.valueOf(
							notificationRecipientSettingsMap.get(
								NotificationRecipientSettingConstants.
									NAME_TO)));
				}));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private TicketLocalService _ticketLocalService;

}