/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class ImportMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpImportMVCResourceCommand();
		_setUpLayoutsImporter();
		_setUpPortal();
		_setUpPortalUtil();
		_setUpThemeDisplay();
	}

	@After
	public void tearDown() {
		_jsonPortletResponseUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-63086")
	public void testServeResourceHasConflicts() throws Exception {
		_assertServeResourceHasConflicts(false, true);
		_assertServeResourceHasConflicts(true, false);
	}

	private void _assertServeResourceHasConflicts(
			boolean hasConflicts, boolean validFile)
		throws Exception {

		Mockito.when(
			_layoutsImporter.validateFile(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(File.class))
		).thenReturn(
			validFile
		);

		JSONObject jsonObject = _serveResource(
			File.createTempFile(RandomTestUtil.randomString(), ".zip"));

		Assert.assertEquals(
			hasConflicts, jsonObject.getBoolean("hasConflicts"));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		File file) {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			UploadPortletRequest.class.getName(),
			_getUploadPortletRequest(file));
		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		return mockLiferayResourceRequest;
	}

	private UploadPortletRequest _getUploadPortletRequest(File file) {
		UploadPortletRequest uploadPortletRequest = Mockito.mock(
			UploadPortletRequest.class);

		Mockito.when(
			uploadPortletRequest.getFile("file")
		).thenReturn(
			file
		);

		return uploadPortletRequest;
	}

	private JSONObject _serveResource(File file) throws Exception {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayResourceRequest(file);

		_setUpPortalRequestBridges(mockLiferayResourceRequest);

		ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor.forClass(
			Object.class);

		_jsonPortletResponseUtilMockedStatic.when(
			() -> JSONPortletResponseUtil.writeJSON(
				Mockito.any(ResourceRequest.class),
				Mockito.any(ResourceResponse.class), argumentCaptor.capture())
		).thenAnswer(
			invocation -> null
		);

		_importMVCResourceCommand.serveResource(
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		return (JSONObject)argumentCaptor.getValue();
	}

	private void _setUpImportMVCResourceCommand() {
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_jsonFactory", new JSONFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_language", _language);
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_layoutsImporter", _layoutsImporter);
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_portal", _portal);
	}

	private void _setUpLayoutsImporter() throws Exception {
		Mockito.when(
			_layoutsImporter.validateFile(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(File.class))
		).thenReturn(
			true
		);
	}

	private void _setUpPortal() {
		Mockito.when(
			_portal.getUploadPortletRequest(Mockito.any(ResourceRequest.class))
		).thenAnswer(
			invocation -> {
				ResourceRequest resourceRequest = invocation.getArgument(0);

				return resourceRequest.getAttribute(
					UploadPortletRequest.class.getName());
			}
		);
	}

	private void _setUpPortalRequestBridges(
		MockLiferayResourceRequest mockLiferayResourceRequest) {

		Mockito.when(
			_portal.getHttpServletRequest(mockLiferayResourceRequest)
		).thenReturn(
			mockLiferayResourceRequest.getHttpServletRequest()
		);

		Mockito.when(
			_portal.getOriginalServletRequest(
				mockLiferayResourceRequest.getHttpServletRequest())
		).thenReturn(
			mockLiferayResourceRequest.getHttpServletRequest()
		);

		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);

		Mockito.when(
			_portal.getLiferayPortletRequest(mockLiferayResourceRequest)
		).thenReturn(
			liferayPortletRequest
		);

		Mockito.when(
			liferayPortletRequest.getPlid()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			liferayPortletRequest.getPortletName()
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private void _setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_themeDisplay.getScopeGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_themeDisplay.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private final ImportMVCResourceCommand _importMVCResourceCommand =
		new ImportMVCResourceCommand();
	private final MockedStatic<JSONPortletResponseUtil>
		_jsonPortletResponseUtilMockedStatic = Mockito.mockStatic(
			JSONPortletResponseUtil.class);
	private final Language _language = Mockito.mock(Language.class);
	private final LayoutsImporter _layoutsImporter = Mockito.mock(
		LayoutsImporter.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}