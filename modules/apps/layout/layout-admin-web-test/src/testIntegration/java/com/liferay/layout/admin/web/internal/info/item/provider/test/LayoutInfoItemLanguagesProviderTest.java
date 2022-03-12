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

package com.liferay.layout.admin.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class LayoutInfoItemLanguagesProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetAvailableLanguages() throws Exception {
		InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemLanguagesProvider.class, Layout.class.getName());

		Assert.assertArrayEquals(
			infoItemLanguagesProvider.getAvailableLanguageIds(_layout),
			_layout.getAvailableLanguageIds());
	}

	@Test
	public void testGetAvailableLanguagesContentLayout() throws Exception {
		_layout.setType(LayoutConstants.TYPE_CONTENT);

		_layout = _layoutLocalService.updateLayout(_layout);

		InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemLanguagesProvider.class, Layout.class.getName());

		Assert.assertArrayEquals(
			_getAvailableLocalesLayoutTranslatedLanguages(),
			infoItemLanguagesProvider.getAvailableLanguageIds(_layout));
	}

	private String[] _getAvailableLocalesLayoutTranslatedLanguages()
		throws Exception {

		if (!_layout.isTypeContent()) {
			return _layout.getAvailableLanguageIds();
		}

		Set<String> availableLocales = new HashSet<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		Set<Locale> siteAvailableLocales = _language.getAvailableLocales(
			_group.getGroupId());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			for (String translatableFragment : _TRANSLATABLE_FRAGMENTS) {
				availableLocales.addAll(
					_getTranslatableFragmentLocales(
						editableValuesJSONObject, translatableFragment,
						siteAvailableLocales));
			}
		}

		availableLocales.add(_layout.getDefaultLanguageId());

		return availableLocales.toArray(new String[0]);
	}

	private Set<String> _getTranslatableFragmentLocales(
		JSONObject jsonObject, String key, Set<Locale> siteAvailableLocales) {

		Set<String> availableLocales = new HashSet<>();

		JSONObject editableFragmentJSONObject = jsonObject.getJSONObject(key);

		if (!(editableFragmentJSONObject instanceof JSONObject) ||
			(editableFragmentJSONObject.length() <= 0)) {

			return availableLocales;
		}

		Iterator<String> editableFragmentIterator =
			editableFragmentJSONObject.keys();

		while (editableFragmentIterator.hasNext()) {
			Object editableValueObject = editableFragmentJSONObject.get(
				editableFragmentIterator.next());

			if (!(editableValueObject instanceof JSONObject)) {
				continue;
			}

			JSONObject editableValueJSONObject =
				(JSONObject)editableValueObject;

			if (editableValueJSONObject.length() <= 0) {
				continue;
			}

			for (Locale siteAvailableLocale : siteAvailableLocales) {
				Object valueObject = editableValueJSONObject.get(
					_language.getLanguageId(siteAvailableLocale));

				if (valueObject != null) {
					availableLocales.add(
						LocaleUtil.toLanguageId(siteAvailableLocale));
				}
			}
		}

		return availableLocales;
	}

	private static final String[] _TRANSLATABLE_FRAGMENTS = {
		"com.liferay.fragment.entry.processor.background.image." +
			"BackgroundImageFragmentEntryProcessor",
		"com.liferay.fragment.entry.processor.editable." +
			"EditableFragmentEntryProcessor"
	};

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private Language _language;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

}