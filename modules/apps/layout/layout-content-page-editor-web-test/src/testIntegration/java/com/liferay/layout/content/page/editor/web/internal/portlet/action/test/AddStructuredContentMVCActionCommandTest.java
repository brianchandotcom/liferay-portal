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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.net.URL;

import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AddStructuredContentMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	@Test
	public void testAddStructuredContentInvalidStructureWithFieldImageBase64Png()
		throws Exception {

		_testAddStructuredContentInvalidStructureWithFieldImage(
			"data:image/png;base64,iVB");
	}

	@Test
	public void testAddStructuredContentInvalidStructureWithFieldImageURL()
		throws Exception {

		_testAddStructuredContentInvalidStructureWithFieldImage(
			"http://nonexistingimage");
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageBase64Bmp()
		throws Exception {

		String fieldValue = _read("liferay_base_64_bmp.txt");

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1]);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageBase64Gif()
		throws Exception {

		String fieldValue = _read("liferay_base_64_gif.txt");

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1]);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageBase64Jpeg()
		throws Exception {

		String fieldValue = _read("liferay_base_64_jpeg.txt");

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1]);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageBase64Jpg()
		throws Exception {

		String fieldValue = _read("liferay_base_64_jpg.txt");

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1]);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageBase64Png()
		throws Exception {

		String fieldValue = _read("liferay_base_64_png.txt");

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1]);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageDocumentLibraryBmp()
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
			"bmp", ContentTypes.IMAGE_BMP, true);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageDocumentLibraryBmpAbsoluteURL()
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
			"bmp", ContentTypes.IMAGE_BMP, false);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageDocumentLibraryGif()
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
			"gif", ContentTypes.IMAGE_GIF, true);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageDocumentLibraryJpeg()
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
			"jpeg", ContentTypes.IMAGE_JPEG, true);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageDocumentLibraryJpg()
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
			"jpg", ContentTypes.IMAGE_JPEG, true);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldImageDocumentLibraryPng()
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
			"png", ContentTypes.IMAGE_PNG, true);
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldText()
		throws Exception {

		String fieldValue = StringUtil.randomString(10);

		_testAddStructuredContentValidStructureWithField(
			DDMFormFieldType.TEXT, StringUtil.randomString(10), fieldValue,
			StringUtil.randomString(10),
			actualFieldValue -> Assert.assertEquals(
				fieldValue, actualFieldValue));
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldTextArea()
		throws Exception {

		String fieldValue = StringUtil.randomString(10);

		_testAddStructuredContentValidStructureWithField(
			DDMFormFieldType.TEXT_AREA, StringUtil.randomString(10), fieldValue,
			StringUtil.randomString(10),
			actualFieldValue -> Assert.assertEquals(
				fieldValue, actualFieldValue));
	}

	@Test
	public void testAddStructuredContentValidStructureWithFieldTextHTML()
		throws Exception {

		String fieldValue = StringUtil.randomString(10);

		_testAddStructuredContentValidStructureWithField(
			DDMFormFieldType.TEXT_HTML, StringUtil.randomString(10), fieldValue,
			StringUtil.randomString(10),
			actualFieldValue -> Assert.assertEquals(
				fieldValue, actualFieldValue));
	}

	@Test
	public void testAddTwoStructuredContentsValidStructureWithFieldImageSameFieldNameSameTitle()
		throws Exception {

		String fieldName = StringUtil.randomString(10);
		String fieldValue = _read("liferay_base_64_bmp.txt");
		String title = StringUtil.randomString(10);

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1], fieldName, title);
		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, fieldValue.split("base64,")[1], fieldName, title);
	}

	private DDMStructure _addDDMStructure(DDMFormField... ddmFormFields)
		throws Exception {

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);

		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmForm.addDDMFormField(ddmFormField);
		}

		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormTestUtil.setIndexTypeProperty(ddmForm, "none");

		return DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);
	}

	private JSONObject _addStructuredContentStructureWithField(
			String fieldType, String fieldName, String fieldValue, String title)
		throws Exception {

		DDMStructure ddmStructure = _addDDMStructure(
			DDMFormTestUtil.createDDMFormField(
				fieldName, StringUtil.randomString(10), fieldType, "string",
				false, false, false));

		DDMFormValues ddmFormValues = _getDDMFormValues(
			ddmStructure, fieldName, fieldValue);

		MockActionRequest mockActionRequest = _getMockActionRequest(
			ddmFormValues, ddmStructure.getStructureId(), title);

		return ReflectionTestUtil.invoke(
			_mvcActionCommand, "addJournalArticle",
			new Class<?>[] {ActionRequest.class}, mockActionRequest);
	}

	private byte[] _getBytes(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return FileUtil.getBytes(inputStream);
	}

	private DDMFormValues _getDDMFormValues(
		DDMStructure ddmStructure, String fieldName, String fieldValue) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmStructure.getDDMForm());

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				fieldName, fieldValue));

		return ddmFormValues;
	}

	private String _getFieldValue(
			JournalArticle journalArticle, String fieldName)
		throws DocumentException {

		Document document = SAXReaderUtil.read(journalArticle.getContent());

		Node node = document.selectSingleNode(
			String.format(
				"/root/dynamic-element[@name='%s']/dynamic-content",
				fieldName));

		return node.getText();
	}

	private MockActionRequest _getMockActionRequest(
			DDMFormValues ddmFormValues, long ddmStructureId, String title)
		throws PortalException {

		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockActionRequest.addParameter(
			"ddmFormValues", _ddm.getDDMFormValuesJSONString(ddmFormValues));

		mockActionRequest.addParameter(
			"ddmStructureId", String.valueOf(ddmStructureId));

		mockActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		mockActionRequest.addParameter("title", title);

		return mockActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _testAddStructuredContentInvalidStructureWithField(
			String fieldType, String fieldName, String fieldValue, String title,
			UnsafeConsumer<JSONObject, Exception> errorValidator)
		throws Exception {

		List<JournalArticle> originalJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JSONObject jsonObject = _addStructuredContentStructureWithField(
			fieldType, fieldName, fieldValue, title);

		List<JournalArticle> actualJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			actualJournalArticles.toString(), originalJournalArticles.size(),
			actualJournalArticles.size());

		errorValidator.accept(jsonObject);
	}

	private void _testAddStructuredContentInvalidStructureWithFieldImage(
			String fieldValue)
		throws Exception {

		String fieldName = StringUtil.randomString(10);
		String title = StringUtil.randomString(10);

		_testAddStructuredContentInvalidStructureWithField(
			DDMFormFieldType.IMAGE, fieldName, fieldValue, title,
			jsonObject -> Assert.assertEquals(
				"image-content-is-invalid-for-field-x",
				jsonObject.getString("errorMessage")));
	}

	private void _testAddStructuredContentValidStructureWithField(
			String fieldType, String fieldName, String fieldValue, String title,
			UnsafeConsumer<String, Exception> fieldValueValidator)
		throws Exception {

		List<JournalArticle> originalJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JSONObject jsonObject = _addStructuredContentStructureWithField(
			fieldType, fieldName, fieldValue, title);

		List<JournalArticle> actualJournalArticles =
			_journalArticleLocalService.getArticles(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			actualJournalArticles.toString(),
			originalJournalArticles.size() + 1, actualJournalArticles.size());

		Assert.assertEquals(
			JournalArticle.class.getName(),
			_portal.getClassName(jsonObject.getLong("classNameId")));

		JournalArticle actualJournalArticle =
			_journalArticleLocalService.getLatestArticle(
				jsonObject.getLong("classPK"));

		Assert.assertEquals(title, actualJournalArticle.getTitle());

		fieldValueValidator.accept(
			_getFieldValue(actualJournalArticle, fieldName));

		Assert.assertEquals(title, jsonObject.getString("title"));
	}

	private void _testAddStructuredContentValidStructureWithFieldImage(
			String fieldValue, String expectedFieldValue)
		throws Exception {

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, expectedFieldValue, StringUtil.randomString(10),
			StringUtil.randomString(10));
	}

	private void _testAddStructuredContentValidStructureWithFieldImage(
			String fieldValue, String expectedFieldValue, String fieldName,
			String title)
		throws Exception {

		_testAddStructuredContentValidStructureWithField(
			DDMFormFieldType.IMAGE, fieldName, fieldValue, title,
			actualFieldValue -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					actualFieldValue);

				Assert.assertEquals(
					_group.getGroupId(), jsonObject.getLong("groupId"));

				String expectedTitle = title + " - " + fieldName;

				String actualTitle = jsonObject.getString("title");

				Assert.assertTrue(actualTitle.startsWith(expectedTitle));

				Assert.assertEquals(
					_group.getGroupId(), jsonObject.getLong("groupId"));

				FileEntry fileEntry =
					_dlAppLocalService.getFileEntryByUuidAndGroupId(
						jsonObject.getString("uuid"),
						jsonObject.getLong("groupId"));

				String fileEntryTitle = fileEntry.getTitle();

				Assert.assertTrue(fileEntryTitle.startsWith(expectedTitle));

				Assert.assertEquals(
					expectedFieldValue,
					Base64.encode(
						FileUtil.getBytes(fileEntry.getContentStream())));
			});
	}

	private void
			_testAddStructuredContentValidStructureWithFieldImageDocumentLibrary(
				String imageType, String contentType, boolean useRootRelative)
		throws Exception {

		User user = UserTestUtil.getAdminUser(_company.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		String fileName = "liferay." + imageType;

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName, contentType,
			_getBytes(fileName), serviceContext);

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);

		String portalURL = _company.getPortalURL(_group.getGroupId());

		String fieldValue = portalURL + previewURL;

		if (useRootRelative) {
			fieldValue = previewURL;
		}

		URL url = new URL(portalURL + previewURL);

		_testAddStructuredContentValidStructureWithFieldImage(
			fieldValue, Base64.encode(FileUtil.getBytes(url.openStream())));
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDM _ddm;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject(filter = "mvc.command.name=/content_layout/add_structured_content")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

}