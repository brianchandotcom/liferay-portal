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

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryPartDisplayContext<CETCustomElement> editClientExtensionEntryPartDisplayContext = (EditClientExtensionEntryPartDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_PART_DISPLAY_CONTEXT);

CETCustomElement cetCustomElement = editClientExtensionEntryPartDisplayContext.getCET();
%>

<aui:input label="html-element-name" name="htmlElementName" type="text" value="<%= cetCustomElement.getHTMLElementName() %>" />

<aui:input label="use-esm" name="useESM" type="checkbox" value="<%= cetCustomElement.isUseESM() %>" />

<div id="<portlet:namespace />_urls_field">

	<%
	for (String url : editClientExtensionEntryPartDisplayContext.getStrings(cetCustomElement.getURLs())) {
	%>

		<div class="lfr-form-row">
			<aui:input ignoreRequestValue="<%= true %>" label="url" name="urls" type="text" value="<%= url %>" />
		</div>

	<%
	}
	%>

</div>

<div id="<portlet:namespace />_cssURLs_field">

	<%
	for (String cssURL : editClientExtensionEntryPartDisplayContext.getStrings(cetCustomElement.getCSSURLs())) {
	%>

		<div class="lfr-form-row">
			<aui:input ignoreRequestValue="<%= true %>" label="css-url" name="cssURLs" type="text" value="<%= cssURL %>" />
		</div>

	<%
	}
	%>

</div>

<c:choose>
	<c:when test="<%= editClientExtensionEntryPartDisplayContext.isNew() %>">
		<aui:input label="instanceable" name="instanceable" type="checkbox" value="<%= cetCustomElement.isInstanceable() %>" />
	</c:when>
	<c:otherwise>
		<aui:input disabled="<%= true %>" label="instanceable" name="instanceable-disabled" type="checkbox" value="<%= cetCustomElement.isInstanceable() %>" />
		<aui:input name="instanceable" type="hidden" value="<%= cetCustomElement.isInstanceable() %>" />
	</c:otherwise>
</c:choose>

<clay:select
	label="portlet-category-name"
	name="portletCategoryName"
	options="<%= editClientExtensionEntryPartDisplayContext.getPortletCategoryNameSelectOptions(cetCustomElement.getPortletCategoryName()) %>"
/>

<aui:input label="friendly-url-mapping" name="friendlyURLMapping" type="text" value="<%= cetCustomElement.getFriendlyURLMapping() %>" />

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_urls_field',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();

	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_cssURLs_field',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();
</aui:script>