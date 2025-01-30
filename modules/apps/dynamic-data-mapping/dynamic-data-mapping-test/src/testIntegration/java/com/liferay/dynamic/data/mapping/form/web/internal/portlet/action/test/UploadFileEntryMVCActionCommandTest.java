/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;
import com.liferay.upload.UploadHandler;

import java.io.File;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class UploadFileEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmFormInstance = DDMFormInstanceTestUtil.addDDMFormInstance(
			_group, TestPropsValues.getUserId());

		_uploadHandler = ReflectionTestUtil.getFieldValue(
			_mvcActionCommand, "_uploadHandler");

		ReflectionTestUtil.setFieldValue(
			_uploadHandler, "_portal",
			ProxyUtil.newProxyInstance(
				UploadFileEntryMVCActionCommandTest.class.getClassLoader(),
				new Class<?>[] {Portal.class},
				(proxy, method, args) -> {
					if (!Objects.equals(
							method.getName(), "getUploadPortletRequest")) {

						return method.invoke(_portal, args);
					}

					return UploadTestUtil.createUploadPortletRequest(
						UploadTestUtil.createUploadServletRequest(
							_getMockMultipartHttpServletRequest(),
							HashMapBuilder.put(
								"file", new FileItem[] {_getFileItem()}
							).build(),
							new HashMap<>()),
						null, RandomTestUtil.randomString());
				}));
	}

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(_uploadHandler, "_portal", _portal);
	}

	@Test
	public void testProcessAction() throws Exception {
		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		_mvcActionCommand.processAction(
			new MockLiferayPortletActionRequest(),
			mockLiferayPortletActionResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		JSONObject fileJSONObject = jsonObject.getJSONObject("file");

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			fileJSONObject.getLong("fileEntryId"));

		Assert.assertNotNull(dlFileEntry);

		User user = _userLocalService.getUserByExternalReferenceCode(
			DDMFormConstants.DDM_FORM_DEFAULT_USER_EXTERNAL_REFERENCE_CODE,
			TestPropsValues.getCompanyId());

		Assert.assertEquals(user.getUserId(), dlFileEntry.getUserId());
	}

	private FileItem _getFileItem() throws Exception {
		Path path = Files.createTempFile(null, ".txt");

		Files.write(path, "".getBytes());

		File file = path.toFile();

		FinalizeManager.register(
			file, new DeleteFileFinalizeAction(file.getAbsolutePath()),
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		return ProxyUtil.newDelegateProxyInstance(
			FileItem.class.getClassLoader(), FileItem.class,
			new Object() {

				public String getFileName() {
					return file.getName();
				}

				public InputStream getInputStream() {
					return null;
				}

				public long getSize() {
					return 0;
				}

				public File getStoreLocation() {
					return file;
				}

				public boolean isInMemory() {
					return true;
				}

			},
			null);
	}

	private MockMultipartHttpServletRequest
			_getMockMultipartHttpServletRequest()
		throws Exception {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.addParameter(
			"formInstanceId",
			String.valueOf(_ddmFormInstance.getFormInstanceId()));
		mockMultipartHttpServletRequest.addParameter(
			"groupId", String.valueOf(_ddmFormInstance.getGroupId()));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		mockMultipartHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());

		return mockMultipartHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private DDMFormInstance _ddmFormInstance;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "mvc.command.name=/dynamic_data_mapping_form/upload_file_entry"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private UploadHandler _uploadHandler;

	@Inject
	private UserLocalService _userLocalService;

}