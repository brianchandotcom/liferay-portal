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
CommerceTaxCategoryDisplayContext commerceTaxCategoryDisplayContext = (CommerceTaxCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceTaxCategories"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceTaxCategoryDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTaxCategoryDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceTaxCategoryDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceTaxCategoryDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceTaxCategoryDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceTaxCategoriesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceTaxCategories();" %>' icon="times" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceTaxCategory" var="editCommerceTaxCategoryActionURL" />

	<aui:form action="<%= editCommerceTaxCategoryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceTaxCategoryIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceTaxCategories"
			searchContainer="<%= commerceTaxCategoryDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceTaxCategory"
				keyProperty="commerceTaxCategoryId"
				modelVar="commerceTaxCategory"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCommerceTaxCategory");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceTaxCategoryId", String.valueOf(commerceTaxCategory.getCommerceTaxCategoryId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
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
					path="/tax_category_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= hasManageCommerceTaxCategoriesPermission %>">
	<portlet:renderURL var="addCommerceTaxCategoryURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceTaxCategory" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-tax-category") %>' url="<%= addCommerceTaxCategoryURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteCommerceTaxCategories() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tax-categories" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceTaxCategoryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>