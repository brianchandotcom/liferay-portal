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
String editorName = (String)request.getAttribute(TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":editorName");
%>

<liferay-util:html-top
	outputKey="js_editor_tinymce"
>

	<%
	long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_TINYMCEEDITOR);
	%>

	<script data-senna-track="temporary" src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_TINYMCEEDITOR) + "/tiny_mce/tinymce.min.js", javaScriptLastModified)) %>" type="text/javascript"></script>

	<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.tinymce.web#" + editorName + "#additionalResources" %>' />

	<script data-senna-track="temporary" type="text/javascript">
		var tinymceInstances = 0;
		var disposeResources = false;

		var cleanupGlobals = function() {
			if (!tinymceInstances && disposeResources) {
				window.tinyMCE = undefined;

				tinymceInstances = 0;
				disposeResources = false;
			}
		};

		Liferay.namespace('EDITORS').tinymce = {
			addInstance: function() {
				tinymceInstances++;
			},
			removeInstance: function() {
				tinymceInstances--;

				cleanupGlobals();
			}
		};

		var destroyGlobalEditors = function() {
			disposeResources = true;

			cleanupGlobals();

			Liferay.detach('beforeScreenFlip', destroyGlobalEditors);
		};

		Liferay.on('beforeScreenFlip', destroyGlobalEditors);
	</script>
</liferay-util:html-top>