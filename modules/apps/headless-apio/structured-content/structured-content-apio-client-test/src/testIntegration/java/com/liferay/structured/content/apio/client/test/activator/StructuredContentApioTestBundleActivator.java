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

package com.liferay.structured.content.apio.client.test.activator;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Ruben Pulido
 */
public class StructuredContentApioTestBundleActivator
	implements BundleActivator {

	public static final String NOT_A_SITE_MEMBER_EMAIL_ADDRESS =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"NotASiteMemberUser@liferay.com";

	public static final String SITE_MEMBER_EMAIL_ADDRESS =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"SiteMemberUser@liferay.com";

	public static final String SITE_NAME =
		StructuredContentApioTestBundleActivator.class.getSimpleName() + "Site";

	public static final String TITLE_1_LOCALE_ES =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"Title1_es";

	public static final String TITLE_2_LOCALE_DEFAULT =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"Title2_DefaultLocale";

	public static final String TITLE_2_LOCALE_ES =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"Title2_es";

	public static final String TITLE_NO_GUEST_NO_GROUP =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"NoGuestNoGroupTitle";

	public static final String TITLE_NO_GUEST_YES_GROUP =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"NoGuestYesGroupTitle";

	public static final String TITLE_YES_GUEST_YES_GROUP =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"YesGuestYesGroupTitle";

	@Override
	public void start(BundleContext bundleContext) {
		_autoCloseables = new ArrayList<>();

		_ddmFormDeserializerTracker = bundleContext.getService(
			bundleContext.getServiceReference(
				DDMFormDeserializerTracker.class));

		try {
			_prepareTest();
		}
		catch (Exception e) {
			_cleanUp();

			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();
	}

	protected DDMForm deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private JournalArticle _addJournalArticle(
			Map<Locale, String> stringMap, long userId, long groupId,
			Locale defaultLocale, boolean addGuestPermissions,
			boolean addGroupPermissions)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(addGroupPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, StringUtil.randomId(),
			false, stringMap, stringMap, stringMap, null, defaultLocale, null,
			true, true, serviceContext);

		_autoCloseables.add(
			() -> JournalArticleLocalServiceUtil.deleteArticle(journalArticle));

		return journalArticle;
	}

	private JournalArticle _addJournalArticle(
			Map<Locale, String> stringMap, long userId, long groupId,
			String content, DDMStructure ddmStructure, DDMTemplate ddmTemplate)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.addArticle(
				userId, groupId,
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, stringMap,
				null, content, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey(), serviceContext);

		_autoCloseables.add(
			() -> JournalArticleLocalServiceUtil.deleteArticle(journalArticle));

		return journalArticle;
	}

	private JournalArticle _addJournalArticle(
			String title, long userId, long groupId,
			boolean addGuestPermissions, boolean addGroupPermissions)
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.getDefault(), title);
			}
		};

		return _addJournalArticle(
			stringMap, userId, groupId, LocaleUtil.getDefault(),
			addGuestPermissions, addGroupPermissions);
	}

	private User _addUser(String emailAddress, long companyId, long groupId)
		throws Exception {

		User existingUser = UserLocalServiceUtil.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (existingUser != null) {
			UserLocalServiceUtil.deleteUser(existingUser.getUserId());
		}

		User user = UserLocalServiceUtil.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, false, Constants.TEST,
			Constants.TEST, true, StringUtil.randomString(20), emailAddress, 0,
			null, PortalUtil.getSiteDefaultLocale(groupId),
			StringUtil.randomString(20), null, StringUtil.randomString(10), 0,
			0, true, 1, 1, 2000, null, new long[] {groupId}, new long[0],
			new long[0], new long[0], false, new ServiceContext());

		_autoCloseables.add(
			() -> UserLocalServiceUtil.deleteUser(user.getUserId()));

		return user;
	}

	private void _cleanUp() {
		Collections.reverse(_autoCloseables);

		for (AutoCloseable autoCloseable : _autoCloseables) {
			try {
				autoCloseable.close();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private DDMStructure _getDDMStructureWithNestedField(Group group)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class), null,
			StructuredContentApioTestBundleActivator.SITE_NAME,
			deserialize(_read("test-journal-structured-nested-fields.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	private void _prepareDataForLocalizationTests(User user, Group group)
		throws Exception {

		Map<Locale, String> titleMap1 = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.SPAIN, TITLE_1_LOCALE_ES);
			}
		};

		_addJournalArticle(
			titleMap1, user.getUserId(), group.getGroupId(), LocaleUtil.SPAIN,
			true, true);

		DDMStructure ddmStructure = _getDDMStructureWithNestedField(group);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-journal-template-nested-fields.xsl"), LocaleUtil.US);

		Map<Locale, String> titleMap2 = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.getDefault(), TITLE_2_LOCALE_DEFAULT);
				put(LocaleUtil.SPAIN, TITLE_2_LOCALE_ES);
			}
		};

		_addJournalArticle(
			titleMap2, user.getUserId(), group.getGroupId(),
			_read("test-journal-content-nested-fields.xml"), ddmStructure,
			ddmTemplate);
	}

	private void _prepareTest() throws Exception {
		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), SITE_NAME);

		Group group = GroupLocalServiceUtil.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(SITE_NAME),
			true, true, ServiceContextTestUtil.getServiceContext());

		_autoCloseables.add(() -> GroupLocalServiceUtil.deleteGroup(group));

		_addUser(
			SITE_MEMBER_EMAIL_ADDRESS, TestPropsValues.getCompanyId(),
			group.getGroupId());

		_addUser(
			NOT_A_SITE_MEMBER_EMAIL_ADDRESS, TestPropsValues.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID);

		_addJournalArticle(
			TITLE_NO_GUEST_NO_GROUP, user.getUserId(), group.getGroupId(),
			false, false);

		_addJournalArticle(
			TITLE_NO_GUEST_YES_GROUP, user.getUserId(), group.getGroupId(),
			false, true);

		_addJournalArticle(
			TITLE_YES_GUEST_YES_GROUP, user.getUserId(), group.getGroupId(),
			true, true);

		_prepareDataForLocalizationTests(user, group);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/com/liferay/structured/content/apio/client/test/activator/" +
				fileName);

		return StringUtil.read(inputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentApioTestBundleActivator.class);

	private List<AutoCloseable> _autoCloseables;
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

}