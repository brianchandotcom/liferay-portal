/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;

import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

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
public class LayoutGetFaviconURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.fetchGroup(TestPropsValues.getGroupId());

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_serviceContext.setRequest(_getHttpServletRequest(_group));
	}

	@Test
	public void testLayout() throws Exception {
		byte[] expectedBytes = _getExpectedBytes();

		FileEntry fileEntry = _addFileEntry(expectedBytes, _group);

		_layout.setFaviconFileEntryERC(fileEntry.getExternalReferenceCode());

		_layout.setFaviconFileEntryScopeERC(null);

		Assert.assertArrayEquals(
			expectedBytes, _getBytes(_layout.getFaviconURL()));
	}

	@Test
	public void testLayoutAfterClear() throws Exception {
		byte[] expectedBytes = _getExpectedBytes();

		FileEntry fileEntry = _addFileEntry(expectedBytes, _group);

		_layout.setFaviconFileEntryERC(fileEntry.getExternalReferenceCode());

		_layout.setFaviconFileEntryScopeERC(null);

		_layout = _layoutLocalService.updateLayout(_layout);

		Assert.assertArrayEquals(
			expectedBytes, _getBytes(_layout.getFaviconURL()));

		_layoutLocalService.updateLayout(
			_layout.getGroupId(), _layout.isPrivateLayout(),
			_layout.getLayoutId(), _layout.getTypeSettings(), null,
			_layout.getThemeId(), _layout.getColorSchemeId(),
			_layout.getStyleBookEntryERC(), _layout.getCss(), null, null,
			_layout.getMasterLayoutPageTemplateEntryERC());

		Layout layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		Assert.assertNull(layout.getFaviconURL());
	}

	@Test
	public void testLayoutPageTemplateEntryCreatedFromLayout()
		throws Exception {

		byte[] expectedBytes = _getExpectedBytes();

		FileEntry fileEntry = _addFileEntry(expectedBytes, _group);

		_layout.setFaviconFileEntryERC(fileEntry.getExternalReferenceCode());

		_layout.setFaviconFileEntryScopeERC(null);

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					null, RandomTestUtil.randomString(), null,
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					_serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				createLayoutPageTemplateEntryFromLayout(
					SegmentsEntryConstants.ID_DEFAULT, _layout,
					RandomTestUtil.randomString(),
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					_serviceContext);

		Layout layoutPageTemplateLayout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Layout layoutPageTemplateDraftLayout =
			layoutPageTemplateLayout.fetchDraftLayout();

		Assert.assertArrayEquals(
			expectedBytes,
			_getBytes(layoutPageTemplateDraftLayout.getFaviconURL()));
	}

	@Test
	public void testLayoutWhenSetToLayoutAndLayoutSet() throws Exception {
		LayoutSet layoutSet = _layout.getLayoutSet();

		FileEntry layoutSetFaviconFileEntry = _addFileEntry(
			_getExpectedBytes("classic_logo.png"), _group);

		layoutSet.setFaviconFileEntryId(
			layoutSetFaviconFileEntry.getFileEntryId());

		_layoutSetLocalService.updateLayoutSet(layoutSet);

		byte[] layoutFaviconBytes = _getExpectedBytes("dxp_logo.png");

		FileEntry layoutFaviconFileEntry = _addFileEntry(
			layoutFaviconBytes, _group);

		_layout.setFaviconFileEntryERC(
			layoutFaviconFileEntry.getExternalReferenceCode());

		_layout.setFaviconFileEntryScopeERC(null);

		Assert.assertArrayEquals(
			layoutFaviconBytes, _getBytes(_layout.getFaviconURL()));
	}

	@Test
	public void testLayoutWhenSetToLayoutAndMasterLayout() throws Exception {
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, null,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Layout masterLayout = _layoutLocalService.fetchLayout(
			masterLayoutPageTemplateEntry.getPlid());

		FileEntry masterLayoutFaviconFileEntry = _addFileEntry(
			_getExpectedBytes("classic_logo.png"), _group);

		masterLayout.setFaviconFileEntryERC(
			masterLayoutFaviconFileEntry.getExternalReferenceCode());

		masterLayout.setFaviconFileEntryScopeERC(null);

		_layoutLocalService.updateLayout(masterLayout);

		_layout.setMasterLayoutPageTemplateEntryERC(
			masterLayoutPageTemplateEntry.getExternalReferenceCode());

		byte[] layoutFaviconBytes = _getExpectedBytes("dxp_logo.png");

		FileEntry layoutFaviconFileEntry = _addFileEntry(
			layoutFaviconBytes, _group);

		_layout.setFaviconFileEntryERC(
			layoutFaviconFileEntry.getExternalReferenceCode());

		_layout.setFaviconFileEntryScopeERC(null);

		Assert.assertArrayEquals(
			layoutFaviconBytes, _getBytes(_layout.getFaviconURL()));
	}

	@Test
	@TestInfo("LPD-68134")
	public void testLayoutWithGlobalScopeERC() throws Exception {
		byte[] expectedBytes = _getExpectedBytes();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		FileEntry fileEntry = _addFileEntry(expectedBytes, companyGroup);

		_layout.setFaviconFileEntryERC(fileEntry.getExternalReferenceCode());

		_layout.setFaviconFileEntryScopeERC(
			companyGroup.getExternalReferenceCode());

		Assert.assertArrayEquals(
			expectedBytes, _getBytes(_layout.getFaviconURL()));
	}

	private FileEntry _addFileEntry(byte[] bytes, Group group)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setRequest(_getHttpServletRequest(group));

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_PNG, bytes, null,
			null, null, serviceContext);
	}

	private byte[] _getBytes(String favicon) throws Exception {
		byte[] bytes = null;

		URL url = new URL(_getPortalURL() + favicon);

		URLConnection urlConnection = url.openConnection();

		try (InputStream inputStream = urlConnection.getInputStream()) {
			bytes = FileUtil.getBytes(inputStream);
		}

		return bytes;
	}

	private byte[] _getExpectedBytes() throws Exception {
		return _getExpectedBytes("dxp_logo.png");
	}

	private byte[] _getExpectedBytes(String fileName) throws Exception {
		return FileUtil.getBytes(getClass(), "dependencies/" + fileName);
	}

	private HttpServletRequest _getHttpServletRequest(Group group)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(group.getCompanyId()));
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private String _getPortalURL() {
		return _portal.getPortalURL(
			_company.getVirtualHostname(), _portal.getPortalServerPort(false),
			false);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}