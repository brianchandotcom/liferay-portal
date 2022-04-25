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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminManagementToolbarDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Test
	public void testGetFilterLabelItems() {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest() {

				@Override
				public Portlet getPortlet() {
					Portlet portlet = Mockito.mock(Portlet.class);

					PortletApp portletApp = Mockito.mock(PortletApp.class);

					Mockito.when(
						portlet.getPortletApp()
					).thenReturn(
						portletApp
					);

					return portlet;
				}

			};

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext = Mockito.mock(
				ContentDashboardAdminDisplayContext.class);

		Mockito.when(
			contentDashboardAdminDisplayContext.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_SCHEDULED
		);

		ContentDashboardAdminManagementToolbarDisplayContext
			contentDashboardAdminManagementToolbarDisplayContext =
				new ContentDashboardAdminManagementToolbarDisplayContext(
					Mockito.mock(AssetCategoryLocalService.class),
					Mockito.mock(AssetVocabularyLocalService.class),
					contentDashboardAdminDisplayContext,
					Mockito.mock(GroupLocalService.class),
					new MockHttpServletRequest(), LanguageUtil.getLanguage(),
					mockLiferayPortletActionRequest,
					new MockLiferayPortletActionResponse(), LocaleUtil.US,
					Mockito.mock(UserLocalService.class));

		List<LabelItem> labelItems =
			contentDashboardAdminManagementToolbarDisplayContext.
				getFilterLabelItems();

		Assert.assertEquals(String.valueOf(labelItems), 1, labelItems.size());

		LabelItem labelItem = labelItems.get(0);

		Assert.assertEquals("status: scheduled", labelItem.get("label"));
	}

}