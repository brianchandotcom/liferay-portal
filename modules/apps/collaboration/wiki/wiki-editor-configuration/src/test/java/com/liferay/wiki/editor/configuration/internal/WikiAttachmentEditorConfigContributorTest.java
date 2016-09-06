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

package com.liferay.wiki.editor.configuration.internal;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.impl.WikiPageImpl;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Roberto Díaz
 */
public class WikiAttachmentEditorConfigContributorTest extends PowerMockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		_requestBackedPortletURLFactory = mock(
			RequestBackedPortletURLFactory.class);

		when(
			_requestBackedPortletURLFactory.createActionURL(
				WikiPortletKeys.WIKI)
		).thenReturn(
			mock(LiferayPortletURL.class)
		);

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");
	}

	@Test
	public void testItemSelectorURLWhenAllowBrowseAndNullWikiPage()
		throws Exception {

		setAllowBrowseDocuments(true);
		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentEditorConfigContributor
			wikiAttachmentEditorConfigContributor =
				new WikiAttachmentEditorConfigContributor();

		wikiAttachmentEditorConfigContributor.setItemSelector(_itemSelector);

		wikiAttachmentEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenAllowBrowseAndValidCreoleWikiPage()
		throws Exception {

		setAllowBrowseDocuments(true);
		setWikiPageResourcePrimKey(1);

		mockWikiPageLocalService(1, "creole");

		PortletURL creoleEngineItemSelectorPortletURL = mock(PortletURL.class);

		when(
			creoleEngineItemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURLWithWikiUrlAndUploadSelectionViews"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			creoleEngineItemSelectorPortletURL
		);

		JSONObject jsonObject = getJSONObjectWithDefaultItemSelectorURL();

		WikiAttachmentEditorConfigContributor
			wikiAttachmentEditorConfigContributor =
				new WikiAttachmentEditorConfigContributor();

		wikiAttachmentEditorConfigContributor.setItemSelector(_itemSelector);

		wikiAttachmentEditorConfigContributor.setWikiPageLocalService(
			_wikiPageLocalService);

		wikiAttachmentEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put(
			"filebrowserImageBrowseLinkUrl",
			"itemSelectorPortletURLWithWikiUrlAndUploadSelectionViews");
		expectedJSONObject.put(
			"filebrowserImageBrowseUrl",
			"itemSelectorPortletURLWithWikiUrlAndUploadSelectionViews");

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenAllowBrowseAndValidHtmlWikiPage()
		throws Exception {

		setAllowBrowseDocuments(true);
		setWikiPageResourcePrimKey(1);

		mockWikiPageLocalService(1, "html");

		PortletURL htmlEngineItemSelectorPortletURL = mock(PortletURL.class);

		when(
			htmlEngineItemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURLWithWikiImageUrlAndUploadSelectionViews"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			htmlEngineItemSelectorPortletURL
		);

		JSONObject jsonObject = getJSONObjectWithDefaultItemSelectorURL();

		WikiAttachmentEditorConfigContributor
			wikiAttachmentEditorConfigContributor =
				new WikiAttachmentEditorConfigContributor();

		wikiAttachmentEditorConfigContributor.setItemSelector(_itemSelector);

		wikiAttachmentEditorConfigContributor.setWikiPageLocalService(
			_wikiPageLocalService);

		wikiAttachmentEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put(
			"filebrowserImageBrowseLinkUrl",
			"itemSelectorPortletURLWithWikiImageUrlAndUploadSelectionViews");
		expectedJSONObject.put(
			"filebrowserImageBrowseUrl",
			"itemSelectorPortletURLWithWikiImageUrlAndUploadSelectionViews");

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenNotAllowBrowseAndNullWikiPage()
		throws Exception {

		setAllowBrowseDocuments(false);
		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentEditorConfigContributor
			wikiAttachmentEditorConfigContributor =
				new WikiAttachmentEditorConfigContributor();

		wikiAttachmentEditorConfigContributor.setItemSelector(_itemSelector);

		wikiAttachmentEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenNotAllowBrowseAndValidWikiPage()
		throws Exception {

		setAllowBrowseDocuments(false);
		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentEditorConfigContributor
			wikiAttachmentEditorConfigContributor =
				new WikiAttachmentEditorConfigContributor();

		wikiAttachmentEditorConfigContributor.setItemSelector(_itemSelector);

		wikiAttachmentEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	protected JSONObject getJSONObjectWithDefaultItemSelectorURL()
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", "defaultItemSelectorPortletURL");
		jsonObject.put(
			"filebrowserImageBrowseUrl", "defaultItemSelectorPortletURL");

		return jsonObject;
	}

	protected void mockWikiPageLocalService(long primKey, String format) {
		ConfigurationFactoryUtil.setConfigurationFactory(
			new ConfigurationFactoryImpl());

		WikiPage wikiPage = new WikiPageImpl();

		wikiPage.setFormat(format);

		try {
			when(
				_wikiPageLocalService.getPage(primKey)
			).thenReturn(
				wikiPage
			);
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}
	}

	protected void setAllowBrowseDocuments(boolean allowBrowseDocuments) {
		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:allowBrowseDocuments",
			allowBrowseDocuments);
	}

	protected void setWikiPageResourcePrimKey(long primKey) {
		Map<String, String> fileBrowserParamsMap = new HashMap<>();

		fileBrowserParamsMap.put(
			"wikiPageResourcePrimKey", String.valueOf(primKey));

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:fileBrowserParams", fileBrowserParamsMap);
	}

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();

	@Mock
	private ItemSelector _itemSelector;

	private RequestBackedPortletURLFactory _requestBackedPortletURLFactory;

	@Mock
	private ThemeDisplay _themeDisplay;

	@Mock
	private WikiPageLocalService _wikiPageLocalService;

}