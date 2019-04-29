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

package com.liferay.adaptive.media.journal.editor.configuration.internal;

import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.audio.criterion.AudioItemSelectorCriterion;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import org.junit.Assert;
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
public class AMJournalEditorConfigContributorTest extends PowerMockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");
	}

	@Test
	public void testAdaptiveMediaFileEntryAttributeNameIsAdded()
		throws Exception {

		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"selectedEventName"
		);

		when(
			_itemSelector.getItemSelectorCriteria(
				"journalItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getBlogsItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"journalItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amBlogsEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amBlogsEditorConfigContributor, "_itemSelector", _itemSelector);

		amBlogsEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"data-fileentryid",
			jsonObject.get("adaptiveMediaFileEntryAttributeName"));
	}

	@Test
	public void testAdaptiveMediaIsAddedToExtraPlugins() throws Exception {
		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"selectedEventName"
		);

		when(
			_itemSelector.getItemSelectorCriteria(
				"journalItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getBlogsItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"journalItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amJournalEditorConfigContributor, "_itemSelector", _itemSelector);

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"adaptivemedia", jsonObject.getString("extraPlugins"));
	}

	@Test
	public void testAdaptiveMediaIsExtraPlugins() throws Exception {
		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"selectedEventName"
		);

		when(
			_itemSelector.getItemSelectorCriteria(
				"journalItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getBlogsItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"extraPlugins", "ae_placeholder,ae_selectionregion,ae_uicore"
		).put(
			"filebrowserImageBrowseLinkUrl",
			"journalItemSelectorCriterionFileEntryItemSelectorReturnType"
		);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amJournalEditorConfigContributor, "_itemSelector", _itemSelector);

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"ae_placeholder,ae_selectionregion,ae_uicore,adaptivemedia",
			jsonObject.getString("extraPlugins"));
	}

	@Test
	public void testAddAMImageFileEntryItemSelectorReturnType() {
		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		JournalItemSelectorCriterion journalItemSelectorCriterion =
			new JournalItemSelectorCriterion();

		journalItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		amJournalEditorConfigContributor.
			addAMImageFileEntryItemSelectorReturnType(
				journalItemSelectorCriterion);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			journalItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(
			desiredItemSelectorReturnTypes.toString(), 2,
			desiredItemSelectorReturnTypes.size());

		_assertContains(
			desiredItemSelectorReturnTypes,
			itemSelectorReturnType -> itemSelectorReturnType instanceof
				AMImageFileEntryItemSelectorReturnType);

		_assertContains(
			desiredItemSelectorReturnTypes,
			itemSelectorReturnType ->
				itemSelectorReturnType instanceof
					FileEntryItemSelectorReturnType);
	}

	@Test
	public void testImgIsAddedToAllowedContent() throws Exception {
		JSONObject originalJSONObject = JSONUtil.put(
			"allowedContent", "a[*](*); div(*);");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONUtil.put(
			"allowedContent", "a[*](*); div(*); img[*](*){*};");

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testImgIsAllowedContent() throws Exception {
		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testImgIsNotAddedToAllowedContentIfEverythingWasAlreadyAllowed()
		throws Exception {

		JSONObject originalJSONObject = JSONUtil.put("allowedContent", true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = JSONUtil.put("allowedContent", true);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenNoFileBrowserImageBrowseLinkUrl()
		throws Exception {

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl", StringPool.BLANK);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amJournalEditorConfigContributor, "_itemSelector", _itemSelector);

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector, Mockito.never()
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWithAudioItemSelectorCriterion()
		throws Exception {

		when(
			_itemSelector.getItemSelectorCriteria(
				"audioItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getAudioItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"audioItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amJournalEditorConfigContributor, "_itemSelector", _itemSelector);

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector, Mockito.never()
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWithBlogsItemSelectorCriterion()
		throws Exception {

		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"selectedEventName"
		);

		when(
			_itemSelector.getItemSelectorCriteria(
				"journalItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getBlogsItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"journalItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amJournalEditorConfigContributor, "_itemSelector", _itemSelector);

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);
	}

	@Test
	public void testItemSelectorURLWithFileItemSelectorCriterion()
		throws Exception {

		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"selectedEventName"
		);

		when(
			_itemSelector.getItemSelectorCriteria(
				"fileItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getFileItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"fileItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AMJournalEditorConfigContributor amJournalEditorConfigContributor =
			new AMJournalEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			amJournalEditorConfigContributor, "_itemSelector", _itemSelector);

		amJournalEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);
	}

	private <T> void _assertContains(
		Collection<T> collection, Predicate<T> predicate) {

		Stream<T> stream = collection.stream();

		Assert.assertTrue(stream.anyMatch(predicate));
	}

	private List<ItemSelectorCriterion>
		_getAudioItemSelectorCriterionFileEntryItemSelectorReturnType() {

		AudioItemSelectorCriterion audioItemSelectorCriterion =
			new AudioItemSelectorCriterion();

		audioItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return Collections.singletonList(audioItemSelectorCriterion);
	}

	private List<ItemSelectorCriterion>
		_getBlogsItemSelectorCriterionFileEntryItemSelectorReturnType() {

		JournalItemSelectorCriterion journalItemSelectorCriterion =
			new JournalItemSelectorCriterion();

		journalItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return Collections.singletonList(journalItemSelectorCriterion);
	}

	private List<ItemSelectorCriterion>
		_getFileItemSelectorCriterionFileEntryItemSelectorReturnType() {

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return Collections.singletonList(fileItemSelectorCriterion);
	}

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();

	@Mock
	private ItemSelector _itemSelector;

	@Mock
	private RequestBackedPortletURLFactory _requestBackedPortletURLFactory;

	@Mock
	private ThemeDisplay _themeDisplay;

}