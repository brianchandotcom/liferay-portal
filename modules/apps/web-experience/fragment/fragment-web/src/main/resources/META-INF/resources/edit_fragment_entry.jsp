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
portletDisplay.setURLBack(fragmentDisplayContext.getEditFragmentEntryRedirect());

renderResponse.setTitle(fragmentDisplayContext.getFragmentEntryTitle());
%>

<aui:nav-bar markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="code" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<portlet:actionURL name="/fragment/edit_fragment_entry" var="editFragmentEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_entry" />
</portlet:actionURL>

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

<aui:form action="<%= editFragmentEntryURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="fragmentEntryId" type="hidden" value="<%= fragmentDisplayContext.getFragmentEntryId() %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentDisplayContext.getFragmentCollectionId() %>" />
	<aui:input name="cssContent" type="hidden" value="" />
	<aui:input name="htmlContent" type="hidden" value="" />
	<aui:input name="jsContent" type="hidden" value="" />

	<aui:model-context bean="<%= fragmentEntry %>" model="<%= FragmentEntry.class %>" />

	<aui:input autoFocus="<%= false %>" name="name" placeholder="name" type="hidden" />

	<div id="<portlet:namespace />fragmentEditor"></div>

	<aui:button-row cssClass="fragment-submit-buttons">
		<aui:button cssClass="btn btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script require="fragment-web/js/FragmentEditor.es as FragmentEditor">
	var cssInput = document.getElementById('<portlet:namespace />cssContent');
	var htmlInput = document.getElementById('<portlet:namespace />htmlContent');
	var jsInput = document.getElementById('<portlet:namespace />jsContent');
	var wrapper = document.getElementById('<portlet:namespace />fragmentEditor');

	var fragmentEditor = new FragmentEditor.default(
		{
			events: {
				contentChanged: function(event) {
					cssInput.value = event.css;
					htmlInput.value = event.html;
					jsInput.value = event.js;
				}
			},
			initialCSS: '<%= HtmlUtil.escapeJS(fragmentDisplayContext.getCssContent()) %>',
			initialHTML: '<%= HtmlUtil.escapeJS(fragmentDisplayContext.getHtmlContent()) %>',
			initialJS: '<%= HtmlUtil.escapeJS(fragmentDisplayContext.getJsContent()) %>',
			namespace: '<portlet:namespace />',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
		},
		wrapper
	);

	function destroyFragmentEditor () {
		fragmentEditor.dispose();

		Liferay.detach('destroyPortlet', destroyFragmentEditor);
	}

	Liferay.on('destroyPortlet', destroyFragmentEditor);
</aui:script>