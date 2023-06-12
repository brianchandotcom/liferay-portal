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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.helper.DefaultInputFragmentEntryConfigurationProvider;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.LongTextInfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class ConfigurationDisplayContext {

	public ConfigurationDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_defaultInputFragmentEntryConfigurationProvider =
			(DefaultInputFragmentEntryConfigurationProvider)
				httpServletRequest.getAttribute(
					DefaultInputFragmentEntryConfigurationProvider.class.
						getName());

		_fragmentCollectionContributorRegistry =
			(FragmentCollectionContributorRegistry)
				httpServletRequest.getAttribute(
					FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
	}

	public Map<String, Object> getData() {
		return HashMapBuilder.<String, Object>put(
			"formTypes",
			() -> {
				Map<String, String> formTypes = new HashMap<>();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				JSONObject defaultInputFragmentEntryKeysJSONObject =
					_defaultInputFragmentEntryConfigurationProvider.
						getDefaultInputFragmentEntryKeysJSONObject(
							themeDisplay.getScopeGroupId());

				for (InfoFieldType infoFieldType : _INFO_FIELD_TYPES) {
					JSONObject jsonObject =
						defaultInputFragmentEntryKeysJSONObject.getJSONObject(
							infoFieldType.getName());

					FragmentEntry fragmentEntry =
						_fragmentCollectionContributorRegistry.getFragmentEntry(
							jsonObject.getString("key"));

					if (fragmentEntry != null) {
						formTypes.put(
							infoFieldType.getLabel(themeDisplay.getLocale()),
							LanguageUtil.get(
								themeDisplay.getLocale(),
								fragmentEntry.getName()));
					}
					else {
						formTypes.put(
							infoFieldType.getLabel(themeDisplay.getLocale()),
							null);
					}
				}

				return formTypes;
			}
		).build();
	}

	private static final InfoFieldType[] _INFO_FIELD_TYPES = {
		BooleanInfoFieldType.INSTANCE, DateInfoFieldType.INSTANCE,
		FileInfoFieldType.INSTANCE, HTMLInfoFieldType.INSTANCE,
		LongTextInfoFieldType.INSTANCE, MultiselectInfoFieldType.INSTANCE,
		NumberInfoFieldType.INSTANCE, RelationshipInfoFieldType.INSTANCE,
		SelectInfoFieldType.INSTANCE, TextInfoFieldType.INSTANCE
	};

	private final DefaultInputFragmentEntryConfigurationProvider
		_defaultInputFragmentEntryConfigurationProvider;
	private final FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;
	private final HttpServletRequest _httpServletRequest;

}