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

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="tags" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= assetTagsSelectorDisplayContext.isShowTagsSearch() %>">
		<aui:nav-bar-search>
			<aui:form action="<%= assetTagsSelectorDisplayContext.getPortletURL() %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= assetTagsSelectorDisplayContext.isDisabledTagsManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="tags"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= assetTagsSelectorDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= assetTagsSelectorDisplayContext.getOrderByCol() %>"
			orderByType="<%= assetTagsSelectorDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= assetTagsSelectorDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= assetTagsSelectorDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= assetTagsSelectorDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="tags"
		searchContainer="<%= assetTagsSelectorDisplayContext.getTagsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetTag"
			keyProperty="name"
			modelVar="tag"
		>
			<liferay-ui:search-container-column-text
				cssClass="content-column title-column name-column"
				name="name"
				truncate="<%= true %>"
				value="<%= tag.getName() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= assetTagsSelectorDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />tags');

	searchContainer.on(
		'rowToggled',
		function(event) {
			var data = '';

			var selectedItems = event.elements.allSelectedElements;

			if (selectedItems.size() > 0) {
				data = selectedItems.attr('value').join(',');
			}

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(assetTagsSelectorDisplayContext.getEventName()) %>',
				{
					data: {
						items: data
					}
				}
			);
		}
	);
</aui:script>