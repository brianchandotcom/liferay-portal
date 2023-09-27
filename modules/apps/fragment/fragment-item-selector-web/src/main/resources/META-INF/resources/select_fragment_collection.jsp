<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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