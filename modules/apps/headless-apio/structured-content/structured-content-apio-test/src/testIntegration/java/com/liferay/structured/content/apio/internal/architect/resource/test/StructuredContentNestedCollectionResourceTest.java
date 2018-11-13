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

package com.liferay.structured.content.apio.internal.architect.resource.test;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.test.util.pagination.PaginationRequest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleWrapper;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class StructuredContentNestedCollectionResourceTest
	extends BaseStructuredContentNestedCollectionResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetDataTypeWithBoolean() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			deserialize(
				_ddmFormDeserializerTracker,
				read("test-journal-all-fields-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("MyBoolean");

		String dataType = getDataType(ddmFormFieldValue, ddmStructure);

		Assert.assertEquals("boolean", dataType);
	}

	@Test
	public void testGetDataTypeWithURL() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			deserialize(
				_ddmFormDeserializerTracker,
				read("test-journal-all-fields-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("MyLinkToPage");

		String dataType = getDataType(ddmFormFieldValue, ddmStructure);

		Assert.assertEquals("url", dataType);
	}

	@Test
	public void testGetInputControlWithCheckBox() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			deserialize(
				_ddmFormDeserializerTracker,
				read("test-journal-all-fields-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("MyBoolean");

		String inputControl = getInputControl(ddmFormFieldValue, ddmStructure);

		Assert.assertEquals("checkbox", inputControl);
	}

	@Test
	public void testGetInputControlWithNullInteger() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			deserialize(
				_ddmFormDeserializerTracker,
				read("test-journal-all-fields-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			read("test-journal-all-fields-template.xsl"), LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("MyInteger");

		String inputControl = getInputControl(ddmFormFieldValue, ddmStructure);

		Assert.assertNull(inputControl);
	}

	@Test
	public void testGetJournalArticleWrapper() throws Throwable {
		Map<Locale, String> stringMap = new HashMap<>();

		String title = RandomTestUtil.randomString();

		stringMap.put(LocaleUtil.getDefault(), title);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, false,
			stringMap, stringMap, stringMap, null, LocaleUtil.getDefault(),
			null, true, true, serviceContext);

		JournalArticleWrapper journalArticleWrapper = getJournalArticleWrapper(
			journalArticle.getResourcePrimKey(),
			getThemeDisplay(_group, LocaleUtil.getDefault()));

		Assert.assertEquals(
			title, journalArticleWrapper.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetJournalArticleWrapperFilterByPermission()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		String title = RandomTestUtil.randomString();

		stringMap.put(LocaleUtil.getDefault(), title);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, false,
			stringMap, stringMap, stringMap, null, LocaleUtil.getDefault(),
			null, true, true, serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(user, permissionChecker)) {

			Assertions.assertThatThrownBy(
				() -> getJournalArticleWrapper(
					journalArticle.getId(),
					getThemeDisplay(_group, LocaleUtil.getDefault()))
			).isInstanceOf(
				PrincipalException.MustHavePermission.class
			);
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItems() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(
			"Journal articles: " + journalArticles,
			journalArticles.contains(journalArticle));
	}

	@Test
	public void testGetPageItemsFilterByPermission() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(user, permissionChecker)) {

			PageItems<JournalArticle> pageItems = getPageItems(
				PaginationRequest.of(10, 1), _group.getGroupId(),
				_acceptLanguage,
				getThemeDisplay(_group, LocaleUtil.getDefault()),
				Filter.emptyFilter(), Sort.emptySort());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWith2VersionsAnd1Scheduled() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, true,
			serviceContext);

		// Create new version scheduled for tomorrow

		stringMap.put(LocaleUtil.getDefault(), "Version 2");

		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000);

		JournalTestUtil.updateArticle(
			serviceContext.getUserId(), journalArticle, stringMap,
			journalArticle.getContent(), displayDate, true, true,
			serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(2, journalArticlesCount);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(
			"Journal articles: " + journalArticles,
			journalArticles.contains(journalArticle));

		JournalArticle foundJournalArticle = journalArticles.get(0);

		Assert.assertEquals(
			"Version 1", foundJournalArticle.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageItemsWith2VersionsAndOnly1Approved()
		throws Exception {

		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, true, true,
			serviceContext);

		// Create new version as a draft

		JournalTestUtil.updateArticle(
			journalArticle, "Version 2", journalArticle.getContent(), true,
			false, serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(2, journalArticlesCount);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(
			"Journal articles: " + journalArticles,
			journalArticles.contains(journalArticle));

		JournalArticle foundJournalArticle = journalArticles.get(0);

		Assert.assertEquals(
			"Version 1", foundJournalArticle.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageItemsWith2VersionsApproved() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, true,
			serviceContext);

		journalArticle = JournalTestUtil.updateArticle(
			journalArticle, "Version 2", journalArticle.getContent(), true,
			true, serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(2, journalArticlesCount);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<JournalArticle> journalArticles =
			(List<JournalArticle>)pageItems.getItems();

		Assert.assertTrue(
			"Journal articles: " + journalArticles,
			journalArticles.contains(journalArticle));

		JournalArticle foundJournalArticle = journalArticles.get(0);

		Assert.assertEquals(
			"Version 2", foundJournalArticle.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageItemsWithOnlyOneDraftVersion() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, false,
			serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(1, journalArticlesCount);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWithOnlyOneExpiredVersion() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), null, null, true, true,
			serviceContext);

		_journalArticleLocalService.updateStatus(
			serviceContext.getUserId(), journalArticle,
			WorkflowConstants.STATUS_EXPIRED, null, serviceContext,
			new HashMap<>());

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(1, journalArticlesCount);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWithOnlyOneSheduledVersion() throws Exception {
		Map<Locale, String> stringMap = new HashMap<>();

		stringMap.put(LocaleUtil.getDefault(), "Version 1");
		stringMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		stringMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000); // Tomorrow

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), false, stringMap, stringMap,
			stringMap, null, LocaleUtil.getDefault(), displayDate, null, true,
			true, serviceContext);

		int journalArticlesCount = _journalArticleLocalService.getArticlesCount(
			journalArticle.getGroupId(), journalArticle.getArticleId());

		Assert.assertEquals(1, journalArticlesCount);

		PageItems<JournalArticle> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId(), _acceptLanguage,
			getThemeDisplay(_group, LocaleUtil.getDefault()),
			Filter.emptyFilter(), Sort.emptySort());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	private static final AcceptLanguage _acceptLanguage =
		() -> LocaleUtil.getDefault();

	@Inject
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}