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
GroupFragmentEntryLinkDisplayContext groupFragmentEntryLinkDisplayContext = new GroupFragmentEntryLinkDisplayContext(renderRequest, renderResponse);

FragmentEntry fragmentEntry = groupFragmentEntryLinkDisplayContext.getFragmentEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(groupFragmentEntryLinkDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.format(request, "usages-and-propagation-x", fragmentEntry.getName()));
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="sheet">

		<%
		GroupFragmentEntryUsageManagementToolbarDisplayContext groupFragmentEntryUsageManagementToolbarDisplayContext = new GroupFragmentEntryUsageManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, groupFragmentEntryLinkDisplayContext.getSearchContainer());
		%>

		<clay:row>
			<clay:col
				lg="12"
			>
				<clay:management-toolbar
					displayContext="<%= groupFragmentEntryUsageManagementToolbarDisplayContext %>"
				/>

				<portlet:actionURL name="/fragment/propagate_group_fragment_entry_changes" var="propagateGroupFragmentEntryChangesURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="fragmentEntryId" value="<%= String.valueOf(fragmentEntry.getFragmentEntryId()) %>" />
				</portlet:actionURL>

				<aui:form action="<%= propagateGroupFragmentEntryChangesURL %>" name="fm">
					<liferay-ui:search-container
						searchContainer="<%= groupFragmentEntryLinkDisplayContext.getSearchContainer() %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.portal.kernel.model.Group"
							keyProperty="groupId"
							modelVar="group"
						>
							<liferay-ui:search-container-column-text
								name="name"
								value="<%= group.getDescriptiveName(locale) %>"
							/>

							<liferay-ui:search-container-column-text
								name="usages"
								translate="<%= true %>"
								value="<%= String.valueOf(groupFragmentEntryLinkDisplayContext.getFragmentGroupUsageCount(group)) %>"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="list"
							markupView="lexicon"
							paginate="<%= false %>"
							searchResultCssClass="show-quick-actions-on-hover table table-autofit"
						/>
					</liferay-ui:search-container>
				</aui:form>
			</clay:col>
		</clay:row>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= groupFragmentEntryUsageManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/FragmentEntryUsageManagementToolbarDefaultEventHandler.es"
/>