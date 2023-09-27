/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				Mockito.nullable(RequestBackedPortletURLFactory.class),
				Mockito.nullable(String.class),
				Mockito.nullable(ItemSelectorCriterion.class))
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
				Mockito.nullable(RequestBackedPortletURLFactory.class),
				Mockito.nullable(String.class),
				Mockito.nullable(ItemSelectorCriterion.class),
				Mockito.nullable(ItemSelectorCriterion.class))
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
	public void setUp() {
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
			originalJSONObject.toString());

		_wikiLinksCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"filebrowserBrowseUrl", "oneTabItemSelectorPortletURL"
			).put(
				"removePlugins", "plugin1"
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testItemSelectorURLWhenValidWikiPageAndNode() throws Exception {
		populateInputEditorWikiPageAttributes(1, 1);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		_wikiLinksCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"filebrowserBrowseUrl", "twoTabsItemSelectorPortletURL"
			).put(
				"removePlugins", "plugin1"
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testItemSelectorURLWhenValidWikiPageAndNullNode()
		throws Exception {

		populateInputEditorWikiPageAttributes(1, 0);

		JSONObject originalJSONObject =
			getJSONObjectWithDefaultItemSelectorURL();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		_wikiLinksCKEditorEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, null, null);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"filebrowserBrowseUrl", "oneTabItemSelectorPortletURL"
			).put(
				"removePlugins", "plugin1"
			).toString(),
			jsonObject.toString(), true);
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

	private static WikiLinksCKEditorConfigContributor
		_wikiLinksCKEditorEditorConfigContributor;

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();

}