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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class MBMessageIndexerIndexedFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();
		setUpIndexedFieldsFixture();
		setUpMBMessageIndexerFixture();
		setUpMBMessageFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		String searchTerm = "新規";

		mbMessageFixture.updateDisplaySettings(locale);

		MBMessage mbMessage = mbMessageFixture.createMBMessageWithCategory(
			searchTerm);

		Document document = mbMessageIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(mbMessage), document, searchTerm);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpMBMessageFixture() throws PortalException {
		mbMessageFixture = new MBFixture(_group);

		_mbMessages = mbMessageFixture.getMbMessages();

		_mbCategories = mbMessageFixture.getMbCategories();

		_mbThreads = mbMessageFixture.getMbThreads();
	}

	protected void setUpMBMessageIndexerFixture() {
		mbMessageIndexerFixture = new IndexerFixture<>(MBMessage.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		_users = userSearchFixture.getUsers();
	}

	protected IndexedFieldsFixture indexedFieldsFixture;
	protected MBFixture mbMessageFixture;
	protected IndexerFixture<MBMessage> mbMessageIndexerFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(MBMessage mbMessage)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(Field.CATEGORY_ID, String.valueOf(mbMessage.getCategoryId()));
		map.put(
			Field.CLASS_NAME_ID, String.valueOf(mbMessage.getClassNameId()));
		map.put(Field.CLASS_PK, String.valueOf(mbMessage.getClassPK()));
		map.put(Field.COMPANY_ID, String.valueOf(mbMessage.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, MBMessage.class.getName());
		map.put(Field.ENTRY_CLASS_PK, String.valueOf(mbMessage.getMessageId()));
		map.put(Field.GROUP_ID, String.valueOf(mbMessage.getGroupId()));
		map.put(
			Field.ROOT_ENTRY_CLASS_PK,
			String.valueOf(mbMessage.getRootMessageId()));
		map.put(Field.SCOPE_GROUP_ID, String.valueOf(mbMessage.getGroupId()));
		map.put(Field.STAGING_GROUP, String.valueOf(_group.isStagingGroup()));
		map.put(Field.STATUS, String.valueOf(mbMessage.getStatus()));
		map.put(Field.USER_ID, String.valueOf(mbMessage.getUserId()));
		map.put(Field.USER_NAME, StringUtil.lowerCase(mbMessage.getUserName()));
		map.put("answer", "false");
		map.put("answer_String_sortable", "false");
		map.put("discussion", "false");
		map.put(
			"parentMessageId", String.valueOf(mbMessage.getParentMessageId()));
		map.put("question", "false");
		map.put("threadId", String.valueOf(mbMessage.getThreadId()));
		map.put("visible", "true");

		indexedFieldsFixture.populatePriority("0.0", map);
		indexedFieldsFixture.populateUID(
			MBMessage.class.getName(), mbMessage.getMessageId(), map);
		indexedFieldsFixture.populateViewCount(
			MBMessage.class, mbMessage.getMessageId(), map);

		_populateDates(mbMessage, map);
		_populateRoles(mbMessage, map);
		_populateTitleContent(mbMessage, map);

		return map;
	}

	private void _populateDates(MBMessage mbMessage, Map<String, String> map) {
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, mbMessage.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, mbMessage.getModifiedDate(), map);
		indexedFieldsFixture.populateDate(
			Field.PUBLISH_DATE, mbMessage.getModifiedDate(), map);
		indexedFieldsFixture.populateExpirationDateWithForever(map);
	}

	private void _populateRoles(MBMessage mbMessage, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			mbMessage.getCompanyId(), MBMessage.class.getName(),
			mbMessage.getMessageId(), mbMessage.getGroupId(), null, map);
	}

	private void _populateTitleContent(
		MBMessage mbMessage, Map<String, String> map) {

		for (Locale locale :
				LanguageUtil.getAvailableLocales(mbMessage.getGroupId())) {

			String title = StringUtil.lowerCase(mbMessage.getSubject());

			String languageId = LocaleUtil.toLanguageId(locale);

			String key = "localized_title_" + languageId;

			map.put(
				LocalizationUtil.getLocalizedName(Field.CONTENT, languageId),
				_processContent(mbMessage));
			map.put(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				mbMessage.getSubject());

			map.put(key, title);
			map.put("localized_title", title);
			map.put(key.concat("_sortable"), title);
		}
	}

	private String _processContent(MBMessage message) {
		String content = message.getBody();

		if (message.isFormatBBCode()) {
			content = BBCodeTranslatorUtil.getHTML(content);
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<MBCategory> _mbCategories;

	@DeleteAfterTestRun
	private List<MBMessage> _mbMessages;

	@DeleteAfterTestRun
	private List<MBThread> _mbThreads;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}