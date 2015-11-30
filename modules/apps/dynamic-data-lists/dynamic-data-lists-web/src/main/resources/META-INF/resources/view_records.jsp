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
String mvcPath = ParamUtil.getString(request, "mvcPath", "/view_record_set.jsp");

String redirect = ParamUtil.getString(request, "redirect", portletDisplay.getURLBack());

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDLViewRecordsDisplayContext ddlViewRecordsDisplayContext = new DDLViewRecordsDisplayContext(liferayPortletRequest, liferayPortletResponse, formDDMTemplateId);

DDLRecordSet recordSet = ddlViewRecordsDisplayContext.getDDLRecordSet();

DDMStructure ddmStructure = ddlViewRecordsDisplayContext.getDDMStructure();

boolean editable = ParamUtil.getBoolean(request, "editable", true);
boolean hasDeletePermission = false;
boolean hasUpdatePermission = false;
boolean showAddRecordButton = false;

if (editable || ddlDisplayContext.isAdminPortlet()) {
	hasDeletePermission = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.DELETE);
	hasUpdatePermission = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.UPDATE);
	showAddRecordButton = DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), DDLActionKeys.ADD_RECORD);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", mvcPath);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("recordSetId", String.valueOf(recordSet.getRecordSetId()));

List<String> headerNames = new ArrayList<String>();

List<DDMFormField> ddmFormfields = ddlViewRecordsDisplayContext.getDDMFormFields();

for (DDMFormField ddmFormField : ddmFormfields) {
	LocalizedValue label = ddmFormField.getLabel();

	headerNames.add(label.getString(locale));
}

if (hasUpdatePermission) {
	headerNames.add("status");
	headerNames.add("modified-date");
	headerNames.add("author");
}

headerNames.add(StringPool.BLANK);

SearchContainer recordSearchContainer = new SearchContainer(renderRequest, new DisplayTerms(request), null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(request, "no-x-records-were-found", HtmlUtil.escape(ddmStructure.getName(locale)), false));

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

OrderByComparator<DDLRecord> orderByComparator = DDLPortletUtil.getDDLRecordOrderByComparator(orderByCol, orderByType);

recordSearchContainer.setOrderByCol(orderByCol);
recordSearchContainer.setOrderByComparator(orderByComparator);
recordSearchContainer.setOrderByType(orderByType);
%>

<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="addRecordURL">
	<portlet:param name="mvcPath" value="/edit_record.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
	<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
</portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<c:if test="<%= showAddRecordButton && ddlDisplayContext.isDisplayPortlet() %>">
		<aui:nav cssClass="navbar-nav" searchContainer="<%= recordSearchContainer %>">
			<aui:nav-item href="<%= addRecordURL %>" iconCssClass="icon-plus" label='<%= LanguageUtil.format(request, "add-x", HtmlUtil.escape(ddmStructure.getName(locale)), false) %>' />

			<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.UPDATE) %>">
				<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="editRecordSetURL">
					<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
					<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= editRecordSetURL %>" iconCssClass="icon-edit" label='<%= LanguageUtil.get(request, "edit-list") %>' />
			</c:if>
		</aui:nav>
	</c:if>

	<aui:nav-bar-search searchContainer="<%= recordSearchContainer %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="searchURL">
			<portlet:param name="mvcPath" value="<%= mvcPath %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
		</portlet:renderURL>

		<aui:form action="<%= searchURL.toString() %>" name="fm1">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" name="<%= DisplayTerms.KEYWORDS %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/view_records_sort_buttons.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 view-records-container" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<liferay-ui:search-container
			searchContainer="<%= recordSearchContainer %>"
		>
			<liferay-ui:search-container-results>
				<%@ include file="/record_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DDLRecord record = (DDLRecord)results.get(i);

				DDLRecordVersion recordVersion = record.getRecordVersion();

				if (editable) {
					recordVersion = record.getLatestRecordVersion();
				}

				DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(recordVersion.getDDMStorageId());

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();

				ResultRow row = new ResultRow(record, record.getRecordId(), i);

				row.setCssClass("entry-display-style");

				row.setParameter("editable", String.valueOf(editable));
				row.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));
				row.setParameter("hasDeletePermission", String.valueOf(hasDeletePermission));
				row.setParameter("hasUpdatePermission", String.valueOf(hasUpdatePermission));

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/view_record.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("recordId", String.valueOf(record.getRecordId()));
				rowURL.setParameter("version", recordVersion.getVersion());
				rowURL.setParameter("editable", String.valueOf(editable));
				rowURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

				// Columns

				for (DDMFormField ddmFormField : ddmFormfields) {
				%>

					<%@ include file="/record_row_value.jspf" %>

				<%
				}

				if (hasUpdatePermission) {
					row.addStatus(recordVersion.getStatus(), recordVersion.getStatusByUserId(), recordVersion.getStatusDate(), rowURL);
					row.addDate(record.getModifiedDate(), rowURL);
					row.addText(HtmlUtil.escape(PortalUtil.getUserName(recordVersion)), rowURL);
				}

				// Action

				row.addJSP("/record_action.jsp", "entry-action", application, request, response);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator displayStyle="<%= ddlViewRecordsDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= showAddRecordButton && ddlDisplayContext.isAdminPortlet() %>">
	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.format(request, "add-x", HtmlUtil.escape(ddmStructure.getName(locale)), false) %>' url="<%= addRecordURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<%@ include file="/export_record_set.jspf" %>

<aui:script>
	AUI().use('liferay-portlet-dynamic-data-lists');
</aui:script>