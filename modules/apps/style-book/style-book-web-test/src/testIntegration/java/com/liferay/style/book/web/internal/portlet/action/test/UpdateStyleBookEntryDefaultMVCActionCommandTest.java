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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
public class UpdateStyleBookEntryDefaultMVCActionCommandTest {

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

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		_themeDisplay.setLayout(layout);
		_themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
	}

	@Test
	public void testMarkAsDefaultStyleBookEntry() throws Exception {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"defaultStyleBookEntry", Boolean.TRUE.toString());
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryDefaultMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertEquals(
			styleBookEntry,
			_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
				_group.getGroupId()));
	}

	@Test
	public void testReplaceDefaultStyleBookEntry() throws Exception {
		StyleBookEntry styleBookEntry1 =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"defaultStyleBookEntry", Boolean.TRUE.toString());
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry1.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryDefaultMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertEquals(
			styleBookEntry1,
			_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
				_group.getGroupId()));

		StyleBookEntry styleBookEntry2 =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		actionRequest = new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"defaultStyleBookEntry", Boolean.TRUE.toString());
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry2.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryDefaultMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertEquals(
			styleBookEntry2,
			_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
				_group.getGroupId()));
		Assert.assertNotEquals(
			styleBookEntry1,
			_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
				_group.getGroupId()));
	}

	@Test
	public void testUnmarkAsDefaultStyleBookEntry() throws Exception {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"defaultStyleBookEntry", Boolean.TRUE.toString());
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryDefaultMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertEquals(
			styleBookEntry,
			_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
				_group.getGroupId()));

		actionRequest = new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"defaultStyleBookEntry", Boolean.FALSE.toString());
		actionRequest.addParameter(
			"styleBookEntryId",
			String.valueOf(styleBookEntry.getStyleBookEntryId()));
		actionRequest.setAttribute(
			WebKeys.PORTLET_ID, StyleBookPortletKeys.STYLE_BOOK);
		actionRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_updateStyleBookEntryDefaultMVCActionCommandTest.processAction(
			actionRequest, new MockActionResponse());

		Assert.assertNull(
			_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
				_group.getGroupId()));
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
		filter = "mvc.command.name=/style_book/update_style_book_entry_default"
	)
	private MVCActionCommand _updateStyleBookEntryDefaultMVCActionCommandTest;

}