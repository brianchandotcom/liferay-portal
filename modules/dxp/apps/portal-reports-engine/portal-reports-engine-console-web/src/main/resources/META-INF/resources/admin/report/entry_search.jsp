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
EntryDisplayTerms displayTerms = new EntryDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_reports_entry_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input inlineField="<%= true %>" name="<%= EntryDisplayTerms.DEFINITION_NAME %>" size="20" value="<%= displayTerms.getDefinitionName() %>" />

		<aui:input inlineField="<%= true %>" label="requested-by" name="<%= EntryDisplayTerms.USERNAME %>" size="20" value="<%= displayTerms.getUserName() %>" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:field-wrapper inlineField="<%= true %>" label="start-date">
			<liferay-ui:input-date
				dayParam="<%= EntryDisplayTerms.START_DATE_DAY %>"
				dayValue="<%= displayTerms.getStartDateDay() %>"
				monthParam="<%= EntryDisplayTerms.START_DATE_MONTH %>"
				monthValue="<%= displayTerms.getStartDateMonth() %>"
				yearParam="<%= EntryDisplayTerms.START_DATE_YEAR %>"
				yearValue="<%= displayTerms.getStartDateYear() %>"
			/>
		</aui:field-wrapper>

		<aui:field-wrapper inlineField="<%= true %>" label="end-date">
			<liferay-ui:input-date
				dayParam="<%= EntryDisplayTerms.END_DATE_DAY %>"
				dayValue="<%= displayTerms.getEndDateDay() %>"
				monthParam="<%= EntryDisplayTerms.END_DATE_MONTH %>"
				monthValue="<%= displayTerms.getEndDateMonth() %>"
				yearParam="<%= EntryDisplayTerms.END_DATE_YEAR %>"
				yearValue="<%= displayTerms.getEndDateYear() %>"
			/>
		</aui:field-wrapper>
	</aui:fieldset>
</liferay-ui:search-toggle>