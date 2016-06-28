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
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.wiki.constants.WikiPortletKeys;

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
public class WikiAttachmentCKEditorEditorConfigContributorTest
	extends PowerMockito {

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

		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");

		setAllowBrowseDocuments(true);
	}

	@Test
	public void testImageSelectorButtonNotRemovedWhenValidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableAndImageSelectorButtonsJSONArray());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", "itemSelectorPortletURL");
		originalJSONObject.put(
			"filebrowserImageBrowseUrl", "itemSelectorPortletURL");

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testImageSelectorButtonRemovedWhenInvalidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(0);

		JSONObject jsonObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableAndImageSelectorButtonsJSONArray());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableButtonJSONArray());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testImageSelectorButtonRemovedWhenNotAllowBrowseDocuments()
		throws Exception {

		setAllowBrowseDocuments(false);
		setWikiPageResourcePrimKey(1);

		JSONObject jsonObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableAndImageSelectorButtonsJSONArray());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableButtonJSONArray());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutCreoleToolbarDoesNotChangeWhenInvalidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutCreoleToolbarDoesNotChangeWithValidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", "itemSelectorPortletURL");
		originalJSONObject.put(
			"filebrowserImageBrowseUrl", "itemSelectorPortletURL");

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutImageSelectorButtonDoesNotChangeWithInvalidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableAndHorizontalRuleButtonsJSONArray());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutImageSelectorButtonDoesNotChangeWithValidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = getJSONObjectWithCreoleToolbar(
			getToolbarsWithTableAndHorizontalRuleButtonsJSONArray());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentCKEditorEditorConfigContributor
			wikiAttachmentCKEditorEditorConfigContributor =
				new WikiAttachmentCKEditorEditorConfigContributor();

		wikiAttachmentCKEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", "itemSelectorPortletURL");
		originalJSONObject.put(
			"filebrowserImageBrowseUrl", "itemSelectorPortletURL");

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	protected JSONObject getJSONObjectWithCreoleToolbar(JSONArray jsonArray)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("extraPlugins", "plugin1,plugin2,plugin3");
		jsonObject.put("toolbar_creole", jsonArray);

		return jsonObject;
	}

	protected JSONArray getToolbarsWithTableAndHorizontalRuleButtonsJSONArray()
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			JSONFactoryUtil.createJSONArray("['Table', 'HorizontalRule']"));

		return jsonArray;
	}

	protected JSONArray getToolbarsWithTableAndImageSelectorButtonsJSONArray()
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			JSONFactoryUtil.createJSONArray("['Table', 'ImageSelector']"));

		return jsonArray;
	}

	protected JSONArray getToolbarsWithTableButtonJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(JSONFactoryUtil.createJSONArray("['Table']"));

		return jsonArray;
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

}