<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CKEditorSampleDisplayContext ckEditorSampleDisplayContext = (CKEditorSampleDisplayContext)request.getAttribute(CKEditorSampleWebKeys.CKEDITOR_SAMPLE_DISPLAY_CONTEXT);
%>

<react:component
	module="{CKEditor5ReactClassicEditor} from frontend-editor-ckeditor-sample-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"editorConfig", ckEditorSampleDisplayContext.getCKEditor5ClassicEditorConfig()
		).put(
			"editorTransformerURLs", ckEditorSampleDisplayContext.getEditorTransformerURLsJSONArray()
		).build()
	%>'
/>