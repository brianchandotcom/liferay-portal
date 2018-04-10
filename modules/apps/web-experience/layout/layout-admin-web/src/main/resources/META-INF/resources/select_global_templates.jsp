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
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(layoutsAdminDisplayContext, request);
%>

<liferay-ui:search-container
	total="<%= selectLayoutPageTemplateEntryDisplayContext.getLayoutPrototypesCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= selectLayoutPageTemplateEntryDisplayContext.getLayoutPrototypes() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.LayoutPrototype"
		modelVar="layoutPrototype"
	>

		<%
		row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
		%>

		<liferay-ui:search-container-column-text>

			<%
			Map<String, Object> addLayoutPrototypeData = new HashMap<>();

			addLayoutPrototypeData.put("layout-prototype-id", layoutPrototype.getLayoutPrototypeId());
			%>

			<liferay-frontend:icon-vertical-card
				actionJspServletContext="<%= application %>"
				cssClass='<%= renderResponse.getNamespace() + "add-layout-prototype-action-option" %>'
				data="<%= addLayoutPrototypeData %>"
				icon="page"
				resultRow="<%= row %>"
				rowChecker="<%= searchContainer.getRowChecker() %>"
				title="<%= HtmlUtil.escape(layoutPrototype.getName(locale)) %>"
				url="javascript:;"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="icon"
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<portlet:actionURL name="/layout/add_layout_prototype_layout" var="addLayoutPrototypeLayoutURL">
	<portlet:param name="mvcPath" value="/select_layout_page_template_entry.jsp" />
	<portlet:param name="redirect" value="<%= layoutsAdminDisplayContext.getRedirect() %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getPortletName() %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getGroupId()) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()) %>" />
	<portlet:param name="stagingGroupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getStagingGroupId()) %>" />
	<portlet:param name="parentLayoutId" value="<%= String.valueOf(layoutsAdminDisplayContext.getParentLayoutId()) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()) %>" />
	<portlet:param name="explicitCreation" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<%
String autoSiteNavigationMenuNames = layoutsAdminDisplayContext.getAutoSiteNavigationMenuNames();
%>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
	var addLayoutPrototypeActionOptionQueryClickHandler = dom.delegate(
		document.body,
		'click',
		'.<portlet:namespace />add-layout-prototype-action-option',
		function(event) {
			var actionElement = event.delegateTarget;

			modalCommands.openSimpleInputModal(
				{
					<c:if test="<%= Validator.isNotNull(autoSiteNavigationMenuNames) %>">
						checkboxFieldLabel: '<liferay-ui:message arguments="<%= autoSiteNavigationMenuNames %>" key="add-this-page-to-the-following-menus-x" />',
						checkboxFieldName: 'TypeSettingsProperties--addToAutoMenus--',
						checkboxFieldValue: true,
					</c:if>

					dialogTitle: '<liferay-ui:message key="add-page" />',
					formSubmitURL: '<%= addLayoutPrototypeLayoutURL %>',
					idFieldName: 'layoutPrototypeId',
					idFieldValue: actionElement.dataset.layoutPrototypeId,
					mainFieldName: 'name',
					mainFieldLabel: '<liferay-ui:message key="name" />',
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}
	);

	function handleDestroyPortlet () {
		addLayoutPrototypeActionOptionQueryClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>