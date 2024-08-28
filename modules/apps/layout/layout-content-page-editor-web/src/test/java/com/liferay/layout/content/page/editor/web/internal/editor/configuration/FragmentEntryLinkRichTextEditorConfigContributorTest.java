/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Bárbara Cabrera
 */
public class FragmentEntryLinkRichTextEditorConfigContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");
		_language = Mockito.mock(Language.class);
	}

	@Test
	public void testAdaptiveMediaIsAddedToExtraPlugins() {
		PortletURL itemSelectorPortletURL = Mockito.mock(PortletURL.class);

		Mockito.when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		PortletURL imageSelectorPortletURL = Mockito.mock(PortletURL.class);

		Mockito.when(
			imageSelectorPortletURL.toString()
		).thenReturn(
			"imageSelectorPortletURL"
		);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			imageSelectorPortletURL
		);

		Mockito.when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"_EDITOR_NAME_selectItem"
		);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Mockito.when(
			_language.get(Mockito.any(Locale.class), Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_language.format(
				Mockito.any(Locale.class), Mockito.anyString(),
				Mockito.anyBoolean())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		FragmentEntryLinkRichTextEditorConfigContributor
			fragmentEntryLinkRichTextEditorConfigContributor =
				new FragmentEntryLinkRichTextEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			fragmentEntryLinkRichTextEditorConfigContributor, "_itemSelector",
			_itemSelector);
		ReflectionTestUtil.setFieldValue(
			fragmentEntryLinkRichTextEditorConfigContributor, "_language",
			_language);

		fragmentEntryLinkRichTextEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"autolink,ae_dragresize,ae_addimages,ae_imagealignment," +
				"ae_placeholder,ae_selectionregion,ae_tableresize," +
					"ae_tabletools,ae_uicore,itemselector,media,adaptivemedia",
			jsonObject.getString("extraPlugins"));
	}

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();
	private final ItemSelector _itemSelector = Mockito.mock(ItemSelector.class);
	private Language _language;
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory = Mockito.mock(
			RequestBackedPortletURLFactory.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}