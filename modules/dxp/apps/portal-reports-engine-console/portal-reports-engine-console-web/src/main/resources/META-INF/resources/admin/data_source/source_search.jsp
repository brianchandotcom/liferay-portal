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