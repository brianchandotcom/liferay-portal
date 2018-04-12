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
CommerceTaxMethodsDisplayContext commerceTaxMethodsDisplayContext = (CommerceTaxMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceTaxMethod> commerceTaxMethodSearchContainer = commerceTaxMethodsDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	searchContainerId="commerceTaxMethods"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
			portletURL="<%= commerceTaxMethodsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceTaxMethodsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceTaxMethods"
		searchContainer="<%= commerceTaxMethodSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceTaxMethod"
			keyProperty="commerceTaxMethodId"
			modelVar="commerceTaxMethod"
		>

			<%
			PortletURL rowURL = renderResponse.createActionURL();

			rowURL.setParameter(Constants.CMD, Constants.EDIT);
			rowURL.setParameter(ActionRequest.ACTION_NAME, "editCommerceTaxMethod");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceTaxMethodId", String.valueOf(commerceTaxMethod.getCommerceTaxMethodId()));
			rowURL.setParameter("engineKey", commerceTaxMethod.getEngineKey());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowURL %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="description"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="active"
				value='<%= LanguageUtil.get(request, commerceTaxMethod.isActive() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/tax_method_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>