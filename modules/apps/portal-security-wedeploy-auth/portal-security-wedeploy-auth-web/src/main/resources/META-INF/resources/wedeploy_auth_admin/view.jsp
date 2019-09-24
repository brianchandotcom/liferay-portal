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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/wedeploy_auth_admin/view");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(portletURL);
						navigationItem.setLabel(LanguageUtil.get(request, "wedeploy-app"));
					});
			}
		}
	%>'
/>

<div class="container-fluid container-fluid-max-xl container-view">
	<liferay-ui:search-container
		emptyResultsMessage="no-wedeploy-apps-were-found"
		id="weDeployAuthApps"
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results>

			<%
			total = WeDeployAuthAppLocalServiceUtil.getWeDeployAuthAppsCount();

			searchContainer.setTotal(total);

			results = WeDeployAuthAppLocalServiceUtil.getWeDeployAuthApps(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp"
			keyProperty="weDeployAuthAppId"
			modelVar="weDeployAuthApp"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="name"
				orderable="<%= false %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="redirect-uri"
				orderable="<%= false %>"
				property="redirectURI"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="client-id"
				orderable="<%= false %>"
				property="clientId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="client-secret"
				orderable="<%= false %>"
				property="clientSecret"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-ws-nowrap"
				name="modified-date"
				orderable="<%= false %>"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-jsp
				path="/wedeploy_auth_admin/wedeploy_auth_app_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<c:if test="<%= WeDeployAuthPermission.contains(permissionChecker, WeDeployAuthActionKeys.ADD_APP) %>">
		<portlet:renderURL var="editWeDeployAuthAppURL">
			<portlet:param name="mvcRenderCommandName" value="/wedeploy_auth_admin/edit_wedeploy_auth_app" />
		</portlet:renderURL>

		<liferay-frontend:add-menu>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-wedeploy-app") %>'
				url="<%= editWeDeployAuthAppURL %>"
			/>
		</liferay-frontend:add-menu>
	</c:if>
</div>