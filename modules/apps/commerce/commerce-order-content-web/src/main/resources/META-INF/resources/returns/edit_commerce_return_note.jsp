<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/returns/init.jsp" %>

<%
CommerceReturnContentDisplayContext commerceReturnContentDisplayContext = (CommerceReturnContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceReturn commerceReturn = commerceReturnContentDisplayContext.getCommerceReturn();
%>

<commerce-ui:modal-content>
	<aui:form cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceReturnId" type="hidden" value="<%= commerceReturnContentDisplayContext.getCommerceReturnId() %>" />

		<aui:input name="note" type="text" value="<%= (commerceReturn == null) ? StringPool.BLANK : commerceReturn.getNote() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"commerceReturnId", commerceReturnContentDisplayContext.getCommerceReturnId()
		).put(
			"fieldName", "note"
		).put(
			"redirectURL", ParamUtil.getString(request, "redirect", currentURL)
		).build()
	%>'
	module="{editCommerceReturn} from commerce-order-content-web"
/>