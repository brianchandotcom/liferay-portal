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

package com.liferay.frontend.icons.web.internal.model;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsResource {

	public FrontendIconsResource(String content, String name, String viewBox) {
		_content = content;
		_name = name;
		_viewBox = viewBox;
	}

	public String asSymbol() {
		return StringUtil.replace(
			_SYMBOL_TMPL,
			new String[] {"[$CONTENT$]", "[$NAME$]", "[$VIEW_BOX$]"},
			new String[] {_content, _name, _viewBox});
	}

	public String getName() {
		return _name;
	}

	private static final String _SYMBOL_TMPL = StringUtil.read(
		FrontendIconsResource.class,
		"/com/liferay/frontend/icon/admin/web/internal/model/dependencies" +
			"/symbol.svg");

	private final String _content;
	private final String _name;
	private final String _viewBox;

}