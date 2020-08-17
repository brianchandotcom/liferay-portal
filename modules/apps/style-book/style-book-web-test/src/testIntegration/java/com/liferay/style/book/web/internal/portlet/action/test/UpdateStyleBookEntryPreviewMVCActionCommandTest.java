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

package com.liferay.style.book.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionResponse;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class UpdateStyleBookEntryPreviewMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		_repository = PortletFileRepositoryUtil.addPortletRepository(
			_group.getGroupId(), StyleBookPortletKeys.STYLE_BOOK,
			_serviceContext);

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		_themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));

		Layout layout = LayoutTestUtil.addLayout(_group);

		_themeDisplay.setLayout(layout);
		_themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());

		_themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());
		_themeDisplay.setRealUser(TestPropsValues.getUser());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testReplaceStyleBookEntryPreview() throws Exception {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		FileEntry tempFileEntry1 = _addFileEntry(
			"thumbnail1.png", styleBookEntry);

		actionRequest.addParameter(
			"fileEntryId", String.valueOf(tempFileEntry1.getFileEntryId()));

		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryPreviewMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertNotNull(
			PortletFileRepositoryUtil.getPortletFileEntry(
				styleBookEntry.getPreviewFileEntryId()));

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), _repository.getDlFolderId(),
			_getFileName(
				tempFileEntry1.getExtension(),
				styleBookEntry.getStyleBookEntryId()));

		Assert.assertNotNull(fileEntry);
		Assert.assertEquals(
			fileEntry.getFileEntryId(), styleBookEntry.getPreviewFileEntryId());

		actionRequest = new MockLiferayPortletActionRequest();

		FileEntry tempFileEntry2 = _addFileEntry(
			"thumbnail2.png", styleBookEntry);

		actionRequest.addParameter(
			"fileEntryId", String.valueOf(tempFileEntry2.getFileEntryId()));

		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryPreviewMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		StyleBookEntry updateStyleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				styleBookEntry.getStyleBookEntryId());

		Assert.assertNotEquals(
			styleBookEntry.getPreviewFileEntryId(),
			updateStyleBookEntry.getPreviewFileEntryId());

		fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), _repository.getDlFolderId(),
			_getFileName(
				tempFileEntry2.getExtension(),
				styleBookEntry.getStyleBookEntryId()));

		Assert.assertNotNull(fileEntry);
		Assert.assertEquals(
			fileEntry.getFileEntryId(), styleBookEntry.getPreviewFileEntryId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			styleBookEntry.getPreviewFileEntryId());
	}

	@Test
	public void testUpdateStyleBookEntryPreview() throws Exception {
		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		FileEntry tempFileEntry = _addFileEntry(
			"thumbnail.png", styleBookEntry);

		actionRequest.addParameter(
			"fileEntryId", String.valueOf(tempFileEntry.getFileEntryId()));

		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryPreviewMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		styleBookEntry = _styleBookEntryLocalService.fetchStyleBookEntry(
			styleBookEntry.getStyleBookEntryId());

		Assert.assertNotNull(
			PortletFileRepositoryUtil.getPortletFileEntry(
				styleBookEntry.getPreviewFileEntryId()));

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), _repository.getDlFolderId(),
			_getFileName(
				tempFileEntry.getExtension(),
				styleBookEntry.getStyleBookEntryId()));

		Assert.assertNotNull(fileEntry);
		Assert.assertEquals(
			fileEntry.getFileEntryId(), styleBookEntry.getPreviewFileEntryId());
	}

	private FileEntry _addFileEntry(
			String fileName, StyleBookEntry styleBookEntry)
		throws Exception {

		Class<?> clazz = getClass();

		return PortletFileRepositoryUtil.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			StyleBookEntry.class.getName(),
			styleBookEntry.getStyleBookEntryId(), RandomTestUtil.randomString(),
			_repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"), fileName,
			ContentTypes.IMAGE_PNG, false);
	}

	private String _getFileName(String extension, long styleBookEntryId) {
		return styleBookEntryId + "_preview." + extension;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Repository _repository;
	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	private ThemeDisplay _themeDisplay;

	@Inject(
		filter = "mvc.command.name=/style_book/update_style_book_entry_preview"
	)
	private MVCActionCommand _updateStyleBookEntryPreviewMVCActionCommandTest;

}