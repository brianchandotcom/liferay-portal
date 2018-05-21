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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL redirectURL = renderResponse.createRenderURL();

	redirectURL.setParameter("mvcPath", "/admin/view.jsp");

	redirect = redirectURL.toString();
}

KaleoFormsViewRecordsDisplayContext kaleoFormsViewRecordsDisplayContext = new KaleoFormsViewRecordsDisplayContext(liferayPortletRequest, liferayPortletResponse);

KaleoProcess kaleoProcess = kaleoFormsViewRecordsDisplayContext.getKaleoProcess();

DDLRecordSet recordSet = kaleoFormsViewRecordsDisplayContext.getDDLRecordSet();

boolean hasSubmitPermission = KaleoProcessPermission.contains(permissionChecker, kaleoProcess, ActionKeys.SUBMIT);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/view_kaleo_process.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));

List<String> headerNames = new ArrayList<String>();

List<DDMFormField> ddmFormfields = kaleoFormsViewRecordsDisplayContext.getDDMFormFields();

for (DDMFormField ddmFormField : ddmFormfields) {
	LocalizedValue label = ddmFormField.getLabel();

	headerNames.add(label.getString(locale));
}

if (hasSubmitPermission) {
	headerNames.add("status");
	headerNames.add("modified-date");
	headerNames.add("author");
}

headerNames.add(StringPool.BLANK);

SearchContainer recordSearchContainer = new SearchContainer(renderRequest, new DisplayTerms(request), null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(request, "no-x-records-were-found", HtmlUtil.escape(kaleoProcess.getName(locale)), false));

if (!user.isDefaultUser()) {
	recordSearchContainer.setRowChecker(new EmptyOnClickRowChecker(renderResponse));
}

OrderByComparator<DDLRecord> orderByComparator = kaleoFormsViewRecordsDisplayContext.getDDLRecordOrderByComparator(kaleoFormsViewRecordsDisplayContext.getOrderByCol(), kaleoFormsViewRecordsDisplayContext.getOrderByType());

recordSearchContainer.setOrderByCol(kaleoFormsViewRecordsDisplayContext.getOrderByCol());
recordSearchContainer.setOrderByComparator(orderByComparator);
recordSearchContainer.setOrderByType(kaleoFormsViewRecordsDisplayContext.getOrderByType());
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="<%= kaleoProcess.getName(locale) %>" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search searchContainer="<%= recordSearchContainer %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="searchURL">
			<portlet:param name="mvcPath" value='<%= "/admin/view_kaleo_process.jsp" %>' />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcess.getKaleoProcessId()) %>" />
		</portlet:renderURL>

		<aui:form action="<%= searchURL.toString() %>" name="fm1">
			<%@ include file="/admin/record_search.jspf" %>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= !user.isDefaultUser() %>"
	searchContainerId="ddlRecord"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= kaleoFormsViewRecordsDisplayContext.getOrderByCol() %>"
			orderByType="<%= kaleoFormsViewRecordsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "modified-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteRecords();" %>'
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="ddlRecordIds" type="hidden" />

		<liferay-ui:search-container
			id="ddlRecord"
			searchContainer="<%= recordSearchContainer %>"
		>
			<liferay-ui:search-container-results>
				<%@ include file="/admin/record_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DDLRecord record = (DDLRecord)results.get(i);

				DDLRecordVersion recordVersion = record.getRecordVersion();

				DDMFormValues ddmFormValues = kaleoFormsAdminDisplayContext.getDDMFormValues(recordVersion.getDDMStorageId());

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();

				ResultRow row = new ResultRow(record, record.getRecordId(), i);

				row.setCssClass("entry-display-style");

				row.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/admin/view_record.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("ddlRecordId", String.valueOf(record.getRecordId()));
				rowURL.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));
				rowURL.setParameter("version", recordVersion.getVersion());

				// Columns

				for (DDMFormField ddmFormField : ddmFormfields) {
					String value = StringPool.BLANK;

					List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(ddmFormField.getName());

					if (ddmFormFieldValues != null) {
						DDMFormFieldValueRenderer ddmFormFieldValueRenderer = DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(ddmFormField.getType());

						value = ddmFormFieldValueRenderer.render(ddmFormFieldValues, themeDisplay.getLocale());
					}

					row.addText(value);
				}

				if (hasSubmitPermission) {
					row.addStatus(recordVersion.getStatus(), recordVersion.getStatusByUserId(), recordVersion.getStatusDate(), rowURL);
					row.addDate(record.getModifiedDate(), rowURL);
					row.addText(HtmlUtil.escape(PortalUtil.getUserName(recordVersion)), rowURL);
				}

				// Action

				row.addJSP("/admin/record_action.jsp", "entry-action", application, request, response);

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator
				displayStyle="<%= kaleoFormsViewRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= hasSubmitPermission %>">
	<portlet:renderURL var="submitURL">
		<portlet:param name="mvcPath" value="/admin/edit_request.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcess.getKaleoProcessId()) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.format(request, "submit-new-x", HtmlUtil.escape(kaleoProcess.getName(locale)), false) %>'
			url="<%= submitURL.toString() %>"
		/>
	</liferay-frontend:add-menu>
</c:if>

<%@ include file="/admin/export_kaleo_process.jspf" %>

<aui:script>
	AUI().use('liferay-portlet-dynamic-data-lists');

	function <portlet:namespace />deleteRecords() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />ddlRecord', form);

			form.attr('method', 'post');
			form.fm('ddlRecordIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteDDLRecord"><portlet:param name="mvcPath" value="/admin/view_kaleo_process.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />openPreviewDialog',
		function(content) {
			var Util = Liferay.Util;

			var dialog = Util.getWindow('<portlet:namespace />previewDialog');

			if (!dialog) {
				dialog = Util.Window.getWindow(
					{
						dialog: {
							bodyContent: content
						},
						id: '<portlet:namespace />previewDialog',
						title: Liferay.Language.get('preview')
					}
				);
			}
			else {
				dialog.show();

				dialog.set('bodyContent', content);
			}
		},
		['liferay-util-window']
	);
</aui:script>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kaleoProcess.getName(locale));

PortalUtil.setPageSubtitle(kaleoProcess.getName(locale), request);
PortalUtil.setPageDescription(kaleoProcess.getDescription(locale), request);

PortalUtil.addPortletBreadcrumbEntry(request, kaleoProcess.getName(locale), currentURL);
%>