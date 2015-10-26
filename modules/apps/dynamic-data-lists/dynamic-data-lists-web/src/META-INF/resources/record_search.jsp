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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

DisplayTerms displayTerms = searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_dynamic_data_lists_record_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input name="<%= DisplayTerms.KEYWORDS %>" size="30" value="<%= displayTerms.getKeywords() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>