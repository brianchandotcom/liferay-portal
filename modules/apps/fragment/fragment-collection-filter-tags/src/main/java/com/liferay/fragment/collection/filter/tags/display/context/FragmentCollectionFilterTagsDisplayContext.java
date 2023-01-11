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

package com.liferay.fragment.collection.filter.tags.display.context;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Pablo Molina
 */
public class FragmentCollectionFilterTagsDisplayContext {

	public FragmentCollectionFilterTagsDisplayContext(
		String configuration,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererContext fragmentRendererContext) {

		_configuration = configuration;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererContext = fragmentRendererContext;

		_fragmentEntryLink = fragmentRendererContext.getFragmentEntryLink();
	}

	public String getHelpText() {
		String helpText = GetterUtil.getString(_getFieldValue("helpText"));

		if (Validator.isNotNull(helpText)) {
			return helpText;
		}

		JSONObject defaultValuesJSONObject =
			_fragmentEntryConfigurationParser.
				getConfigurationDefaultValuesJSONObject(_configuration);

		return defaultValuesJSONObject.getString("helpText", StringPool.BLANK);
	}

	public String getLabel() {
		String label = GetterUtil.getString(_getFieldValue("label"));

		if (Validator.isNotNull(label)) {
			return label;
		}

		return StringPool.BLANK;
	}

	public Map<String, Object> getProps() {
		if (_props != null) {
			return _props;
		}

		_props = HashMapBuilder.<String, Object>put(
			"fragmentEntryLinkId",
			String.valueOf(_fragmentEntryLink.getFragmentEntryLinkId())
		).put(
			"helpText",
			() -> {
				if (isShowHelpText()) {
					return getHelpText();
				}

				return StringPool.BLANK;
			}
		).put(
			"label", getLabel()
		).put(
			"showLabel", isShowLabel()
		).build();

		return _props;
	}

	public boolean isShowHelpText() {
		return GetterUtil.getBoolean(_getFieldValue("showHelpText"));
	}

	public boolean isShowLabel() {
		return GetterUtil.getBoolean(_getFieldValue("showLabel"));
	}

	private Object _getFieldValue(String fieldName) {
		return _fragmentEntryConfigurationParser.getFieldValue(
			_configuration, _fragmentEntryLink.getEditableValues(),
			_fragmentRendererContext.getLocale(), fieldName);
	}

	private final String _configuration;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final FragmentEntryLink _fragmentEntryLink;
	private final FragmentRendererContext _fragmentRendererContext;
	private Map<String, Object> _props;

}