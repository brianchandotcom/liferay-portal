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

KaleoFormsViewRecordsDisplayContext kaleoFormsViewRecordsDisplayContext = kaleoFormsAdminDisplayContext.getKaleoFormsViewRecordsDisplayContext();

KaleoProcess kaleoProcess = kaleoFormsViewRecordsDisplayContext.getKaleoProcess();

boolean hasSubmitPermission = KaleoProcessPermission.contains(permissionChecker, kaleoProcess, ActionKeys.SUBMIT);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/view_kaleo_process.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= kaleoFormsViewRecordsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= kaleoFormsViewRecordsDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= kaleoFormsViewRecordsDisplayContext.getClearResultsURL() %>"
	componentId="kaleoFormsRecordsManagementToolbar"
	creationMenu="<%= kaleoFormsViewRecordsDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= kaleoFormsViewRecordsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= kaleoFormsViewRecordsDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= kaleoFormsViewRecordsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= kaleoFormsViewRecordsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm"
	sortingOrder="<%= kaleoFormsViewRecordsDisplayContext.getOrderByType() %>"
	sortingURL="<%= kaleoFormsViewRecordsDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="ddlRecordIds" type="hidden" />

		<liferay-ui:search-container
			id="<%= kaleoFormsViewRecordsDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= kaleoFormsViewRecordsDisplayContext.getSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecord"
				keyProperty="recordId"
				modelVar="record"
			>

				<%
				row.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));

				DDLRecordVersion recordVersion = record.getRecordVersion();

				DDMFormValues ddmFormValues = kaleoFormsAdminDisplayContext.getDDMFormValues(recordVersion.getDDMStorageId());

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/admin/view_record.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("ddlRecordId", String.valueOf(record.getRecordId()));
				rowURL.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));
				rowURL.setParameter("version", recordVersion.getVersion());

				// Columns

				for (DDMFormField ddmFormField : kaleoFormsViewRecordsDisplayContext.getDDMFormFields()) {
					String value = StringPool.BLANK;

					List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(ddmFormField.getName());

					if (ddmFormFieldValues != null) {
						DDMFormFieldValueRenderer ddmFormFieldValueRenderer = DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(ddmFormField.getType());

						value = ddmFormFieldValueRenderer.render(ddmFormFieldValues, themeDisplay.getLocale());
					}
				%>

					<liferay-ui:search-container-column-text
						name="<%= HtmlUtil.escape(kaleoFormsViewRecordsDisplayContext.getColumnName(ddmFormField)) %>"
						value="<%= value %>"
					/>

				<%
				}
				%>

				<c:if test="<%= hasSubmitPermission %>">
					<liferay-ui:search-container-column-status
						name="status"
						status="<%= recordVersion.getStatus() %>"
						statusByUserId="<%= recordVersion.getStatusByUserId() %>"
						statusDate="<%= recordVersion.getStatusDate() %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= record.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-text
						name="author"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(recordVersion)) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-jsp
					path="/admin/record_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= kaleoFormsViewRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
				paginate="<%= false %>"
				searchContainer="<%= kaleoFormsViewRecordsDisplayContext.getSearch() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<div class="container-fluid-1280">
	<liferay-ui:search-paginator
		searchContainer="<%= kaleoFormsViewRecordsDisplayContext.getSearch() %>"
	/>
</div>

<%@ include file="/admin/export_kaleo_process.jspf" %>

<aui:script>
	AUI().use('liferay-portlet-dynamic-data-lists');

	Liferay.provide(
		window,
		'<portlet:namespace />openPreviewDialog',
		function(content) {
			var Util = Liferay.Util;

			var dialog = Util.getWindow('<portlet:namespace />previewDialog');

			if (!dialog) {
				dialog = Util.Window.getWindow({
					dialog: {
						bodyContent: content
					},
					id: '<portlet:namespace />previewDialog',
					title: Liferay.Language.get('preview')
				});
			}
			else {
				dialog.show();

				dialog.set('bodyContent', content);
			}
		},
		['liferay-util-window']
	);
</aui:script>

<aui:script sandbox="<%= true %>">
	var deleteRecords = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace /><%= kaleoFormsViewRecordsDisplayContext.getSearchContainerId() %>'
			);

			if (searchContainer) {
				Liferay.Util.postForm(
					document.<portlet:namespace />searchContainerForm,
					{
						data: {
							ddlRecordIds: Liferay.Util.listCheckedExcept(
								searchContainer,
								'<portlet:namespace />allRowIds'
							)
						},

						<portlet:actionURL name="deleteDDLRecord" var="deleteDDLRecordURL">
							<portlet:param name="mvcPath" value="/admin/view_kaleo_process.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:actionURL>

						url: '<%= deleteDDLRecordURL %>'
					}
				);
			}
		}
	};

	var ACTIONS = {
		deleteRecords: deleteRecords
	};

	Liferay.componentReady('kaleoFormsRecordsManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on(['actionItemClicked'], function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kaleoProcess.getName(locale));

PortalUtil.setPageSubtitle(kaleoProcess.getName(locale), request);
PortalUtil.setPageDescription(kaleoProcess.getDescription(locale), request);

PortalUtil.addPortletBreadcrumbEntry(request, kaleoProcess.getName(locale), currentURL);
%>