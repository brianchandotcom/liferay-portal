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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.exception.StyleBookEntryNameException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import javax.portlet.ActionRequest;

import org.junit.After;
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
public class UpdateStyleBookEntryNameMVCActionCommandTest {

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

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		_themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
		_themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());
		_themeDisplay.setRealUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpdateStyleBookEntryName() throws PortalException {
		String oldName = RandomTestUtil.randomString();

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), oldName,
				StringPool.BLANK, _serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		String newName = RandomTestUtil.randomString();

		actionRequest.addParameter("name", newName);

		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		ReflectionTestUtil.invoke(
			_updateStyleBookEntryNameMVCActionCommandTest,
			"_updateStyleBookEntry", new Class<?>[] {ActionRequest.class},
			actionRequest);

		Assert.assertEquals(
			1,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_group.getGroupId()));
		Assert.assertEquals(
			1,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_group.getGroupId(), newName));
		Assert.assertEquals(
			0,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_group.getGroupId(), oldName));
	}

	@Test(expected = StyleBookEntryNameException.class)
	public void testUpdateStyleBookEntryNameWithEmptyName()
		throws PortalException {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter("name", StringPool.BLANK);
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		ReflectionTestUtil.invoke(
			_updateStyleBookEntryNameMVCActionCommandTest,
			"_updateStyleBookEntry", new Class<?>[] {ActionRequest.class},
			actionRequest);
	}

	@Test(expected = StyleBookEntryNameException.class)
	public void testUpdateStyleBookEntryNameWithInvalidCharPeriod()
		throws PortalException {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);
		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter("name", ".");
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		ReflectionTestUtil.invoke(
			_updateStyleBookEntryNameMVCActionCommandTest,
			"_updateStyleBookEntry", new Class<?>[] {ActionRequest.class},
			actionRequest);
	}

	@Test(expected = StyleBookEntryNameException.class)
	public void testUpdateStyleBookEntryNameWithInvalidCharSlash()
		throws PortalException {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);
		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter("name", "/");
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		ReflectionTestUtil.invoke(
			_updateStyleBookEntryNameMVCActionCommandTest,
			"_updateStyleBookEntry", new Class<?>[] {ActionRequest.class},
			actionRequest);
	}

	@Test(expected = StyleBookEntryNameException.class)
	public void testUpdateStyleBookEntryNameWithNameMaxLength()
		throws PortalException {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);
		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter("name", RandomTestUtil.randomString(256));
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		ReflectionTestUtil.invoke(
			_updateStyleBookEntryNameMVCActionCommandTest,
			"_updateStyleBookEntry", new Class<?>[] {ActionRequest.class},
			actionRequest);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	private ThemeDisplay _themeDisplay;

	@Inject(
		filter = "mvc.command.name=/style_book/update_style_book_entry_name"
	)
	private MVCActionCommand _updateStyleBookEntryNameMVCActionCommandTest;

}