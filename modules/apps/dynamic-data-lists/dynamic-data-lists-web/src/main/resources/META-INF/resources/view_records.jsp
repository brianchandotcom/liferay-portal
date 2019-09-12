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
long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDLViewRecordsDisplayContext ddlViewRecordsDisplayContext = new DDLViewRecordsDisplayContext(liferayPortletRequest, liferayPortletResponse, formDDMTemplateId);

PortletURL portletURL = ddlViewRecordsDisplayContext.getPortletURL();

if (!ddlDisplayContext.isAdminPortlet()) {
	DDLRecordSet recordSet = ddlDisplayContext.getRecordSet();

	renderResponse.setTitle(recordSet.getName(locale));
}
%>

<c:if test="<%= ddlViewRecordsDisplayContext.isAdminPortlet() %>">
	<clay:navigation-bar
		inverted="<%= true %>"
		navigationItems="<%= ddlViewRecordsDisplayContext.getNavigationItems() %>"
	/>
</c:if>

<clay:management-toolbar
	actionDropdownItems="<%= ddlViewRecordsDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= ddlViewRecordsDisplayContext.getClearResultsURL() %>"
	componentId="ddlViewRecordsManagementToolbar"
	creationMenu="<%= ddlViewRecordsDisplayContext.getCreationMenu() %>"
	disabled="<%= ddlViewRecordsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddlViewRecordsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddlViewRecordsDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddlViewRecordsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= ddlViewRecordsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= ddlViewRecordsDisplayContext.isSelectable() %>"
	sortingOrder="<%= ddlViewRecordsDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddlViewRecordsDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280 view-records-container" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="recordIds" type="hidden" />

		<liferay-ui:search-container
			id="<%= ddlViewRecordsDisplayContext.getSearchContainerId() %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddlViewRecordsDisplayContext.getSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecord"
				keyProperty="recordId"
				modelVar="record"
			>

				<%
				DDLRecordVersion recordVersion = record.getRecordVersion();

				if (ddlViewRecordsDisplayContext.isEditable()) {
					recordVersion = record.getLatestRecordVersion();
				}

				row.setParameter("editable", String.valueOf(ddlViewRecordsDisplayContext.isEditable()));
				row.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));
				row.setParameter("hasDeletePermission", String.valueOf(ddlViewRecordsDisplayContext.hasDeletePermission()));
				row.setParameter("hasUpdatePermission", String.valueOf(ddlViewRecordsDisplayContext.hasUpdatePermission()));

				String href = StringPool.BLANK;

				if (ddlViewRecordsDisplayContext.isEditable()) {
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcPath", "/view_record.jsp");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("recordId", String.valueOf(record.getRecordId()));
					rowURL.setParameter("version", recordVersion.getVersion());
					rowURL.setParameter("editable", String.valueOf(ddlViewRecordsDisplayContext.isEditable()));
					rowURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

					href = rowURL.toString();
				}

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddlViewRecordsDisplayContext.getDDMFormFieldValuesMap(recordVersion);

				for (DDMFormField ddmFormField : ddlViewRecordsDisplayContext.getDDMFormFields()) {
					LocalizedValue label = ddmFormField.getLabel();

					String value = StringPool.BLANK;

					List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(ddmFormField.getName());

					if (ddmFormFieldValues != null) {
						DDMFormFieldValueRenderer ddmFormFieldValueRenderer = DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(ddmFormField.getType());

						value = ddmFormFieldValueRenderer.render(ddmFormFieldValues, themeDisplay.getLocale());
					}
				%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= href %>"
						name="<%= label.getString(themeDisplay.getLocale()) %>"
						value="<%= value %>"
					/>

				<%
				}
				%>

				<c:if test="<%= ddlViewRecordsDisplayContext.hasUpdatePermission() %>">
					<liferay-ui:search-container-column-status
						href="<%= href %>"
						name="status"
						status="<%= recordVersion.getStatus() %>"
						statusByUserId="<%= recordVersion.getStatusByUserId() %>"
						statusDate="<%= recordVersion.getStatusDate() %>"
					/>

					<liferay-ui:search-container-column-date
						href="<%= href %>"
						name="modified-date"
						value="<%= record.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= href %>"
						name="author"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(recordVersion)) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-jsp
					path="/record_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= ddlViewRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<%@ include file="/export_record_set.jspf" %>

<aui:script use="liferay-portlet-dynamic-data-lists">
	var deleteRecords = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var form = document.<portlet:namespace />fm;

			var searchContainer = form.querySelector(
				'#<portlet:namespace />ddlRecord'
			);

			if (searchContainer) {
				<portlet:actionURL name="deleteRecord" var="deleteRecordURL">
					<portlet:param name="mvcPath" value="/view_records.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				Liferay.Util.postForm(form, {
					data: {
						recordIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							'<portlet:namespace />allRowIds'
						)
					},
					url: '<%= deleteRecordURL %>'
				});
			}
		}
	};

	var ACTIONS = {
		deleteRecords: deleteRecords
	};

	Liferay.componentReady('ddlViewRecordsManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>