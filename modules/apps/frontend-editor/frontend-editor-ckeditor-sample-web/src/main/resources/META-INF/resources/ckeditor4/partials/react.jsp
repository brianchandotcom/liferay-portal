<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CKEditorSampleDisplayContext ckEditorSampleDisplayContext = (CKEditorSampleDisplayContext)request.getAttribute(CKEditorSampleWebKeys.CKEDITOR_SAMPLE_DISPLAY_CONTEXT);
%>

<react:component
	module="{CKEditor4ReactClassicEditor} from frontend-editor-ckeditor-sample-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"editorTransformerURLs", ckEditorSampleDisplayContext.getEditorTransformerURLsJSONArray()
		).put(
			"name", "sampleReactClassicEditor"
		).put(
			"title", "Classic Editor used from a React component"
		).build()
	%>'
/>