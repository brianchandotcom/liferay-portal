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

<%
FeedDisplayTerms displayTerms = (FeedDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_journal_feed_search"
>
	<aui:fieldset>
		<aui:input inlineField="<%= true %>" label="id" name="<%= FeedDisplayTerms.FEED_ID %>" size="20" type="text" value="<%= displayTerms.getFeedId() %>" />

		<aui:input inlineField="<%= true %>" name="<%= FeedDisplayTerms.NAME %>" size="20" type="text" value="<%= displayTerms.getName() %>" />

		<aui:input inlineField="<%= true %>" name="<%= FeedDisplayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>