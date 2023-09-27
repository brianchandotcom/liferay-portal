<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_price_list_external_reference_code" var="editCommercePriceListExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommercePriceListExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceList.getCommercePriceListId() %>" />

		<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= commercePriceList.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>