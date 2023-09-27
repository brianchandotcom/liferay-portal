<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTermEntryDisplayContext commerceTermEntryDisplayContext = (CommerceTermEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="pt-4">
	<aui:form action="<%= commerceTermEntryDisplayContext.getPortletURL() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= String.valueOf(commerceTermEntryDisplayContext.getPortletURL()) %>" />
		<aui:input name="deleteCommerceTermEntries" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-order/v1.0/terms?sort=priority:asc"
			creationMenu="<%= commerceTermEntryDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= commerceTermEntryDisplayContext.getCommerceTermEntryFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommerceTermEntryFDSNames.TERM_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>