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
String searchContainerId = ParamUtil.getString(request, "searchContainerId", "cpDefinitions");

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpDefinitionsDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= cpDefinitionsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= cpDefinitionsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionsDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date", "display-date", "title"} %>'
			portletURL="<%= cpDefinitionsDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(cpDefinitionsDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPDefinitions();" %>' icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />DefinitionToolbarFilter">
</div>

<liferay-portlet:resourceURL id="cpDefinitionsFacets" var="cpDefinitionsFacetsURL">

</liferay-portlet:resourceURL>

<aui:script require="commerce-product-definitions-web/DefinitionToolbarFilter.es">
	var definitionToolbarFilter = new commerceProductDefinitionsWebDefinitionToolbarFilterEs.default(
		{
			categorySelectorURL: '<%= cpDefinitionsDisplayContext.getCategorySelectorURL(renderResponse.getNamespace() + "selectCategory") %>',
			cpDefinitionsFacetsURL : '<%= cpDefinitionsFacetsURL.toString() %>',
			groupIds: '<%= themeDisplay.getScopeGroupId() %>',
			namespace : '<portlet:namespace />',
			pathThemeImages: '<%= themeDisplay.getPathThemeImages() %>',
			portletURL: '<%= cpDefinitionsDisplayContext.getPortletURL() %>',
			vocabularyIds: '<%= cpDefinitionsDisplayContext.getVocabularyIds() %>'
		},
		'#<portlet:namespace />DefinitionToolbarFilter'
	);
</aui:script>

<aui:script>
	function <portlet:namespace />deleteCPDefinitions() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-products") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
			form.fm('deleteCPDefinitionIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editProductDefinition" />');
		}
	}
</aui:script>