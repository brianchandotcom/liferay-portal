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
FragmentEntriesDisplayContext fragmentEntriesDisplayContext = (FragmentEntriesDisplayContext)request.getAttribute(FragmentEntriesDisplayContext.class.getName());
%>

<c:choose>
	<c:when test="<%= fragmentEntriesDisplayContext.getFragmentCollectionId() > 0 %>">
		<liferay-util:include page="/select_fragment_entry.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new FragmentCollectionsItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fragmentEntriesDisplayContext.getFragmentCollectionsSearchContainer()) %>"
		/>

		<clay:container-fluid>
			<liferay-site-navigation:breadcrumb
				breadcrumbEntries="<%= fragmentEntriesDisplayContext.getBreadcrumbEntries() %>"
			/>

			<liferay-ui:search-container
				searchContainer="<%= fragmentEntriesDisplayContext.getFragmentCollectionsSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.fragment.model.FragmentCollection"
					modelVar="fragmentCollection"
				>
					<liferay-ui:search-container-column-text>
						<clay:horizontal-card
							horizontalCard="<%= new FragmentCollectionHorizontalCard(fragmentCollection, currentURLObj) %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="icon"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</clay:container-fluid>
	</c:otherwise>
</c:choose>