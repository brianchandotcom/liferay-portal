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
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

COREntry corEntry = corEntryDisplayContext.getCOREntry();
%>

<portlet:actionURL name="/cor_entry/edit_cor_entry_external_reference_code" var="editCOREntryExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCOREntryExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="corEntryId" type="hidden" value="<%= corEntry.getCOREntryId() %>" />

		<aui:model-context bean="<%= corEntry %>" model="<%= COREntry.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= corEntry.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>