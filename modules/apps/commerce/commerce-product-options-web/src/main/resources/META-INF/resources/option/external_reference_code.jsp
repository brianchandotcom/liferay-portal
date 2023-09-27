<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPOption cpOption = cpOptionDisplayContext.getCPOption();
%>

<portlet:actionURL name="/cp_options/edit_cp_option_external_reference_code" var="editCPOptionExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCPOptionExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpOptionId" type="hidden" value="<%= cpOption.getCPOptionId() %>" />

		<aui:model-context bean="<%= cpOption %>" model="<%= CPOption.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= cpOption.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>