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

package com.liferay.journal.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Rodrigo Paulino
 */
@RunWith(Arquillian.class)
public class ImportDataDefinitionMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testProcessActionWithInvalidDataDefinition() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"invalid_data_definition.json", "Imported Structure");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		_assertFailure(mockLiferayPortletActionRequest);
	}

	@Test
	public void testProcessActionWithoutName() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"valid_data_definition.json", null);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		_assertFailure(mockLiferayPortletActionRequest);
	}

	@Test
	public void testProcessActionWithValidDataDefinitionAndName()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"valid_data_definition.json", "Imported Structure");

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertNotNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest,
				_portal.getPortletId(mockLiferayPortletActionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE));
		Assert.assertNotNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest,
				"importDataDefinitionSuccessMessage"));
	}

	private void _assertFailure(
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest) {

		Assert.assertNotNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest,
				_portal.getPortletId(mockLiferayPortletActionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE));
		Assert.assertNotNull(
			SessionErrors.get(
				mockLiferayPortletActionRequest,
				"importDataDefinitionErrorMessage"));
	}

	private MockLiferayPortletActionRequest
			_createMockLiferayPortletActionRequest(String fileName, String name)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest(
				_createMockMultipartHttpServletRequest(fileName));

		mockLiferayPortletActionRequest.addParameter("name", name);
		mockLiferayPortletActionRequest.addParameter("redirect", "fakeURL");
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletActionRequest;
	}

	private MockMultipartHttpServletRequest
			_createMockMultipartHttpServletRequest(String fileName)
		throws Exception {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		Class<?> clazz = getClass();

		byte[] bytes = _file.getBytes(
			clazz.getResourceAsStream("dependencies/" + fileName));

		mockMultipartHttpServletRequest.addFile(
			new MockMultipartFile(fileName, bytes));

		mockMultipartHttpServletRequest.setCharacterEncoding(StringPool.UTF8);

		String boundary = "WebKitFormBoundary" + StringUtil.randomString();

		mockMultipartHttpServletRequest.setContent(
			_getContent(boundary, bytes, fileName));
		mockMultipartHttpServletRequest.setContentType(
			MediaType.MULTIPART_FORM_DATA_VALUE + "; boundary=" + boundary);

		return mockMultipartHttpServletRequest;
	}

	private byte[] _getContent(String boundary, byte[] bytes, String fileName) {
		String start = StringBundler.concat(
			StringPool.DOUBLE_DASH, boundary,
			"\r\nContent-Disposition:form-data;name=\"jsonFile\";filename=\"",
			fileName, "\";\r\nContent-type:application/json\r\n\r\n");

		String end = StringBundler.concat(
			"\r\n--", boundary, StringPool.DOUBLE_DASH);

		return ArrayUtil.append(start.getBytes(), bytes, end.getBytes());
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		Layout layout = new LayoutImpl();

		layout.setType(LayoutConstants.TYPE_CONTROL_PANEL);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteDefaultLocale(LocaleUtil.US);
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private File _file;

	@Inject(filter = "mvc.command.name=/journal/import_data_definition")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

}