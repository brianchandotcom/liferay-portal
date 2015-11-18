<%--
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
--%>

<%@ include file="/html/js/editor/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%
ScriptData newScriptData = new ScriptData() {

	@Override
	public void append(String portletId, StringBundler contentSB, String use) {
		_replaceVariations(contentSB);

		super.append(portletId, contentSB, use);
	}

};

request.setAttribute(WebKeys.AUI_SCRIPT_DATA, newScriptData);

OutputData newOutputData = new OutputData() {

	@Override
	public void addData(String outputKey, String webKey, StringBundler sb) {
		_replaceVariations(sb);

		super.addData(outputKey, webKey, sb);
	}

};

request.setAttribute(WebKeys.OUTPUT_DATA, newOutputData);
%>

<liferay-util:buffer var="html">
	<liferay-util:include page="/html/js/editor/ckeditor.jsp" />
</liferay-util:buffer>

<%= _replaceVariations(html) %>

<%!
private static String _replaceVariations(String content) {
	content = StringUtil.replace(content, "/ckeditor/", "/ckeditor_latest/");
	content = StringUtil.replace(content, "CKEDITOR.env.isCompatible = true;", "");

	return content;
}

private static void _replaceVariations(StringBundler sb) {
	String content = sb.toString();

	content = _replaceVariations(content);

	sb.setIndex(0);

	sb.append(content);
}
%>