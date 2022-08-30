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
EditClientExtensionEntryDisplayContext<CustomElementCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

CustomElementCET customElementCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="html-element-name" name="htmlElementName" required="<%= true %>" type="text" value="<%= customElementCET.getHTMLElementName() %>" />

	<div class="form-text">
		<liferay-ui:message key="the-name-for-the-custom-element-thats-declared-in-its-js-file" />
	</div>
</aui:field-wrapper>

<aui:input label="use-esm" name="useESM" type="checkbox" value="<%= customElementCET.isUseESM() %>" />

<div class="lfr-form-rows" id="<portlet:namespace />_urls_field">

	<%
	for (String url : editClientExtensionEntryDisplayContext.getStrings(customElementCET.getURLs())) {
	%>

		<div class="lfr-form-row">
			<aui:field-wrapper cssClass="form-group">
				<aui:input ignoreRequestValue="<%= true %>" label="js-url" name="urls" required="<%= true %>" type="text" value="<%= url %>" />

				<div class="form-text">
					<liferay-ui:message key="enter-individual-urls-for-each-of-your-client-extension-js-files" />
				</div>
			</aui:field-wrapper>
		</div>

	<%
	}
	%>

</div>

<div class="lfr-form-rows" id="<portlet:namespace />_cssURLs_field">

	<%
	for (String cssURL : editClientExtensionEntryDisplayContext.getStrings(customElementCET.getCSSURLs())) {
	%>

		<div class="lfr-form-row">
			<aui:field-wrapper cssClass="form-group">
				<aui:input ignoreRequestValue="<%= true %>" label="css-url" name="cssURLs" type="text" value="<%= cssURL %>" />

				<div class="form-text">
					<liferay-ui:message key="enter-individual-urls-for-each-of-your-client-extension-css-files" />
				</div>
			</aui:field-wrapper>
		</div>

	<%
	}
	%>

</div>

<c:choose>
	<c:when test="<%= editClientExtensionEntryDisplayContext.isNew() %>">
		<aui:input label="instanceable" name="instanceable" type="checkbox" value="<%= customElementCET.isInstanceable() %>" />
	</c:when>
	<c:otherwise>
		<aui:input disabled="<%= true %>" label="instanceable" name="instanceable-disabled" type="checkbox" value="<%= customElementCET.isInstanceable() %>" />
		<aui:input name="instanceable" type="hidden" value="<%= customElementCET.isInstanceable() %>" />
	</c:otherwise>
</c:choose>

<clay:select
	label="portlet-category-name"
	name="portletCategoryName"
	options="<%= editClientExtensionEntryDisplayContext.getPortletCategoryNameSelectOptions(customElementCET.getPortletCategoryName()) %>"
/>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="friendly-url-mapping" name="friendlyURLMapping" type="text" value="<%= customElementCET.getFriendlyURLMapping() %>" />

	<div class="form-text">
		<liferay-ui:message key="define-the-widgets-friendly-url-mapping-so-you-can-refer-to-it-using-a-more-user-readable-url" />
	</div>
</aui:field-wrapper>

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