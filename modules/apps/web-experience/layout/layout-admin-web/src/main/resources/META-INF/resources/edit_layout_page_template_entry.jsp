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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);

PortletURL editLayoutPageTemplateFragmentsURL = renderResponse.createActionURL();

editLayoutPageTemplateFragmentsURL.setParameter(ActionRequest.ACTION_NAME, "/layout/edit_layout_page_template_fragments");
editLayoutPageTemplateFragmentsURL.setParameter("mvcPath", "/edit_layout_page_template_entry.jsp");

FragmentsEditorContext fragmentsEditorContext = new FragmentsEditorContext(request, renderResponse, LayoutPageTemplateEntry.class.getName(), layoutPageTemplateDisplayContext.getLayoutPageTemplateEntryId(), editLayoutPageTemplateFragmentsURL.toString());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(layoutPageTemplateDisplayContext.getEditLayoutPageTemplateEntryRedirect());

renderResponse.setTitle(layoutPageTemplateDisplayContext.getLayoutPageTemplateEntryTitle());
%>

<liferay-util:html-top outputKey="layout_page_template_entry">
	<link data-senna-track="temporary" href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR) + "/alloyeditor/assets/alloy-editor-atlas.css") %>" rel="stylesheet" />

	<script data-senna-track="temporary">
		window.ALLOYEDITOR_BASEPATH = '/o/frontend-editor-alloyeditor-web/alloyeditor/';
	</script>

	<%
	long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR);
	%>

	<script data-senna-track="temporary" id="layoutPageTemplateEntryCkEditorScript" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/ckeditor.js", javaScriptLastModified)) %>"></script>

	<script data-senna-track="temporary" id="layoutPageTemplateEntryAlloyEditorScript" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR) + "/alloyeditor/alloy-editor-no-ckeditor-min.js", javaScriptLastModified)) %>"></script>

	<script data-senna-track="temporary">
		AlloyEditor.regexBasePath = /(^|.*[\\\/])(?:liferay-alloy-editor[^/]+|liferay-alloy-editor)\.js(?:\?.*|;.*)?$/i;

		CKEDITOR.scriptLoader.loadScripts = function(scripts, success, failure) {
			CKEDITOR.scriptLoader.load(scripts, success, failure);
		};

		CKEDITOR.getNextZIndex = function() {
			return CKEDITOR.dialog._.currentZIndex ? CKEDITOR.dialog._.currentZIndex + 10 : Liferay.zIndex.WINDOW + 10;
		};

		var destroyGlobalEditors = function() {
			window.AlloyEditor = undefined;
			window.CKEDITOR = undefined;

			Liferay.detach('beforeScreenFlip', destroyGlobalEditors);
		};

		Liferay.on('beforeScreenFlip', destroyGlobalEditors);
	</script>
</liferay-util:html-top>

<soy:template-renderer
	context="<%= fragmentsEditorContext.getEditorContext() %>"
	module="layout-admin-web/js/fragments_editor/FragmentsEditor.es"
	templateNamespace="FragmentsEditor.render"
/>