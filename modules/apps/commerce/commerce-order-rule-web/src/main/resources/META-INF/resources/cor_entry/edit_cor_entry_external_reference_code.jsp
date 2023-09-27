<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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