<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String contents = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":contents");
Map<String, Object> data = (Map<String, Object>)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":data");
boolean formInputEnabled = GetterUtil.getBoolean((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":formInputEnabled"));
String formInputName = GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":formInputName"));

JSONObject editorConfigJSONObject = null;

if (data != null) {
	editorConfigJSONObject = (JSONObject)data.get("editorConfig");
}

if (contents != null) {
	editorConfigJSONObject.put("initialData", contents);
}
%>

<div>
	<react:component
		module="{CKEditor5BalloonEditor} from frontend-editor-ckeditor-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"config", editorConfigJSONObject
			).put(
				"formInputEnabled", formInputEnabled
			).put(
				"formInputName", HtmlUtil.escapeAttribute(namespace + formInputName)
			).build()
		%>'
	/>
</div>