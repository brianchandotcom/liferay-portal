<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String contents = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":contents");
Map<String, Object> editorData = (Map<String, Object>)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":data");
String name = namespace + GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":name"));

String onChangeMethod = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}
%>

<div>
	<react:component
		module="editor/BalloonEditor"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"config", (editorData == null) ? null : (JSONObject)editorData.get("editorConfig")
			).put(
				"contents", contents
			).put(
				"name", HtmlUtil.escapeAttribute(name)
			).put(
				"onChangeMethodName", HtmlUtil.escapeJS(onChangeMethod)
			).build()
		%>'
	/>
</div>