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
String contents = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":contents");
Map<String, Object> editorData = (Map<String, Object>)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":data");
String name = namespace + GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));
String onChangeMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":onChangeMethod");
String placeholder = GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":placeholder"));
String toolbarSet = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":toolbarSet");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

JSONObject editorConfigJSONObject = null;

if (editorData != null) {
	editorConfigJSONObject = (JSONObject)editorData.get("editorConfig");
}
%>

<div>
	<react:component
		module="editor/ClassicEditor"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"contents", contents
			).put(
				"editorConfig", editorConfigJSONObject
			).put(
				"initialToolbarSet", toolbarSet
			).put(
				"name", HtmlUtil.escapeAttribute(name)
			).put(
				"onChangeMethodName", HtmlUtil.escapeJS(onChangeMethod)
			).put(
				"title", LanguageUtil.get(request, placeholder)
			).build()
		%>'
	/>
</div>