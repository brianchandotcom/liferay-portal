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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLWrapper;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;
import com.liferay.wiki.configuration.WikiFileUploadConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.HashMap;
import java.util.Map;

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
public class WikiAttachmentImageCreoleEditorConfigContributorTest
	extends PowerMockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");
	}

	@Test
	public void testItemSelectorURLWhenAllowBrowseAndNullWikiPage()
		throws Exception {

		setAllowBrowseDocuments(true);
		setWikiPageResourcePrimKey(0);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			new PortletURLWrapper(null) {

				@Override
				public String toString() {
					return "itemSelectorPortletURLWithUrlSelectionViews";
				}

			}
		);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		WikiAttachmentImageCreoleEditorConfigContributor
			wikiAttachmentImageCreoleEditorConfigContributor =
				new WikiAttachmentImageCreoleEditorConfigContributor();

		wikiAttachmentImageCreoleEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentImageCreoleEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONObject expectedJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"itemSelectorPortletURLWithUrlSelectionViews"
		).put(
			"filebrowserImageBrowseUrl",
			"itemSelectorPortletURLWithUrlSelectionViews"
		).put(
			"removePlugins", "plugin1,ae_addimages"
		);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenAllowBrowseAndValidWikiPage()
		throws Exception {

		setAllowBrowseDocuments(true);
		setWikiPageResourcePrimKey(1);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class),
				Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			new PortletURLWrapper(null) {

				@Override
				public String toString() {
					return "itemSelectorPortletURLWithWikiUrl" +
						"AndUploadSelectionViews";
				}

			}
		);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory = mock(
			RequestBackedPortletURLFactory.class);

		when(
			requestBackedPortletURLFactory.createActionURL(WikiPortletKeys.WIKI)
		).thenReturn(
			ProxyFactory.newDummyInstance(LiferayPortletURL.class)
		);

		JSONObject jsonObject = getJSONObjectWithDefaultItemSelectorURL();

		WikiAttachmentImageCreoleEditorConfigContributor
			wikiAttachmentImageCreoleEditorConfigContributor =
				new WikiAttachmentImageCreoleEditorConfigContributor();

		wikiAttachmentImageCreoleEditorConfigContributor.setItemSelector(
			_itemSelector);
		wikiAttachmentImageCreoleEditorConfigContributor.
			setWikiFileUploadConfiguration(
				new WikiFileUploadConfiguration() {

					@Override
					public long attachmentMaxSize() {
						return 0;
					}

					@Override
					public String[] attachmentMimeTypes() {
						return new String[] {StringPool.STAR};
					}

				});

		wikiAttachmentImageCreoleEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, new ThemeDisplay(),
				requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"itemSelectorPortletURLWithWikiUrlAndUploadSelectionViews"
		).put(
			"filebrowserImageBrowseUrl",
			"itemSelectorPortletURLWithWikiUrlAndUploadSelectionViews"
		).put(
			"removePlugins", "plugin1"
		);

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

		WikiAttachmentImageCreoleEditorConfigContributor
			wikiAttachmentImageCreoleEditorConfigContributor =
				new WikiAttachmentImageCreoleEditorConfigContributor();

		wikiAttachmentImageCreoleEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentImageCreoleEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		expectedJSONObject.put("removePlugins", "plugin1");

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
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

		WikiAttachmentImageCreoleEditorConfigContributor
			wikiAttachmentImageCreoleEditorConfigContributor =
				new WikiAttachmentImageCreoleEditorConfigContributor();

		wikiAttachmentImageCreoleEditorConfigContributor.setItemSelector(
			_itemSelector);

		wikiAttachmentImageCreoleEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		expectedJSONObject.put("removePlugins", "plugin1");

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	protected JSONObject getJSONObjectWithDefaultItemSelectorURL()
		throws Exception {

		return JSONUtil.put(
			"filebrowserImageBrowseLinkUrl", "defaultItemSelectorPortletURL"
		).put(
			"filebrowserImageBrowseUrl", "defaultItemSelectorPortletURL"
		).put(
			"removePlugins", "plugin1"
		);
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

}