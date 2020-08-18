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

package com.liferay.content.dashboard.web.internal.servlet.taglib.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class ContentDashboardDropdownItemsProviderTest {

	@BeforeClass
	public static void setUpClass() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		_http = new HttpImpl();

		_language = new LanguageImpl();

		LanguageResources languageResources = new LanguageResources();

		languageResources.setConfig(StringPool.BLANK);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testGetEditURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider =
				new ContentDashboardDropdownItemsProvider(
					_http, _language, mockLiferayPortletRenderRequest,
					new MockLiferayPortletRenderResponse(), new PortalImpl());

		ContentDashboardItem contentDashboardItem = _getContentDashboardItem(
			Collections.singletonList(
				_getContentDashboardItemAction(
					"edit", ContentDashboardItemAction.Type.EDIT, "validURL")));

		List<DropdownItem> dropdownItems =
			contentDashboardDropdownItemsProvider.getDropdownItems(
				contentDashboardItem);

		Stream<DropdownItem> stream = dropdownItems.stream();

		DropdownItem editDropdownItem = stream.filter(
			dropdownItem -> Objects.equals(
				String.valueOf(dropdownItem.get("label")), "edit")
		).findFirst(
		).orElseThrow(
			() -> new AssertionError()
		);

		Assert.assertEquals(
			"validURL",
			_http.getPath(String.valueOf(editDropdownItem.get("href"))));
	}

	@Test
	public void testGetURLBackURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider =
				new ContentDashboardDropdownItemsProvider(
					_http, _language, mockLiferayPortletRenderRequest,
					new MockLiferayPortletRenderResponse(), new PortalImpl());

		ContentDashboardItem contentDashboardItem = _getContentDashboardItem(
			Collections.singletonList(
				_getContentDashboardItemAction(
					"view", ContentDashboardItemAction.Type.VIEW, "validURL")));

		List<DropdownItem> dropdownItems =
			contentDashboardDropdownItemsProvider.getDropdownItems(
				contentDashboardItem);

		Stream<DropdownItem> stream = dropdownItems.stream();

		DropdownItem viewDropdownItem = stream.filter(
			dropdownItem -> Objects.equals(
				String.valueOf(dropdownItem.get("label")), "view")
		).findFirst(
		).orElseThrow(
			() -> new AssertionError()
		);

		Assert.assertEquals(
			HtmlUtil.escapeURL(String.valueOf(mockLiferayPortletURL)),
			_http.getParameter(
				String.valueOf(viewDropdownItem.get("href")), "p_l_back_url"));
	}

	@Test
	public void testGetURLBackURLWithBackURLParameter() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			new MockLiferayPortletURL());

		String backURL = RandomTestUtil.randomString();

		mockLiferayPortletRenderRequest.setParameter("backURL", backURL);

		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider =
				new ContentDashboardDropdownItemsProvider(
					_http, _language, mockLiferayPortletRenderRequest,
					new MockLiferayPortletRenderResponse(), new PortalImpl());

		ContentDashboardItem contentDashboardItem = _getContentDashboardItem(
			Collections.singletonList(
				_getContentDashboardItemAction(
					"view", ContentDashboardItemAction.Type.VIEW, "validURL")));

		List<DropdownItem> dropdownItems =
			contentDashboardDropdownItemsProvider.getDropdownItems(
				contentDashboardItem);

		Stream<DropdownItem> stream = dropdownItems.stream();

		DropdownItem viewDropdownItem = stream.filter(
			dropdownItem -> Objects.equals(
				String.valueOf(dropdownItem.get("label")), "view")
		).findFirst(
		).orElseThrow(
			() -> new AssertionError()
		);

		Assert.assertEquals(
			backURL,
			_http.getParameter(
				String.valueOf(viewDropdownItem.get("href")), "p_l_back_url"));
	}

	@Test
	public void testGetViewInPanelURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider =
				new ContentDashboardDropdownItemsProvider(
					_http, _language, mockLiferayPortletRenderRequest,
					new MockLiferayPortletRenderResponse(), new PortalImpl());

		ContentDashboardItem contentDashboardItem = _getContentDashboardItem(
			Collections.singletonList(
				_getContentDashboardItemAction(
					"viewInPanel",
					ContentDashboardItemAction.Type.VIEW_IN_PANEL,
					"validURL")));

		List<DropdownItem> dropdownItems =
			contentDashboardDropdownItemsProvider.getDropdownItems(
				contentDashboardItem);

		Stream<DropdownItem> stream = dropdownItems.stream();

		DropdownItem viewInPanelDropdownItem = stream.filter(
			dropdownItem -> Objects.equals(
				String.valueOf(dropdownItem.get("label")), "viewInPanel")
		).findFirst(
		).orElseThrow(
			() -> new AssertionError()
		);

		Map<String, Object> data =
			(Map<String, Object>)viewInPanelDropdownItem.get("data");

		Assert.assertEquals("showMetrics", String.valueOf(data.get("action")));
		Assert.assertEquals("validURL", String.valueOf(data.get("fetchURL")));
	}

	@Test
	public void testGetViewURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider =
				new ContentDashboardDropdownItemsProvider(
					_http, _language, mockLiferayPortletRenderRequest,
					new MockLiferayPortletRenderResponse(), new PortalImpl());

		ContentDashboardItem contentDashboardItem = _getContentDashboardItem(
			Collections.singletonList(
				_getContentDashboardItemAction(
					"view", ContentDashboardItemAction.Type.VIEW, "validURL")));

		List<DropdownItem> dropdownItems =
			contentDashboardDropdownItemsProvider.getDropdownItems(
				contentDashboardItem);

		Stream<DropdownItem> stream = dropdownItems.stream();

		DropdownItem viewDropdownItem = stream.filter(
			dropdownItem -> Objects.equals(
				String.valueOf(dropdownItem.get("label")), "view")
		).findFirst(
		).orElseThrow(
			() -> new AssertionError()
		);

		Assert.assertEquals(
			"validURL",
			_http.getPath(String.valueOf(viewDropdownItem.get("href"))));
	}

	private ContentDashboardItem _getContentDashboardItem(
		List<ContentDashboardItemAction> contentDashboardItemActions) {

		return new ContentDashboardItem() {

			@Override
			public List<AssetCategory> getAssetCategories() {
				return Collections.emptyList();
			}

			@Override
			public List<AssetCategory> getAssetCategories(long vocabularyId) {
				return Collections.emptyList();
			}

			@Override
			public List<AssetTag> getAssetTags() {
				return Collections.emptyList();
			}

			@Override
			public List<Locale> getAvailableLocales() {
				return null;
			}

			@Override
			public List<ContentDashboardItemAction>
				getContentDashboardItemActions(
					HttpServletRequest httpServletRequest,
					ContentDashboardItemAction.Type... types) {

				Stream<ContentDashboardItemAction> stream =
					contentDashboardItemActions.stream();

				return stream.filter(
					contentDashboardItemAction -> ArrayUtil.contains(
						types, contentDashboardItemAction.getType())
				).collect(
					Collectors.toList()
				);
			}

			@Override
			public ContentDashboardItemType getContentDashboardItemType() {
				return null;
			}

			@Override
			public Date getCreateDate() {
				return null;
			}

			@Override
			public Map<String, Object> getData(Locale locale) {
				return null;
			}

			@Override
			public Locale getDefaultLocale() {
				return null;
			}

			@Override
			public Object getDisplayFieldValue(
				String fieldName, Locale locale) {

				return null;
			}

			@Override
			public InfoItemReference getInfoItemReference() {
				return new InfoItemReference(
					RandomTestUtil.randomString(), RandomTestUtil.randomLong());
			}

			@Override
			public Date getModifiedDate() {
				return null;
			}

			@Override
			public String getScopeName(Locale locale) {
				return null;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public long getUserId() {
				return 0;
			}

			@Override
			public List<Version> getVersions(Locale locale) {
				return null;
			}

			@Override
			public boolean isViewable(HttpServletRequest httpServletRequest) {
				return false;
			}

		};
	}

	private ContentDashboardItemAction _getContentDashboardItemAction(
		String label, ContentDashboardItemAction.Type type, String url) {

		return new ContentDashboardItemAction() {

			@Override
			public String getIcon() {
				return null;
			}

			@Override
			public String getLabel(Locale locale) {
				return label;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public Type getType() {
				return type;
			}

			@Override
			public String getURL() {
				return url;
			}

			@Override
			public String getURL(Locale locale) {
				return getURL();
			}

		};
	}

	private static Http _http;
	private static Language _language;

}