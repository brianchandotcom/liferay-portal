<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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