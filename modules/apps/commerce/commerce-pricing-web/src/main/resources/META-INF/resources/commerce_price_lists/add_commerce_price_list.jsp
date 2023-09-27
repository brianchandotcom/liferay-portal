<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceCatalog> commerceCatalogs = commercePriceListDisplayContext.getCommerceCatalogs();
CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();
%>

<portlet:actionURL name="/commerce_price_list/edit_commerce_price_list" var="editCommercePriceListActionURL" />

<commerce-ui:modal-content
	title="<%= commercePriceListDisplayContext.getModalContextTitle(portletName) %>"
>
	<div class="col-12 lfr-form-content">
		<aui:form action="<%= editCommercePriceListActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="type" type="hidden" value="<%= commercePriceListDisplayContext.getCommercePriceListType(portletName) %>" />

			<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

			<liferay-ui:error exception="<%= CommercePriceListCurrencyException.class %>" message="please-select-a-valid-store-currency" />
			<liferay-ui:error exception="<%= CommercePriceListParentPriceListGroupIdException.class %>" message="please-select-a-valid-parent-price-list-for-the-selected-catalog" />
			<liferay-ui:error exception="<%= NoSuchCatalogException.class %>" message="please-select-a-valid-catalog" />

			<aui:input name="name" required="<%= true %>" />

			<aui:select disabled="<%= commercePriceList != null %>" label="catalog" name="commerceCatalogGroupId" required="<%= true %>" showEmptyOption="<%= true %>">

				<%
				for (CommerceCatalog commerceCatalog : commerceCatalogs) {
				%>

					<aui:option label="<%= commerceCatalog.getName() %>" selected="<%= (commercePriceList == null) ? (commerceCatalogs.size() == 1) : commercePriceListDisplayContext.isSelectedCatalog(commerceCatalog) %>" value="<%= commerceCatalog.getGroupId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select label="currency" name="commerceCurrencyId" required="<%= true %>" showEmptyOption="<%= true %>">

				<%
				for (CommerceCurrency commerceCurrency : commercePriceListDisplayContext.getCommerceCurrencies()) {
				%>

					<aui:option label="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" selected="<%= (commercePriceList != null) && (commercePriceList.getCommerceCurrencyId() == commerceCurrency.getCommerceCurrencyId()) %>" value="<%= commerceCurrency.getCommerceCurrencyId() %>" />

				<%
				}
				%>

			</aui:select>
		</aui:form>
	</div>
</commerce-ui:modal-content>