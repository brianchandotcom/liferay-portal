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
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class FragmentCollectionFilterKeywordDisplayContext {

	public FragmentCollectionFilterKeywordDisplayContext(
		String configuration,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		_configuration = configuration;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererContext = fragmentRendererContext;
		_httpServletRequest = httpServletRequest;

		_fragmentEntryLink = _fragmentRendererContext.getFragmentEntryLink();
	}

	public String getLabel() {
		return GetterUtil.getString(_getFieldValue("label"));
	}

	public boolean isDisabled() {
		return !Objects.equals(
			_fragmentRendererContext.getMode(),
			FragmentEntryLinkConstants.VIEW);
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
	private final HttpServletRequest _httpServletRequest;

}