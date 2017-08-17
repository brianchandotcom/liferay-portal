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

<portlet:actionURL name="editMSBFragmentEntry" var="editMSBFragmentEntryURL">
	<portlet:param name="mvcPath" value="/edit_msb_fragment_entry.jsp" />
</portlet:actionURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="code" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<aui:form action="<%= editMSBFragmentEntryURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="msbFragmentEntryId" type="hidden" value="<%= msbFragmentDisplayContext.getMSBFragmentEntryId() %>" />
	<aui:input name="msbFragmentCollectionId" type="hidden" value="<%= msbFragmentDisplayContext.getMSBFragmentCollectionId() %>" />
	<aui:input name="htmlContent" type="hidden" value="<%= HtmlUtil.escape(msbFragmentEntry != null ? msbFragmentEntry.getHtml() : StringPool.BLANK) %>" />
	<aui:input name="cssContent" type="hidden" value="<%= HtmlUtil.escape(msbFragmentEntry != null ? msbFragmentEntry.getCss() : StringPool.BLANK) %>" />
	<aui:input name="jsContent" type="hidden" value="<%= HtmlUtil.escape(msbFragmentEntry != null ? msbFragmentEntry.getJs() : StringPool.BLANK) %>" />
	<aui:input name="name" type="hidden" value="<%= HtmlUtil.escape(msbFragmentEntry != null ? msbFragmentEntry.getName() : StringPool.BLANK) %>" />

	<aui:model-context bean="<%= msbFragmentEntry %>" model="<%= MSBFragmentEntry.class %>" />

	<liferay-ui:error exception="<%= DuplicateMSBFragmentEntryException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= MSBFragmentEntryNameException.class %>" message="please-enter-a-valid-name" />

	<%
	Map<String, Object> editorContext = new HashMap<>();

	editorContext.put("namespace", portletDisplay.getNamespace());
	%>

	<soy:template-renderer
		context="<%= editorContext %>"
		module="modern-site-building-fragment-web/js/MSBFragmentEditor.es"
		templateNamespace="MSBFragmentEditor.render"
	/>

	<aui:button-row cssClass="msb-fragment-submit-buttons">
		<aui:button cssClass="btn" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script require="modern-site-building-fragment-web/js/MSBFragmentNameEditor.es">
	new modernSiteBuildingFragmentWebJsMSBFragmentNameEditorEs.default({
		namespace: "<%= portletDisplay.getNamespace() %>",
		backUrl: "<%= redirect %>",
		title: "<%= LanguageUtil.get(request, "add-fragment-entry") %>"
	});
</aui:script>