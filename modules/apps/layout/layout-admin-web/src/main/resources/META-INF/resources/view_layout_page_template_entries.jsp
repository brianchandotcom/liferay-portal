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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);

request.setAttribute(LayoutAdminWebKeys.LAYOUT_PAGE_TEMPLATE_DISPLAY_CONTEXT, layoutPageTemplateDisplayContext);

LayoutPageTemplateManagementToolbarDisplayContext layoutPageTemplateManagementToolbarDisplayContext = new LayoutPageTemplateManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutPageTemplateDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= new LayoutPageTemplateManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutPageTemplateDisplayContext) %>"
/>

<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteLayoutPageTemplateEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPageTemplateEntryURL %>" name="fm">
	<liferay-ui:error key="<%= PortalException.class.getName() %>" message="one-or-more-entries-could-not-be-deleted" />

	<liferay-ui:search-container
		id="layoutPageTemplateEntries"
		searchContainer="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new LayoutPageTemplateEntryVerticalCard(layoutPageTemplateEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/layout/update_layout_page_template_entry_preview" var="updateLayoutPageTemplateEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateLayoutPageTemplateEntryPreviewURL %>" name="layoutPageTemplateEntryPreviewFm">
	<aui:input name="layoutPageTemplateEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
	var updateLayoutPageTemplateEntryMenuItemClickHandler = dom.delegate(
		document.body,
		'click',
		'.<portlet:namespace />update-layout-page-template-action-option > a',
		function(event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			modalCommands.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="rename-layout-page-template" />',
					formSubmitURL: data.formSubmitUrl,
					idFieldName: data.idFieldName,
					idFieldValue: data.idFieldValue,
					mainFieldLabel: '<liferay-ui:message key="name" />',
					mainFieldName: 'name',
					mainFieldPlaceholder: '<liferay-ui:message key="name" />',
					mainFieldValue: data.mainFieldValue,
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}
	);

	var updateLayoutPageTemplateEntryPreviewMenuItemClickHandler = dom.delegate(
		document.body,
		'click',
		'.update-layout-page-template-entry-preview > a',
		function(event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			AUI().use(
				'liferay-item-selector-dialog',
				function(A) {
					var itemSelectorDialog = new A.LiferayItemSelectorDialog(
						{
							eventName: '<portlet:namespace />changePreview',
							on: {
								selectedItemChange: function(event) {
									var selectedItem = event.newVal;

									if (selectedItem) {
										var itemValue = JSON.parse(selectedItem.value);

										document.<portlet:namespace />layoutPageTemplateEntryPreviewFm.<portlet:namespace />layoutPageTemplateEntryId.value = data.layoutPageTemplateEntryId;
										document.<portlet:namespace />layoutPageTemplateEntryPreviewFm.<portlet:namespace />fileEntryId.value = itemValue.fileEntryId;

										submitForm(document.<portlet:namespace />layoutPageTemplateEntryPreviewFm);
									}
								}
							},
							'strings.add': '<liferay-ui:message key="ok" />',
							title: '<liferay-ui:message key="page-template-thumbnail" />',
							url: data.itemSelectorUrl
						}
					);

					itemSelectorDialog.open();
				}
			);
		}
	);

	function handleDestroyPortlet() {
		updateLayoutPageTemplateEntryMenuItemClickHandler.removeListener();
		updateLayoutPageTemplateEntryPreviewMenuItemClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>

<aui:script require='<%= npmResolvedPackageName + "/js/LayoutPageTemplateEntryManagementToolbarDefaultEventHandler.es as LayoutPageTemplateEntryManagementToolbarDefaultEventHandler" %>'>
	Liferay.component(
		'<%= layoutPageTemplateManagementToolbarDisplayContext.getDefaultEventHandler() %>',
		new LayoutPageTemplateEntryManagementToolbarDefaultEventHandler.default(
			{
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>