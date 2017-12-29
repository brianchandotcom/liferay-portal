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
List<String> types = ListUtil.filter(
	ListUtil.fromArray(LayoutTypeControllerTracker.getTypes()),
	new PredicateFilter<String>() {

		@Override
		public boolean filter(String type) {
			LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

			return layoutTypeController.isInstanceable();
		}

	});
%>

<liferay-ui:search-container
	total="<%= types.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= types %>"
	/>

	<liferay-ui:search-container-row
		className="java.lang.String"
		modelVar="type"
	>

		<%
		row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
		%>

		<liferay-ui:search-container-column-text>

			<%
			LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

			ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());

			Map<String, Object> addLayoutData = new HashMap<>();

			addLayoutData.put("type", type);
			%>

			<liferay-frontend:icon-vertical-card
				actionJspServletContext="<%= application %>"
				cssClass='<%= renderResponse.getNamespace() + "add-layout-action-option" %>'
				data="<%= addLayoutData %>"
				icon="page"
				resultRow="<%= row %>"
				rowChecker="<%= searchContainer.getRowChecker() %>"
				title='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type) %>'
				url="javascript:;"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" />
</liferay-ui:search-container>

<portlet:actionURL name="/layout/add_simple_layout" var="addLayoutURL">
	<portlet:param name="mvcPath" value="/select_layout_page_template_entry.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getGroupId()) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()) %>" />
	<portlet:param name="stagingGroupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getStagingGroupId()) %>" />
	<portlet:param name="parentLayoutId" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()) %>" />
</portlet:actionURL>

<aui:script require="metal-dom/src/all/dom as dom">
	var addLayoutActionOptionQueryClickHandler = dom.delegate(
		document.body,
		'click',
		'.<portlet:namespace />add-layout-action-option',
		function(event) {
			var actionElement = event.delegateTarget;

			Liferay.Util.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="add-page" />',
					formSubmitURL: '<%= addLayoutURL %>',
					idFieldName: 'type',
					idFieldValue: actionElement.dataset.type,
					mainFieldName: 'name',
					mainFieldLabel: '<liferay-ui:message key="name" />',
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}
	);

	function handleDestroyPortlet () {
		addLayoutActionOptionQueryClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>