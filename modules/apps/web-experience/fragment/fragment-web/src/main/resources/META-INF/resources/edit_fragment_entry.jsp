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
String redirect = ParamUtil.getString(request, "redirect");

FragmentEntry fragmentEntry = fragmentDisplayContext.getFragmentEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fragmentDisplayContext.getEditFragmentEntryRedirect());

String title = fragmentDisplayContext.getFragmentEntryTitle();

if (WorkflowConstants.STATUS_DRAFT == fragmentEntry.getStatus()) {
	title = fragmentDisplayContext.getFragmentEntryTitle() + " (" + LanguageUtil.get(request, WorkflowConstants.getStatusLabel(fragmentEntry.getStatus())) + ")";
}

renderResponse.setTitle(title);
%>

<clay:navigation-bar
	items="<%= fragmentDisplayContext.getEditFragmentEntryNavigationItems() %>"
/>

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

<aui:form action="<%= editFragmentEntryURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fragmentEntryId" type="hidden" value="<%= fragmentDisplayContext.getFragmentEntryId() %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentDisplayContext.getFragmentCollectionId() %>" />
	<aui:input name="cssContent" type="hidden" value="" />
	<aui:input name="htmlContent" type="hidden" value="" />
	<aui:input name="jsContent" type="hidden" value="" />
	<aui:input name="status" type="hidden" value="<%= fragmentEntry.getStatus() %>" />

	<aui:model-context bean="<%= fragmentEntry %>" model="<%= FragmentEntry.class %>" />

	<aui:input autoFocus="<%= false %>" name="name" placeholder="name" type="hidden" />

	<div id="<portlet:namespace />fragmentEditor"></div>

	<aui:button-row cssClass="fragment-submit-buttons">
		<c:if test="<%= WorkflowConstants.STATUS_DRAFT == fragmentEntry.getStatus() %>">
			<aui:button primary="<%= false %>" type="submit" value="save-as-draft" />
		</c:if>

		<aui:button name="publishButton" type="submit" value="publish" />
	</aui:button-row>
</aui:form>

<portlet:actionURL name="/fragment/render_fragment_entry" var="renderFragmentEntryURL">
	<portlet:param name="fragmentEntryId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentEntryId()) %>" />
</portlet:actionURL>

<aui:script require="fragment-web/js/FragmentEditor.es as FragmentEditor, metal-dom/src/all/dom as dom">
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
			renderFragmentEntryURL: '<%= renderFragmentEntryURL %>',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
		},
		wrapper
	);

	var publishButtonClickHandler = dom.delegate(
		document.body,
		'click',
		'#<portlet:namespace />publishButton',
		function(event) {
			event.preventDefault();

			dom.toElement('#<portlet:namespace />status').value = '<%= WorkflowConstants.STATUS_APPROVED %>';

			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	);

	function destroyFragmentEditor () {
		fragmentEditor.dispose();
		publishButtonClickHandler.removeListener();

		Liferay.detach('destroyPortlet', destroyFragmentEditor);
	}

	Liferay.on('destroyPortlet', destroyFragmentEditor);
</aui:script>