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
import com.liferay.portal.kernel.json.JSONException;
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
 * @author Sergio González
 */
public class WikiAttachmentAlloyEditorEditorConfigContributorTest
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
	public void testImageButtonNotRemovedWhenValidWikiPage() throws Exception {
		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = getJSONObjectWithToolbar(
			getToolbarsWithCameraAndImageButtonsJSONObject());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
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
	public void testImageButtonRemovedWhenInvalidWikiPage() throws Exception {
		setWikiPageResourcePrimKey(0);

		JSONObject jsonObject = getJSONObjectWithToolbar(
			getToolbarsWithCameraAndImageButtonsJSONObject());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = getJSONObjectWithToolbar(
			getToolbarsWithCameraButtonJSONObject());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testImageButtonRemovedWhenNotAllowBrowseDocuments()
		throws Exception {

		setAllowBrowseDocuments(false);
		setWikiPageResourcePrimKey(1);

		JSONObject jsonObject = getJSONObjectWithToolbar(
			getToolbarsWithCameraAndImageButtonsJSONObject());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("extraPlugins", "plugin1,plugin2,plugin3");
		expectedJSONObject.put(
			"toolbars", getToolbarsWithCameraButtonJSONObject());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutImageButtonDoesNotChangeWithInvalidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject = getJSONObjectWithToolbar(
			getToolbarsWithCameraAndHlineButtonsJSONObject());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutImageButtonDoesNotChangeWithValidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = getJSONObjectWithToolbar(
			getToolbarsWithCameraAndHlineButtonsJSONObject());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
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
	public void testJSONWithoutToolbarDoesNotChangeWhenInvalidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithoutToolbarDoesNotChangeWithValidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
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
	public void testJSONWithToolbarStylesDoesNotChangeWithInvalidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(0);

		JSONObject originalJSONObject = getJSONObjectWithToolbar(
			getToolbarsWithStylesJSONObject());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testJSONWithToolbarStylesDoesNotChangeWithValidWikiPage()
		throws Exception {

		setWikiPageResourcePrimKey(1);

		JSONObject originalJSONObject = getJSONObjectWithToolbar(
			getToolbarsWithStylesJSONObject());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentAlloyEditorEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", "itemSelectorPortletURL");
		originalJSONObject.put(
			"filebrowserImageBrowseUrl", "itemSelectorPortletURL");

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	protected JSONObject getJSONObjectWithToolbar(JSONObject jsonObject)
		throws Exception {

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		originalJSONObject.put("toolbars", jsonObject);

		return originalJSONObject;
	}

	protected JSONObject getToolbarsStylesSelectionsJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("selections", "AlloyEditor.Selections");
		jsonObject.put("tabIndex", 1);

		return jsonObject;
	}

	protected JSONObject getToolbarsWidthAddAndCameraAndHlineButtonsJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons", JSONFactoryUtil.createJSONArray("['camera', 'hline']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsWithAddAndCameraAndImageButtonsJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons", JSONFactoryUtil.createJSONArray("['camera', 'image']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsWithAddAndCameraButtonJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons", JSONFactoryUtil.createJSONArray("['camera']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsWithCameraAndHlineButtonsJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"add", getToolbarsWidthAddAndCameraAndHlineButtonsJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsWithCameraAndImageButtonsJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"add", getToolbarsWithAddAndCameraAndImageButtonsJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsWithCameraButtonJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsWithAddAndCameraButtonJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsWithStylesJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("styles", getToolbarsStylesSelectionsJSONObject());

		return jsonObject;
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