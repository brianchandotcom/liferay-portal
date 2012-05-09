/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptData implements Mergeable<ScriptData> {

	public void append(String portletId, String content, String use) {
		PortletData portletData = getPortletData(portletId);

		portletData.append(content, use);
	}

	public void append(String portletId, StringBundler contentSB, String use) {
		PortletData portletData = getPortletData(portletId);

		portletData.append(contentSB, use);
	}

	public StringBundler getCallbackSB() {
		StringBundler callbackSB = new StringBundler();

		for (PortletData portletData : _portletDataMap.values()) {
			callbackSB.append(portletData._callbackSB);
		}

		return callbackSB;
	}

	public StringBundler getRawSB() {
		StringBundler rawSB = new StringBundler();

		for (PortletData portletData : _portletDataMap.values()) {
			rawSB.append(portletData._rawSB);
		}

		return rawSB;
	}

	public Set<String> getUseSet() {
		Set<String> useSet = new TreeSet<String>();

		for (PortletData portletData : _portletDataMap.values()) {
			useSet.addAll(portletData._useSet);
		}

		return useSet;
	}

	public ScriptData merge(ScriptData slaveScriptData) {
		if ((slaveScriptData != null) && (slaveScriptData != this)) {
			_portletDataMap.putAll(slaveScriptData._portletDataMap);
		}

		return this;
	}

	private PortletData getPortletData(String portletId) {
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

	private ConcurrentMap<String, PortletData> _portletDataMap =
		new ConcurrentHashMap<String, PortletData>();

	private class PortletData {

		public void append(String content, String use) {
			if (Validator.isNull(use)) {
				_rawSB.append(content);
			}
			else {
				_callbackSB.append("(function() {");
				_callbackSB.append(content);
				_callbackSB.append("})();");

				String[] useArray = StringUtil.split(use);

				for (int i = 0; i < useArray.length; i++) {
					_useSet.add(useArray[i]);
				}
			}
		}

		public void append(StringBundler contentSB, String use) {
			if (Validator.isNull(use)) {
				_rawSB.append(contentSB);
			}
			else {
				_callbackSB.append("(function() {");
				_callbackSB.append(contentSB);
				_callbackSB.append("})();");

				String[] useArray = StringUtil.split(use);

				for (int i = 0; i < useArray.length; i++) {
					_useSet.add(useArray[i]);
				}
			}
		}

		private StringBundler _callbackSB = new StringBundler();
		private StringBundler _rawSB = new StringBundler();
		private Set<String> _useSet = new TreeSet<String>();

	}

}