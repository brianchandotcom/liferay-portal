/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.test.util.NotificationTemplateUtil;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

		_freeMarkTermValues = LinkedHashMapBuilder.<String, Object>put(
			"${ObjectField_booleanObjectField.getData()}",
			childObjectEntryValues.get("booleanObjectField")
		).put(
			"${ObjectField_dateObjectField.getData()}",
			() -> {
				SimpleDateFormat dateInfoFieldSimpleDateFormat =
					new SimpleDateFormat("M/d/yy hh:mm a");
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
			"${ObjectField_picklistObjectField.getData()}",
			() -> {
				ListEntry listEntry = (ListEntry)childObjectEntryValues.get(
					"picklistObjectField");

				return listEntry.getName();
			}
		).put(
			"${ObjectField_textObjectField.getData()}",
			childObjectEntryValues.get("textObjectField")
		).build();
	}

	@Test
	public void testFreeMarkerNotification() throws Exception {
		String body = LocalizationUtil.updateLocalization(
			LocalizedMapUtil.getLocalizedMap(
				HashMapBuilder.put(
					LanguageUtil.getLanguageId(LocaleUtil.US),
					StringUtil.merge(
						_freeMarkTermValues.keySet(), StringPool.COMMA)
				).build()),
			null, "Body", LanguageUtil.getLanguageId(LocaleUtil.US));

		_executeNotificationObjectAction(
			0,
			_addNotificationTemplate(
				body, NotificationTemplateConstants.EDITOR_TYPE_FREEMARKER,
				false,
				Collections.singletonMap(
					LocaleUtil.US,
					StringBundler.concat(
						user1.getEmailAddress(), StringPool.COMMA,
						user2.getEmailAddress()))));

		List<NotificationQueueEntry> notificationQueueEntries =
			_getNotificationQueueEntries();

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		NotificationQueueEntry notificationQueueEntry = 
			notificationQueueEntries.get(0);

		assertTermValues(
			new ArrayList<>(_freeMarkTermValues.values()),
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
				ListUtil.toString(getTermNames(), StringPool.BLANK),
				NotificationConstants.TYPE_EMAIL,
				Collections.singletonList(objectField.getObjectFieldId())));
	}

	private void _assertNotificationQueueEntry(
			String expectedFileName, boolean expectedSingleRecipient,
			String expectedToEmailAddress,
			NotificationQueueEntry notificationQueueEntry)
		throws Exception {

		Assert.assertNotNull(
			MailServiceTestUtil.getMailMessages("To", expectedToEmailAddress));

		assertTermValues(
			getTermValues(),
			ListUtil.fromString(
				notificationQueueEntry.getBody(), StringPool.COMMA));
		assertTermValues(
			getTermValues(),
			ListUtil.fromString(
				notificationQueueEntry.getSubject(), StringPool.COMMA));

		Map<String, Object> notificationRecipientSettingsMap =
			NotificationRecipientSettingUtil.
				getNotificationRecipientSettingsMap(notificationQueueEntry);

		Assert.assertEquals(
			user2.getEmailAddress() + ",bcc@liferay.com",
			notificationRecipientSettingsMap.get("bcc"));
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

		String[] expectedToEmailAddressesArray = StringUtil.split(
			expectedToEmailAddress);

		Arrays.sort(expectedToEmailAddressesArray);

		String[] actualToEmailAddressesArray = StringUtil.split(
			String.valueOf(notificationRecipientSettingsMap.get("to")));

		Arrays.sort(actualToEmailAddressesArray);

		Assert.assertArrayEquals(
			expectedToEmailAddressesArray, actualToEmailAddressesArray);

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
			ObjectDefinitionConstants.SCOPE_COMPANY);

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

	private List<NotificationQueueEntry> _getNotificationQueueEntries() {
		return ListUtil.sort(
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
				ListUtil.toString(getTermNames(), StringPool.BLANK),
				NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
				singleRecipient, Collections.singletonMap(LocaleUtil.US, to)));

		List<NotificationQueueEntry> notificationQueueEntries =
			_getNotificationQueueEntries();

		Assert.assertEquals(
			notificationQueueEntries.toString(),
			expectedNotificationQueueEntriesCount,
			notificationQueueEntries.size());

		_assertNotificationQueueEntry(
			TempFileEntryUtil.getOriginalTempFileName(fileEntry.getFileName()),
			singleRecipient, expectedToEmailAddresses.get(0),
			notificationQueueEntries.get(0));

		if (singleRecipient) {
			_assertNotificationQueueEntry(
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

	private static LinkedHashMap<String, Object> _freeMarkTermValues;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Inject
	private PortletFileRepository _portletFileRepository;

}