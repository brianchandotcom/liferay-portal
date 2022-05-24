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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataPart;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class AUIPortletDataPart implements PortletDataPart {

	public AUIPortletDataPart(String content, String modules) {
		_content = StringUtil.trim(content);

		for (String module : StringUtil.split(modules)) {
			_modules.add(StringUtil.trim(module));
		}
	}

	@Override
	public Collection<AMDRequire> getAMDRequires() {
		return null;
	}

	@Override
	public List<String> getAUIModules() {
		return _modules;
	}

	@Override
	public String getContent(
		Map<AMDRequire, AMDRequire> amdRequireMap,
		Map<ESMImport, ESMImport> esmImportMap) {

		if (Validator.isNull(_content)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(3);

		sb.append("(function() {\n");

		sb.append(_content);

		sb.append("\n})();");

		return sb.toString();
	}

	@Override
	public Collection<ESMImport> getESMImports() {
		return null;
	}

	@Override
	public String getRawContent() {
		return null;
	}

	private final String _content;
	private final List<String> _modules = new ArrayList<>();

}