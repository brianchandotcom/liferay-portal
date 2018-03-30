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
UADExportProcessDisplayContext uadExportProcessDisplayContext = new UADExportProcessDisplayContext(request, renderResponse);

portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "export-personal-data")));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "processes"));
					});
			}
		}
	%>'
/>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "in-progress", "successful", "failed"} %>'
			portletURL="<%= PortletURLUtil.clone(uadExportProcessDisplayContext.getPortletURL(), renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= uadExportProcessDisplayContext.getOrderByCol() %>"
			orderByType="<%= uadExportProcessDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "name"} %>'
			portletURL="<%= PortletURLUtil.clone(uadExportProcessDisplayContext.getPortletURL(), renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<portlet:renderURL var="addExportProcessesURL">
				<portlet:param name="mvcRenderCommandName" value="/add_uad_export_processes" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-export-processes") %>'
				url="<%= addExportProcessesURL.toString() %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280">
	<liferay-ui:search-container
		searchContainer="<%= uadExportProcessDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.backgroundtask.BackgroundTask"
			keyProperty="backgroundTaskId"
			modelVar="backgroundTask"
		>
			<liferay-ui:search-container-column-text
				cssClass="autofit-col-expand"
			>
				<div>
					<h5>
						<liferay-ui:message key="<%= backgroundTask.getName() %>" />
					</h5>

					<clay:label
						label="<%= LanguageUtil.get(request, backgroundTask.getStatusLabel()) %>"
						style="<%= UADExportProcessUtil.getStatusStyle(backgroundTask.getStatus()) %>"
					/>
				</div>
			</liferay-ui:search-container-column-text>

			<%
			Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy.MM.dd - hh:mm a", locale, themeDisplay.getTimeZone());
			%>

			<liferay-ui:search-container-column-text
				cssClass="autofit-col-expand"
			>
				<%= LanguageUtil.get(request, "create-date") + StringPool.COLON + dateFormat.format(backgroundTask.getCreateDate()) %>
			</liferay-ui:search-container-column-text>

			<c:if test="<%= backgroundTask.isCompleted() %>">
				<liferay-ui:search-container-column-text
					cssClass="autofit-col-expand"
				>
					<%= LanguageUtil.get(request, "completion-date") + StringPool.COLON + dateFormat.format(backgroundTask.getCompletionDate()) %>
				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/export_process_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			resultRowSplitter="<%= new UADExportProcessResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</aui:form>