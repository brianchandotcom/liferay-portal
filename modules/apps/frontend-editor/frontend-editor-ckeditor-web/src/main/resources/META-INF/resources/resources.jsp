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
String editorName = (String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":editorName");
boolean inlineEdit = GetterUtil.getBoolean((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":inlineEdit"));
String inlineEditSaveURL = GetterUtil.getString((String)request.getAttribute(CKEditorConstants.ATTRIBUTE_NAMESPACE + ":inlineEditSaveURL"));
%>

<liferay-util:html-top
	outputKey="js_editor_ckeditor_skip_editor_loading"
>
	<style type="text/css">
		table.cke_dialog {
			position: absolute !important;
		}
	</style>

	<%
	long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR);
	%>

	<script data-senna-track="temporary" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/ckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>

	<c:if test="<%= inlineEdit && Validator.isNotNull(inlineEditSaveURL) %>">
		<script data-senna-track="temporary" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/main.js", javaScriptLastModified)) %>" type="text/javascript"></script>
	</c:if>

	<liferay-util:dynamic-include key='<%= "com.liferay.frontend.editor.ckeditor.web#" + editorName + "#additionalResources" %>' />

	<script data-senna-track="temporary" type="text/javascript">
		CKEDITOR.scriptLoader.loadScripts = function(scripts, success, failure) {
			CKEDITOR.scriptLoader.load(scripts, success, failure);
		};

		CKEDITOR.getNextZIndex = function() {
			return CKEDITOR.dialog._.currentZIndex
				? CKEDITOR.dialog._.currentZIndex + 10
				: Liferay.zIndex.WINDOW + 10;
		};

		var ckEditorDisposeResources = false;
		var ckEditorInstances = 0;

		var cleanupCkEditorResources = function() {
			if (!ckEditorInstances && ckEditorDisposeResources) {
				window.CKEDITOR = undefined;

				ckEditorInstances = 0;
				ckEditorDisposeResources = false;
			}
		};

		Liferay.namespace('EDITORS').ckeditor = {
			addInstance: function() {
				ckEditorInstances++;
			},
			removeInstance: function() {
				ckEditorInstances--;

				cleanupCkEditorResources();
			}
		};

		var destroyGlobalCkEditor = function() {
			ckEditorDisposeResources = true;

			cleanupCkEditorResources();

			Liferay.detach('beforeScreenFlip', destroyGlobalCkEditor);
		};

		Liferay.on('beforeScreenFlip', destroyGlobalCkEditor);
	</script>
</liferay-util:html-top>