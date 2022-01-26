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
CommerceTermEntryDisplayContext commerceTermEntryDisplayContext = (CommerceTermEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTermEntry commerceTermEntry = commerceTermEntryDisplayContext.getCommerceTermEntry();
%>

<portlet:actionURL name="/commerce_term_entry/edit_commerce_term_entry_external_reference_code" var="editCommerceTermEntryExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceTermEntryExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceTermEntryId" type="hidden" value="<%= commerceTermEntry.getCommerceTermEntryId() %>" />

		<aui:model-context bean="<%= commerceTermEntry %>" model="<%= CommerceTermEntry.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= commerceTermEntry.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>