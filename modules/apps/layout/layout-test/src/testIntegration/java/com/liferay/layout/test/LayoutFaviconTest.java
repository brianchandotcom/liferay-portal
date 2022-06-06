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

package com.liferay.layout.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

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
public class LayoutFaviconTest {

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

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_serviceContext.setRequest(_getHttpServletRequest());
	}

	@Test
	public void testGetFaviconFromLayout() throws Exception {
		byte[] expectedBytes = _getExpectedBytes();

		FileEntry fileEntry = _addFileEntry(expectedBytes);

		_layout.setFaviconFileEntryId(fileEntry.getFileEntryId());

		Assert.assertArrayEquals(
			expectedBytes, _getBytes(_layout.getFavicon()));
	}

	@Test
	public void testGetFaviconFromLayoutAfterClear() throws Exception {
		byte[] expectedBytes = _getExpectedBytes();

		FileEntry fileEntry = _addFileEntry(expectedBytes);

		_layout.setFaviconFileEntryId(fileEntry.getFileEntryId());

		_layoutLocalService.updateLayout(_layout);

		Assert.assertArrayEquals(
			expectedBytes, _getBytes(_layout.getFavicon()));

		_layoutLocalService.updateLayout(
			_layout.getGroupId(), _layout.isPrivateLayout(),
			_layout.getLayoutId(), _layout.getTypeSettings(), null,
			_layout.getThemeId(), _layout.getColorSchemeId(),
			_layout.getStyleBookEntryId(), _layout.getCss(), 0,
			_layout.getMasterLayoutPlid());

		Layout layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		Assert.assertNull(layout.getFavicon());
	}

	@Test
	public void testGetFaviconFromLayoutSet() throws Exception {
		LayoutSet layoutSet = _layout.getLayoutSet();

		byte[] expectedBytes = _getExpectedBytes();

		FileEntry fileEntry = _addFileEntry(expectedBytes);

		layoutSet.setFaviconFileEntryId(fileEntry.getFileEntryId());

		_layoutSetLocalService.updateLayoutSet(layoutSet);

		Assert.assertArrayEquals(
			expectedBytes, _getBytes(_layout.getFavicon()));
	}

	private FileEntry _addFileEntry(byte[] bytes) throws Exception {
		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG, bytes, null,
			null, _serviceContext);
	}

	private byte[] _getBytes(String favicon) throws Exception {
		URL url = new URL(_getPortalURL() + favicon);

		URLConnection urlConnection = url.openConnection();

		byte[] bytes;

		try (InputStream inputStream = urlConnection.getInputStream()) {
			bytes = FileUtil.getBytes(inputStream);
		}

		return bytes;
	}

	private byte[] _getExpectedBytes() throws Exception {
		return FileUtil.getBytes(getClass(), "dependencies/dxp.ico");
	}

	private HttpServletRequest _getHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());

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

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}