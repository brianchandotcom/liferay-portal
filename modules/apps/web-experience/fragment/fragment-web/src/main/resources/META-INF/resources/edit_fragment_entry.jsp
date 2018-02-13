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

String title = fragmentDisplayContext.getFragmentEntryTitle();

if (WorkflowConstants.STATUS_DRAFT == fragmentEntry.getStatus()) {
	title = fragmentDisplayContext.getFragmentEntryTitle() + " (" + LanguageUtil.get(request, WorkflowConstants.getStatusLabel(fragmentEntry.getStatus())) + ")";
}

renderResponse.setTitle(title);
%>

<div class="nav-bar-container">
	<div class="navbar navbar-default">
		<div class="container">
			<div class="navbar navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
				<ul class="navbar-nav">
					<li class="nav-item">
						<portlet:renderURL var="mainURL" />

						<aui:a cssClass="active nav-link" href="<%= mainURL %>" label="code" />
					</li>
				</ul>
			</div>

			<div class="mt-1 pull-right">
				<c:if test="<%= WorkflowConstants.STATUS_DRAFT == fragmentEntry.getStatus() %>">
					<button class="btn btn-default" onclick="<%= "submitForm(document.querySelector('#" + renderResponse.getNamespace() + "fm'));" %>">
						<span class="lfr-btn-label">
							<liferay-ui:message key="save-as-draft" />
						</span>
					</button>
				</c:if>

				<button class="btn btn-primary" id="<portlet:namespace />publishButton">
					<span class="lfr-btn-label">
						<liferay-ui:message key="publish" />
					</span>
				</button>
			</div>
		</div>
	</div>
</div>

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
	<aui:input name="redirect" type="hidden" value="<%= fragmentDisplayContext.getEditFragmentEntryRedirect() %>" />
	<aui:input name="fragmentEntryId" type="hidden" value="<%= fragmentDisplayContext.getFragmentEntryId() %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentDisplayContext.getFragmentCollectionId() %>" />
	<aui:input name="cssContent" type="hidden" value="" />
	<aui:input name="htmlContent" type="hidden" value="" />
	<aui:input name="jsContent" type="hidden" value="" />
	<aui:input name="status" type="hidden" value="<%= fragmentEntry.getStatus() %>" />

	<aui:model-context bean="<%= fragmentEntry %>" model="<%= FragmentEntry.class %>" />

	<aui:input autoFocus="<%= false %>" name="name" placeholder="name" type="hidden" />

	<div id="<portlet:namespace />fragmentEditor"></div>
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