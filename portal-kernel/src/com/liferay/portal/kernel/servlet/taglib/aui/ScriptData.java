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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		Collection<PortletData> portletDataCollection =
			_portletDataMap.values();

		_writeEs6ModulesTo(writer, portletDataCollection);

		_writeAuiModulesTo(writer, portletDataCollection);

		writer.write("\n// ]]>\n</script>");
	}

	public void writeTo(Writer writer, String portletId) throws IOException {
		PortletData portletData = _portletDataMap.remove(portletId);

		if (portletData == null) {
			return;
		}

		writer.write("<script type=\"text/javascript\">\n// <![CDATA[\n");

		portletData._rawSB.writeTo(writer);

		Collection<PortletData> portletDataCollection = Collections.singleton(
			portletData);

		if (!portletData._es6TagInvocationDatas.isEmpty()) {
			_writeEs6ModulesTo(writer, portletDataCollection);
		}

		if (!portletData._auiModulesSet.isEmpty()) {
			_writeAuiModulesTo(writer, portletDataCollection);
		}

		writer.write("\n// ]]>\n</script>");
	}

	public static enum ModulesType {

		AUI, ES6

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

	private void _writeAuiModulesTo(
			Writer writer, Collection<PortletData> portletDataCollection)
		throws IOException {

		StringBundler auiModulesSB = new StringBundler(
			portletDataCollection.size());
		Set<String> auiModulesSet = new HashSet<>();

		for (PortletData portletData : portletDataCollection) {
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
	}

	private void _writeEs6ModulesTo(
			Writer writer, Collection<PortletData> portletDataCollection)
		throws IOException {

		StringBundler es6CallbacksSB = new StringBundler(
			portletDataCollection.size());
		List<String> es6Modules = new ArrayList<>();
		List<String> es6Variables = new ArrayList<>();
		Set<String> usedVariables = new HashSet<>();

		for (PortletData portletData : portletDataCollection) {
			if (portletData._es6TagInvocationDatas.isEmpty()) {
				continue;
			}

			for (TagInvocationData tagInvocationData :
					portletData._es6TagInvocationDatas) {

				List<String> modules = tagInvocationData.getModules();

				Stream<String> stream = modules.stream();

				List<String> variables = stream.map(
					module -> VariableUtil.generateVariable(
						module, usedVariables)
				).collect(
					Collectors.toList()
				);

				es6Modules.addAll(modules);
				es6Variables.addAll(variables);

				es6CallbacksSB.append(tagInvocationData.getContent(variables));
				es6CallbacksSB.append(StringPool.NEW_LINE);
			}
		}

		if (es6Modules.isEmpty()) {
			return;
		}

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
		writer.write("try {\n");

		es6CallbacksSB.writeTo(writer);

		writer.write("} catch (err) {\n");
		writer.write("\tconsole.error(err);\n");
		writer.write("}\n");

		writer.write("});");
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
					_es6TagInvocationDatas.add(
						new TagInvocationData(content, modules));
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
					_es6TagInvocationDatas.add(
						new TagInvocationData(contentSB, modules));
				}
			}
		}

		public void mark() {
			_auiCallbackSBIndex = _auiCallbackSB.index();
			_es6TagInvocationDatasIndex = _es6TagInvocationDatas.size();
			_rawSBIndex = _rawSB.index();
		}

		public void reset() {
			if (_auiCallbackSBIndex >= 0) {
				_auiCallbackSB.setIndex(_auiCallbackSBIndex);
			}

			if (_es6TagInvocationDatasIndex >= 0) {
				_es6TagInvocationDatas = _es6TagInvocationDatas.subList(
					0, _es6TagInvocationDatasIndex);
			}

			if (_rawSBIndex >= 0) {
				_rawSB.setIndex(_rawSBIndex);
			}
		}

		private static final long serialVersionUID = 1L;

		private final StringBundler _auiCallbackSB = new StringBundler();
		private int _auiCallbackSBIndex = -1;
		private final Set<String> _auiModulesSet = new HashSet<>();
		private List<TagInvocationData> _es6TagInvocationDatas =
			new ArrayList<>();
		private int _es6TagInvocationDatasIndex = -1;
		private final StringBundler _rawSB = new StringBundler();
		private int _rawSBIndex = -1;

	}

}