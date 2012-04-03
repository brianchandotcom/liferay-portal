<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

DisplayTerms displayTerms = searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_dynamic_data_lists_record_search"
>
	<aui:fieldset>
		<aui:input name="<%= displayTerms.KEYWORDS %>" size="30" value="<%= displayTerms.getKeywords() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>

<%
boolean showAddRecordButton = GetterUtil.getBoolean(request.getAttribute("liferay-ui:search:showAddButton"));

long detailDDMTemplateId = ParamUtil.getLong(request, "detailDDMTemplateId");
%>

<c:if test="<%= showAddRecordButton %>">
	<div class="add-record-button-row">
		<aui:button onClick='<%= renderResponse.getNamespace() + "addRecord();" %>' value="add-record" />
	</div>

	<aui:script>
		function <portlet:namespace />addRecord() {
			submitForm(document.<portlet:namespace />fm, '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="backURL" value="<%= currentURL %>" /><portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" /><portlet:param name="detailDDMTemplateId" value="<%= String.valueOf(detailDDMTemplateId) %>" /></liferay-portlet:renderURL>');
		}
	</aui:script>
</c:if>