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
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);
%>

<div id="<portlet:namespace />layoutPageTemplateEntries">
	<liferay-ui:search-container
		total="<%= selectLayoutPageTemplateEntryDisplayContext.getGlobalLayoutPageTemplateEntriesCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= selectLayoutPageTemplateEntryDisplayContext.getGlobalLayoutPageTemplateEntries() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<portlet:renderURL var="addLayoutURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/layout/add_layout" />
				<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> addLayoutPrototypeData = new HashMap<>();

				addLayoutPrototypeData.put("add-layout-url", addLayoutURL);
				%>

				<liferay-frontend:icon-vertical-card
					actionJspServletContext="<%= application %>"
					cssClass="add-layout-prototype-action-option"
					data="<%= addLayoutPrototypeData %>"
					icon="page-template"
					resultRow="<%= row %>"
					rowChecker="<%= searchContainer.getRowChecker() %>"
					title="<%= HtmlUtil.escape(layoutPageTemplateEntry.getName()) %>"
					url="javascript:;"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="aui-base">
	var addLayoutActionOptionQueryClickHandler = A.one('#<portlet:namespace />layoutPageTemplateEntries').delegate(
		'click',
		function(event) {
			var actionElement = event.currentTarget;

			Liferay.Util.openWindow(
				{
					dialog: {
						destroyOnHide: true,
						resizable: false
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: '<portlet:namespace />addLayoutDialog',
					title: '<liferay-ui:message key="add-page" />',
					uri: actionElement.getData('add-layout-url')
				}
			);
		},
		'.add-layout-prototype-action-option'
	);

	function handleDestroyPortlet () {
		addLayoutActionOptionQueryClickHandler.detach();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>