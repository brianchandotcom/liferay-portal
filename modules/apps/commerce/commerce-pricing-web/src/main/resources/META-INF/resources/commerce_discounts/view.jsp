<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceDiscountDisplayContext.getPortletURL();
%>

<div class="pt-4">
	<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteDiscounts" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-pricing/v2.0/discounts?sort=modifiedDate:desc"
			creationMenu="<%= commerceDiscountDisplayContext.getDiscountCreationMenu() %>"
			fdsActionDropdownItems="<%= commerceDiscountDisplayContext.getDiscountFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.DISCOUNTS %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>