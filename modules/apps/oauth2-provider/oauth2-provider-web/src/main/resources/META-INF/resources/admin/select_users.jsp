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

<%@ include file="/admin/init.jsp" %>

<%
SelectUsersDisplayContext selectUsersDisplayContext = new SelectUsersDisplayContext(request, renderRequest, renderResponse);
%>

<clay:management-toolbar
	displayContext="<%= new SelectUsersManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, selectUsersDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280" name="selectUser">
	<liferay-ui:search-container
		id="users"
		searchContainer="<%= selectUsersDisplayContext.getUserSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="userRow"
			rowIdProperty="screenName"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="screen-name"
				orderable="<%= true %>"
			>

				<%
				Map<String, Object> data = new HashMap<>();

				data.put("screenname", userRow.getScreenName());
				data.put("userid", userRow.getUserId());
				%>

				<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
					<%= userRow.getScreenName() %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="name"
				orderable="<%= true %>"
				property="fullName"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectUsersDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectUser',
		'<%= HtmlUtil.escapeJS(selectUsersDisplayContext.getEventName()) %>'
	);
</aui:script>