/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2024-09
 */

package com.liferay.site.cms.site.initializer.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectEntryFolderTestUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
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
import java.util.List;

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
 * @author Balázs Sáfrány-Kovalik
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-34594")}
)
@RunWith(Arquillian.class)
public class TranslateObjectEntryServletTest {

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

		_basicWebContentObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", _group.getCompanyId());

		_blogObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BLOG", _group.getCompanyId());

		_contentsObjectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					"L_CONTENTS", _depotEntry.getGroupId(),
					_depotEntry.getCompanyId());
	}

	@Test
	public void testTranslateBulkAction() throws Exception {
		_testTranslateBulkActionWithBulkActionItems();
		_testTranslateBulkActionWithSelectAll();
	}

	private ObjectEntry _addObjectEntry(
			long objectDefinitionId, long objectEntryFolderId,
			ServiceContext serviceContext)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			_depotEntry.getGroupId(), _depotEntry.getUserId(),
			objectDefinitionId, objectEntryFolderId, "en_US",
			HashMapBuilder.<String, Serializable>put(
				"content_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build(),
			serviceContext);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(byte[] content)
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
		mockHttpServletRequest.setMethod(HttpMethods.GET);

		mockHttpServletRequest.setServletPath("/cms/translations");

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

	private void _testTranslateBulkActionWithBulkActionItems()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		ObjectEntry objectEntry1 = _addObjectEntry(
			_basicWebContentObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);
		ObjectEntry objectEntry2 = _addObjectEntry(
			_basicWebContentObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);

		_addObjectEntry(
			_basicWebContentObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);

		ObjectEntryFolder objectEntryFolder =
			ObjectEntryFolderTestUtil.addObjectEntryFolder(
				_depotEntry.getGroupId(),
				_contentsObjectEntryFolder.getObjectEntryFolderId());

		_addObjectEntry(
			_basicWebContentObjectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(
				JSONUtil.put(
					"bulkActionItems",
					JSONUtil.putAll(
						JSONUtil.put(
							"classExternalReferenceCode",
							objectEntry1.getExternalReferenceCode()
						).put(
							"className", objectEntry1.getModelClassName()
						).put(
							"classPK", objectEntry1.getObjectEntryId()
						).put(
							"name", objectEntry1.getTitleValue()
						),
						JSONUtil.put(
							"classExternalReferenceCode",
							objectEntry2.getExternalReferenceCode()
						).put(
							"className", objectEntry2.getModelClassName()
						).put(
							"classPK", objectEntry2.getObjectEntryId()
						).put(
							"name", objectEntry2.getTitleValue()
						),
						JSONUtil.put(
							"classExternalReferenceCode",
							objectEntryFolder.getExternalReferenceCode()
						).put(
							"className", objectEntryFolder.getModelClassName()
						).put(
							"classPK",
							objectEntryFolder.getObjectEntryFolderId()
						).put(
							"name", objectEntryFolder.getName()
						))
				).put(
					"selectionScope", JSONUtil.put("selectAll", false)
				).put(
					"sourceLanguageId", "en_US"
				).put(
					"targetLanguageIds", new String[] {"nl_NL", "de_DE"}
				).put(
					"type", "ExportTranslationBulkAction"
				).put(
					"xliffMimeType", "application/x-xliff+xml"
				).toString(
				).getBytes());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
	}

	private void _testTranslateBulkActionWithSelectAll() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		_addObjectEntry(
			_basicWebContentObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);
		_addObjectEntry(
			_basicWebContentObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);
		_addObjectEntry(
			_blogObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);
		_addObjectEntry(
			_blogObjectDefinition.getObjectDefinitionId(),
			_contentsObjectEntryFolder.getObjectEntryFolderId(),
			serviceContext);

		ObjectEntryFolder objectEntryFolder =
			ObjectEntryFolderTestUtil.addObjectEntryFolder(
				_depotEntry.getGroupId(),
				_contentsObjectEntryFolder.getObjectEntryFolderId());

		_addObjectEntry(
			_blogObjectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(
				JSONUtil.put(
					"selectionScope", JSONUtil.put("selectAll", true)
				).put(
					"sourceLanguageId", "en_US"
				).put(
					"targetLanguageIds", new String[] {"nl_NL", "de_DE"}
				).put(
					"type", "ExportTranslationBulkAction"
				).put(
					"xliffMimeType", "application/x-xliff+xml"
				).toString(
				).getBytes());

		mockHttpServletRequest.setParameter(
			"filter",
			"cmsRoot eq true and cmsSection eq 'contents' and status in (0, " +
				"2, 3)");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			ContentTypes.APPLICATION_ZIP,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		ZipReader zipReader = _zipReaderFactory.getZipReader(
			new ByteArrayInputStream(
				mockHttpServletResponse.getContentAsByteArray()));

		List<String> entries = zipReader.getEntries();

		Assert.assertEquals(entries.toString(), 2, entries.size());
		Assert.assertTrue(
			entries.contains("Basic Web Content Translations-en_US.zip"));
		Assert.assertTrue(entries.contains("Blog Translations-en_US.zip"));
	}

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	private ObjectDefinition _basicWebContentObjectDefinition;
	private ObjectDefinition _blogObjectDefinition;

	@Inject
	private CompanyLocalService _companyLocalService;

	private ObjectEntryFolder _contentsObjectEntryFolder;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	private Group _group;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.site.cms.site.initializer.internal.servlet.TranslateObjectEntryServlet"
	)
	private Servlet _servlet;

	@Inject
	private ZipReaderFactory _zipReaderFactory;

}