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
ContributedFragmentManagementToolbarDisplayContext contributedFragmentManagementToolbarDisplayContext = new ContributedFragmentManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fragmentDisplayContext);
%>

<clay:management-toolbar
	additionalProps="<%= contributedFragmentManagementToolbarDisplayContext.getComponentContext() %>"
	managementToolbarDisplayContext="<%= contributedFragmentManagementToolbarDisplayContext %>"
	propsTransformer="js/ViewContributedFragmentEntriesManagementToolbarPropsTransformer"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentDisplayContext.getContributedEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>
			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= object instanceof FragmentComposition %>">
						<clay:vertical-card
							verticalCard="<%= new ContributedFragmentCompositionVerticalCard((FragmentComposition)object, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</c:when>
					<c:otherwise>
						<clay:vertical-card
							verticalCard="<%= new ContributedFragmentEntryVerticalCard((FragmentEntry)object, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:form name="fragmentEntryFm">
	<aui:input name="contributedEntryKeys" type="hidden" />
	<aui:input name="fragmentCollectionId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= FragmentWebKeys.FRAGMENT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/FragmentEntryDropdownDefaultEventHandler.es"
/>