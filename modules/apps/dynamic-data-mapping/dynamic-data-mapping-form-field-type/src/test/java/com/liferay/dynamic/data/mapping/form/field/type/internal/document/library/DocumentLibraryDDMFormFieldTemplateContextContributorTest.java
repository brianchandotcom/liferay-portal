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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.form.item.selector.criterion.DDMUserPersonalFolderItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.HtmlImpl;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pedro Queiroz
 */
public class DocumentLibraryDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpCompanyLocalService();
		_setUpDLAppService();
		_setUpFileEntry();
		_setUpGroupLocalService();
		_setUpHtml();
		_setUpItemSelector();
		_setUpJSONFactory();
		_setUpJSONFactoryUtil();
		_setUpModelResourcePermission();
		_setUpParamUtil();
		_setUpPortal();
		_setUpPortletFileRepository();
		_setUpPortletURLFactoryUtil();
		_setUpUserLocalService();
	}

	@Test
	public void testDDMFormPortletItemSelector() {
		_mockDDMFormPortletItemSelector();

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			_createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setPortletNamespace(
			_PORTLET_NAMESPACE_DDM_FORM);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("itemSelectorURL"));
	}

	@Test
	public void testGetParametersForAllowedGuestUser() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		ddmFormField.setProperty("allowGuestUsers", true);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertTrue((boolean)parameters.get("allowGuestUsers"));
		Assert.assertEquals(_FORMS_FOLDER_ID, parameters.get("folderId"));

		String guestUploadURL = String.valueOf(
			parameters.get("guestUploadURL"));

		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString(
				"param_javax.portlet.action=/dynamic_data_mapping_form" +
					"/upload_file_entry"));
		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString(
				"param_formInstanceId=" + _FORM_INSTANCE_ID));
		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString("param_groupId=" + _GROUP_ID));
		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString("param_folderId=" + _FORMS_FOLDER_ID));
	}

	@Test
	public void testGetParametersForGuestUser() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertFalse(parameters.containsKey("folderId"));
		Assert.assertFalse(parameters.containsKey("guestUploadURL"));
	}

	@Test
	public void testGetParametersForSignedInUser() {
		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertEquals(_PRIVATE_FOLDER_ID, parameters.get("folderId"));
		Assert.assertFalse(parameters.containsKey("guestUploadURL"));
		Assert.assertTrue(parameters.containsKey("itemSelectorURL"));
	}

	@Test
	public void testGetParametersForUserWithoutPermission() throws Exception {
		Mockito.when(
			_modelResourcePermission.contains(
				Matchers.any(PermissionChecker.class), Mockito.anyLong(),
				Mockito.anyString())
		).thenReturn(
			false
		);

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertFalse(parameters.containsKey("folderId"));
		Assert.assertFalse(parameters.containsKey("itemSelectorURL"));
		Assert.assertTrue(
			(boolean)parameters.get("showUploadPermissionMessage"));
	}

	@Test
	public void testGetParametersShouldContainFileEntryURL() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("fileEntryURL"));
	}

	@Test
	public void testGetParametersShouldContainMaximumRepetitions() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		ddmFormField.setProperty("maximumRepetitions", 8);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(8, parameters.get("maximumRepetitions"));
	}

	@Test
	public void testGetParametersShouldUseExistingGuestUploadURL() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		ddmFormField.setProperty("allowGuestUsers", true);

		String expectedGuestUploadURL = RandomTestUtil.randomString();

		ddmFormField.setProperty("guestUploadURL", expectedGuestUploadURL);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(
			expectedGuestUploadURL,
			String.valueOf(parameters.get("guestUploadURL")));
	}

	@Test
	public void testGetParametersShouldUseExistingItemSelectorURL() {
		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		String expectedItemSelectorURL = RandomTestUtil.randomString();

		ddmFormField.setProperty("itemSelectorURL", expectedItemSelectorURL);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(
			expectedItemSelectorURL,
			String.valueOf(parameters.get("itemSelectorURL")));
	}

	@Test
	public void testGetParametersShouldUseFileEntryTitle() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertEquals("New Title", parameters.get("fileEntryTitle"));
	}

	@Test
	public void testGetParametersWithNullGroupShouldContainItemSelectorURL() {
		_mockGroupLocalServiceFetchGroup(null);

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			_scopeGroup
		);

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("itemSelectorURL"));
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setHttpServletRequest(
			_createHttpServletRequest());
		ddmFormFieldRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);
		ddmFormFieldRenderingContext.setProperty("groupId", _GROUP_ID);
		ddmFormFieldRenderingContext.setValue(
			JSONUtil.put(
				"groupId", _GROUP_ID
			).put(
				"title", "File Title"
			).put(
				"uuid", _FILE_ENTRY_UUID
			).toString());

		return ddmFormFieldRenderingContext;
	}

	private HttpServletRequest _createHttpServletRequest() {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setParameter(
			"formInstanceId", String.valueOf(_FORM_INSTANCE_ID));

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		themeDisplay.setPermissionChecker(
			Mockito.mock(PermissionChecker.class));

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	private DocumentLibraryDDMFormFieldTemplateContextContributor _createSpy(
		ThemeDisplay themeDisplay) {

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = Mockito.spy(
				_documentLibraryDDMFormFieldTemplateContextContributor);

		Mockito.doReturn(
			_resourceBundle
		).when(
			documentLibraryDDMFormFieldTemplateContextContributor
		).getResourceBundle(
			Matchers.any(Locale.class)
		);

		Mockito.doReturn(
			themeDisplay
		).when(
			documentLibraryDDMFormFieldTemplateContextContributor
		).getThemeDisplay(
			Matchers.any(HttpServletRequest.class)
		);

		return documentLibraryDDMFormFieldTemplateContextContributor;
	}

	private Company _mockCompany() {
		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getMx()
		).thenReturn(
			"liferay.com"
		);

		return company;
	}

	private void _mockDDMFormPortletItemSelector() {
		Mockito.when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(), Mockito.eq(_group), Mockito.eq(_GROUP_ID),
				Mockito.eq(
					_PORTLET_NAMESPACE_DDM_FORM + "selectDocumentLibrary"),
				Mockito.any(DDMUserPersonalFolderItemSelectorCriterion.class))
		).thenReturn(
			new MockLiferayPortletURL()
		);
	}

	private Folder _mockFolder(long folderId) {
		Folder folder = Mockito.mock(Folder.class);

		Mockito.when(
			folder.getFolderId()
		).thenReturn(
			folderId
		);

		return folder;
	}

	private void _mockGroupLocalServiceFetchGroup(Group group) {
		Mockito.when(
			_groupLocalService.fetchGroup(_GROUP_ID)
		).thenReturn(
			group
		);
	}

	private Repository _mockRepository() {
		Repository repository = Mockito.mock(Repository.class);

		Mockito.when(
			repository.getRepositoryId()
		).thenReturn(
			_REPOSITORY_ID
		);

		return repository;
	}

	private ThemeDisplay _mockThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			themeDisplay.getPathContext()
		).thenReturn(
			"/my/path/context/"
		);

		Mockito.when(
			themeDisplay.getPathThemeImages()
		).thenReturn(
			"/my/theme/images/"
		);

		User user = _mockUser();

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			user
		);

		return themeDisplay;
	}

	private User _mockUser() {
		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getScreenName()
		).thenReturn(
			"Test"
		);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			0L
		);

		return user;
	}

	private void _setUpCompanyLocalService() throws Exception {
		CompanyLocalService companyLocalService = Mockito.mock(
			CompanyLocalService.class);

		Company company = _mockCompany();

		Mockito.when(
			companyLocalService.getCompany(_COMPANY_ID)
		).thenReturn(
			company
		);

		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_companyLocalService", companyLocalService);
	}

	private void _setUpDLAppService() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_dlAppService", _dlAppService);

		Mockito.when(
			_dlAppService.getFileEntryByUuidAndGroupId(
				_FILE_ENTRY_UUID, _GROUP_ID)
		).thenReturn(
			_fileEntry
		);

		Folder folder = _mockFolder(_PRIVATE_FOLDER_ID);

		Mockito.when(
			_dlAppService.getFolder(_REPOSITORY_ID, _FORMS_FOLDER_ID, "Test")
		).thenReturn(
			folder
		);
	}

	private void _setUpFileEntry() {
		_fileEntry.setUuid(_FILE_ENTRY_UUID);
		_fileEntry.setGroupId(_GROUP_ID);

		Mockito.when(
			_fileEntry.getTitle()
		).thenReturn(
			"New Title"
		);
	}

	private void _setUpGroupLocalService() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_groupLocalService", _groupLocalService);

		_mockGroupLocalServiceFetchGroup(_group);
	}

	private void _setUpHtml() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor, "_html",
			_html);
	}

	private void _setUpItemSelector() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_itemSelector", _itemSelector);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(),
				Mockito.argThat(
					new ArgumentMatcher<Group>() {

						@Override
						public boolean matches(Object object) {
							Group group = (Group)object;

							if ((group == _group) || (group == _scopeGroup)) {
								return true;
							}

							return false;
						}

					}),
				Mockito.eq(_GROUP_ID),
				Mockito.eq(_PORTLET_NAMESPACE + "selectDocumentLibrary"),
				Mockito.any(DDMUserPersonalFolderItemSelectorCriterion.class),
				Mockito.any(FileItemSelectorCriterion.class))
		).thenReturn(
			new MockLiferayPortletURL()
		);
	}

	private void _setUpJSONFactory() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_jsonFactory", _jsonFactory);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpModelResourcePermission() throws Exception {
		Mockito.when(
			_modelResourcePermission.contains(
				Matchers.any(PermissionChecker.class), Mockito.anyLong(),
				Mockito.anyString())
		).thenReturn(
			true
		);

		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_dlFolderModelResourcePermission", _modelResourcePermission);
	}

	private void _setUpParamUtil() {
		PropsUtil.setProps(Mockito.mock(Props.class));
	}

	private void _setUpPortal() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor, "_portal",
			_portal);

		Mockito.when(
			_portal.getPortletNamespace(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM)
		).thenReturn(
			_PORTLET_NAMESPACE_DDM_FORM
		);
	}

	private void _setUpPortletFileRepository() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_portletFileRepository", _portletFileRepository);

		Folder folder = _mockFolder(_FORMS_FOLDER_ID);

		Mockito.when(
			_portletFileRepository.getPortletFolder(
				_REPOSITORY_ID, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DDMFormConstants.DDM_FORM_UPLOADED_FILES_FOLDER_NAME)
		).thenReturn(
			folder
		);

		Repository repository = _mockRepository();

		Mockito.when(
			_portletFileRepository.fetchPortletRepository(
				_GROUP_ID, DDMFormConstants.SERVICE_NAME)
		).thenReturn(
			repository
		);
	}

	private void _setUpPortletURLFactoryUtil() {
		PortletURLFactoryUtil portletURLFactoryUtil =
			new PortletURLFactoryUtil();

		PortletURLFactory portletURLFactory = Mockito.mock(
			PortletURLFactory.class);

		LiferayPortletURL mockLiferayPortletURL = new MockLiferayPortletURL();

		Mockito.doReturn(
			mockLiferayPortletURL
		).when(
			portletURLFactory
		).create(
			Matchers.any(PortletRequest.class),
			Matchers.eq(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM),
			Mockito.anyString()
		);

		Mockito.doReturn(
			mockLiferayPortletURL
		).when(
			portletURLFactory
		).create(
			Matchers.any(HttpServletRequest.class),
			Matchers.eq(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM),
			Mockito.anyLong(), Mockito.anyString()
		);

		portletURLFactoryUtil.setPortletURLFactory(portletURLFactory);
	}

	private void _setUpUserLocalService() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			"_userLocalService", _userLocalService);

		User user = _mockUser();

		Mockito.when(
			_userLocalService.getUserByEmailAddress(
				_COMPANY_ID,
				DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME +
					"@liferay.com")
		).thenReturn(
			user
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _FILE_ENTRY_UUID =
		RandomTestUtil.randomString();

	private static final long _FORM_INSTANCE_ID = RandomTestUtil.randomLong();

	private static final long _FORMS_FOLDER_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _PORTLET_NAMESPACE =
		RandomTestUtil.randomString();

	private static final String _PORTLET_NAMESPACE_DDM_FORM =
		"_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_";

	private static final long _PRIVATE_FOLDER_ID = RandomTestUtil.randomLong();

	private static final long _REPOSITORY_ID = RandomTestUtil.randomLong();

	private final DLAppService _dlAppService = Mockito.mock(DLAppService.class);
	private final DocumentLibraryDDMFormFieldTemplateContextContributor
		_documentLibraryDDMFormFieldTemplateContextContributor =
			new DocumentLibraryDDMFormFieldTemplateContextContributor();
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final Html _html = new HtmlImpl();
	private final ItemSelector _itemSelector = Mockito.mock(ItemSelector.class);
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final ModelResourcePermission<?> _modelResourcePermission =
		Mockito.mock(ModelResourcePermission.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final PortletFileRepository _portletFileRepository = Mockito.mock(
		PortletFileRepository.class);
	private final ResourceBundle _resourceBundle = Mockito.mock(
		ResourceBundle.class);
	private final Group _scopeGroup = Mockito.mock(Group.class);
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}