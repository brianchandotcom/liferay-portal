<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
Map<String, Object> editorData = (Map<String, Object>)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":data");
String name = namespace + GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));
String toolbarSet = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":toolbarSet");

JSONObject editorConfigJSONObject = null;

if (editorData != null) {
	editorConfigJSONObject = (JSONObject)editorData.get("editorConfig");
}

Map<String, Object> data = HashMapBuilder.<String, Object>put(
	"editorConfig", editorConfigJSONObject
).put(
	"initialToolbarSet", toolbarSet
).put(
	"name", HtmlUtil.escapeAttribute(name)
).build();
%>

<div>
	<react:component
		data="<%= data %>"
		module="editor/ClassicEditor"
	/>
</div>