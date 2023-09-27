/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.notification.term.util.ObjectDefinitionNotificationTermUtil;
import com.liferay.object.field.builder.BooleanObjectFieldBuilder;
import com.liferay.object.field.builder.DateObjectFieldBuilder;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
				false, Collections.singletonList(listTypeEntry));

		childObjectEntryValues = LinkedHashMapBuilder.<String, Object>put(
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

		parentObjectEntryValues = LinkedHashMapBuilder.<String, Object>put(
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
		childObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				user1.getUserId(), 0, false, false, false,
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

		childObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user1.getUserId(),
				childObjectDefinition.getObjectDefinitionId());

		parentObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				user1.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectField"
					).objectFieldSettings(
						Collections.emptyList()
					).build()));

		parentObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user1.getUserId(),
				parentObjectDefinition.getObjectDefinitionId());

		objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				parentObjectDefinition.getObjectDefinitionId(),
				childObjectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_childAuthorTermValues = HashMapBuilder.<String, Object>put(
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
		_parentAuthorTermValues = HashMapBuilder.<String, Object>put(
			getTermName(true, "AUTHOR_EMAIL_ADDRESS"), user2.getEmailAddress()
		).put(
			getTermName(true, "AUTHOR_FIRST_NAME"), user2.getFirstName()
		).put(
			getTermName(true, "AUTHOR_ID"), user2.getUserId()
		).put(
			getTermName(true, "AUTHOR_LAST_NAME"), user2.getLastName()
		).put(
			getTermName(true, "AUTHOR_MIDDLE_NAME"), user2.getMiddleName()
		).put(
			getTermName(true, "AUTHOR_PREFIX"), _getListType("PREFIX", user2)
		).put(
			getTermName(true, "AUTHOR_SUFFIX"), _getListType("SUFFIX", user2)
		).build();

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(),
			childObjectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(),
			parentObjectDefinition.getResourceName(),
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

	protected String getObjectRelationshipObjectField2Name()
		throws PortalException {

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		return objectField.getName();
	}

	protected String getTermName(boolean parent, String termNameSuffix) {
		String termNamePrefix = childObjectDefinition.getShortName();

		if (parent) {
			termNamePrefix =
				ObjectRelationshipUtil.getNotificationTermNamePrefix(
					parentObjectDefinition, objectRelationship);
		}

		return ObjectDefinitionNotificationTermUtil.getObjectFieldTermName(
			termNamePrefix, termNameSuffix);
	}

	protected String getTermName(String termNameSuffix) {
		return getTermName(false, termNameSuffix);
	}

	protected List<String> getTermNames() {
		return ListUtil.concat(
			ListUtil.fromMapKeys(_childAuthorTermValues),
			ListUtil.fromMapKeys(_currentUserTermValues),
			ListUtil.fromMapKeys(_parentAuthorTermValues),
			Arrays.asList(
				getTermName("booleanObjectField"),
				getTermName("dateObjectField"),
				getTermName("integerObjectField"),
				getTermName("picklistObjectField"),
				getTermName("textObjectField"),
				getTermName(true, "textObjectField")));
	}

	protected List<Object> getTermValues() {
		return ListUtil.concat(
			ListUtil.fromMapValues(_childAuthorTermValues),
			ListUtil.fromMapValues(_currentUserTermValues),
			ListUtil.fromMapValues(_parentAuthorTermValues),
			ListUtil.fromMapValues(childObjectEntryValues),
			ListUtil.fromMapValues(parentObjectEntryValues));
	}

	@DeleteAfterTestRun
	protected static ObjectDefinition childObjectDefinition;

	protected static LinkedHashMap<String, Object> childObjectEntryValues;
	protected static DTOConverterContext dtoConverterContext =
		new DefaultDTOConverterContext(
			false, Collections.emptyMap(),
			BaseNotificationTypeTest._dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, BaseNotificationTypeTest.user1);

	@DeleteAfterTestRun
	protected static ObjectRelationship objectRelationship;

	@DeleteAfterTestRun
	protected static ObjectDefinition parentObjectDefinition;

	protected static LinkedHashMap<String, Object> parentObjectEntryValues;
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
	private static ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private static ObjectRelationshipLocalService
		_objectRelationshipLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	private Map<String, Object> _childAuthorTermValues;
	private Map<String, Object> _currentUserTermValues;

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	private Map<String, Object> _parentAuthorTermValues;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}