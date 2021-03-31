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
SelectFragmentCollectionDisplayContext selectFragmentCollectionDisplayContext = new SelectFragmentCollectionDisplayContext(request, renderRequest, renderResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new SelectFragmentCollectionManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, selectFragmentCollectionDisplayContext.getFragmentCollectionsSearchContainer()) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="selectFragmentCollectionFm">
	<liferay-ui:search-container
		searchContainer="<%= selectFragmentCollectionDisplayContext.getFragmentCollectionsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentCollection"
			keyProperty="fragmentCollectionId"
			modelVar="fragmentCollection"
		>
			<liferay-ui:search-container-column-text>
				<clay:navigation-card
					navigationCard="<%= new FragmentCollectionNavigationCard(fragmentCollection) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>