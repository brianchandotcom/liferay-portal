<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ShoppingCategory category = (ShoppingCategory)request.getAttribute(WebKeys.SHOPPING_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId");

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId", ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
%>

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/shopping/edit_category" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCategory();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="category"
	/>

	<liferay-ui:error exception="<%= CategoryNameException.class %>" message="please-enter-a-valid-name" />

	<c:if test="<%= parentCategoryId != ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
		<div class="breadcrumbs">
			<%= ShoppingUtil.getBreadcrumbs(parentCategoryId, pageContext, renderRequest, renderResponse) %>
		</div>
	</c:if>

	<aui:model-context bean="<%= category %>" model="<%= ShoppingCategory.class %>" />

	<aui:fieldset>
		<c:if test="<%= category != null %>">

			<%
			String parentCategoryName = "";

			try {
				ShoppingCategory parentCategory = ShoppingCategoryServiceUtil.getCategory(parentCategoryId);

				parentCategoryName = parentCategory.getName();
			}
			catch (NoSuchCategoryException nsce) {
			}
			%>

			<portlet:renderURL var="viewCategoryURL">
				<portlet:param name="struts_action" value="/shopping/view" />
				<portlet:param name="categoryId" value="<%= String.valueOf(parentCategoryId) %>" />
			</portlet:renderURL>

			<div class="control-group">
				<aui:input label="parent-category" name="parentCategoryName" type="resource" value="<%= parentCategoryName %>" />

				<portlet:renderURL var="selectCategoryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/shopping/select_category" />
					<portlet:param name="categoryId" value="<%= String.valueOf(parentCategoryId) %>" />
				</portlet:renderURL>

				<%
				String taglibOpenCategoryWindow = "var categoryWindow = window.open('" + selectCategoryURL + "', 'category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void('');categoryWindow.focus();";
				%>

				<aui:button onClick="<%= taglibOpenCategoryWindow %>" value="select" />

				<aui:button onClick='<%= renderResponse.getNamespace() + "removeCategory();" %>' value="remove" />
			</div>

			<div id="<portlet:namespace />merge-with-parent-checkbox-div"
				<c:if test="<%= category.getParentCategoryId() == ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
					style="display: none;"
				</c:if>
			>
				<aui:input name="mergeWithParentCategory" type="checkbox" />
			</div>
		</c:if>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" cssClass="lfr-input-text-container" name="name" />

		<aui:input cssClass="lfr-textarea-container" name="description" />

		<c:if test="<%= category == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= ShoppingCategory.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCategory() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (category == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />removeCategory',
		function() {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = "<%= ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>";

			document.getElementById('<portlet:namespace />parentCategoryName').value = '';

			var mergeWithParent = A.one('#<portlet:namespace />merge-with-parent-checkbox-div');
			var mergeWithParentCategory = A.one('#<portlet:namespace />mergeWithParentCategoryCheckbox');

			if (mergeWithParent) {
				mergeWithParent.hide();
			}

			if (mergeWithParentCategory) {
				mergeWithParentCategory.set('checked', false);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectCategory',
		function(parentCategoryId, parentCategoryName) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />parentCategoryId.value = parentCategoryId;

			document.getElementById('<portlet:namespace />parentCategoryName').value = parentCategoryName;

			if (parentCategoryId != <%= ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>) {
				var mergeWithParent = A.one('#<portlet:namespace />merge-with-parent-checkbox-div');

				if (mergeWithParent) {
					mergeWithParent.show();
				}
			}
		},
		['aui-base']
	);
</aui:script>