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
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.content.dashboard.item.filter.ContentDashboardItemFilter;
import com.liferay.content.dashboard.item.filter.provider.ContentDashboardItemFilterProvider;
import com.liferay.content.dashboard.web.internal.item.filter.ContentDashboardItemFilterProviderRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

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
	public void testGetClearResultsURL() {
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

		ContentDashboardItemFilterProviderRegistry
			contentDashboardItemFilterProviderRegistry = Mockito.mock(
				ContentDashboardItemFilterProviderRegistry.class);

		Mockito.when(
			contentDashboardItemFilterProviderRegistry.
				getContentDashboardItemFilterProviders()
		).thenReturn(
			Collections.singletonList(
				new ContentDashboardItemFilterProvider() {

					@Override
					public ContentDashboardItemFilter
							getContentDashboardItemFilter(
								HttpServletRequest httpServletRequest)
						throws ContentDashboardItemActionException {

						return new ContentDashboardItemFilter() {

							@Override
							public DropdownItem getDropdownItem() {
								return null;
							}

							@Override
							public String getIcon() {
								return null;
							}

							@Override
							public String getLabel(Locale locale) {
								return "contentDashboardItemFilterLabel";
							}

							@Override
							public String getName() {
								return "contentDashboardItemFilterName";
							}

							@Override
							public String getParameterLabel(Locale locale) {
								return "contentDashboardItemFilterParameter" +
									"Label";
							}

							@Override
							public String getParameterName() {
								return "contentDashboardItemFilterParameter" +
									"Name";
							}

							@Override
							public List<String> getParameterValues() {
								return Arrays.asList("value1", "value2");
							}

							@Override
							public Type getType() {
								return Type.ITEM_SELECTOR;
							}

							@Override
							public String getURL() {
								return "";
							}

						};
					}

					@Override
					public String getKey() {
						return "key";
					}

					@Override
					public ContentDashboardItemFilter.Type getType() {
						return ContentDashboardItemFilter.Type.ITEM_SELECTOR;
					}

					@Override
					public boolean isShow(
						HttpServletRequest httpServletRequest) {

						return true;
					}

				})
		);

		ContentDashboardAdminManagementToolbarDisplayContext
			contentDashboardAdminManagementToolbarDisplayContext =
				new ContentDashboardAdminManagementToolbarDisplayContext(
					Mockito.mock(AssetCategoryLocalService.class),
					Mockito.mock(AssetVocabularyLocalService.class),
					contentDashboardAdminDisplayContext,
					contentDashboardItemFilterProviderRegistry,
					Mockito.mock(GroupLocalService.class),
					new MockHttpServletRequest(),
					Mockito.mock(ItemSelector.class),
					LanguageUtil.getLanguage(), mockLiferayPortletActionRequest,
					new MockLiferayPortletActionResponse(), LocaleUtil.US,
					Mockito.mock(UserLocalService.class));

		Assert.assertEquals(
			"http//localhost/test?param_keywords=;param_status=-1",
			contentDashboardAdminManagementToolbarDisplayContext.
				getClearResultsURL());
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
					Mockito.mock(
						ContentDashboardItemFilterProviderRegistry.class),
					Mockito.mock(GroupLocalService.class),
					new MockHttpServletRequest(),
					Mockito.mock(ItemSelector.class),
					LanguageUtil.getLanguage(), mockLiferayPortletActionRequest,
					new MockLiferayPortletActionResponse(), LocaleUtil.US,
					Mockito.mock(UserLocalService.class));

		List<LabelItem> labelItems =
			contentDashboardAdminManagementToolbarDisplayContext.
				getFilterLabelItems();

		Assert.assertEquals(String.valueOf(labelItems), 1, labelItems.size());

		LabelItem labelItem = labelItems.get(0);

		Assert.assertEquals("status: scheduled", labelItem.get("label"));
	}

	@Test
	public void testGetFilterLabelItemsWithContentDashboardItemFilterProvider() {
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

		ContentDashboardItemFilterProviderRegistry
			contentDashboardItemFilterProviderRegistry = Mockito.mock(
				ContentDashboardItemFilterProviderRegistry.class);

		Mockito.when(
			contentDashboardItemFilterProviderRegistry.
				getContentDashboardItemFilterProviders()
		).thenReturn(
			Collections.singletonList(
				new ContentDashboardItemFilterProvider() {

					@Override
					public ContentDashboardItemFilter
							getContentDashboardItemFilter(
								HttpServletRequest httpServletRequest)
						throws ContentDashboardItemActionException {

						return new ContentDashboardItemFilter() {

							@Override
							public DropdownItem getDropdownItem() {
								return null;
							}

							@Override
							public String getIcon() {
								return null;
							}

							@Override
							public String getLabel(Locale locale) {
								return "contentDashboardItemFilterLabel";
							}

							@Override
							public String getName() {
								return "contentDashboardItemFilterName";
							}

							@Override
							public String getParameterLabel(Locale locale) {
								return "contentDashboardItemFilterParameter" +
									"Label";
							}

							@Override
							public String getParameterName() {
								return "contentDashboardItemFilterParameter" +
									"Name";
							}

							@Override
							public List<String> getParameterValues() {
								return Arrays.asList("value1", "value2");
							}

							@Override
							public Type getType() {
								return Type.ITEM_SELECTOR;
							}

							@Override
							public String getURL() {
								return "";
							}

						};
					}

					@Override
					public String getKey() {
						return "key";
					}

					@Override
					public ContentDashboardItemFilter.Type getType() {
						return ContentDashboardItemFilter.Type.ITEM_SELECTOR;
					}

					@Override
					public boolean isShow(
						HttpServletRequest httpServletRequest) {

						return true;
					}

				})
		);

		ContentDashboardAdminManagementToolbarDisplayContext
			contentDashboardAdminManagementToolbarDisplayContext =
				new ContentDashboardAdminManagementToolbarDisplayContext(
					Mockito.mock(AssetCategoryLocalService.class),
					Mockito.mock(AssetVocabularyLocalService.class),
					Mockito.mock(ContentDashboardAdminDisplayContext.class),
					contentDashboardItemFilterProviderRegistry,
					Mockito.mock(GroupLocalService.class),
					new MockHttpServletRequest(),
					Mockito.mock(ItemSelector.class),
					LanguageUtil.getLanguage(), mockLiferayPortletActionRequest,
					new MockLiferayPortletActionResponse(), LocaleUtil.US,
					Mockito.mock(UserLocalService.class));

		Assert.assertEquals(
			2,
			ListUtil.count(
				contentDashboardAdminManagementToolbarDisplayContext.
					getFilterLabelItems(),
				labelItem ->
					Objects.equals(
						labelItem.get("label"),
						"contentDashboardItemFilterParameterLabel: value1") ||
					Objects.equals(
						labelItem.get("label"),
						"contentDashboardItemFilterParameterLabel: value2")));
	}

}