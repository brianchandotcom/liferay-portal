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
FragmentEntry fragmentEntry = fragmentDisplayContext.getFragmentEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fragmentDisplayContext.getRedirect());

String title = fragmentDisplayContext.getFragmentEntryTitle();

if (WorkflowConstants.STATUS_DRAFT == fragmentEntry.getStatus()) {
	title = fragmentDisplayContext.getFragmentEntryTitle() + " (" + LanguageUtil.get(request, WorkflowConstants.getStatusLabel(fragmentEntry.getStatus())) + ")";
}

renderResponse.setTitle(title);
%>

<liferay-ui:error exception="<%= FragmentEntryContentException.class %>">

	<%
	FragmentEntryContentException fece = (FragmentEntryContentException)errorException;
	%>

	<c:choose>
		<c:when test="<%= Validator.isNotNull(fece.getMessage()) %>">
			<%= fece.getMessage() %>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="fragment-html-is-invalid" />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>

<portlet:actionURL name="/fragment/edit_fragment_entry" var="editFragmentEntryURL" />

<div id="<portlet:namespace />fragmentEditor"></div>

<liferay-portlet:renderURL plid="<%= fragmentDisplayContext.getRenderLayoutPlid() %>" var="renderFragmentEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/fragment/render_fragment_entry" />
	<portlet:param name="fragmentEntryId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentEntryId()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL plid="<%= fragmentDisplayContext.getRenderLayoutPlid() %>" var="previewFragmentEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/fragment/preview_fragment_entry" />
</liferay-portlet:renderURL>

<aui:script require="fragment-web/js/FragmentEditor.es as FragmentEditor">
	var cssInput = document.getElementById('<portlet:namespace />cssContent');
	var htmlInput = document.getElementById('<portlet:namespace />htmlContent');
	var jsInput = document.getElementById('<portlet:namespace />jsContent');
	var wrapper = document.getElementById('<portlet:namespace />fragmentEditor');

	var fragmentEditor = new FragmentEditor.default(
		{
			allowedStatus: {
				approved: '<%= WorkflowConstants.STATUS_APPROVED %>',
				draft: '<%= WorkflowConstants.STATUS_DRAFT %>'
			},
			fragmentCollectionId: '<%= fragmentDisplayContext.getFragmentCollectionId() %>',
			fragmentEntryId: '<%= fragmentDisplayContext.getFragmentEntryId() %>',
			initialCSS: '<%= HtmlUtil.escapeJS(fragmentDisplayContext.getCssContent()) %>',
			initialHTML: '<%= HtmlUtil.escapeJS(fragmentDisplayContext.getHtmlContent()) %>',
			initialJS: '<%= HtmlUtil.escapeJS(fragmentDisplayContext.getJsContent()) %>',
			name: '<%= fragmentDisplayContext.getName() %>',
			portletNamespace: '<portlet:namespace />',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
			status: '<%= fragmentEntry.getStatus() %>',
			urls: {
				current: '<%= currentURL %>',
				edit: '<%= editFragmentEntryURL %>',
				preview: '<%= previewFragmentEntryURL %>',
				redirect: '<%= fragmentDisplayContext.getRedirect() %>',
				render: '<%= renderFragmentEntryURL %>'
			}
		},
		wrapper
	);

	function destroyFragmentEditor () {
		fragmentEditor.dispose();

		Liferay.detach('destroyPortlet', destroyFragmentEditor);
	}

	Liferay.on('destroyPortlet', destroyFragmentEditor);
</aui:script>