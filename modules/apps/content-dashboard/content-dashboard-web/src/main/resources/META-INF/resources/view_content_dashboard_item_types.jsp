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
ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext contentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext = (ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext)request.getAttribute(ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext.class.getName());

ContentDashboardItemTypeItemSelectorViewDisplayContext contentDashboardItemTypeItemSelectorViewDisplayContext = (ContentDashboardItemTypeItemSelectorViewDisplayContext)request.getAttribute(ContentDashboardItemTypeItemSelectorViewDisplayContext.class.getName());
%>

<clay:management-toolbar-v2
	displayContext="<%= contentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		id="contentDashboardItemTypes"
		searchContainer="<%= contentDashboardItemTypeItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType"
			modelVar="contentDashboardItemType"
		>

			<%
			InfoItemReference infoItemReference = contentDashboardItemType.getInfoItemReference();

			row.setPrimaryKey(
				HtmlUtil.toInputSafe(
					JSONUtil.put(
						"className", infoItemReference.getClassName()
					).put(
						"classPK", infoItemReference.getClassPK()
					).toJSONString()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="name"
				value="<%= contentDashboardItemType.getFullLabel(locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />contentDashboardItemTypes'
	);

	searchContainer.on('rowToggled', function (event) {
		var allSelectedElements = event.elements.allSelectedElements;
		var arr = [];

		allSelectedElements.each(function () {
			var payload = JSON.parse(Liferay.Util.unescape(this.getDOM().value));

			arr.push({
				classPK: payload.classPK,
				className: payload.className,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(contentDashboardItemTypeItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: arr,
			}
		);
	});
</aui:script>