/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class UserNotificationTypeTest extends BaseNotificationTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws PortalException {
		_userNotificationEventLocalService.deleteUserNotificationEvents(
			user1.getUserId());
		_userNotificationEventLocalService.deleteUserNotificationEvents(
			user2.getUserId());
	}

	@Test
	public void testSendNotificationRecipientTypeRole() throws Exception {
		_testSendNotification(
			Arrays.asList(
				createNotificationRecipientSetting(
					"roleName", RoleConstants.ADMINISTRATOR),
				createNotificationRecipientSetting("roleName", role.getName())),
			NotificationRecipientConstants.TYPE_ROLE);
	}

	@Test
	public void testSendNotificationRecipientTypeTermChildAuthorTerm()
		throws Exception {

		_testSendNotificationRecipientTypeTerm(
			Arrays.asList(
				createNotificationRecipientSetting(
					"term", getTermName("AUTHOR_ID"))),
			NotificationRecipientConstants.TYPE_TERM);
	}

	@Test
	public void testSendNotificationRecipientTypeTermCurrentUserTerm()
		throws Exception {

		_testSendNotificationRecipientTypeTerm(
			Arrays.asList(
				createNotificationRecipientSetting(
					"term", "[%CURRENT_USER_ID%]")),
			NotificationRecipientConstants.TYPE_TERM);
	}

	@Test
	public void testSendNotificationRecipientTypeTermParentAuthorTerm()
		throws Exception {

		_testSendNotificationRecipientTypeTerm(
			Arrays.asList(
				createNotificationRecipientSetting(
					"term", getTermName(true, "AUTHOR_ID"))),
			NotificationRecipientConstants.TYPE_TERM);
	}

	@Test
	public void testSendNotificationRecipientTypeTermScreenName()
		throws Exception {

		_testSendNotification(
			Arrays.asList(
				createNotificationRecipientSetting(
					"term", getTermName("creator")),
				createNotificationRecipientSetting(
					"term", user1.getScreenName())),
			NotificationRecipientConstants.TYPE_TERM);
	}

	@Test
	public void testSendNotificationRecipientTypeUser() throws Exception {
		_testSendNotification(
			Arrays.asList(
				createNotificationRecipientSetting(
					"userScreenName", user1.getScreenName()),
				createNotificationRecipientSetting(
					"userScreenName", user2.getScreenName())),
			NotificationRecipientConstants.TYPE_USER);
	}

	private void _assertNotificationRecipientSetting(
		NotificationRecipientSetting notificationRecipientSetting,
		String userFullName) {

		Assert.assertEquals(
			"userFullName", notificationRecipientSetting.getName());
		Assert.assertEquals(
			notificationRecipientSetting.getValue(), userFullName);
	}

	private NotificationContext _createNotificationContext(
		List<NotificationRecipientSetting> notificationRecipientSettings,
		String recipientType) {

		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setClassName(RandomTestUtil.randomString());
		notificationContext.setClassPK(RandomTestUtil.randomLong());

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.createNotificationTemplate(0L);

		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubject(
			ListUtil.toString(getTermNames(), StringPool.BLANK));
		notificationTemplate.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		notificationContext.setNotificationTemplate(notificationTemplate);

		notificationContext.setNotificationRecipient(
			notificationRecipientLocalService.createNotificationRecipient(0L));
		notificationContext.setNotificationRecipientSettings(
			notificationRecipientSettings);
		notificationContext.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		return notificationContext;
	}

	private void _testSendNotification(
			List<NotificationRecipientSetting> notificationRecipientSettings,
			String recipientType)
		throws Exception {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 0,
			notificationQueueEntries.size());

		Assert.assertEquals(
			0,
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user1.getUserId()));

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.addNotificationTemplate(
				_createNotificationContext(
					notificationRecipientSettings, recipientType));

		objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			childObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_NOTIFICATION,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"notificationTemplateId",
				notificationTemplate.getNotificationTemplateId()
			).build());

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			dtoConverterContext, parentObjectDefinition,
			new ObjectEntry() {
				{
					properties = parentObjectEntryValues;
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		objectEntryManager.addObjectEntry(
			dtoConverterContext, childObjectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.putAll(
						childObjectEntryValues
					).put(
						getObjectRelationshipObjectField2Name(),
						objectEntry.getId()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		notificationQueueEntry = notificationQueueEntries.get(0);

		assertTermValues(
			getTermValues(),
			ListUtil.fromString(
				notificationQueueEntry.getSubject(), StringPool.COMMA));

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		notificationRecipientSettings =
			notificationRecipient.getNotificationRecipientSettings();

		Assert.assertEquals(
			notificationRecipientSettings.toString(), 2,
			notificationRecipientSettings.size());
		_assertNotificationRecipientSetting(
			notificationRecipientSettings.get(0), user1.getFullName());
		_assertNotificationRecipientSetting(
			notificationRecipientSettings.get(1), user2.getFullName());

		Assert.assertEquals(
			1,
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user1.getUserId()));
	}

	private void _testSendNotificationRecipientTypeTerm(
			List<NotificationRecipientSetting> notificationRecipientSettings,
			String recipientType)
		throws Exception {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 0,
			notificationQueueEntries.size());

		Assert.assertEquals(
			0,
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user2.getUserId()));

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.addNotificationTemplate(
				_createNotificationContext(
					notificationRecipientSettings, recipientType));

		objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			childObjectDefinition.getObjectDefinitionId(), true,
			StringPool.BLANK, RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_NOTIFICATION,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"notificationTemplateId",
				notificationTemplate.getNotificationTemplateId()
			).build());

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			dtoConverterContext, parentObjectDefinition,
			new ObjectEntry() {
				{
					properties = parentObjectEntryValues;
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		objectEntryManager.addObjectEntry(
			dtoConverterContext, childObjectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.putAll(
						childObjectEntryValues
					).put(
						getObjectRelationshipObjectField2Name(),
						objectEntry.getId()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		notificationQueueEntry = notificationQueueEntries.get(0);

		assertTermValues(
			getTermValues(),
			ListUtil.fromString(
				notificationQueueEntry.getSubject(), StringPool.COMMA));

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		notificationRecipientSettings =
			notificationRecipient.getNotificationRecipientSettings();

		Assert.assertEquals(
			notificationRecipientSettings.toString(), 1,
			notificationRecipientSettings.size());
		_assertNotificationRecipientSetting(
			notificationRecipientSettings.get(0), user2.getFullName());

		Assert.assertEquals(
			1,
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user2.getUserId()));
	}

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}