<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String editorName = (String)request.getAttribute(AlloyEditorConstants.ATTRIBUTE_NAMESPACE + ":editorName");
%>

<liferay-util:html-top
	outputKey="js_editor_alloyeditor_skip_editor_loading"
>
	<link data-senna-track="temporary" href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR) + "/alloyeditor/assets/alloy-editor-atlas.css") %>" rel="stylesheet" type="text/css" />

	<%
	long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR);
	%>

	<script data-senna-track="temporary" type="text/javascript">
		window.ALLOYEDITOR_BASEPATH =
			'<%= PortalUtil.getPathProxy() + application.getContextPath() %>/alloyeditor/';
	</script>

	<script data-senna-track="temporary" id="<%= namespace %>ckEditorScript" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/ckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>

	<script data-senna-track="temporary" id="<%= namespace %>alloyEditorScript" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR) + "/alloyeditor/alloy-editor-no-ckeditor-min.js", javaScriptLastModified)) %>" type="text/javascript"></script>

	<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.alloyeditor.web#" + editorName + "#additionalResources" %>' />

	<script data-senna-track="temporary" type="text/javascript">
		AlloyEditor.regexBasePath = /(^|.*[\\\/])(?:liferay-alloy-editor[^/]+|liferay-alloy-editor)\.js(?:\?.*|;.*)?$/i;

		var alloyEditorDisposeResources = false;
		var alloyEditorInstances = 0;

		var cleanupAlloyEditorResources = function () {
			if (!alloyEditorInstances && alloyEditorDisposeResources) {
				window.AlloyEditor = undefined;

				alloyEditorInstances = 0;
				alloyEditorDisposeResources = false;

				if (
					window.CKEDITOR &&
					Object.keys(window.CKEDITOR.instances).length === 0
				) {
					delete window.CKEDITOR;
				}
			}
		};

		Liferay.namespace('EDITORS').alloyEditor = {
			addInstance: function () {
				alloyEditorInstances++;
			},
			removeInstance: function () {
				alloyEditorInstances--;

				cleanupAlloyEditorResources();
			},
		};

		CKEDITOR.scriptLoader.loadScripts = function (scripts, success, failure) {
			CKEDITOR.scriptLoader.load(scripts, success, failure);
		};

		CKEDITOR.getNextZIndex = function () {
			return CKEDITOR.dialog._.currentZIndex
				? CKEDITOR.dialog._.currentZIndex + 10
				: Liferay.zIndex.WINDOW + 10;
		};

		var destroyGlobalAlloyEditor = function () {
			alloyEditorDisposeResources = true;

			cleanupAlloyEditorResources();

			Liferay.detach('beforeScreenFlip', destroyGlobalAlloyEditor);
		};

		Liferay.on('beforeScreenFlip', destroyGlobalAlloyEditor);
	</script>
</liferay-util:html-top>