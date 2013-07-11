<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
AssetSearch searchContainer = (AssetSearch)request.getAttribute("liferay-ui:search:searchContainer");

AssetDisplayTerms displayTerms = (AssetDisplayTerms)searchContainer.getDisplayTerms();

long[] selectedGroupIds = StringUtil.split(ParamUtil.getString(request, "selectedGroupIds"), 0L);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_asset_search"
>
	<aui:fieldset>
		<aui:input name="<%= displayTerms.TITLE %>" size="20" type="text" value="<%= displayTerms.getTitle() %>" />

		<aui:input name="<%= displayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />

		<aui:input name="<%= displayTerms.USER_NAME %>" size="20" type="text" value="<%= displayTerms.getUserName() %>" />

		<aui:select label="my-sites" name="<%= displayTerms.GROUP_ID %>">

			<%
			for (long groupId : selectedGroupIds) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);
			%>

				<aui:option label="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>" selected="<%= displayTerms.getGroupId() == groupId %>" value="<%= groupId %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>
</liferay-ui:search-toggle>