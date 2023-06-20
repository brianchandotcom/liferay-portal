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

import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.text.SimpleDateFormat;

import java.time.Month;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
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
		ListTypeEntry listTypeEntry = ListTypeEntryUtil.createListTypeEntry(
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonList(listTypeEntry));

		randomObjectEntryValues = LinkedHashMapBuilder.<String, Object>put(
			"booleanObjectField", RandomTestUtil.randomBoolean()
		).put(
			"dateObjectField",
			() -> {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00.0");

				return simpleDateFormat.format(RandomTestUtil.nextDate());
			}
		).put(
			"integerObjectField", RandomTestUtil.nextInt()
		).put(
			"picklistObjectField",
			new ListEntry() {
				{
					key = listTypeEntry.getKey();
					name = listTypeEntry.getName(LocaleUtil.US);
				}
			}
		).put(
			"textObjectField", RandomTestUtil.randomString()
		).build();

		user1 = TestPropsValues.getUser();

		ListType prefixListType = _listTypeLocalService.getListType(
			"dr", ListTypeConstants.CONTACT_PREFIX);
		ListType suffixListType = _listTypeLocalService.getListType(
			"ii", ListTypeConstants.CONTACT_SUFFIX);

		role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		user2 = _userLocalService.addUser(
			user1.getUserId(), user1.getCompanyId(), true, null, null, true,
			null, RandomTestUtil.randomString() + "@liferay.com",
			user1.getLocale(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			prefixListType.getListTypeId(), suffixListType.getListTypeId(),
			true, Month.FEBRUARY.getValue(), 7, 1988, null,
			UserConstants.TYPE_REGULAR, null, null,
			new long[] {role.getRoleId()}, null, true, null);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user2));
		PrincipalThreadLocal.setName(user2.getUserId());
	}

	@Before
	public void setUp() throws Exception {
		objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				user1.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					new BooleanObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"booleanObjectField"
					).build(),
					new DateObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"dateObjectField"
					).build(),
					new IntegerObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"integerObjectField"
					).build(),
					new PicklistObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"picklistObjectField"
					).listTypeDefinitionId(
						_listTypeDefinition.getListTypeDefinitionId()
					).build(),
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectField"
					).build()));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user1.getUserId(), objectDefinition.getObjectDefinitionId());

		_authorTermValues = HashMapBuilder.<String, Object>put(
			getTermName("AUTHOR_EMAIL_ADDRESS"), user2.getEmailAddress()
		).put(
			getTermName("AUTHOR_FIRST_NAME"), user2.getFirstName()
		).put(
			getTermName("AUTHOR_ID"), user2.getUserId()
		).put(
			getTermName("AUTHOR_LAST_NAME"), user2.getLastName()
		).put(
			getTermName("AUTHOR_MIDDLE_NAME"), user2.getMiddleName()
		).put(
			getTermName("AUTHOR_PREFIX"), _getListType("PREFIX", user2)
		).put(
			getTermName("AUTHOR_SUFFIX"), _getListType("SUFFIX", user2)
		).build();
		_currentUserTermValues = HashMapBuilder.<String, Object>put(
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
			"[%CURRENT_USER_PREFIX%]", _getListType("PREFIX", user2)
		).put(
			"[%CURRENT_USER_SUFFIX%]", _getListType("SUFFIX", user2)
		).build();

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);
	}

	protected void assertTermValues(
		List<Object> expectedTermValues, List<String> actualTermValues) {

		Assert.assertEquals(
			expectedTermValues.toString(), expectedTermValues.size(),
			actualTermValues.size());

		for (int i = 0; i < actualTermValues.size(); i++) {
			Object expectedTermValue = expectedTermValues.get(i);
			Object actualTermValue = actualTermValues.get(i);

			if (expectedTermValue instanceof ListEntry) {
				ListEntry listEntry = (ListEntry)expectedTermValue;

				Assert.assertEquals(listEntry.getName(), actualTermValue);
			}
			else {
				Assert.assertEquals(
					String.valueOf(expectedTermValue), actualTermValue);
			}
		}
	}

	protected NotificationRecipientSetting createNotificationRecipientSetting(
		String name, Object value) {

		NotificationRecipientSetting notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
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

	protected String getTermName(String objectFieldName) {
		return StringBundler.concat(
			"[%", StringUtil.upperCase(objectDefinition.getShortName()), "_",
			StringUtil.upperCase(objectFieldName), "%]");
	}

	protected List<String> getTermNames() {
		return ListUtil.concat(
			ListUtil.fromMapKeys(_authorTermValues),
			ListUtil.fromMapKeys(_currentUserTermValues),
			Arrays.asList(
				getTermName("booleanObjectField"),
				getTermName("dateObjectField"),
				getTermName("integerObjectField"),
				getTermName("picklistObjectField"),
				getTermName("textObjectField")));
	}

	protected List<Object> getTermValues() {
		return ListUtil.concat(
			ListUtil.fromMapValues(_authorTermValues),
			ListUtil.fromMapValues(_currentUserTermValues),
			ListUtil.fromMapValues(randomObjectEntryValues));
	}

	protected static DTOConverterContext dtoConverterContext =
		new DefaultDTOConverterContext(
			false, Collections.emptyMap(),
			BaseNotificationTypeTest._dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, BaseNotificationTypeTest.user1);

	@DeleteAfterTestRun
	protected static ObjectDefinition objectDefinition;

	protected static LinkedHashMap<String, Object> randomObjectEntryValues;
	protected static Role role;
	protected static User user1;
	protected static User user2;

	@DeleteAfterTestRun
	protected NotificationQueueEntry notificationQueueEntry;

	@Inject
	protected NotificationQueueEntryLocalService
		notificationQueueEntryLocalService;

	@Inject
	protected NotificationRecipientLocalService
		notificationRecipientLocalService;

	@Inject
	protected NotificationTemplateLocalService notificationTemplateLocalService;

	@Inject
	protected ObjectActionLocalService objectActionLocalService;

	@Inject(
		filter = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	protected ObjectEntryManager objectEntryManager;

	private String _getListType(String type, User user) throws Exception {
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

		ListType listType = _listTypeLocalService.getListType(listTypeId);

		return listType.getName();
	}

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	private static ListTypeDefinition _listTypeDefinition;

	@Inject
	private static ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;

	@Inject
	private static ListTypeLocalService _listTypeLocalService;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	private Map<String, Object> _authorTermValues;
	private Map<String, Object> _currentUserTermValues;

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Inject
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}