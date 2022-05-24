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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptData implements Mergeable<ScriptData>, Serializable {

	public void append(String portletId, PortletDataPart portletDataPart) {
		PortletData portletData = _getPortletData(portletId);

		portletData.add(portletDataPart);
	}

	/**
	 * @review
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 * 				ScriptData#append(String, PortletDataPart)}
	 */
	@Deprecated
	public void append(
		String portletId, String content, String modules,
		ModulesType modulesType) {

		PortletData portletData = _getPortletData(portletId);

		if (Validator.isNull(modules)) {
			portletData.add(
				PortletDataPartFactoryUtil.createSimplePortletDataPart(
					content));
		}
		else {
			if (modulesType == ScriptData.ModulesType.AUI) {
				portletData.add(
					PortletDataPartFactoryUtil.createAUIPortletDataPart(
						content, modules));
			}
			else {
				portletData.add(
					PortletDataPartFactoryUtil.createAMDPortletDataPart(
						content, modules));
			}
		}
	}

	/**
	 * @review
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 * 				ScriptData#append(String, PortletDataPart)}
	 */
	@Deprecated
	public void append(
		String portletId, StringBundler contentSB, String modules,
		ModulesType modulesType) {

		append(portletId, contentSB.toString(), modules, modulesType);
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
		PortletDataRendererUtil.writeTo(writer, _portletDataMap.values());
	}

	public void writeTo(Writer writer, String portletId) throws IOException {
		PortletData portletData = _portletDataMap.remove(portletId);

		if (portletData == null) {
			return;
		}

		PortletDataRendererUtil.writeTo(
			writer, Collections.singleton(portletData));
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

	private static final long serialVersionUID = 1L;

	private final ConcurrentMap<String, PortletData> _portletDataMap =
		new ConcurrentHashMap<>();

}