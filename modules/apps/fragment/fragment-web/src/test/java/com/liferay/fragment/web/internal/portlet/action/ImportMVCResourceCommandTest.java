/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.importer.FragmentsImportStrategy;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONObject;
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
import java.io.FileOutputStream;

import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class ImportMVCResourceCommandTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpFragmentsImporter();
		_setUpImportMVCResourceCommand();
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
		_assertServeResourceHasConflicts(true, false);
		_assertServeResourceHasConflicts(false, true);
	}

	@Test
	@TestInfo("LPD-63086")
	public void testServeResourceNeedsFragmentCollection() throws Exception {
		_assertResponseJSONObject(
			_serveResource(_createZipFile(false, false, false)), false, false);

		_assertResponseJSONObject(
			_serveResource(_createZipFile(true, false, false)), false, false);

		_assertResponseJSONObject(
			_serveResource(_createZipFile(false, false, true)), false, false);

		_assertResponseJSONObject(
			_serveResource(_createZipFile(false, true, false)), false, true);
	}

	private void _assertResponseJSONObject(
		JSONObject jsonObject, boolean hasConflicts,
		boolean needsFragmentCollection) {

		Assert.assertEquals(
			hasConflicts, jsonObject.getBoolean("hasConflicts"));
		Assert.assertEquals(
			needsFragmentCollection,
			jsonObject.getBoolean("needsFragmentCollection"));
	}

	private void _assertServeResourceHasConflicts(
			boolean hasConflicts, boolean validFile)
		throws Exception {

		Mockito.when(
			_fragmentsImporter.validateFragmentEntries(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(File.class))
		).thenReturn(
			validFile
		);

		_assertResponseJSONObject(
			_serveResource(_createZipFile(true, true, false)), hasConflicts,
			false);
	}

	private File _createZipFile(
			boolean includeCollectionEntry, boolean includeFragmentEntry,
			boolean includeNonfragmentEntry)
		throws Exception {

		File file = File.createTempFile(RandomTestUtil.randomString(), ".zip");

		file.deleteOnExit();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(file))) {

			if (includeCollectionEntry) {
				zipOutputStream.putNextEntry(
					new ZipEntry(
						FragmentExportImportConstants.FILE_NAME_COLLECTION));
				zipOutputStream.write(
					RandomTestUtil.randomString(
					).getBytes());
				zipOutputStream.closeEntry();
			}

			if (includeFragmentEntry) {
				zipOutputStream.putNextEntry(
					new ZipEntry(
						FragmentExportImportConstants.FILE_NAME_FRAGMENT));
				zipOutputStream.write(
					RandomTestUtil.randomString(
					).getBytes());
				zipOutputStream.closeEntry();
			}

			if (includeNonfragmentEntry) {
				zipOutputStream.putNextEntry(
					new ZipEntry(RandomTestUtil.randomString() + ".json"));
				zipOutputStream.write(
					RandomTestUtil.randomString(
					).getBytes());
				zipOutputStream.closeEntry();
			}
		}

		return file;
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

	private void _setUpFragmentsImporter() throws Exception {
		Mockito.when(
			_fragmentsImporter.importFragmentEntries(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(File.class),
				Mockito.any(FragmentsImportStrategy.class),
				Mockito.anyBoolean())
		).thenReturn(
			Collections.emptyList()
		);

		Mockito.when(
			_fragmentsImporter.validateFragmentEntries(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.any(File.class))
		).thenReturn(
			true
		);
	}

	private void _setUpImportMVCResourceCommand() {
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_fragmentsImporter",
			_fragmentsImporter);
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_jsonFactory", new JSONFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_importMVCResourceCommand, "_portal", _portal);
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
			_themeDisplay.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_themeDisplay.getScopeGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private final FragmentsImporter _fragmentsImporter = Mockito.mock(
		FragmentsImporter.class);
	private final ImportMVCResourceCommand _importMVCResourceCommand =
		new ImportMVCResourceCommand();
	private final MockedStatic<JSONPortletResponseUtil>
		_jsonPortletResponseUtilMockedStatic = Mockito.mockStatic(
			JSONPortletResponseUtil.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}