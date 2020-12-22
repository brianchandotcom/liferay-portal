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
UserItemSelectorViewDisplayContext userItemSelectorViewDisplayContext = (UserItemSelectorViewDisplayContext)request.getAttribute(UserItemSelectorViewConstants.USER_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

String displayStyle = userItemSelectorViewDisplayContext.getDisplayStyle();
%>

<clay:management-toolbar-v2
	displayContext="<%= new UserItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, userItemSelectorViewDisplayContext) %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "userSelectorWrapper" %>'
>
	<liferay-ui:search-container
		id="<%= userItemSelectorViewDisplayContext.getSearchContainerId() %>"
		searchContainer="<%= userItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			cssClass="user-row"
			keyProperty="userId"
			modelVar="user"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"id", user.getUserId()
				).put(
					"name", user.getFullName()
				).build());
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-ui:user-portrait
							userId="<%= user.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5 class="table-title"><%= user.getFullName() %></h5>

						<h6 class="text-default">
							<span><%= user.getScreenName() %></span>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("card-page-item card-page-item-asset selectable");
					%>

					<liferay-ui:search-container-column-text>
						<clay:user-card
							userCard="<%= new SelectUserUserCard(user, renderRequest, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-title"
						name="name"
						value="<%= HtmlUtil.escape(user.getFullName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="screen-name"
						property="screenName"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= userItemSelectorViewDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= userItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace /><%= HtmlUtil.escape(userItemSelectorViewDisplayContext.getSearchContainerId()) %>'
	);

	searchContainer.on('rowToggled', function (event) {
		var allSelectedElements = event.elements.allSelectedElements;
		var selectedData = [];

		allSelectedElements.each(function () {
			<c:choose>
				<c:when test='<%= displayStyle.equals("list") %>'>
					var row = this.ancestor('tr');
				</c:when>
				<c:otherwise>
					var row = this.ancestor('li');
				</c:otherwise>
			</c:choose>

			var data = row.getDOM().dataset;

			selectedData.push({
				id: data.id,
				name: data.name,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(userItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: selectedData,
			}
		);
	});
</aui:script>