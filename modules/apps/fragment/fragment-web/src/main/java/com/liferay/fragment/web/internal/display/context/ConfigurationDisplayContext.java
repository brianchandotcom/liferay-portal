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
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class ConfigurationDisplayContext {

	public ConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

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

		_liferayPortletResponse = liferayPortletResponse;
	}

	public Map<String, Object> getData() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"formTypes",
			() -> {
				List<Map<String, String>> formTypes = new ArrayList<>();

				JSONObject defaultInputFragmentEntryKeysJSONObject =
					_defaultInputFragmentEntryConfigurationProvider.
						getDefaultInputFragmentEntryKeysJSONObject(
							themeDisplay.getScopeGroupId());

				Map<String, FragmentEntry> fragmentEntries =
					_fragmentCollectionContributorRegistry.getFragmentEntries(
						themeDisplay.getLocale());

				for (InfoFieldType infoFieldType : _INFO_FIELD_TYPES) {
					JSONObject jsonObject =
						defaultInputFragmentEntryKeysJSONObject.getJSONObject(
							infoFieldType.getName());

					formTypes.add(
						HashMapBuilder.put(
							"fragmentName",
							() -> {
								FragmentEntry fragmentEntry =
									fragmentEntries.get(
										jsonObject.getString("key"));

								if (fragmentEntry != null) {
									return fragmentEntry.getName();
								}

								Group group = GroupLocalServiceUtil.fetchGroup(
									themeDisplay.getCompanyId(),
									jsonObject.getString("groupKey"));

								if (group == null) {
									return null;
								}

								fragmentEntry =
									FragmentEntryLocalServiceUtil.
										fetchFragmentEntry(
											group.getGroupId(),
											jsonObject.getString("key"));

								if (fragmentEntry != null) {
									return fragmentEntry.getName();
								}

								return null;
							}
						).put(
							"label",
							infoFieldType.getLabel(themeDisplay.getLocale())
						).put(
							"name", infoFieldType.getName()
						).build());
				}

				return formTypes;
			}
		).put(
			"selectFragmentURL",
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/fragment/select_default_input_fragment"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"updateInputFragmentsURL",
			PortletURLBuilder.createActionURL(
				_liferayPortletResponse
			).setActionName(
				"/fragment/update_default_input_fragments"
			).setRedirect(
				themeDisplay.getURLCurrent()
			).buildString()
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
	private final LiferayPortletResponse _liferayPortletResponse;

}