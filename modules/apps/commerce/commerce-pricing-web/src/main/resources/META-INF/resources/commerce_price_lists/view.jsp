<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="pt-4">
	<aui:form action="<%= currentURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deletePriceLists" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="<%= commercePriceListDisplayContext.getPriceListsAPIURL(portletName) %>"
			creationMenu="<%= commercePriceListDisplayContext.getPriceListCreationMenu(portletName) %>"
			fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceListFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.PRICE_LISTS %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>