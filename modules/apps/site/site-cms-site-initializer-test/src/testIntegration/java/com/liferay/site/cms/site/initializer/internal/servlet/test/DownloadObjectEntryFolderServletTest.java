/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2024-09
 */

package com.liferay.site.cms.site.initializer.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Roberto Díaz
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-34594")}
)
@RunWith(Arquillian.class)
public class DownloadObjectEntryFolderServletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
	}

	@Before
	public void setUp() throws Exception {
		_group = CMSTestUtil.getOrAddGroup(
			DownloadObjectEntryFolderServletTest.class);

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_SPACE,
			new ServiceContext() {
				{
					setCompanyId(_group.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
	}

	@Test
	public void testDownloadBulkAction() throws Exception {
		_testDownloadBulkActionWithBulkActionItems();
		_testDownloadBulkActionWithSelectAll();
	}

	@Test
	public void testDownloadFolder() throws Exception {
		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder(
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(
				null, HttpMethods.GET,
				objectEntryFolder.getObjectEntryFolderId());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	private long _addFileEntry() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_depotEntry.getGroupId());
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			RandomTestUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			null,
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId()));

		return dlFileEntry.getFileEntryId();
	}

	private ObjectEntry _addObjectEntry(
			long objectDefinitionId, long objectEntryFolderId,
			ServiceContext serviceContext)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			_depotEntry.getGroupId(), _depotEntry.getUserId(),
			objectDefinitionId, objectEntryFolderId, "en_US",
			HashMapBuilder.<String, Serializable>put(
				"file", String.valueOf(_addFileEntry())
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build(),
			serviceContext);
	}

	private ObjectEntryFolder _addObjectEntryFolder(
			long parentObjectEntryFolderId)
		throws Exception {

		return _objectEntryFolderLocalService.addObjectEntryFolder(
			StringUtil.randomString(), _depotEntry.getGroupId(),
			TestPropsValues.getUserId(), parentObjectEntryFolderId,
			RandomTestUtil.randomString(), null, StringUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			byte[] content, String method, long objectEntryFolderId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:8080/");
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(mockHttpServletRequest));
		mockHttpServletRequest.setAttribute(
			WebKeys.USER, TestPropsValues.getUser());

		if (content != null) {
			mockHttpServletRequest.setContent(content);
		}

		mockHttpServletRequest.setContextPath("/o");
		mockHttpServletRequest.setMethod(method);

		if (objectEntryFolderId != 0) {
			mockHttpServletRequest.setRequestURI(
				StringBundler.concat(
					"/o/cmd/download-folder/",
					_portal.getClassNameId(ObjectEntryFolder.class), "/",
					objectEntryFolderId));
		}

		mockHttpServletRequest.setServletPath("/cms/download-folder");

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			MockHttpServletRequest mockHttpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setRequest(mockHttpServletRequest);

		return themeDisplay;
	}

	private void _testDownloadBulkActionWithBulkActionItems() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_DOCUMENT", _group.getCompanyId());
		ObjectEntryFolder parentObjectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					"L_FILES", _depotEntry.getGroupId(),
					_depotEntry.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		ObjectEntry objectEntry = _addObjectEntry(
			objectDefinition.getObjectDefinitionId(),
			parentObjectEntryFolder.getObjectEntryFolderId(), serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(
				JSONUtil.put(
					"bulkActionItems",
					JSONUtil.put(
						JSONUtil.put(
							"classExternalReferenceCode",
							objectEntry.getExternalReferenceCode()
						).put(
							"className", objectEntry.getModelClassName()
						).put(
							"classPK", objectEntry.getObjectEntryId()
						).put(
							"name", objectEntry.getTitleValue()
						))
				).put(
					"selectAll", false
				).put(
					"type", "DownloadBulkAction"
				).toString(
				).getBytes(),
				HttpMethods.POST, 0);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	private void _testDownloadBulkActionWithSelectAll() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_DOCUMENT", _group.getCompanyId());
		ObjectEntryFolder parentObjectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					"L_FILES", _depotEntry.getGroupId(),
					_depotEntry.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		_addObjectEntry(
			objectDefinition.getObjectDefinitionId(),
			parentObjectEntryFolder.getObjectEntryFolderId(), serviceContext);

		ObjectEntryFolder objectEntryFolder1 = _addObjectEntryFolder(
			parentObjectEntryFolder.getObjectEntryFolderId());

		_addObjectEntry(
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder1.getObjectEntryFolderId(), serviceContext);

		ObjectEntryFolder objectEntryFolder2 = _addObjectEntryFolder(
			parentObjectEntryFolder.getObjectEntryFolderId());

		_addObjectEntry(
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder2.getObjectEntryFolderId(), serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(
				_jsonFactory.createJSONObject(
				).put(
					"selectAll", true
				).put(
					"type", "DownloadBulkAction"
				).toString(
				).getBytes(),
				HttpMethods.POST, 0);

		mockHttpServletRequest.setParameter(
			"filter",
			"cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, " +
				"3)");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private Group _group;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.site.cms.site.initializer.internal.servlet.DownloadObjectEntryFolderServlet"
	)
	private Servlet _servlet;

}