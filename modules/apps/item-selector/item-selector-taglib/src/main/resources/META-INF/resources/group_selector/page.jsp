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

<%@ include file="/group_selector/init.jsp" %>

<%
GroupSelectorDisplayContext groupSelectorDisplayContext = new GroupSelectorDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<%
List<Group> groups = (List<Group>)request.getAttribute("liferay-item-selector:group-selector:groups");
int groupsCount = GetterUtil.getInteger(request.getAttribute("liferay-item-selector:group-selector:groupsCount"));

boolean repositories = ParamUtil.getBoolean(request, "repositories");

SearchContainer searchContainer = new GroupSearch(liferayPortletRequest, groupSelectorDisplayContext.getIteratorURL());
%>

<div class="container-fluid-1280">
	<div class="btn-group" role="group">
		<a class="btn btn-secondary <%= !repositories ? "active" : StringPool.BLANK %>" href="<%= groupSelectorDisplayContext.getSitesURL() %>"><liferay-ui:message key="sites" /></a>
		<a class="btn btn-secondary <%= repositories? "active" : StringPool.BLANK %>" href="<%= groupSelectorDisplayContext.getRepositoriesURL() %>"><liferay-ui:message key="repositories" /></a>
	</div>
</div>

<div class="container-fluid-1280 lfr-item-viewer">
	<liferay-ui:search-container
		searchContainer="<%= searchContainer %>"
		total="<%= groupsCount %>"
		var="listSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= groups %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			modelVar="curGroup"
		>

			<%
			PortletURL viewGroupURL = groupSelectorDisplayContext.getViewGroupURL(curGroup);

			row.setCssClass("entry-card lfr-asset-item");
			%>

			<liferay-ui:search-container-column-text
				colspan="<%= 3 %>"
			>
				<liferay-frontend:horizontal-card
					cardCssClass="card-interactive card-interactive-primary"
					resultRow="<%= row %>"
					text="<%= curGroup.getDescriptiveName(locale) %>"
					url="<%= viewGroupURL.toString() %>"
				>
					<liferay-frontend:horizontal-card-col>
						<liferay-frontend:horizontal-card-icon
							icon='<%= repositories? "repository" : "folder" %>'
						/>
					</liferay-frontend:horizontal-card-col>
				</liferay-frontend:horizontal-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</div>