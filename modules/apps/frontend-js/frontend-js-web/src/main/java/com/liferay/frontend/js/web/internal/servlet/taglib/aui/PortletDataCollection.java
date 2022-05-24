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

package com.liferay.frontend.js.web.internal.servlet.taglib.aui;

import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.ESMPortletDataPart;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletData;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataPart;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Iván Zaera Avellón
 */
public class PortletDataCollection {

	public PortletDataCollection(Collection<PortletData> portletDatas) {
		_portletDatas = portletDatas;

		Map<String, Integer> usedVariables = new HashMap<>();

		_initAMDRequireMap(usedVariables);
		_initESMImportMap(usedVariables);

		_initAUIModules();

		_initContent();
		_initRawContent();
		_initUseESM();
	}

	public Collection<AMDRequire> getAMDRequires() {
		return _amdRequireMap.values();
	}

	public Collection<String> getAUIModules() {
		return _auiModules;
	}

	public String getContent() {
		return _content;
	}

	public Collection<ESMImport> getESMImports() {
		return _esmImportMap.values();
	}

	public String getRawContent() {
		return _rawContent;
	}

	public boolean isUseESM() {
		return _useESM;
	}

	private void _initAMDRequireMap(Map<String, Integer> usedVariables) {
		for (PortletData portletData : _portletDatas) {
			for (PortletDataPart portletDataPart :
					portletData.getPortletDataParts()) {

				Collection<AMDRequire> amdRequires =
					portletDataPart.getAMDRequires();

				if ((amdRequires == null) || amdRequires.isEmpty()) {
					continue;
				}

				for (AMDRequire amdRequire : amdRequires) {
					if (_amdRequireMap.containsKey(amdRequire)) {
						continue;
					}

					String variable = amdRequire.getAlias();

					if (usedVariables.containsKey(variable)) {
						int index = usedVariables.get(variable);

						variable += index;

						usedVariables.put(variable, index + 1);
					}
					else {
						usedVariables.put(variable, 0);
					}

					_amdRequireMap.put(
						amdRequire,
						new AMDRequire(amdRequire.getModule(), variable));
				}
			}
		}
	}

	private void _initAUIModules() {
		for (PortletData portletData : _portletDatas) {
			for (PortletDataPart portletDataPart :
					portletData.getPortletDataParts()) {

				List<String> auiModules = portletDataPart.getAUIModules();

				if (auiModules == null) {
					continue;
				}

				_auiModules.addAll(auiModules);
			}
		}
	}

	private void _initContent() {
		StringBundler sb = new StringBundler();

		for (PortletData portletData : _portletDatas) {
			for (PortletDataPart portletDataPart :
					portletData.getPortletDataParts()) {

				String content = portletDataPart.getContent(
					_amdRequireMap, _esmImportMap);

				if (Validator.isNull(content)) {
					continue;
				}

				sb.append(content);

				if (!content.endsWith(StringPool.NEW_LINE)) {
					sb.append(StringPool.NEW_LINE);
				}
			}
		}

		_content = sb.toString();
	}

	private void _initESMImportMap(Map<String, Integer> usedVariables) {
		for (PortletData portletData : _portletDatas) {
			for (PortletDataPart portletDataPart :
					portletData.getPortletDataParts()) {

				Collection<ESMImport> esmImports =
					portletDataPart.getESMImports();

				if ((esmImports == null) || esmImports.isEmpty()) {
					continue;
				}

				for (ESMImport esmImport : esmImports) {
					if (_esmImportMap.containsKey(esmImport)) {
						continue;
					}

					String variable = esmImport.getAlias();

					if (usedVariables.containsKey(variable)) {
						int index = usedVariables.get(variable);

						usedVariables.put(variable, index + 1);

						variable += index;
					}
					else {
						usedVariables.put(variable, 0);
					}

					_esmImportMap.put(
						esmImport,
						new ESMImport(
							esmImport.getSymbol(), variable,
							esmImport.getModule()));
				}
			}
		}
	}

	private void _initRawContent() {
		StringBundler sb = new StringBundler();

		for (PortletData portletData : _portletDatas) {
			for (PortletDataPart portletDataPart :
					portletData.getPortletDataParts()) {

				String rawContent = portletDataPart.getRawContent();

				if (Validator.isNull(rawContent)) {
					continue;
				}

				sb.append(rawContent);

				if (!rawContent.endsWith(StringPool.NEW_LINE)) {
					sb.append(StringPool.NEW_LINE);
				}
			}
		}

		_rawContent = sb.toString();
	}

	private void _initUseESM() {
		_useESM = false;

		for (PortletData portletData : _portletDatas) {
			if (portletData.hasPortletDataParts(ESMPortletDataPart.class)) {
				_useESM = true;

				break;
			}
		}
	}

	private final Map<AMDRequire, AMDRequire> _amdRequireMap = new HashMap<>();
	private final Set<String> _auiModules = new HashSet<>();
	private String _content;
	private final Map<ESMImport, ESMImport> _esmImportMap = new HashMap<>();
	private final Collection<PortletData> _portletDatas;
	private String _rawContent;
	private boolean _useESM;

}