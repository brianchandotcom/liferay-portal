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

package com.liferay.frontend.js.web.internal.servlet.taglib.aui.part;

import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataPart;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class SimplePortletDataPart implements PortletDataPart {

	public SimplePortletDataPart(String content) {
		_content = StringUtil.trim(content);
	}

	@Override
	public Collection<AMDRequire> getAMDRequires() {
		return null;
	}

	@Override
	public List<String> getAUIModules() {
		return null;
	}

	@Override
	public String getContent(
		Map<AMDRequire, AMDRequire> amdRequireMap,
		Map<ESMImport, ESMImport> esmImportMap) {

		return null;
	}

	@Override
	public Collection<ESMImport> getESMImports() {
		return null;
	}

	@Override
	public String getRawContent() {
		return _content;
	}

	private final String _content;

}