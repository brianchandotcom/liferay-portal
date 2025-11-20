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
String name = namespace + GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

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
				"formInputName", HtmlUtil.escapeAttribute(name)
			).build()
		%>'
	/>
</div>