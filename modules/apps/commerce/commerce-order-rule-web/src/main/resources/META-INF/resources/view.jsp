<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = corEntryDisplayContext.getPortletURL();
%>

<div class="pt-4">
	<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteCOREntries" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-order/v1.0/order-rules"
			creationMenu="<%= corEntryDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= corEntryDisplayContext.getCOREntryFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= COREntryFDSNames.COR_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>