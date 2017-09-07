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
String redirect = msbFragmentDisplayContext.getEditMSBFragmentEntryRedirect();

MSBFragmentEntry msbFragmentEntry = msbFragmentDisplayContext.getMSBFragmentEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(msbFragmentDisplayContext.getMSBFragmentEntryTitle());
%>

<aui:nav-bar markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="code" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<portlet:actionURL name="editMSBFragmentEntry" var="editMSBFragmentEntryURL">
	<portlet:param name="mvcPath" value="/edit_msb_fragment_entry.jsp" />
</portlet:actionURL>

<aui:form action="<%= editMSBFragmentEntryURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="msbFragmentEntryId" type="hidden" value="<%= msbFragmentDisplayContext.getMSBFragmentEntryId() %>" />
	<aui:input name="msbFragmentCollectionId" type="hidden" value="<%= msbFragmentDisplayContext.getMSBFragmentCollectionId() %>" />
	<aui:input name="cssContent" type="hidden" value="" />
	<aui:input name="htmlContent" type="hidden" value="" />
	<aui:input name="jsContent" type="hidden" value="" />

	<aui:model-context bean="<%= msbFragmentEntry %>" model="<%= MSBFragmentEntry.class %>" />

	<aui:input autoFocus="<%= false %>" name="name" placeholder="name" type="hidden" />

	<div id="<portlet:namespace />msbFragmentEditor"></div>

	<aui:button-row cssClass="msb-fragment-submit-buttons">
		<aui:button cssClass="btn btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script require="modern-site-building-fragment-web/js/MSBFragmentEditor">
	var cssInput = document.getElementById('<portlet:namespace />cssContent');
	var htmlInput = document.getElementById('<portlet:namespace />htmlContent');
	var jsInput = document.getElementById('<portlet:namespace />jsContent');
	var wrapper = document.getElementById('<portlet:namespace />msbFragmentEditor');

	new modernSiteBuildingFragmentWebJsMSBFragmentEditor.default(
		{
			events: {
				contentChanged: function(event) {
					cssInput.value = event.css;
					htmlInput.value = event.html;
					jsInput.value = event.js;
				}
			},
			initialCSS: '<%= HtmlUtil.escapeJS((msbFragmentEntry != null) ? msbFragmentEntry.getCss() : StringPool.BLANK) %>',
			initialHTML: '<%= HtmlUtil.escapeJS((msbFragmentEntry != null) ? msbFragmentEntry.getHtml() : StringPool.BLANK) %>',
			initialJS: '<%= HtmlUtil.escapeJS((msbFragmentEntry != null) ? msbFragmentEntry.getJs() : StringPool.BLANK) %>',
			namespace: '<portlet:namespace />',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
		},
		wrapper
	);
</aui:script>