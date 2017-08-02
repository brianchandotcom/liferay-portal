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
CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext = (CommerceCurrenciesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceCurrency> commerceCurrencySearchContainer = commerceCurrenciesDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	searchContainerId="commerceCurrencies"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
			portletURL="<%= commerceCurrenciesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceCurrenciesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceCurrenciesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceCurrenciesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<liferay-ui:search-container
	id="commerceCurrencies"
	searchContainer="<%= commerceCurrencySearchContainer %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.commerce.currency.model.CommerceCurrency"
		keyProperty="commerceCurrencyId"
		modelVar="commerceCurrency"
	>

		<%
		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("mvcRenderCommandName", "editCommerceCurrency");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("commerceCurrencyId", String.valueOf(commerceCurrency.getCommerceCurrencyId()));
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			href="<%= rowURL %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			property="code"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="exchange-rate"
			property="rate"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="primary"
			value='<%= LanguageUtil.get(request, commerceCurrency.isPrimary() ? "yes" : "no") %>'
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="active"
			value='<%= LanguageUtil.get(request, commerceCurrency.isActive() ? "yes" : "no") %>'
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			property="priority"
		/>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/currency_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" />
</liferay-ui:search-container>

<c:if test="<%= CommerceCurrencyPermission.contains(permissionChecker, scopeGroupId, CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES) %>">
	<portlet:renderURL var="addCommerceCurrencyURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceCurrency" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-currency") %>' url="<%= addCommerceCurrencyURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>