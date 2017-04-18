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
String redirect = ParamUtil.getString(request, "redirect");

long categoryId = ParamUtil.getLong(request, "categoryId");

AssetCategory category = AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId");

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

if (Validator.isNull(redirect)) {
	PortletURL backURL = renderResponse.createRenderURL();

	backURL.setParameter("mvcPath", "/view_categories.jsp");

	if (parentCategoryId > 0) {
		backURL.setParameter("categoryId", String.valueOf(parentCategoryId));
	}

	if (vocabularyId > 0) {
		backURL.setParameter("vocabularyId", String.valueOf(vocabularyId));
	}

	redirect = backURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((category == null) ? LanguageUtil.get(request, "add-new-category") : category.getTitle(locale)));
%>

<portlet:actionURL name="editCategory" var="editCategoryURL">
	<portlet:param name="mvcPath" value="/edit_category.jsp" />
	<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabularyId) %>" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

	<liferay-ui:error exception="<%= AssetCategoryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= CategoryPropertyKeyException.class %>" message="please-enter-a-valid-property-key" />
	<liferay-ui:error exception="<%= CategoryPropertyValueException.class %>" message="please-enter-a-valid-property-value" />
	<liferay-ui:error exception="<%= DuplicateCategoryException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= DuplicateCategoryPropertyException.class %>" message="please-enter-a-unique-property-key" />

	<liferay-ui:form-navigator
		formModelBean="<%= category %>"
		formName="fm1"
		id="<%= AssetCategoriesConstants.FORM_NAVIGATOR_ID_EDIT_CATEGORY %>"
		markupView="lexicon"
		showButtons="<%= false %>"
	/>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>