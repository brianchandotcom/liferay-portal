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

<%@ include file="/admin/init.jsp" %>

<%
KBTemplatesManagementToolbarDisplayContext kbTemplatesManagementToolbarDisplayContext = new KBTemplatesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, templatePath);
%>

<liferay-util:include page="/admin/common/top_tabs.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	actionDropdownItems="<%= kbTemplatesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= String.valueOf(kbTemplatesManagementToolbarDisplayContext.getSearchURL()) %>"
	componentId="kbTemplatesManagementToolbar"
	creationMenu="<%= kbTemplatesManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= kbTemplatesManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= kbTemplatesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= kbTemplatesManagementToolbarDisplayContext.getTotal() %>"
	searchActionURL="<%= String.valueOf(kbTemplatesManagementToolbarDisplayContext.getSearchURL()) %>"
	searchContainerId="kbTemplates"
	selectable="<%= true %>"
	sortingOrder="<%= kbTemplatesManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(kbTemplatesManagementToolbarDisplayContext.getSortingURL()) %>"
/>

<div class="container-fluid-1280">
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcPath" value="/admin/view_templates.jsp" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="get" name="fm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />
		<aui:input name="kbTemplateIds" type="hidden" />

		<aui:fieldset>
			<liferay-ui:search-container
				id="kbTemplates"
				rowChecker="<%= AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.DELETE_KB_TEMPLATES) ? new RowChecker(renderResponse) : null %>"
				searchContainer="<%= kbTemplatesManagementToolbarDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.knowledge.base.model.KBTemplate"
					keyProperty="kbTemplateId"
					modelVar="kbTemplate"
				>

					<%
					Map<String, Object> rowData = new HashMap<String, Object>();

					rowData.put("actions", StringUtil.merge(kbTemplatesManagementToolbarDisplayContext.getAvailableActions(kbTemplate)));

					row.setData(rowData);
					%>

					<liferay-ui:search-container-column-user
						showDetails="<%= false %>"
						userId="<%= kbTemplate.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date modifiedDate = kbTemplate.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbTemplate.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</span>

						<liferay-portlet:renderURL var="editURL">
							<portlet:param name="mvcPath" value='<%= templatePath + "edit_template.jsp" %>' />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="kbTemplateId" value="<%= String.valueOf(kbTemplate.getKbTemplateId()) %>" />
						</liferay-portlet:renderURL>

						<h2 class="h5">
							<aui:a href="<%= editURL.toString() %>">
								<%= HtmlUtil.escape(kbTemplate.getTitle()) %>
							</aui:a>
						</h2>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/admin/template_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="descriptive"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:fieldset>
	</aui:form>
</div>

<aui:script>
	var deleteKBTemplates = function() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-templates" />'
			)
		) {
			var form = document.querySelector('#<portlet:namespace />fm');

			if (form) {
				form.setAttribute('method', 'post');

				form.querySelector(
					'#<portlet:namespace />kbTemplateIds'
				).value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(
					form,
					'<liferay-portlet:actionURL name="deleteKBTemplates"><portlet:param name="mvcPath" value="/admin/view_templates.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:actionURL>'
				);
			}
		}
	};

	var ACTIONS = {
		deleteKBTemplates: deleteKBTemplates
	};

	Liferay.componentReady('kbTemplatesManagementToolbar').then(function(
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