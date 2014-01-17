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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

boolean editable = ParamUtil.getBoolean(request, "editable", true);
boolean showAddRecordButton = false;

if (editable || portletName.equals(PortletKeys.DYNAMIC_DATA_LISTS)) {
	editable = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.UPDATE);

	showAddRecordButton = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.ADD_RECORD);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_record_set");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("recordSetId", String.valueOf(recordSet.getRecordSetId()));
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">

	<%
	DDMStructure ddmStructure = recordSet.getDDMStructure(formDDMTemplateId);

	String languageId = LanguageUtil.getLanguageId(request);

	Map<String, Map<String, String>> fieldsMap = ddmStructure.getFieldsMap(languageId);

	List<String> headerNames = new ArrayList<String>();

	for (Map<String, String> fields : fieldsMap.values()) {
		if (GetterUtil.getBoolean(fields.get(FieldConstants.PRIVATE))) {
			continue;
		}

		String label = fields.get(FieldConstants.LABEL);

		headerNames.add(label);
	}

	if (editable) {
		headerNames.add("status");
		headerNames.add("modified-date");
		headerNames.add("author");
		headerNames.add(StringPool.BLANK);
	}
	%>

	<liferay-ui:search-container
		searchContainer='<%= new SearchContainer(renderRequest, new DisplayTerms(request), null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-x-records-were-found", HtmlUtil.escape(ddmStructure.getName(locale)), false)) %>'
	>

		<aui:nav-bar>
			<aui:nav>
				<c:if test="<%= showAddRecordButton %>">
					<portlet:renderURL var="addRecordURL">
						<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
						<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= addRecordURL %>" iconCssClass="icon-plus" label='<%= LanguageUtil.format(pageContext, "add-x", HtmlUtil.escape(ddmStructure.getName(locale)), false) %>' />
				</c:if>
			</aui:nav>

			<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/dynamic_data_lists/record_search.jsp" searchContainer="<%= searchContainer %>" />
		</aui:nav-bar>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_lists/record_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			DDLRecord record = (DDLRecord)results.get(i);

			DDLRecordVersion recordVersion = record.getRecordVersion();

			if (editable) {
				recordVersion = record.getLatestRecordVersion();
			}

			Fields fieldsModel = StorageEngineUtil.getFields(recordVersion.getDDMStorageId());

			ResultRow row = new ResultRow(record, record.getRecordId(), i);

			row.setParameter("editable", String.valueOf(editable));
			row.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/dynamic_data_lists/view_record");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("recordId", String.valueOf(record.getRecordId()));
			rowURL.setParameter("version", recordVersion.getVersion());
			rowURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

			// Columns

			for (Map<String, String> fields : fieldsMap.values()) {
				if (GetterUtil.getBoolean(fields.get(FieldConstants.PRIVATE))) {
					continue;
				}
			%>

				<%@ include file="/html/portlet/dynamic_data_lists/record_row_value.jspf" %>

			<%
			}

			if (editable) {
				row.addStatus(recordVersion.getStatus(), recordVersion.getStatusByUserId(), recordVersion.getStatusDate(), rowURL);
				row.addDate(record.getModifiedDate(), rowURL);
				row.addText(HtmlUtil.escape(PortalUtil.getUserName(recordVersion)), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/dynamic_data_lists/record_action.jsp");
			}

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	AUI().use('liferay-portlet-dynamic-data-lists');
</aui:script>