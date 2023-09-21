/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Objects;

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
 * @author Rebeca Silva
 */
@RunWith(Arquillian.class)
public class ImportDataDefinitionMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDataLayoutFieldNamesAreEqualToDataDefinitionFieldNames()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"valid_data_definition.json", "Imported Structure");

		_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		DataDefinition dataDefinition = _getImportedDataDefinition();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		DataLayoutRow[] dataLayoutRows =
			dataLayout.getDataLayoutPages()[0].getDataLayoutRows();

		Assert.assertEquals(
			dataDefinitionFields[0].getName(),
			dataLayoutRows[0].getDataLayoutColumns()[0].getFieldNames()[0]);
		Assert.assertEquals(
			dataDefinitionFields[1].getName(),
			dataLayoutRows[1].getDataLayoutColumns()[0].getFieldNames()[0]);
	}

	@Test
	public void testImportDataDefinitionWithUniqueFieldNames()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"valid_data_definition.json", "Imported Structure");

		_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		DataDefinition dataDefinition = _getImportedDataDefinition();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		String previousTextFieldName = "Text12293201";

		Assert.assertNotEquals(
			previousTextFieldName, dataDefinitionFields[0].getName());

		String previousFieldsGroupFieldName = "FieldsGroup19507604";

		Assert.assertNotEquals(
			previousFieldsGroupFieldName, dataDefinitionFields[1].getName());

		DataDefinitionField[] nestedDataDefinitionFields =
			dataDefinitionFields[1].getNestedDataDefinitionFields();

		String previousNumericFieldName = "Numeric34461674";

		Assert.assertNotEquals(
			previousNumericFieldName, nestedDataDefinitionFields[0].getName());

		String previousDateFieldName = "Date46675757";

		Assert.assertNotEquals(
			previousDateFieldName, nestedDataDefinitionFields[1].getName());
	}

	@Test
	public void testProcessActionWithDataDefinitionFromPreviousVersion()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"previous_version_valid_data_definition.json",
				"Imported Structure");

		_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest,
				_portal.getPortletId(mockLiferayPortletActionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE));
		Assert.assertNull(
			SessionErrors.get(
				mockLiferayPortletActionRequest,
				"importDataDefinitionErrorMessage"));

		DataDefinition dataDefinition = _getImportedDataDefinition();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		String previousTextFieldName = "Text1";

		Assert.assertNotEquals(
			previousTextFieldName, dataDefinitionFields[0].getName());
	}

	@Test
	public void testProcessActionWithInvalidDataDefinition() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"invalid_data_definition.json", "Imported Structure");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.journal.web.internal.portlet.action." +
					"ImportDataDefinitionMVCActionCommand",
				LoggerTestUtil.ERROR)) {

			_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			_assertFailure(mockLiferayPortletActionRequest);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(LoggerTestUtil.ERROR, logEntry.getPriority());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertEquals(
				"The sum of all column sizes of a row must be less than the " +
					"maximum row size of 12",
				throwable.getMessage());
		}
	}

	@Test
	public void testProcessActionWithoutName() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.journal.web.internal.portlet.action." +
					"ImportDataDefinitionMVCActionCommand",
				LoggerTestUtil.ERROR)) {

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				_createMockLiferayPortletActionRequest(
					"valid_data_definition.json", null);

			_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			_assertFailure(mockLiferayPortletActionRequest);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(LoggerTestUtil.ERROR, logEntry.getPriority());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertTrue(
				StringUtil.startsWith(
					throwable.getMessage(), "Name is null for locale"));
		}
	}

	@Test
	public void testProcessActionWithValidDataDefinitionAndName()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_createMockLiferayPortletActionRequest(
				"valid_data_definition.json", "Imported Structure");

		_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

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

	private DataDefinition _getImportedDataDefinition() throws Exception {
		DataDefinitionResource.Builder builder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource = builder.user(
			TestPropsValues.getUser()
		).build();

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					TestPropsValues.getGroupId(), "journal",
					"Imported Structure", Pagination.of(1, 1), null);

		List<DataDefinition> items = (List<DataDefinition>)page.getItems();

		return items.get(0);
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Layout layout = new LayoutImpl();

		layout.setType(LayoutConstants.TYPE_CONTROL_PANEL);

		themeDisplay.setLayout(layout);

		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteDefaultLocale(LocaleUtil.US);
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _setUpUploadPortletRequest(
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest) {

		ReflectionTestUtil.setFieldValue(
			_mvcActionCommand, "_portal",
			ProxyUtil.newProxyInstance(
				ImportDataDefinitionMVCActionCommandTest.class.getClassLoader(),
				new Class<?>[] {Portal.class},
				(proxy, method, args) -> {
					if (Objects.equals(
							method.getName(), "getUploadPortletRequest")) {

						LiferayPortletRequest liferayPortletRequest =
							_portal.getLiferayPortletRequest(
								mockLiferayPortletActionRequest);

						return UploadTestUtil.createUploadPortletRequest(
							_portal.getUploadServletRequest(
								liferayPortletRequest.getHttpServletRequest()),
							liferayPortletRequest,
							_portal.getPortletNamespace(
								liferayPortletRequest.getPortletName()));
					}

					return method.invoke(_portal, args);
				}));
	}

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject
	private File _file;

	@Inject(filter = "mvc.command.name=/journal/import_data_definition")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

}