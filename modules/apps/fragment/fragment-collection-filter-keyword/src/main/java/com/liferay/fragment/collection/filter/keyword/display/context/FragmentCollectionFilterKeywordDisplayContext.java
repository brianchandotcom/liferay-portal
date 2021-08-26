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

package com.liferay.fragment.collection.filter.keyword.display.context;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class FragmentCollectionFilterKeywordDisplayContext {

	public FragmentCollectionFilterKeywordDisplayContext(
		String configuration,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererContext fragmentRendererContext) {

		_configuration = configuration;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;

		_fragmentRendererContext = fragmentRendererContext;

		_fragmentEntryLink = _fragmentRendererContext.getFragmentEntryLink();
	}

	public String getFragmentEntryLinkNamespace() {
		return _fragmentEntryLink.getFragmentEntryLinkId() +
			StringPool.UNDERLINE + _fragmentEntryLink.getNamespace();
	}

	public String getLabel() {
		return GetterUtil.getString(_getFieldValue("label"));
	}

	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"fragmentEntryLinkId",
			String.valueOf(_fragmentEntryLink.getFragmentEntryLinkId())
		).put(
			"fragmentEntryLinkNamespace", getFragmentEntryLinkNamespace()
		).put(
			"isDisabled", isDisabled()
		).build();
	}

	public boolean isDisabled() {
		return Objects.equals(
			_fragmentRendererContext.getMode(),
			FragmentEntryLinkConstants.EDIT);
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

}