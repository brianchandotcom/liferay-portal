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
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");

List<Group> selectedGroups = GroupLocalServiceUtil.getGroups(assetPublisherDisplayContext.getGroupIds());
%>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	iteratorURL="<%= configurationRenderURL %>"
	total="<%= selectedGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= selectedGroups %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		keyProperty="groupId"
		modelVar="group"
	>
		<liferay-ui:search-container-column-text
			name="name"
			truncate="<%= true %>"
			value="<%= group.getScopeDescriptiveName(themeDisplay) %>"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="deleteURL">
				<portlet:param name="<%= Constants.CMD %>" value="remove-scope" />
				<portlet:param name="redirect" value="<%= configurationRenderURL.toString() %>" />
				<portlet:param name="scopeId" value="<%= assetPublisherHelper.getScopeId(group, scopeGroupId) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				icon="times-circle"
				markupView="lexicon"
				message="delete"
				url="<%= deleteURL %>"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" varImpl="addScopeURL">
	<portlet:param name="<%= Constants.CMD %>" value="add-scope" />
	<portlet:param name="redirect" value="<%= configurationRenderURL.toString() %>" />
</liferay-portlet:actionURL>

<clay:dropdown-menu
	displayType="secondary"
	dropdownItems="<%= assetPublisherDisplayContext.getScopeDropdownItems(addScopeURL) %>"
	label="select"
	propsTransformer="js/ScopeActionDropdownPropsTransformer"
/>