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

package com.liferay.notification.internal.type.test;

import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.time.Month;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Feliphe Marinho
 */
public class BaseNotificationTypeTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		user1 = TestPropsValues.getUser();

		ListType prefixListType = listTypeLocalService.getListType(
			"dr", Contact.class.getName() + ".prefix");

		ListType suffixListType = listTypeLocalService.getListType(
			"ii", Contact.class.getName() + ".suffix");

		user2 = userLocalService.addUser(
			user1.getUserId(), user1.getCompanyId(), true, null, null, true,
			null, "user2@liferay.com", user1.getLocale(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), prefixListType.getListTypeId(),
			suffixListType.getListTypeId(), true, Month.FEBRUARY.getValue(), 7,
			1988, null, null, null, null, null, true, null);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user2));

		PrincipalThreadLocal.setName(user2.getUserId());
	}

	@Before
	public void setUp() throws Exception {
		objectDefinition =
			objectDefinitionLocalService.addCustomObjectDefinition(
				user1.getUserId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					new BooleanObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"booleanObjectField"
					).objectFieldSettings(
						Collections.emptyList()
					).build(),
					new DateObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"dateObjectField"
					).objectFieldSettings(
						Collections.emptyList()
					).build(),
					new IntegerObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"integerObjectField"
					).objectFieldSettings(
						Collections.emptyList()
					).build(),
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectField"
					).objectFieldSettings(
						Collections.emptyList()
					).build()));

		objectDefinition =
			objectDefinitionLocalService.publishCustomObjectDefinition(
				user1.getUserId(), objectDefinition.getObjectDefinitionId());
	}

	protected void assertTerms(
		List<Object> expectedValues, List<String> termValues) {

		for (int i = 0; i < termValues.size(); i++) {
			Assert.assertEquals(expectedValues.get(i), termValues.get(i));
		}
	}

	protected NotificationRecipientSetting createNotificationRecipientSetting(
		String name, Object value) {

		NotificationRecipientSetting notificationRecipientSetting =
			notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0L);

		notificationRecipientSetting.setName(name);

		if (value instanceof String) {
			notificationRecipientSetting.setValue(String.valueOf(value));
		}
		else {
			notificationRecipientSetting.setValueMap(
				(Map<Locale, String>)value);
		}

		return notificationRecipientSetting;
	}

	protected HashMap<String, Object> getAuthorValues() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			getTerm("AUTHOR_EMAIL_ADDRESS"), user2.getEmailAddress()
		).put(
			getTerm("AUTHOR_FIRST_NAME"), user2.getFirstName()
		).put(
			getTerm("AUTHOR_ID"), user2.getUserId()
		).put(
			getTerm("AUTHOR_LAST_NAME"), user2.getLastName()
		).put(
			getTerm("AUTHOR_MIDDLE_NAME"), user2.getMiddleName()
		).put(
			getTerm("AUTHOR_PREFIX"), getListType("PREFIX", user2)
		).put(
			getTerm("AUTHOR_SUFFIX"), getListType("SUFFIX", user2)
		).build();
	}

	protected HashMap<String, Object> getCurrentUserValues()
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"[%CURRENT_USER_EMAIL_ADDRESS%]", user2.getEmailAddress()
		).put(
			"[%CURRENT_USER_FIRST_NAME%]", user2.getFirstName()
		).put(
			"[%CURRENT_USER_ID%]", user2.getUserId()
		).put(
			"[%CURRENT_USER_LAST_NAME%]", user2.getLastName()
		).put(
			"[%CURRENT_USER_MIDDLE_NAME%]", user2.getMiddleName()
		).put(
			"[%CURRENT_USER_PREFIX%]", getListType("PREFIX", user2)
		).put(
			"[%CURRENT_USER_SUFFIX%]", getListType("SUFFIX", user2)
		).build();
	}

	protected String getListType(String type, User user)
		throws PortalException {

		Contact contact = user.fetchContact();

		if (contact == null) {
			return StringPool.BLANK;
		}

		long listTypeId = contact.getPrefixListTypeId();

		if (type.equals("SUFFIX")) {
			listTypeId = contact.getSuffixListTypeId();
		}

		if (listTypeId == 0) {
			return StringPool.BLANK;
		}

		ListType listType = listTypeLocalService.getListType(listTypeId);

		return listType.getName();
	}

	protected String getTerm(String objectFieldName) {
		return StringBundler.concat(
			"[%", StringUtil.upperCase(objectDefinition.getShortName()), "_",
			StringUtil.upperCase(objectFieldName), "%]");
	}

	protected List<String> objectEntryTermNames() {
		return ListUtil.fromArray(
			getTerm("booleanObjectField"), getTerm("dateObjectField"),
			getTerm("integerObjectField"), getTerm("textObjectField"));
	}

	protected HashMap<String, Serializable> randomObjectEntry() {
		return HashMapBuilder.<String, Serializable>put(
			"booleanObjectField", RandomTestUtil.randomBoolean()
		).put(
			"dateObjectField", RandomTestUtil.nextDate()
		).put(
			"integerObjectField", RandomTestUtil.nextInt()
		).put(
			"textObjectField", RandomTestUtil.randomString()
		).build();
	}

	protected void sendNotification(
			NotificationContext notificationContext, String type)
		throws PortalException {

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(type);

		Assert.assertNotNull(
			"There is no notification type with type " + type,
			notificationType);

		notificationType.sendNotification(notificationContext);
	}

	@Inject
	protected static ListTypeLocalService listTypeLocalService;

	@DeleteAfterTestRun
	protected static ObjectDefinition objectDefinition;

	@Inject
	protected static ObjectDefinitionLocalService objectDefinitionLocalService;

	protected static User user1;
	protected static User user2;

	@Inject
	protected static UserLocalService userLocalService;

	@DeleteAfterTestRun
	protected NotificationQueueEntry notificationQueueEntry;

	@Inject
	protected NotificationQueueEntryLocalService
		notificationQueueEntryLocalService;

	@Inject
	protected NotificationRecipientLocalService
		notificationRecipientLocalService;

	@Inject
	protected NotificationRecipientSettingLocalService
		notificationRecipientSettingLocalService;

	@Inject
	protected NotificationTemplateLocalService notificationTemplateLocalService;

	@Inject
	protected ObjectEntryLocalService objectEntryLocalService;

	@Inject
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

}