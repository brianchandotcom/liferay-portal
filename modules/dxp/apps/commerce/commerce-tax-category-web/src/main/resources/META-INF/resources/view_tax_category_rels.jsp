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
CommerceTaxCategoryRelDisplayContext commerceTaxCategoryRelDisplayContext = (CommerceTaxCategoryRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = commerceTaxCategoryRelDisplayContext.getCPDefinition();

long cpDefinitionId = commerceTaxCategoryRelDisplayContext.getCPDefinitionId();

PortletURL portletURL = commerceTaxCategoryRelDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceTaxCategoryRels"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<portlet:actionURL name="editCommerceTaxCategoryRel" var="addCommercetaxCategoryRelURL" />

		<aui:form action="<%= addCommercetaxCategoryRelURL %>" cssClass="hide" name="addCommerceTaxCategoryRelFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
			<aui:input name="commerceTaxCategoryIds" type="hidden" value="" />
			<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= commerceTaxCategoryRelDisplayContext.getScreenNavigationCategoryKey() %>" />
		</aui:form>

		<liferay-frontend:add-menu inline="<%= true %>">
			<liferay-frontend:add-menu-item id="addCommerceTaxCategory" title='<%= LanguageUtil.get(request, "add-tax-category") %>' url="javascript:;" />
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTaxCategoryRelDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceTaxCategoryRelDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceTaxCategoryRels();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceTaxCategoryRelIds" type="hidden" />
	<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= commerceTaxCategoryRelDisplayContext.getScreenNavigationCategoryKey() %>" />

	<liferay-ui:search-container
		id="commerceTaxCategoryRels"
		iteratorURL="<%= portletURL %>"
		searchContainer="<%= commerceTaxCategoryRelDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceTaxCategoryRel"
			cssClass="entry-display-style"
			keyProperty="commerceTaxCategoryRelId"
			modelVar="commerceTaxCategoryRel"
		>

			<%
			CommerceTaxCategory commerceTaxCategory = commerceTaxCategoryRel.getCommerceTaxCategory();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= HtmlUtil.escape(commerceTaxCategory.getName(languageId)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				value="<%= HtmlUtil.escape(commerceTaxCategory.getDescription(languageId)) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/tax_category_rel_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteCommerceTaxCategoryRels() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tax-categories" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceTaxCategoryRelIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceTaxCategoryRel" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceTaxCategory').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'taxCategoriesSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />commerceTaxCategoryIds').val(selectedItems);

								var addCommerceTaxCategoryRelFm = $('#<portlet:namespace />addCommerceTaxCategoryRelFm');

								submitForm(addCommerceTaxCategoryRelFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpDefinition.getTitle(languageId) %>" key="add-tax-category-to-x" />',
					url: '<%= commerceTaxCategoryRelDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>