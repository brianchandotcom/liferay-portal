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
DefinitionDisplayTerms displayTerms = new DefinitionDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_reports_definition_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input name="<%= DefinitionDisplayTerms.DEFINITION_NAME %>" size="20" value="<%= displayTerms.getDefinitionName() %>" />

		<aui:select label="data-source-name" name="<%= DefinitionDisplayTerms.SOURCE_ID %>">
			<aui:option label="all" />
			<aui:option label="<%= ReportDataSourceType.PORTAL %>" value="0" />

			<%
			List<Source> sources = SourceServiceUtil.getSources(themeDisplay.getSiteGroupId(), null, null, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (Source source : sources) {
			%>

				<aui:option label="<%= HtmlUtil.escape(source.getName(locale)) %>" value="<%= source.getSourceId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="<%= DefinitionDisplayTerms.DESCRIPTION %>" size="20" value="<%= displayTerms.getDescription() %>" />

		<aui:input label="template" name="<%= DefinitionDisplayTerms.REPORT_NAME %>" size="20" value="<%= displayTerms.getReportName() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>