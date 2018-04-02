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

package com.liferay.portal.kernel.servlet.taglib.aui;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptData implements Mergeable<ScriptData>, Serializable {

	public void append(
		String portletId, String content, String modules,
		ModulesType modulesType) {

		PortletData portletData = _getPortletData(portletId);

		portletData.append(content, modules, modulesType);
	}

	public void append(
		String portletId, StringBundler contentSB, String modules,
		ModulesType modulesType) {

		PortletData portletData = _getPortletData(portletId);

		portletData.append(contentSB, modules, modulesType);
	}

	public void mark() {
		for (PortletData portletData : _portletDataMap.values()) {
			portletData.mark();
		}
	}

	@Override
	public ScriptData merge(ScriptData scriptData) {
		if ((scriptData != null) && (scriptData != this)) {
			_portletDataMap.putAll(scriptData._portletDataMap);
		}

		return this;
	}

	public void reset() {
		for (PortletData portletData : _portletDataMap.values()) {
			portletData.reset();
		}
	}

	@Override
	public ScriptData split() {
		return new ScriptData();
	}

	public void writeTo(Writer writer) throws IOException {
		writer.write("<script type=\"text/javascript\">\n// <![CDATA[\n");

		for (PortletData portletData : _portletDataMap.values()) {
			portletData._rawSB.writeTo(writer);
		}

		_writeEs6ModulesTo(writer);

		_writeAuiModulesTo(writer);
	}

	public static enum ModulesType {

		AUI, ES6

	}

	private String _generateModuleName(String name) {
		String[] nameAlias = _splitNameAlias(name);

		return nameAlias[0];
	}

	private String _generateVariable(
		String name, Set<String> names, boolean useAlias) {

		String[] nameAlias = _splitNameAlias(name);

		if (useAlias && !Validator.isBlank(nameAlias[1])) {
			return nameAlias[1];
		}

		name = nameAlias[0];

		StringBuilder sb = new StringBuilder(name.length());

		char c = name.charAt(0);

		boolean modified = true;

		if (((CharPool.LOWER_CASE_A <= c) && (c <= CharPool.LOWER_CASE_Z)) ||
			(c == CharPool.UNDERLINE)) {

			sb.append(c);

			modified = false;
		}
		else if ((CharPool.UPPER_CASE_A <= c) && (c <= CharPool.UPPER_CASE_Z)) {
			sb.append((char)(c + 32));
		}
		else {
			sb.append(CharPool.UNDERLINE);
		}

		boolean startNewWord = false;

		for (int i = 1; i < name.length(); i++) {
			c = name.charAt(i);

			if ((CharPool.LOWER_CASE_A <= c) && (c <= CharPool.LOWER_CASE_Z)) {
				if (startNewWord) {
					sb.append((char)(c - 32));

					startNewWord = false;
				}
				else {
					sb.append(c);
				}
			}
			else if (((CharPool.UPPER_CASE_A <= c) &&
					  (c <= CharPool.UPPER_CASE_Z)) ||
					 ((CharPool.NUMBER_0 <= c) && (c <= CharPool.NUMBER_9)) ||
					 (c == CharPool.UNDERLINE)) {

				sb.append(c);

				startNewWord = false;
			}
			else {
				modified = true;

				startNewWord = true;
			}
		}

		String safeName = name;

		if (modified) {
			safeName = sb.toString();

			name = safeName;
		}

		int i = 1;

		while (!names.add(name)) {
			name = safeName.concat(String.valueOf(i++));
		}

		return name;
	}

	private PortletData _getPortletData(String portletId) {
		if (Validator.isNull(portletId)) {
			portletId = StringPool.BLANK;
		}

		PortletData portletData = _portletDataMap.get(portletId);

		if (portletData == null) {
			portletData = new PortletData();

			PortletData oldPortletData = _portletDataMap.putIfAbsent(
				portletId, portletData);

			if (oldPortletData != null) {
				portletData = oldPortletData;
			}
		}

		return portletData;
	}

	private String[] _splitNameAlias(String name) {
		String[] parts = name.split("\\s+");

		if ((parts.length == 3) &&
			StringUtil.equalsIgnoreCase(parts[1], "as")) {

			return new String[] {parts[0], parts[2]};
		}

		return new String[] {name, StringPool.BLANK};
	}

	private void _writeAuiModulesTo(Writer writer) throws IOException {
		StringBundler auiModulesSB = new StringBundler(_portletDataMap.size());
		Set<String> auiModulesSet = new HashSet<>();

		for (PortletData portletData : _portletDataMap.values()) {
			if (!portletData._auiModulesSet.isEmpty()) {
				auiModulesSB.append(portletData._auiCallbackSB);
				auiModulesSet.addAll(portletData._auiModulesSet);
			}
		}

		if (!auiModulesSet.isEmpty()) {
			writer.write("AUI().use(");

			for (String use : auiModulesSet) {
				writer.write(StringPool.APOSTROPHE);
				writer.write(use);
				writer.write(StringPool.APOSTROPHE);
				writer.write(StringPool.COMMA_AND_SPACE);
			}

			writer.write("function(A) {");

			auiModulesSB.writeTo(writer);

			writer.write("});");
		}

		writer.write("\n// ]]>\n</script>");
	}

	private void _writeEs6ModulesTo(Writer writer) throws IOException {
		StringBundler es6CallbacksSB = new StringBundler(
			_portletDataMap.size());
		List<String> es6Modules = new ArrayList<>();
		List<String> es6Variables = new ArrayList<>();
		Set<String> variableNames = new HashSet<>();

		for (PortletData portletData : _portletDataMap.values()) {
			if (!portletData._es6ModulesSet.isEmpty()) {
				es6CallbacksSB.append("(function(){\n");

				for (String es6Module : portletData._es6ModulesSet) {
					es6Modules.add(_generateModuleName(es6Module));

					String rawVariable = _generateVariable(
						es6Module, variableNames, false);

					es6Variables.add(rawVariable);

					es6CallbacksSB.append("var ");

					String aliasedVariable = _generateVariable(
						es6Module, variableNames, true);

					es6CallbacksSB.append(aliasedVariable);

					es6CallbacksSB.append(" = ");
					es6CallbacksSB.append(rawVariable);
					es6CallbacksSB.append(";\n");
				}

				es6CallbacksSB.append(portletData._es6CallbackSB);

				es6CallbacksSB.append("})();\n");
			}
		}

		if (!es6Modules.isEmpty()) {
			writer.write("Liferay.Loader.require(");

			for (String es6Module : es6Modules) {
				writer.write(StringPool.APOSTROPHE);
				writer.write(es6Module);
				writer.write(StringPool.APOSTROPHE);
				writer.write(StringPool.COMMA_AND_SPACE);
			}

			writer.write("function(");

			String delimiter = StringPool.BLANK;

			for (String es6Variable : es6Variables) {
				writer.write(delimiter);
				writer.write(es6Variable);

				delimiter = StringPool.COMMA_AND_SPACE;
			}

			writer.write(") {\n");

			es6CallbacksSB.writeTo(writer);

			writer.write("},\nfunction(error) {\nconsole.error(error);\n});");
		}
	}

	private static final long serialVersionUID = 1L;

	private final ConcurrentMap<String, PortletData> _portletDataMap =
		new ConcurrentHashMap<>();

	private static class PortletData implements Serializable {

		public void append(
			String content, String modules, ModulesType modulesType) {

			if (Validator.isNull(modules)) {
				_rawSB.append(content);
			}
			else {
				String[] modulesArray = StringUtil.split(modules);

				if (modulesType == ModulesType.AUI) {
					_auiCallbackSB.append("(function() {");
					_auiCallbackSB.append(content);
					_auiCallbackSB.append("})();");

					for (String module : modulesArray) {
						_auiModulesSet.add(StringUtil.trim(module));
					}
				}
				else {
					_es6CallbackSB.append("(function() {");
					_es6CallbackSB.append(content);
					_es6CallbackSB.append("})();");

					for (String module : modulesArray) {
						_es6ModulesSet.add(StringUtil.trim(module));
					}
				}
			}
		}

		public void append(
			StringBundler contentSB, String modules, ModulesType modulesType) {

			if (Validator.isNull(modules)) {
				_rawSB.append(contentSB);
			}
			else {
				String[] modulesArray = StringUtil.split(modules);

				if (modulesType == ModulesType.AUI) {
					_auiCallbackSB.append("(function() {");
					_auiCallbackSB.append(contentSB);
					_auiCallbackSB.append("})();");

					for (String module : modulesArray) {
						_auiModulesSet.add(StringUtil.trim(module));
					}
				}
				else {
					_es6CallbackSB.append("(function() {");
					_es6CallbackSB.append(contentSB);
					_es6CallbackSB.append("})();");

					for (String module : modulesArray) {
						_es6ModulesSet.add(StringUtil.trim(module));
					}
				}
			}
		}

		public void mark() {
			_auiCallbackSBIndex = _auiCallbackSB.index();
			_es6CallbackSBIndex = _es6CallbackSB.index();
			_rawSBIndex = _rawSB.index();
		}

		public void reset() {
			if (_auiCallbackSBIndex >= 0) {
				_auiCallbackSB.setIndex(_auiCallbackSBIndex);
			}

			if (_es6CallbackSBIndex >= 0) {
				_es6CallbackSB.setIndex(_es6CallbackSBIndex);
			}

			if (_rawSBIndex >= 0) {
				_rawSB.setIndex(_rawSBIndex);
			}
		}

		private static final long serialVersionUID = 1L;

		private final StringBundler _auiCallbackSB = new StringBundler();
		private int _auiCallbackSBIndex = -1;
		private final Set<String> _auiModulesSet = new HashSet<>();
		private final StringBundler _es6CallbackSB = new StringBundler();
		private int _es6CallbackSBIndex = -1;
		private final Set<String> _es6ModulesSet = new HashSet<>();
		private final StringBundler _rawSB = new StringBundler();
		private int _rawSBIndex = -1;

	}

}