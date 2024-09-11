/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.NotificationRecipientLocalServiceUtil;
import com.liferay.notification.test.util.NotificationTemplateUtil;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
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
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		MailServiceTestUtil.clearMessages();
	}

	@After
	public void tearDown() {
		ObjectActionThreadLocal.setHttpServletRequest(null);
	}

	@Test
	public void testFreeMarkerNotification() throws Exception {

		// Notification triggered by admin user

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			dtoConverterContext, childObjectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.putAll(
						childObjectEntryValues
					).build();
				}
			},
			group.getGroupKey());

		Map<String, Object> termValues = _getFreeMarkerTermValues(
			childObjectDefinition,
			_objectEntryLocalService.getPersistedModel(objectEntry.getId()),
			_getTermNames(
				childObjectDefinition.getObjectDefinitionId(),
				SetUtil.fromArray(
					"Basic Information",
					childObjectDefinition.getLabel(
						LanguageUtil.getLanguageId(LocaleUtil.US)))));

		String body = StringUtil.merge(termValues.keySet(), StringPool.POUND);

		ObjectAction objectAction = _addNotificationTemplateObjectAction(
			body, ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			childObjectDefinition);

		objectEntryManager.updateObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), childObjectDefinition,
			objectEntry, group.getGroupKey());

		_assertNotificationQueueEntryTermValues(
			new ArrayList<>(termValues.values()), StringPool.POUND);

		_objectActionLocalService.deleteObjectAction(objectAction);

		// Notification triggered by guest user

		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		resourcePermissionLocalService.addResourcePermission(
			guestUser.getCompanyId(), childObjectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(guestUser.getCompanyId()), guestRole.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);
		resourcePermissionLocalService.addResourcePermission(
			guestUser.getCompanyId(), childObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(guestUser.getCompanyId()), guestRole.getRoleId(),
			ActionKeys.VIEW);

		objectAction = _addNotificationTemplateObjectAction(
			body, ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			childObjectDefinition);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(guestUser));
			PrincipalThreadLocal.setName(guestUser.getUserId());

			objectEntry = objectEntryManager.addObjectEntry(
				dtoConverterContext, childObjectDefinition,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.putAll(
							childObjectEntryValues
						).build();
					}
				},
				group.getGroupKey());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}

		termValues = _getFreeMarkerTermValues(
			childObjectDefinition,
			_objectEntryLocalService.getPersistedModel(objectEntry.getId()),
			_getTermNames(
				childObjectDefinition.getObjectDefinitionId(),
				SetUtil.fromArray(
					"Basic Information",
					childObjectDefinition.getLabel(
						LanguageUtil.getLanguageId(LocaleUtil.US)))));

		_assertNotificationQueueEntryTermValues(
			new ArrayList<>(termValues.values()), StringPool.POUND);

		_objectActionLocalService.deleteObjectAction(objectAction);
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

		executeNotificationObjectAction(
			0,
			_addNotificationTemplate(
				body, NotificationTemplateConstants.EDITOR_TYPE_FREEMARKER,
				Collections.singletonMap(
					LocaleUtil.US, "[%CURRENT_USER_FIRST_NAME%]"),
				false,
				Collections.singletonMap(
					LocaleUtil.US, user1.getEmailAddress())));

		ListEntry listEntry = (ListEntry)childObjectEntryValues.get(
			"picklistObjectField");

		_assertNotificationQueueEntryTermValues(
			Collections.singletonList(listEntry.getName()), StringPool.COMMA);
	}

	@Test
	public void testFreeMarkerNotificationWithCommerceOrder() throws Exception {
		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				TestPropsValues.getCompanyId());

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			TestPropsValues.getGroupId(), commerceCurrency.getCode());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			TestPropsValues.getUserId(), commerceChannel.getGroupId(),
			commerceCurrency);

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, TestPropsValues.getUserId(), true, true);

		ObjectDefinition commerceOrderObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), CommerceOrder.class.getName());

		Map<String, Object> termValues = _getFreeMarkerTermValues(
			commerceOrderObjectDefinition, commerceOrder,
			_getTermNames(
				commerceOrderObjectDefinition.getObjectDefinitionId(),
				SetUtil.fromArray(
					"Basic Information", "Workflow Status Information")));

		String body =
			StringUtil.merge(termValues.keySet(), StringPool.POUND) +
				StringPool.POUND;

		ObjectAction objectAction = _addNotificationTemplateObjectAction(
			body, DestinationNames.COMMERCE_PAYMENT_STATUS,
			commerceOrderObjectDefinition);

		_commerceOrderLocalService.updatePaymentStatus(
			TestPropsValues.getUserId(), commerceOrder.getCommerceOrderId(),
			CommerceOrderPaymentConstants.STATUS_PENDING);

		_assertNotificationQueueEntryTermValues(
			new ArrayList<>(termValues.values()), StringPool.POUND);

		_objectActionLocalService.deleteObjectAction(objectAction);

		_commerceOrderLocalService.deleteCommerceOrder(
			commerceOrder.getCommerceOrderId());

		_accountEntryLocalService.deleteAccountEntry(
			_accountEntryLocalService.fetchPersonAccountEntry(
				TestPropsValues.getUserId()));
	}

	@Test
	public void testFreeMarkerNotificationWithCustomService() throws Exception {
		List<NotificationQueueEntry> initialNotificationQueueEntries =
			_getNotificationQueueEntries();

		executeNotificationObjectAction(
			0,
			_addNotificationTemplate(
				"${templateComponent}",
				NotificationTemplateConstants.EDITOR_TYPE_FREEMARKER,
				Collections.singletonMap(
					LocaleUtil.US, "[%CURRENT_USER_FIRST_NAME%]"),
				false,
				Collections.singletonMap(
					LocaleUtil.US, user1.getEmailAddress())));

		List<NotificationQueueEntry> currentNotificationQueueEntries =
			_getNotificationQueueEntries();

		Assert.assertEquals(
			currentNotificationQueueEntries.toString(),
			initialNotificationQueueEntries.size() + 1,
			currentNotificationQueueEntries.size());
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
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_CC,
								"[%CURRENT_USER_EMAIL_ADDRESS%]," +
									"cc@liferay.com"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_FROM,
								"[%CURRENT_USER_EMAIL_ADDRESS%]"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_FROM_NAME,
								Collections.singletonMap(
									LocaleUtil.US,
									"[%CURRENT_USER_FIRST_NAME%]")),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_SINGLE_RECIPIENT,
								Boolean.FALSE.toString()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								role1.getName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_TO_TYPE,
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

		NotificationTemplate notificationTemplate1 =
			notificationTemplateLocalService.addNotificationTemplate(
				NotificationTemplateUtil.createNotificationContext(
					TestPropsValues.getUser(), 0, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
					Arrays.asList(
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_BCC,
								accountRole3.getRoleName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_BCC,
								organizationRole2.getName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_BCC_TYPE,
								NotificationRecipientConstants.TYPE_ROLE),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_CC,
								"[%CURRENT_USER_EMAIL_ADDRESS%]," +
									"cc@liferay.com"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_FROM,
								"[%CURRENT_USER_EMAIL_ADDRESS%]"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_FROM_NAME,
								Collections.singletonMap(
									LocaleUtil.US,
									"[%CURRENT_USER_FIRST_NAME%]")),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_SINGLE_RECIPIENT,
								Boolean.FALSE.toString()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								accountRole1.getRoleName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								accountRole2.getRoleName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								accountRole4.getRoleName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								organizationRole1.getName()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_TO_TYPE,
								NotificationRecipientConstants.TYPE_ROLE)),
					RandomTestUtil.randomString(),
					NotificationConstants.TYPE_EMAIL, Collections.emptyList()));

		_testSendNotificationWithRoles(
			null, null, 0, null, notificationTemplate1);

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
			notificationTemplate1);

		// Send email with an object definition restricted by account entry

		_testSendNotificationWithRoles(
			accountEntry1, StringPool.BLANK, 1, user1.getEmailAddress(),
			notificationTemplate1);
		_testSendNotificationWithRoles(
			accountEntry2, user2.getEmailAddress(), 1,
			StringUtil.merge(
				ListUtil.fromArray(
					user1.getEmailAddress(), user3.getEmailAddress())),
			notificationTemplate1);

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
			notificationTemplate1);

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
			1, user2.getEmailAddress(), notificationTemplate1);

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry3.getAccountEntryId(),
				childOrganization.getOrganizationId());

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry3.getAccountEntryId(),
			organization2.getOrganizationId());

		_testSendNotificationWithRoles(
			accountEntry3, user6.getEmailAddress(), 1, user2.getEmailAddress(),
			notificationTemplate1);

		NotificationTemplate notificationTemplate2 =
			notificationTemplateLocalService.addNotificationTemplate(
				NotificationTemplateUtil.createNotificationContext(
					TestPropsValues.getUser(), 0, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
					Arrays.asList(
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_BCC,
								AccountRoleConstants.
									REQUIRED_ROLE_NAME_ACCOUNT_MEMBER),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_BCC_TYPE,
								NotificationRecipientConstants.TYPE_ROLE),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_CC,
								"[%CURRENT_USER_EMAIL_ADDRESS%]," +
									"cc@liferay.com"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_FROM,
								"[%CURRENT_USER_EMAIL_ADDRESS%]"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_FROM_NAME,
								Collections.singletonMap(
									LocaleUtil.US,
									"[%CURRENT_USER_FIRST_NAME%]")),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_SINGLE_RECIPIENT,
								Boolean.FALSE.toString()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								RoleConstants.ORGANIZATION_USER),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_TO_TYPE,
								NotificationRecipientConstants.TYPE_ROLE)),
					RandomTestUtil.randomString(),
					NotificationConstants.TYPE_EMAIL, Collections.emptyList()));

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntry1.getAccountEntryId(),
			new long[] {user1.getUserId(), user2.getUserId()});
		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntry2.getAccountEntryId(), new long[] {user3.getUserId()});
		_organizationLocalService.addUserOrganization(
			user4.getUserId(), organization1.getOrganizationId());
		_organizationLocalService.addUserOrganization(
			user5.getUserId(), organization2.getOrganizationId());
		_organizationLocalService.addUserOrganization(
			user6.getUserId(), childOrganization.getOrganizationId());

		// Send email with an object definition not restricted by account entry

		_testSendNotificationWithRoles(
			null,
			StringUtil.merge(
				ListUtil.fromArray(
					user1.getEmailAddress(), user2.getEmailAddress(),
					user3.getEmailAddress())),
			1,
			StringUtil.merge(
				ListUtil.fromArray(
					user4.getEmailAddress(), user5.getEmailAddress(),
					user6.getEmailAddress())),
			notificationTemplate2);

		// Send email with an object definition restricted by account entry

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			childOrganization.getOrganizationId());
		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry2.getAccountEntryId(),
			organization1.getOrganizationId());

		_testSendNotificationWithRoles(
			accountEntry1,
			StringUtil.merge(
				ListUtil.fromArray(
					user1.getEmailAddress(), user2.getEmailAddress())),
			1,
			StringUtil.merge(
				ListUtil.fromArray(
					user5.getEmailAddress(), user6.getEmailAddress())),
			notificationTemplate2);
		_testSendNotificationWithRoles(
			accountEntry2, user3.getEmailAddress(), 1, user4.getEmailAddress(),
			notificationTemplate2);
		_testSendNotificationWithRoles(
			accountEntry3, null, 1, user5.getEmailAddress(),
			notificationTemplate2);
	}

	@Test
	public void testSendNotificationWithRolesToCurrentUser() throws Exception {
		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.addNotificationTemplate(
				NotificationTemplateUtil.createNotificationContext(
					TestPropsValues.getUser(), 0, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
					Arrays.asList(
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_FROM,
								"test@liferay.com"),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_FROM_NAME,
								Collections.singletonMap(
									LocaleUtil.US, "Test")),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.
									NAME_SINGLE_RECIPIENT,
								Boolean.FALSE.toString()),
						NotificationRecipientSettingUtil.
							createNotificationRecipientSetting(
								NotificationRecipientSettingConstants.NAME_TO,
								"[%CURRENT_USER_EMAIL_ADDRESS%]")),
					RandomTestUtil.randomString(),
					NotificationConstants.TYPE_EMAIL, Collections.emptyList()));

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, true, false, false,
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

		_addObjectAction(
			objectDefinition.getObjectDefinitionId(),
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			notificationTemplate.getNotificationTemplateId());
		_addObjectAction(
			objectDefinition.getObjectDefinitionId(),
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			notificationTemplate.getNotificationTemplateId());

		objectDefinition =
			objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String originalName = PrincipalThreadLocal.getName();

		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		String name = objectDefinition.getClassName();

		String[] actionIds = {ObjectActionKeys.ADD_OBJECT_ENTRY};

		if (ArrayUtil.contains(actionIds, ObjectActionKeys.ADD_OBJECT_ENTRY)) {
			name = objectDefinition.getResourceName();
		}

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), name,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			actionIds);

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY, "0", role.getRoleId(),
			ActionKeys.UPDATE);

		_roleLocalService.addUserRole(_user.getUserId(), role.getRoleId());

		// Notification sent on after add

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			dtoConverterContext, objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"textObjectField", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_testSendNotificationWithRolesToCurrentUser();

		// Notification sent on after update

		objectEntryManager.updateObjectEntry(
			_user.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), objectDefinition,
			objectEntry, ObjectDefinitionConstants.SCOPE_COMPANY);

		_testSendNotificationWithRolesToCurrentUser();

		_objectEntryLocalService.deleteObjectEntry(objectEntry.getId());

		objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		PermissionThreadLocal.setPermissionChecker(originalPermissionChecker);

		PrincipalThreadLocal.setName(originalName);
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
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			accountEntryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

	private NotificationTemplate _addNotificationTemplate(
			String body, String editorType, Map<Locale, String> fromName,
			boolean singleRecipient, Map<Locale, String> to)
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
					NotificationRecipientSettingUtil.
						createNotificationRecipientSetting(
							"bcc",
							"[%CURRENT_USER_EMAIL_ADDRESS%],bcc@liferay.com"),
					NotificationRecipientSettingUtil.
						createNotificationRecipientSetting(
							"cc",
							"[%CURRENT_USER_EMAIL_ADDRESS%],cc@liferay.com"),
					NotificationRecipientSettingUtil.
						createNotificationRecipientSetting(
							"from", "[%CURRENT_USER_EMAIL_ADDRESS%]"),
					NotificationRecipientSettingUtil.
						createNotificationRecipientSetting(
							"fromName", fromName),
					NotificationRecipientSettingUtil.
						createNotificationRecipientSetting(
							"singleRecipient", String.valueOf(singleRecipient)),
					NotificationRecipientSettingUtil.
						createNotificationRecipientSetting("to", to)),
				ListUtil.toString(
					getTermNames(), StringPool.BLANK, StringPool.SEMICOLON),
				NotificationConstants.TYPE_EMAIL,
				Collections.singletonList(objectField.getObjectFieldId())));
	}

	private ObjectAction _addNotificationTemplateObjectAction(
			String body, String objectActionTriggerKey,
			ObjectDefinition objectDefinition)
		throws Exception {

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.createNotificationTemplate(
				RandomTestUtil.randomInt());

		notificationTemplate.setUserId(TestPropsValues.getUserId());
		notificationTemplate.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		notificationTemplate.setBody(body);
		notificationTemplate.setDescription(RandomTestUtil.randomString());
		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_FREEMARKER);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setSubject(RandomTestUtil.randomString());
		notificationTemplate.setType(NotificationConstants.TYPE_EMAIL);

		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setAttachmentObjectFieldIds(
			Collections.emptyList());
		notificationContext.setNotificationRecipient(
			NotificationRecipientLocalServiceUtil.createNotificationRecipient(
				RandomTestUtil.randomInt()));
		notificationContext.setNotificationRecipientSettings(
			Arrays.asList(
				NotificationRecipientSettingUtil.
					createNotificationRecipientSetting(
						"from", "[%CURRENT_USER_EMAIL_ADDRESS%]"),
				NotificationRecipientSettingUtil.
					createNotificationRecipientSetting(
						"fromName",
						Collections.singletonMap(
							LocaleUtil.US, RandomTestUtil.randomString())),
				NotificationRecipientSettingUtil.
					createNotificationRecipientSetting(
						"to", "[%CURRENT_USER_EMAIL_ADDRESS%]")));
		notificationContext.setNotificationTemplate(notificationTemplate);
		notificationContext.setType(NotificationConstants.TYPE_EMAIL);

		notificationTemplate =
			notificationTemplateLocalService.addNotificationTemplate(
				notificationContext);

		return _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_NOTIFICATION,
			objectActionTriggerKey,
			UnicodePropertiesBuilder.put(
				"notificationTemplateId",
				notificationTemplate.getNotificationTemplateId()
			).build(),
			false);
	}

	private void _addObjectAction(
			long objectDefinitionId, String objectActionTriggerKey,
			long objectNotificationTemplateId)
		throws Exception {

		objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			objectDefinitionId, true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_NOTIFICATION,
			objectActionTriggerKey,
			UnicodePropertiesBuilder.put(
				"notificationTemplateId", objectNotificationTemplateId
			).build(),
			false);
	}

	private Role _addRole(int type, User user) throws Exception {
		return _roleLocalService.addRole(
			RandomTestUtil.randomString(), user.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null, type, null, null);
	}

	private void _assertNotificationQueueEntry(
		String expectedBcc, boolean expectedSingleRecipient,
		String expectedToEmailAddress,
		NotificationQueueEntry notificationQueueEntry) {

		Assert.assertNotNull(
			MailServiceTestUtil.getMailMessage(
				"To", StringUtil.split(expectedToEmailAddress)));

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

	private void _assertNotificationQueueEntryTermValues(
			List<Object> expectedTermValues, String delimiter)
		throws Exception {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntries.get(0);

		assertTermValues(
			TransformUtil.transform(
				expectedTermValues, this::parseTermValueToString),
			Arrays.asList(
				StringUtil.split(notificationQueueEntry.getBody(), delimiter)));

		notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry);
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

	private Map<String, Object> _getFreeMarkerTermValues(
			ObjectDefinition objectDefinition, PersistedModel persistedModel,
			Set<String> termNames)
		throws Exception {

		Map<String, Object> termValues = new HashMap<>();

		for (String termName : termNames) {
			termValues.put(termName, null);
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				objectDefinition.getClassName());

		InfoItemFieldValues infoItemFieldValues;

		try {
			_pushServiceContext();

			infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(
					persistedModel);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			InfoField infoField = infoFieldValue.getInfoField();

			String termName = "${" + infoField.getUniqueId() + ".getData()}";

			if (!termNames.contains(termName)) {
				continue;
			}

			Object termValue = infoFieldValue.getValue();

			if (Validator.isNull(termValue)) {
				termValue = StringPool.BLANK;
			}
			else if (termValue instanceof Date) {
				SimpleDateFormat dateInfoFieldSimpleDateFormat =
					new SimpleDateFormat(
						DateTimeFormatterBuilder.getLocalizedDateTimePattern(
							FormatStyle.SHORT, FormatStyle.SHORT,
							IsoChronology.INSTANCE, LocaleUtil.US));

				termValue = dateInfoFieldSimpleDateFormat.format(
					(Date)termValue);
			}

			termValues.put(termName, termValue);
		}

		termValues.put(
			"${portalURL}",
			_portal.getPortalURL(
				ObjectActionThreadLocal.getHttpServletRequest()));

		return termValues;
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

	private Set<String> _getTermNames(
			long objectDefinitionId, Set<String> termCategories)
		throws Exception {

		Set<String> termNames = new HashSet<>();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter(
			"objectDefinitionId", String.valueOf(objectDefinitionId));

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONArray ftlTermCategoriesJSONArray = JSONFactoryUtil.createJSONArray(
			byteArrayOutputStream.toString());

		for (int i = 0; i < ftlTermCategoriesJSONArray.length(); i++) {
			JSONObject ftlTermCategoryJSONObject =
				ftlTermCategoriesJSONArray.getJSONObject(i);

			if (!termCategories.contains(
					ftlTermCategoryJSONObject.getString("label"))) {

				continue;
			}

			JSONArray itemsJSONArray = ftlTermCategoryJSONObject.getJSONArray(
				"items");

			for (int j = 0; j < itemsJSONArray.length(); j++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(j);

				if (itemJSONObject == null) {
					continue;
				}

				termNames.add(itemJSONObject.getString("content"));
			}
		}

		return termNames;
	}

	private void _pushServiceContext() throws PortalException {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest(
			null, StringPool.BLANK, RandomTestUtil.randomString());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.US);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		ObjectActionThreadLocal.setHttpServletRequest(httpServletRequest);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setRequest(httpServletRequest);

		themeDisplay = serviceContext.getThemeDisplay();

		themeDisplay.setScopeGroupId(serviceContext.getScopeGroupId());
		themeDisplay.setSiteGroupId(serviceContext.getScopeGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
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

		executeNotificationObjectAction(
			fileEntry.getFileEntryId(),
			_addNotificationTemplate(
				ListUtil.toString(
					getTermNames(), StringPool.BLANK, StringPool.SEMICOLON),
				NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
				Collections.singletonMap(
					LocaleUtil.US, "[%CURRENT_USER_FIRST_NAME%]"),
				singleRecipient, Collections.singletonMap(LocaleUtil.US, to)));

		List<NotificationQueueEntry> notificationQueueEntries =
			_getNotificationQueueEntries();

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

		Assert.assertTrue(
			MailServiceTestUtil.lastMailMessageContains(
				ListUtil.toString(
					getTermValues(), StringPool.BLANK, StringPool.SEMICOLON)));

		MailServiceTestUtil.clearMessages();

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
				TestPropsValues.getUserId(), 0, false, true, false, false,
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

		MailServiceTestUtil.clearMessages();

		notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntries.get(0));

		objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	private void _testSendNotificationWithRolesToCurrentUser()
		throws Exception {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationEntries(
				NotificationConstants.TYPE_EMAIL,
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		Map<String, Object> notificationRecipientSettingsMap =
			NotificationRecipientSettingUtil.
				getNotificationRecipientSettingsMap(
					notificationQueueEntries.get(0));

		AssertUtils.assertEqualsSorted(
			StringUtil.split(_user.getEmailAddress()),
			StringUtil.split(
				String.valueOf(notificationRecipientSettingsMap.get("to"))));

		MailServiceTestUtil.clearMessages();

		notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntries.get(0));
	}

	@Inject
	private static Portal _portal;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Inject
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject(
		filter = "mvc.command.name=/notification_templates/notification_template_ftl_elements"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectActionTriggerRegistry _objectActionTriggerRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}