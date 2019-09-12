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
LayoutPageTemplateCollectionsDisplayContext layoutPageTemplateCollectionsDisplayContext = new LayoutPageTemplateCollectionsDisplayContext(renderRequest, renderResponse, request);
%>

<clay:management-toolbar
	displayContext="<%= new LayoutPageTemplateCollectionsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutPageTemplateCollectionsDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="layoutPageTemplateCollections"
		searchContainer="<%= layoutPageTemplateCollectionsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateCollection"
			keyProperty="layoutPageTemplateCollectionId"
			modelVar="layoutPageTemplateCollection"
		>
			<liferay-ui:search-container-column-text
				name="name"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				property="createDate"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />layoutPageTemplateCollections'
	);

	searchContainer.on('rowToggled', function(event) {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(layoutPageTemplateCollectionsDisplayContext.getEventName()) %>',
			{
				data: event.elements.allSelectedElements.getDOMNodes()
			}
		);
	});
</aui:script>