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
SourceDisplayTerms displayTerms = new SourceDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_reports_source_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input label="source-name" name="<%= SourceDisplayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

		<aui:input label="jdbc-url" name="<%= SourceDisplayTerms.DRIVER_URL %>" size="20" value="<%= displayTerms.getDriverUrl() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>