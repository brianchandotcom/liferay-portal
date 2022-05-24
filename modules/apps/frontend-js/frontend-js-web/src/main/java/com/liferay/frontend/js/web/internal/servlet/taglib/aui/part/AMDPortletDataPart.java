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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataPart;
import com.liferay.portal.kernel.servlet.taglib.aui.VariableUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class AMDPortletDataPart implements PortletDataPart {

	public AMDPortletDataPart(String content, String amdRequire) {
		_content = StringUtil.trim(content);

		Map<String, String> moduleAliasMap = VariableUtil.parseRequire(
			amdRequire);

		for (Map.Entry<String, String> entry : moduleAliasMap.entrySet()) {
			_amdRequires.add(new AMDRequire(entry.getKey(), entry.getValue()));
		}
	}

	@Override
	public Collection<AMDRequire> getAMDRequires() {
		return _amdRequires;
	}

	@Override
	public List<String> getAUIModules() {
		return null;
	}

	@Override
	public String getContent(
		Map<AMDRequire, AMDRequire> amdRequireMap,
		Map<ESMImport, ESMImport> esmImportMap) {

		if (Validator.isNull(_content) && _amdRequires.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("(function() {\n");

		for (AMDRequire amdRequire : _amdRequires) {
			String alias = amdRequire.getAlias();

			AMDRequire mappedAMDRequire = amdRequireMap.get(amdRequire);

			String variable = mappedAMDRequire.getAlias();

			if (!alias.equals(variable)) {
				sb.append("var ");
				sb.append(alias);
				sb.append(" = ");
				sb.append(variable);
				sb.append(";\n");
			}
		}

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

	private final List<AMDRequire> _amdRequires = new ArrayList<>();
	private final String _content;

}