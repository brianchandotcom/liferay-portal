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

String redirect = layoutPageTemplateDisplayContext.getEditLayoutPageTemplateEntryRedirect();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(layoutPageTemplateDisplayContext.getLayoutPageTemplateEntryTitle());
%>

<portlet:actionURL name="/layout/edit_layout_page_template_fragments" var="editLayoutPageTemplateFragmentsURL">
	<portlet:param name="mvcPath" value="/edit_layout_page_template_entry.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/layout/render_fragment_entry" var="renderFragmentEntryURL" />

<liferay-util:html-top outputKey="layout_page_template_entry">
	<link data-senna-track="temporary" href="<%= PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR) + "/alloyeditor/assets/alloy-editor-atlas.css") %>" rel="stylesheet" />

	<%
	long javaScriptLastModified = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR);
	String namespace = "layout_page_template_entry";
	%>

	<script data-senna-track="temporary">
		window.ALLOYEDITOR_BASEPATH = '/o/frontend-editor-alloyeditor-web/alloyeditor/';
	</script>

	<script data-senna-track="temporary" id="<%= namespace %>ckEditorScript" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_CKEDITOR) + "/ckeditor/ckeditor.js", javaScriptLastModified)) %>"></script>

	<script data-senna-track="temporary" id="<%= namespace %>alloyEditorScript" src="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR) + "/alloyeditor/liferay-alloy-editor-no-ckeditor-min.js", javaScriptLastModified)) %>"></script>

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

<%
Map<String, Object> layoutPageTemplateEditorContext = new HashMap<>();

layoutPageTemplateEditorContext.put("fragments", layoutPageTemplateDisplayContext.getFragmentEntryLinksJSONArray());
layoutPageTemplateEditorContext.put("fragmentCollections", layoutPageTemplateDisplayContext.getFragmentCollectionsJSONArray());
layoutPageTemplateEditorContext.put("layoutPageTemplateEntryId", layoutPageTemplateDisplayContext.getLayoutPageTemplateEntryId());
layoutPageTemplateEditorContext.put("portletNamespace", renderResponse.getNamespace());
layoutPageTemplateEditorContext.put("renderFragmentEntryURL", renderFragmentEntryURL);
layoutPageTemplateEditorContext.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
layoutPageTemplateEditorContext.put("updatePageTemplateURL", String.valueOf(editLayoutPageTemplateFragmentsURL));
%>

<soy:template-renderer
	context="<%= layoutPageTemplateEditorContext %>"
	module="layout-admin-web/js/fragments_editor/LayoutPageTemplateEditor.es"
	templateNamespace="LayoutPageTemplateEditor.render"
/>