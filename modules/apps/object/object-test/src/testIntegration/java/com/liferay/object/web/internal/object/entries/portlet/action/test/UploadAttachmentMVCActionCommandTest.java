/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.upload.UploadHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class UploadAttachmentMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() throws Exception {
		ReflectionTestUtil.setFieldValue(_uploadHandler, "_portal", _portal);
	}

	@Test
	public void testDoProcessAction() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			UploadAttachmentMVCActionCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				false,
				Collections.singletonList(
					new AttachmentObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"attachmentObjectFieldName"
					).objectFieldSettings(
						Arrays.asList(
							_createObjectFieldSetting(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS,
								"txt"),
							_createObjectFieldSetting(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE,
								ObjectFieldSettingConstants.
									VALUE_USER_COMPUTER),
							_createObjectFieldSetting(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE,
								"100"))
					).build()));

		List<ServiceReference<MVCActionCommand>> serviceReferences =
			new ArrayList<>(
				bundleContext.getServiceReferences(
					MVCActionCommand.class,
					StringBundler.concat(
						"(&(javax.portlet.name=",
						objectDefinition.getPortletId(),
						")(mvc.command.name=/object_entries/upload_attachment",
						"))")));

		Assert.assertEquals(
			serviceReferences.toString(), 1, serviceReferences.size());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		MVCActionCommand mvcActionCommand = bundleContext.getService(
			serviceReferences.get(0));

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(),
			"attachmentObjectFieldName");

		_setUpUploadPortletRequest(
			mvcActionCommand, objectField.getObjectFieldId());

		ReflectionTestUtil.invoke(
			mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		JSONObject fileJSONObject = jsonObject.getJSONObject("file");

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			fileJSONObject.getLong("fileEntryId"));

		Assert.assertNotNull(dlFileEntry);

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				TestPropsValues.getCompanyId(), DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(dlFileEntry.getFileEntryId()),
				RoleLocalServiceUtil.getRole(
					TestPropsValues.getCompanyId(), RoleConstants.GUEST
				).getRoleId(),
				ActionKeys.DOWNLOAD));
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
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

				public InputStream getInputStream() throws IOException {
					return new FileInputStream(file);
				}

				public long getSize() {
					return 1;
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

	private MockHttpServletRequest _getMockHttpServletRequest(
			Long objectFieldId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			"objectFieldId", String.valueOf(objectFieldId));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);

		User user = UserLocalServiceUtil.getGuestUser(
			TestPropsValues.getCompanyId());

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());

		return mockHttpServletRequest;
	}

	private void _setUpUploadPortletRequest(
		MVCActionCommand mvcActionCommand, Long objectFieldId) {

		_uploadHandler = ReflectionTestUtil.getFieldValue(
			mvcActionCommand, "_uploadHandler");

		ReflectionTestUtil.setFieldValue(
			_uploadHandler, "_portal",
			ProxyUtil.newProxyInstance(
				UploadAttachmentMVCActionCommandTest.class.getClassLoader(),
				new Class<?>[] {Portal.class},
				(proxy, method, args) -> {
					if (!Objects.equals(
							method.getName(), "getUploadPortletRequest")) {

						return method.invoke(_portal, args);
					}

					return UploadTestUtil.createUploadPortletRequest(
						UploadTestUtil.createUploadServletRequest(
							_getMockHttpServletRequest(objectFieldId),
							HashMapBuilder.put(
								"file", new FileItem[] {_getFileItem()}
							).build(),
							new HashMap<>()),
						null, RandomTestUtil.randomString());
				}));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private UploadHandler _uploadHandler;

}