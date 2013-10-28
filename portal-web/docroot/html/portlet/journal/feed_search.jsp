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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
FeedSearch searchContainer = (FeedSearch)request.getAttribute("liferay-ui:search:searchContainer");

FeedDisplayTerms displayTerms = (FeedDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	autoFocus="<%= (windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP)) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_journal_feed_search"
>
	<aui:fieldset>
		<aui:input label="id" name="<%= displayTerms.FEED_ID %>" size="20" type="text" value="<%= displayTerms.getFeedId() %>" />

		<aui:input name="<%= displayTerms.NAME %>" size="20" type="text" value="<%= displayTerms.getName() %>" />

		<aui:input name="<%= displayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>

<%
boolean showAddFeedButtonButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FEED);
boolean showPermissionsButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
%>

<c:if test="<%= showAddFeedButtonButton || showPermissionsButton %>">
	<aui:button-row>
		<c:if test="<%= showAddFeedButtonButton %>">
			<aui:button onClick='<%= renderResponse.getNamespace() + "addFeed();" %>' value="add-feed" />
		</c:if>

		<c:if test="<%= showPermissionsButton %>">
			<liferay-security:permissionsURL
				modelResource="com.liferay.portlet.journal"
				modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
				resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
				var="permissionsURL"
				windowState="<%= LiferayWindowState.POP_UP.toString() %>"
			/>

			<aui:button href="<%= permissionsURL %>" useDialog="<%= true %>" value="permissions" />
		</c:if>
	</aui:button-row>
</c:if>

<aui:script>
	function <portlet:namespace />addFeed() {
		var url = '<portlet:renderURL><portlet:param name="struts_action" value="/journal/edit_feed" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';

		if (document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.ADVANCED_SEARCH %>.value == 'false') {
			url += '&<portlet:namespace /><%= displayTerms.NAME %>=' + document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>.value;

			submitForm(document.hrefFm, url);
		}
		else {
			document.<portlet:namespace />fm.method = 'post';

			submitForm(document.<portlet:namespace />fm, url);
		}
	}
</aui:script>