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

<clay:management-toolbar
	displayContext="<%= new UserItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, userItemSelectorViewDisplayContext) %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "userSelectorWrapper" %>'
>
	<liferay-ui:search-container
		id="users"
		searchContainer="<%= userItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			cssClass="user-row"
			keyProperty="userId"
			modelVar="user"
		>

			<%
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"id", user.getUserId()
			).put(
				"name", user.getFullName()
			).build();

			row.setData(data);
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
						<h5><%= user.getFullName() %></h5>

						<h6 class="text-default">
							<span><%= user.getScreenName() %></span>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item selectable");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:user-vertical-card
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							resultRow="<%= row %>"
							rowChecker="<%= userItemSelectorViewDisplayContext.getRowChecker() %>"
							subtitle="<%= user.getScreenName() %>"
							title="<%= user.getFullName() %>"
							userId="<%= user.getUserId() %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= HtmlUtil.escape(user.getFullName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="screen-name"
						property="screenName"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchContainer="<%= userItemSelectorViewDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />users');

	searchContainer.on('rowToggled', function (event) {
		var allSelectedElements = event.elements.allSelectedElements;
		var arr = [];

		allSelectedElements.each(function () {
			var row = this.ancestor('tr');

			var data = row.getDOM().dataset;

			arr.push({id: data.id, name: data.name});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(userItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: arr,
			}
		);
	});
</aui:script>