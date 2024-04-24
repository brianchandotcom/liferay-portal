/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.test.util.NotificationTemplateUtil;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.object.action.util.ObjectActionThreadLocal;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.text.SimpleDateFormat;

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class EmailNotificationTypeTest extends BaseNotificationTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseNotificationTypeTest.setUpClass();

		_freeMarkerTermValues = LinkedHashMapBuilder.<String, Object>put(
			"${ObjectField_booleanObjectField.getData()}",
			childObjectEntryValues.get("booleanObjectField")
		).put(
			"${ObjectField_dateObjectField.getData()}",
			() -> {
				SimpleDateFormat dateInfoFieldSimpleDateFormat =
					new SimpleDateFormat(
						DateTimeFormatterBuilder.getLocalizedDateTimePattern(
							FormatStyle.SHORT, FormatStyle.SHORT,
							IsoChronology.INSTANCE, LocaleUtil.US));

				SimpleDateFormat dateObjectFieldSimpleDateFormat =
					new SimpleDateFormat("yyyy-MM-dd");

				return dateInfoFieldSimpleDateFormat.format(
					dateObjectFieldSimpleDateFormat.parse(
						(String)childObjectEntryValues.get("dateObjectField")));
			}
		).put(
			"${ObjectField_dateTimeObjectField.getData()}",
			() -> {
				SimpleDateFormat dateTimeObjectFieldSimpleDateFormat =
					new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
				SimpleDateFormat defaultInfoFieldSimpleDateFormat =
					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

				return defaultInfoFieldSimpleDateFormat.format(
					dateTimeObjectFieldSimpleDateFormat.parse(
						(String)childObjectEntryValues.get(
							"dateTimeObjectField")));
			}
		).put(
			"${ObjectField_emailTextObjectField.getData()}",
			childObjectEntryValues.get("emailTextObjectField")
		).put(
			"${ObjectField_integerObjectField.getData()}",
			childObjectEntryValues.get("integerObjectField")
		).put(
			"${ObjectField_textObjectField.getData()}",
			childObjectEntryValues.get("textObjectField")
		).put(
			"${portalURL}",
			() -> {
				_originalHttpServletRequest =
					ObjectActionThreadLocal.getHttpServletRequest();

				HttpServletRequest httpServletRequest =
					_originalHttpServletRequest;

				if (httpServletRequest == null) {
					httpServletRequest = new MockHttpServletRequest(
						null, StringPool.BLANK, RandomTestUtil.randomString());

					ObjectActionThreadLocal.setHttpServletRequest(
						httpServletRequest);
				}

				return _portal.getPortalURL(httpServletRequest);
			}
		).build();
	}

	@AfterClass
	public static void tearDownClass() {
		ObjectActionThreadLocal.setHttpServletRequest(
			_originalHttpServletRequest);
	}

	@Test
	public void testFreeMarkerNotification() throws Exception {
		String body = LocalizationUtil.updateLocalization(
			LocalizedMapUtil.getLocalizedMap(
				HashMapBuilder.put(
					LanguageUtil.getLanguageId(LocaleUtil.US),
					StringUtil.merge(
						_freeMarkerTermValues.keySet(), StringPool.POUND)
				).build()),
			null, "Body", LanguageUtil.getLanguageId(LocaleUtil.US));

		_executeNotificationObjectAction(
			0,
			_addNotificationTemplate(
				body, NotificationTemplateConstants.EDITOR_TYPE_FREEMARKER,
				false,
				Collections.singletonMap(
					LocaleUtil.US, user1.getEmailAddress())));

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		notificationQueueEntry = notificationQueueEntries.get(0);

		assertTermValues(
			new ArrayList<>(_freeMarkerTermValues.values()),
			Arrays.asList(
				StringUtil.split(
					notificationQueueEntry.getBody(), StringPool.POUND)));
	}

	@Test
	public void testFreeMarkerNotificationPicklistObjectFieldTerm()
		throws Exception {

		String body = LocalizationUtil.updateLocalization(
			LocalizedMapUtil.getLocalizedMap(
				HashMapBuilder.put(
					LanguageUtil.getLanguageId(LocaleUtil.US),
					"${ObjectField_picklistObjectField.getData()}"
				).build()),
			null, "Body", LanguageUtil.getLanguageId(LocaleUtil.US));

		_executeNotificationObjectAction(
			0,
			_addNotificationTemplate(
				body, NotificationTemplateConstants.EDITOR_TYPE_FREEMARKER,
				false,
				Collections.singletonMap(
					LocaleUtil.US, user1.getEmailAddress())));

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		notificationQueueEntry = notificationQueueEntries.get(0);

		ListEntry listEntry = (ListEntry)childObjectEntryValues.get(
			"picklistObjectField");

		assertTermValues(
			Arrays.asList(listEntry.getName()),
			Arrays.asList(
				StringUtil.split(
					notificationQueueEntry.getBody(), StringPool.COMMA)));
	}

	@Test
	public void testSendNotification() throws Exception {

		// Multiples emails for each main recipient with a "," separator

		_testSendNotification(
			2,
			ListUtil.sort(
				Arrays.asList(
					user1.getEmailAddress(), user2.getEmailAddress())),
			true,
			StringBundler.concat(
				user1.getEmailAddress(), StringPool.COMMA,
				user2.getEmailAddress()));

		// Multiples emails for each main recipient with a ", " separator

		_testSendNotification(
			2,
			ListUtil.sort(
				Arrays.asList(
					user1.getEmailAddress(), user2.getEmailAddress())),
			true,
			StringBundler.concat(
				user1.getEmailAddress(), StringPool.COMMA_AND_SPACE,
				user2.getEmailAddress()));

		// Multiples emails for each main recipient with a ";" separator

		_testSendNotification(
			2,
			ListUtil.sort(
				Arrays.asList(
					user1.getEmailAddress(), user2.getEmailAddress())),
			true,
			StringBundler.concat(
				user1.getEmailAddress(), StringPool.SEMICOLON,
				user2.getEmailAddress()));

		// Multiples emails for each main recipient and terms with a ","
		// separator

		_testSendNotification(
			2,
			ListUtil.sort(
				Arrays.asList(
					user2.getEmailAddress(),
					GetterUtil.getString(
						childObjectEntryValues.get("emailTextObjectField")))),
			true,
			"[%CURRENT_USER_EMAIL_ADDRESS%]," +
				getTermName("emailTextObjectField"));

		// Multiples emails for each main recipient and terms with a ", "
		// separator

		_testSendNotification(
			2,
			ListUtil.sort(
				Arrays.asList(
					user2.getEmailAddress(),
					GetterUtil.getString(
						childObjectEntryValues.get("emailTextObjectField")))),
			true,
			"[%CURRENT_USER_EMAIL_ADDRESS%], " +
				getTermName("emailTextObjectField"));

		// Multiples emails for each main recipient and terms with a ";"
		// separator

		_testSendNotification(
			2,
			ListUtil.sort(
				Arrays.asList(
					user2.getEmailAddress(),
					GetterUtil.getString(
						childObjectEntryValues.get("emailTextObjectField")))),
			true,
			"[%CURRENT_USER_EMAIL_ADDRESS%];" +
				getTermName("emailTextObjectField"));

		// One email including all main recipients

		_testSendNotification(
			1,
			ListUtil.sort(
				Arrays.asList(
					StringBundler.concat(
						user1.getEmailAddress(), StringPool.COMMA,
						user2.getEmailAddress()))),
			false,
			StringBundler.concat(
				user1.getEmailAddress(), StringPool.COMMA,
				user2.getEmailAddress()));
	}

	@FeatureFlags("LPD-11165")
	@Test
	public void testSendNotificationWithRegularRoles() throws Exception {
		Role role1 = _addRole(RoleConstants.TYPE_REGULAR, user1);
		Role role2 = _addRole(RoleConstants.TYPE_REGULAR, user2);

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.addNotificationTemplate(
				NotificationTemplateUtil.createNotificationContext(
					TestPropsValues.getUser(), 0, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
					Arrays.asList(
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_CC,
							"[%CURRENT_USER_EMAIL_ADDRESS%],cc@liferay.com"),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_FROM,
							"[%CURRENT_USER_EMAIL_ADDRESS%]"),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.
								NAME_FROM_NAME,
							Collections.singletonMap(
								LocaleUtil.US, "[%CURRENT_USER_FIRST_NAME%]")),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.
								NAME_SINGLE_RECIPIENT,
							Boolean.FALSE.toString()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO,
							role1.getName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO_TYPE,
							NotificationRecipientConstants.TYPE_ROLE)),
					RandomTestUtil.randomString(),
					NotificationConstants.TYPE_EMAIL, Collections.emptyList()));

		_testSendNotificationWithRoles(
			null, StringPool.BLANK, 0, null, notificationTemplate);

		_roleLocalService.addUserRole(user1.getUserId(), role1.getRoleId());
		_roleLocalService.addUserRole(user2.getUserId(), role2.getRoleId());

		_testSendNotificationWithRoles(
			null, StringPool.BLANK, 1, user1.getEmailAddress(),
			notificationTemplate);
	}

	@FeatureFlags("LPD-11165")
	@Test
	public void testSendNotificationWithRoles() throws Exception {
		AccountEntry accountEntry1 = _addAccountEntry();

		AccountRole accountRole1 = _addAccountRole(
			accountEntry1.getAccountEntryId());

		AccountEntry accountEntry2 = _addAccountEntry();

		AccountRole accountRole2 = _addAccountRole(
			accountEntry2.getAccountEntryId());
		AccountRole accountRole3 = _addAccountRole(
			accountEntry2.getAccountEntryId());

		AccountRole accountRole4 = _addAccountRole(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);

		Role organizationRole1 = _addRole(
			RoleConstants.TYPE_ORGANIZATION, TestPropsValues.getUser());
		Role organizationRole2 = _addRole(
			RoleConstants.TYPE_ORGANIZATION, TestPropsValues.getUser());

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.addNotificationTemplate(
				NotificationTemplateUtil.createNotificationContext(
					TestPropsValues.getUser(), 0, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
					Arrays.asList(
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_BCC,
							accountRole3.getRoleName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_BCC,
							organizationRole2.getName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_BCC_TYPE,
							NotificationRecipientConstants.TYPE_ROLE),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_CC,
							"[%CURRENT_USER_EMAIL_ADDRESS%],cc@liferay.com"),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_FROM,
							"[%CURRENT_USER_EMAIL_ADDRESS%]"),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.
								NAME_FROM_NAME,
							Collections.singletonMap(
								LocaleUtil.US, "[%CURRENT_USER_FIRST_NAME%]")),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.
								NAME_SINGLE_RECIPIENT,
							Boolean.FALSE.toString()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO,
							accountRole1.getRoleName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO,
							accountRole2.getRoleName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO,
							accountRole4.getRoleName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO,
							organizationRole1.getName()),
						createNotificationRecipientSetting(
							NotificationRecipientSettingConstants.NAME_TO_TYPE,
							NotificationRecipientConstants.TYPE_ROLE)),
					RandomTestUtil.randomString(),
					NotificationConstants.TYPE_EMAIL, Collections.emptyList()));

		_testSendNotificationWithRoles(
			null, null, 0, null, notificationTemplate);

		User user1 = UserTestUtil.addUser();

		_accountRoleLocalService.associateUser(
			accountEntry1.getAccountEntryId(), accountRole1.getAccountRoleId(),
			user1.getUserId());
		_accountRoleLocalService.associateUser(
			accountEntry2.getAccountEntryId(), accountRole2.getAccountRoleId(),
			user1.getUserId());

		User user2 = UserTestUtil.addUser();

		_accountRoleLocalService.associateUser(
			accountEntry2.getAccountEntryId(), accountRole3.getAccountRoleId(),
			user2.getUserId());

		User user3 = UserTestUtil.addUser();

		_accountRoleLocalService.associateUser(
			accountEntry2.getAccountEntryId(), accountRole4.getAccountRoleId(),
			user3.getUserId());

		Organization organization1 = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);

		_userGroupRoleLocalService.addUserGroupRole(
			user3.getUserId(), organization1.getGroupId(),
			organizationRole1.getRoleId());

		User user4 = UserTestUtil.addUser();

		_userGroupRoleLocalService.addUserGroupRole(
			user4.getUserId(), organization1.getGroupId(),
			organizationRole1.getRoleId());

		User user5 = UserTestUtil.addUser();

		Organization organization2 = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);

		Organization childOrganization =
			_organizationLocalService.addOrganization(
				TestPropsValues.getUserId(), organization2.getOrganizationId(),
				RandomTestUtil.randomString(), false);

		_userGroupRoleLocalService.addUserGroupRole(
			user5.getUserId(), childOrganization.getGroupId(),
			organizationRole2.getRoleId());

		// Send email with an object definition not restricted by account entry

		_testSendNotificationWithRoles(
			null,
			StringUtil.merge(
				ListUtil.fromArray(
					user2.getEmailAddress(), user5.getEmailAddress())),
			1,
			StringUtil.merge(
				ListUtil.fromArray(
					user1.getEmailAddress(), user3.getEmailAddress(),
					user4.getEmailAddress())),
			notificationTemplate);

		// Send email with an object definition restricted by account entry

		_testSendNotificationWithRoles(
			accountEntry1, StringPool.BLANK, 1, user1.getEmailAddress(),
			notificationTemplate);
		_testSendNotificationWithRoles(
			accountEntry2, user2.getEmailAddress(), 1,
			StringUtil.merge(
				ListUtil.fromArray(
					user1.getEmailAddress(), user3.getEmailAddress())),
			notificationTemplate);

		AccountEntry accountEntry3 = _addAccountEntry();

		_accountRoleLocalService.associateUser(
			accountEntry3.getAccountEntryId(), accountRole4.getAccountRoleId(),
			user2.getUserId());

		// Send email with an object definition not restricted by account entry

		_testSendNotificationWithRoles(
			null,
			StringUtil.merge(
				ListUtil.fromArray(
					user2.getEmailAddress(), user5.getEmailAddress())),
			1,
			StringUtil.merge(
				ListUtil.fromArray(
					user1.getEmailAddress(), user2.getEmailAddress(),
					user3.getEmailAddress(), user4.getEmailAddress())),
			notificationTemplate);

		// Send email with an object definition restricted by account entry

		_userGroupRoleLocalService.addUserGroupRole(
			user4.getUserId(), organization1.getGroupId(),
			organizationRole2.getRoleId());

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry3.getAccountEntryId(),
			childOrganization.getOrganizationId());

		User user6 = UserTestUtil.addUser();

		_userGroupRoleLocalService.addUserGroupRole(
			user6.getUserId(), organization2.getGroupId(),
			organizationRole2.getRoleId());

		_testSendNotificationWithRoles(
			accountEntry3,
			StringUtil.merge(
				ListUtil.fromArray(
					user5.getEmailAddress(), user6.getEmailAddress())),
			1, user2.getEmailAddress(), notificationTemplate);

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry3.getAccountEntryId(),
				childOrganization.getOrganizationId());

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry3.getAccountEntryId(),
			organization2.getOrganizationId());

		_testSendNotificationWithRoles(
			accountEntry3, user6.getEmailAddress(), 1, user2.getEmailAddress(),
			notificationTemplate);
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(), 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private AccountRole _addAccountRole(long accountEntryId) throws Exception {
		return _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntryId,
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

	private NotificationTemplate _addNotificationTemplate(
			String body, String editorType, boolean singleRecipient,
			Map<Locale, String> to)
		throws Exception {

		ObjectField objectField = objectFieldLocalService.getObjectField(
			childObjectDefinition.getObjectDefinitionId(),
			"attachmentObjectField");

		return notificationTemplateLocalService.addNotificationTemplate(
			NotificationTemplateUtil.createNotificationContext(
				TestPropsValues.getUser(),
				childObjectDefinition.getObjectDefinitionId(), body,
				RandomTestUtil.randomString(), editorType,
				Arrays.asList(
					createNotificationRecipientSetting(
						"bcc",
						"[%CURRENT_USER_EMAIL_ADDRESS%],bcc@liferay.com"),
					createNotificationRecipientSetting(
						"cc", "[%CURRENT_USER_EMAIL_ADDRESS%],cc@liferay.com"),
					createNotificationRecipientSetting(
						"from", "[%CURRENT_USER_EMAIL_ADDRESS%]"),
					createNotificationRecipientSetting(
						"fromName",
						Collections.singletonMap(
							LocaleUtil.US, "[%CURRENT_USER_FIRST_NAME%]")),
					createNotificationRecipientSetting(
						"singleRecipient", String.valueOf(singleRecipient)),
					createNotificationRecipientSetting("to", to)),
				ListUtil.toString(
					getTermNames(), StringPool.BLANK, StringPool.SEMICOLON),
				NotificationConstants.TYPE_EMAIL,
				Collections.singletonList(objectField.getObjectFieldId())));
	}

	private Role _addRole(int type, User user) throws Exception {
		return _roleLocalService.addRole(
			user.getUserId(), null, 0, RandomTestUtil.randomString(), null,
			null, type, null, null);
	}

	private void _assertNotificationQueueEntry(
		String expectedBcc, boolean expectedSingleRecipient,
		String expectedToEmailAddress,
		NotificationQueueEntry notificationQueueEntry) {

		Assert.assertNotNull(
			MailServiceTestUtil.getMailMessages("To", expectedToEmailAddress));

		Map<String, Object> notificationRecipientSettingsMap =
			NotificationRecipientSettingUtil.
				getNotificationRecipientSettingsMap(notificationQueueEntry);

		Assert.assertEquals(
			user2.getEmailAddress() + ",cc@liferay.com",
			notificationRecipientSettingsMap.get("cc"));
		Assert.assertEquals(
			user2.getEmailAddress(),
			notificationRecipientSettingsMap.get("from"));
		Assert.assertEquals(
			user2.getFirstName(),
			notificationRecipientSettingsMap.get("fromName"));
		Assert.assertEquals(
			expectedSingleRecipient,
			notificationRecipientSettingsMap.get("singleRecipient"));
		AssertUtils.assertEqualsSorted(
			StringUtil.split(expectedBcc),
			StringUtil.split(
				String.valueOf(notificationRecipientSettingsMap.get("bcc"))));
		AssertUtils.assertEqualsSorted(
			StringUtil.split(expectedToEmailAddress),
			StringUtil.split(
				String.valueOf(notificationRecipientSettingsMap.get("to"))));
	}

	private void _assertNotificationQueueEntry(
			String expectedBcc, String expectedFileName,
			boolean expectedSingleRecipient, String expectedToEmailAddress,
			NotificationQueueEntry notificationQueueEntry)
		throws Exception {

		_assertNotificationQueueEntry(
			expectedBcc, expectedSingleRecipient, expectedToEmailAddress,
			notificationQueueEntry);

		assertTermValues(
			getTermValues(),
			ListUtil.fromString(
				notificationQueueEntry.getBody(), StringPool.SEMICOLON));
		assertTermValues(
			getTermValues(),
			ListUtil.fromString(
				notificationQueueEntry.getSubject(), StringPool.SEMICOLON));

		Folder folder = _getFolder(notificationQueueEntry);

		FileEntry fileEntry = _portletFileRepository.getPortletFileEntry(
			folder.getGroupId(), folder.getFolderId(), expectedFileName);

		List<NotificationQueueEntryAttachment>
			notificationQueueEntryAttachments =
				_notificationQueueEntryAttachmentLocalService.
					getNotificationQueueEntryNotificationQueueEntryAttachments(
						notificationQueueEntry.getNotificationQueueEntryId());

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			notificationQueueEntryAttachments.get(0);

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			notificationQueueEntryAttachment.getFileEntryId());
	}

	private void _executeNotificationObjectAction(
			long fileEntryId, NotificationTemplate notificationTemplate)
		throws Exception {

		ObjectAction objectAction = objectActionLocalService.addObjectAction(
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
			).build(),
			false);

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
					).put(
						"attachmentObjectField", fileEntryId
					).build();
				}
			},
			group.getGroupKey());

		objectActionLocalService.deleteObjectAction(
			objectAction.getObjectActionId());
	}

	private Folder _getFolder(NotificationQueueEntry notificationQueueEntry)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			notificationQueueEntry.getCompanyId());

		Repository repository = _portletFileRepository.getPortletRepository(
			group.getGroupId(), NotificationPortletKeys.NOTIFICATION_TEMPLATES);

		return _portletFileRepository.getPortletFolder(
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(
				notificationQueueEntry.getNotificationQueueEntryId()));
	}

	private void _testSendNotification(
			int expectedNotificationQueueEntriesCount,
			List<String> expectedToEmailAddresses, boolean singleRecipient,
			String to)
		throws Exception {

		FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			StringUtil.randomString(),
			TempFileEntryUtil.getTempFileName(
				StringUtil.randomString() + ".txt"),
			FileUtil.createTempFile(RandomTestUtil.randomBytes()),
			ContentTypes.TEXT_PLAIN);

		_executeNotificationObjectAction(
			fileEntry.getFileEntryId(),
			_addNotificationTemplate(
				ListUtil.toString(
					getTermNames(), StringPool.BLANK, StringPool.SEMICOLON),
				NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
				singleRecipient, Collections.singletonMap(LocaleUtil.US, to)));

		List<NotificationQueueEntry> notificationQueueEntries = ListUtil.sort(
			notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT),
			Comparator.comparing(
				notificationQueueEntry -> {
					Map<String, Object> notificationRecipientSettingsMap =
						NotificationRecipientSettingUtil.
							getNotificationRecipientSettingsMap(
								notificationQueueEntry);

					return String.valueOf(
						notificationRecipientSettingsMap.get(
							NotificationRecipientSettingConstants.NAME_TO));
				}));

		Assert.assertEquals(
			notificationQueueEntries.toString(),
			expectedNotificationQueueEntriesCount,
			notificationQueueEntries.size());

		_assertNotificationQueueEntry(
			user2.getEmailAddress() + ",bcc@liferay.com",
			TempFileEntryUtil.getOriginalTempFileName(fileEntry.getFileName()),
			singleRecipient, expectedToEmailAddresses.get(0),
			notificationQueueEntries.get(0));

		if (singleRecipient) {
			_assertNotificationQueueEntry(
				user2.getEmailAddress() + ",bcc@liferay.com",
				TempFileEntryUtil.getOriginalTempFileName(
					fileEntry.getFileName()),
				singleRecipient, expectedToEmailAddresses.get(1),
				notificationQueueEntries.get(1));
		}

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			Folder folder = _getFolder(notificationQueueEntry);

			notificationQueueEntryLocalService.deleteNotificationQueueEntry(
				notificationQueueEntry);

			AssertUtils.assertFailure(
				NoSuchFolderException.class,
				StringBundler.concat(
					"No Folder exists with the key {folderId=",
					folder.getFolderId(), "}"),
				() -> _portletFileRepository.getPortletFolder(
					folder.getFolderId()));

			Assert.assertTrue(
				ListUtil.isEmpty(
					_notificationQueueEntryAttachmentLocalService.
						getNotificationQueueEntryNotificationQueueEntryAttachments(
							notificationQueueEntry.
								getNotificationQueueEntryId())));
		}
	}

	private void _testSendNotificationWithRoles(
			AccountEntry accountEntry, String expectedBcc,
			int expectedNotificationQueueEntriesCount,
			String expectedToEmailAddress,
			NotificationTemplate notificationTemplate)
		throws Exception {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectField"
					).build()));

		if (accountEntry != null) {
			ObjectDefinition accountEntryObjectDefinition =
				objectDefinitionLocalService.fetchObjectDefinition(
					TestPropsValues.getCompanyId(),
					AccountEntry.class.getSimpleName());

			objectDefinition =
				objectDefinitionLocalService.enableAccountEntryRestricted(
					objectRelationshipLocalService.addObjectRelationship(
						null, TestPropsValues.getUserId(),
						accountEntryObjectDefinition.getObjectDefinitionId(),
						objectDefinition.getObjectDefinitionId(), 0,
						ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString()),
						"relationship", false,
						ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null));
		}

		objectDefinition =
			objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_NOTIFICATION,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"notificationTemplateId",
				notificationTemplate.getNotificationTemplateId()
			).build(),
			false);

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			dtoConverterContext, objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"r_relationship_accountEntryId",
						() -> {
							if (accountEntry == null) {
								return null;
							}

							return accountEntry.getAccountEntryId();
						}
					).put(
						"textObjectField", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectEntryLocalService.deleteObjectEntry(objectEntry.getId());

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertEquals(
			notificationQueueEntries.toString(),
			expectedNotificationQueueEntriesCount,
			notificationQueueEntries.size());

		if (expectedNotificationQueueEntriesCount == 0) {
			return;
		}

		_assertNotificationQueueEntry(
			expectedBcc, false, expectedToEmailAddress,
			notificationQueueEntries.get(0));

		notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntries.get(0));

		objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	private static Map<String, Object> _freeMarkerTermValues;
	private static HttpServletRequest _originalHttpServletRequest;

	@Inject
	private static Portal _portal;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}