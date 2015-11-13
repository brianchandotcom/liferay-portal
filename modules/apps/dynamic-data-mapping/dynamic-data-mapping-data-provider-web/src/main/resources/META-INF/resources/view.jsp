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
PortletURL portletURL = ddmDataProviderDisplayContext.getPortletURL();

portletURL.setParameter("displayStyle", "descriptive");

DDMDataProviderSearch ddmDataProviderSearch = new DDMDataProviderSearch(renderRequest, portletURL);

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

OrderByComparator<DDMDataProviderInstance> orderByComparator = DDMDataProviderPortletUtil.getDDMDataProviderOrderByComparator(orderByCol, orderByType);

ddmDataProviderSearch.setOrderByCol(orderByCol);
ddmDataProviderSearch.setOrderByComparator(orderByComparator);
ddmDataProviderSearch.setOrderByType(orderByType);
%>

<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

		<liferay-ui:search-container
			emptyResultsMessage="no-data-providers-were-found"
			id="searchContainer"
			searchContainer="<%= ddmDataProviderSearch %>"
		>

			<%
			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>

			<liferay-ui:search-container-results
				results="<%= ddmDataProviderDisplayContext.getSearchContainerResults(searchContainer) %>"
				total="<%= ddmDataProviderDisplayContext.getSearchContainerTotal(searchContainer) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance"
				cssClass="entry-display-style"
				escapedModel="<%= true %>"
				keyProperty="dataProviderInstanceId"
				modelVar="dataProviderInstance"
			>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/edit_data_provider.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(dataProviderInstance.getDataProviderInstanceId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-image
					src="<%= ddmDataProviderDisplayContext.getUserPortraitURL(dataProviderInstance.getUserId()) %>"
					toggleRowChecker="<%= true %>"
				/>

				<liferay-ui:search-container-column-jsp
					colspan="2"
					href="<%= rowURL %>"
					path="/data_provider_descriptive.jsp"
				/>

				<liferay-ui:search-container-column-jsp
					path="/data_provider_action.jsp"
				/>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= ddmDataProviderDisplayContext.isShowAddDataProviderButton() %>">
	<liferay-frontend:add-menu>

		<%
		Set<String> dataProviderTypes = ddmDataProviderDisplayContext.getDataProviderTypes();

		for (String dataProviderType : dataProviderTypes) {
		%>

			<portlet:renderURL var="addDataProviderURL">
				<portlet:param name="mvcPath" value="/edit_data_provider.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="dataProviderType" value="<%= dataProviderType %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu-item title="<%= LanguageUtil.get(request, dataProviderType) %>" url="<%= addDataProviderURL.toString() %>" />

		<%
		}
		%>

	</liferay-frontend:add-menu>
</c:if>