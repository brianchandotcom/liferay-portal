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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletURLWrapper;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Roberto Díaz
 */
public class WikiLinksCKEditorEditorConfigContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		ItemSelector itemSelector = Mockito.mock(ItemSelector.class);

		Mockito.when(
			itemSelector.getItemSelectorURL(
				Matchers.any(RequestBackedPortletURLFactory.class),
				Matchers.anyString(), Matchers.any(ItemSelectorCriterion.class))
		).thenReturn(
			new PortletURLWrapper(null) {

				@Override
				public String toString() {
					return "oneTabItemSelectorPortletURL";
				}

			}
		);

		Mockito.when(
			itemSelector.getItemSelectorURL(
				Matchers.any(RequestBackedPortletURLFactory.class),
				Matchers.anyString(), Matchers.any(ItemSelectorCriterion.class),
				Matchers.any(ItemSelectorCriterion.class))
		).thenReturn(
			new PortletURLWrapper(null) {

				@Override
				public String toString() {
					return "twoTabsItemSelectorPortletURL";
				}

			}
		);

		_wikiLinksCKEditorEditorConfigContributor =
			new WikiLinksCKEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			_wikiLinksCKEditorEditorConfigContributor, "itemSelector",
			itemSelector);
	}

	@Before
	public void setUp(){
		_inputEditorTaglibAttributes.put(
				"liferay-ui:input-editor:name", "testEditor");
	}

	@Test
	public void testItemSelectorURLWhenNullWikiPageAndValidNode()
		throws Exception {

		populateInputEditorWikiPageAttributes(0, 1);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		_wikiLinksCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONObject expectedJSONObject = JSONUtil.put(
			"filebrowserBrowseUrl", "oneTabItemSelectorPortletURL"
		).put(
			"removePlugins", "plugin1"
		);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenValidWikiPageAndNode() throws Exception {
		populateInputEditorWikiPageAttributes(1, 1);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		_wikiLinksCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONObject expectedJSONObject = JSONUtil.put(
			"filebrowserBrowseUrl", "twoTabsItemSelectorPortletURL"
		).put(
			"removePlugins", "plugin1"
		);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenValidWikiPageAndNullNode()
		throws Exception {

		populateInputEditorWikiPageAttributes(1, 0);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		_wikiLinksCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONObject expectedJSONObject = JSONUtil.put(
			"filebrowserBrowseUrl", "oneTabItemSelectorPortletURL"
		).put(
			"removePlugins", "plugin1"
		);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	protected JSONObject getJSONObjectWithDefaultItemSelectorURL() {
		return JSONUtil.put(
			"filebrowserBrowseUrl", "defaultItemSelectorPortletURL"
		).put(
			"removePlugins", "plugin1"
		);
	}

	protected void populateInputEditorWikiPageAttributes(
		long wikiPageResourcePrimKey, long nodeId) {

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:fileBrowserParams",
			HashMapBuilder.put(
				"nodeId", String.valueOf(nodeId)
			).put(
				"wikiPageResourcePrimKey",
				String.valueOf(wikiPageResourcePrimKey)
			).build());
	}

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();
	private static WikiLinksCKEditorConfigContributor
		_wikiLinksCKEditorEditorConfigContributor;

}