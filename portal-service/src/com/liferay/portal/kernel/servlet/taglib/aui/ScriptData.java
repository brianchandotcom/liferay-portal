/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptData implements Mergeable<ScriptData>, Serializable {

	public void append(String portletId, String content, String use) {
		PortletData portletData = _getPortletData(portletId);

		portletData.append(content, use);
	}

	public void append(String portletId, StringBundler contentSB, String use) {
		PortletData portletData = _getPortletData(portletId);

		portletData.append(contentSB, use);
	}

	public void mark() {
		for (PortletData portletData : _portletDataMap.values()) {
			_addToSBIndexList(portletData._callbackSB);
			_addToSBIndexList(portletData._rawSB);
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
		for (ObjectValuePair<StringBundler, Integer> ovp : _sbIndexList) {
			StringBundler sb = ovp.getKey();

			sb.setIndex(ovp.getValue());
		}
	}

	public void writeTo(HttpServletRequest request, Writer writer)
		throws IOException {

		writer.write("<script type=\"text/javascript\">\n// <![CDATA[\n");

		StringBundler callbackSB = new StringBundler(_portletDataMap.size());

		for (PortletData portletData : _portletDataMap.values()) {
			portletData._rawSB.writeTo(writer);

			callbackSB.append(portletData._callbackSB);
		}

		if (callbackSB.index() == 0) {
			writer.write("\n// ]]>\n</script>");

			return;
		}

		String loadMethod = "use";

		if (BrowserSnifferUtil.isIe(request) &&
			(BrowserSnifferUtil.getMajorVersion(request) < 8)) {

			loadMethod = "ready";
		}

		writer.write("AUI().");
		writer.write(loadMethod);
		writer.write("(");

		Set<String> useSet = new TreeSet<String>();

		for (PortletData portletData : _portletDataMap.values()) {
			useSet.addAll(portletData._useSet);
		}

		for (String use : useSet) {
			writer.write(StringPool.APOSTROPHE);
			writer.write(use);
			writer.write(StringPool.APOSTROPHE);
			writer.write(StringPool.COMMA_AND_SPACE);
		}

		writer.write("function(A) {");

		callbackSB.writeTo(writer);

		writer.write("});");

		writer.write("\n// ]]>\n</script>");
	}

	private void _addToSBIndexList(StringBundler sb) {
		ObjectValuePair<StringBundler, Integer> ovp =
			new ObjectValuePair<StringBundler, Integer>(sb, sb.index());

		int index = _sbIndexList.indexOf(ovp);

		if (index == -1) {
			_sbIndexList.add(ovp);
		}
		else {
			ovp = _sbIndexList.get(index);

			ovp.setValue(sb.index());
		}
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

	private ConcurrentMap<String, PortletData> _portletDataMap =
		new ConcurrentHashMap<String, PortletData>();
	private List<ObjectValuePair<StringBundler, Integer>> _sbIndexList =
		new ArrayList<ObjectValuePair<StringBundler, Integer>>();

	private class PortletData implements Serializable {

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
					_useSet.add(StringUtil.trim(useArray[i]));
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
					_useSet.add(StringUtil.trim(useArray[i]));
				}
			}
		}

		private static final long serialVersionUID = 1L;

		private StringBundler _callbackSB = new StringBundler();
		private StringBundler _rawSB = new StringBundler();
		private Set<String> _useSet = new TreeSet<String>();

	}

}