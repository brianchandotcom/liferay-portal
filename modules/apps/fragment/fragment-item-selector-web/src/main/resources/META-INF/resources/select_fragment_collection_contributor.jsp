<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DefaultFragmentEntriesDisplayContext defaultFragmentEntriesDisplayContext = new DefaultFragmentEntriesDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(defaultFragmentEntriesDisplayContext.getFragmentCollectionKey()) %>">
		<liferay-util:include page="/select_contributed_fragment_entry.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new FragmentCollectionContributorsItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, defaultFragmentEntriesDisplayContext.getFragmentCollectionContributorsSearchContainer()) %>"
		/>

		<clay:container-fluid>
			<liferay-site-navigation:breadcrumb
				breadcrumbEntries="<%= defaultFragmentEntriesDisplayContext.getBreadcrumbEntries() %>"
			/>

			<liferay-ui:search-container
				searchContainer="<%= defaultFragmentEntriesDisplayContext.getFragmentCollectionContributorsSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.fragment.contributor.FragmentCollectionContributor "
					modelVar="fragmentCollectionContributor"
				>
					<liferay-ui:search-container-column-text>
						<clay:horizontal-card
							horizontalCard="<%= new FragmentCollectionContributorHorizontalCard(fragmentCollectionContributor, currentURLObj) %>"
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