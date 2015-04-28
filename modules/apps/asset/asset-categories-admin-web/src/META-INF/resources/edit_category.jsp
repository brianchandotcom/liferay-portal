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

int[] categoryPropertiesIndexes = null;

List<AssetCategoryProperty> categoryProperties = Collections.emptyList();

String categoryPropertiesIndexesParam = ParamUtil.getString(request, "categoryPropertiesIndexes");

if (Validator.isNotNull(categoryPropertiesIndexesParam)) {
	categoryProperties = new ArrayList<AssetCategoryProperty>();

	categoryPropertiesIndexes = StringUtil.split(categoryPropertiesIndexesParam, 0);

	for (int categoryPropertiesIndex : categoryPropertiesIndexes) {
		categoryProperties.add(new AssetCategoryPropertyImpl());
	}
}
else {
	if (category != null) {
		categoryProperties = AssetCategoryPropertyServiceUtil.getCategoryProperties(category.getCategoryId());

		categoryPropertiesIndexes = new int[categoryProperties.size()];

		for (int i = 0; i < categoryProperties.size(); i++) {
			categoryPropertiesIndexes[i] = i;
		}
	}

	if (categoryProperties.isEmpty()) {
		categoryProperties = new ArrayList<AssetCategoryProperty>();

		categoryProperties.add(new AssetCategoryPropertyImpl());

		categoryPropertiesIndexes = new int[] {0};
	}

	if (categoryPropertiesIndexes == null) {
		categoryPropertiesIndexes = new int[0];
	}
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= (category != null) ? category.getTitle(locale) : "add-new-category" %>'
/>

<portlet:actionURL name="editCategory" var="editCategoryURL">
	<portlet:param name="mvcPath" value="/edit_category.jsp" />
	<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabularyId) %>" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

	<liferay-ui:error exception="<%= AssetCategoryNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= DuplicateCategoryException.class %>" message="please-enter-a-unique-name" />

	<aui:model-context bean="<%= category %>" model="<%= AssetCategory.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= true %>" label="name" name="title" />

		<aui:input name="description" />

		<liferay-ui:panel-container extended="<%= false %>" id="assetCategoryPanelContainer" persistState="<%= true %>">
			<c:if test="<%= category == null %>">
				<liferay-ui:panel collapsible="<%= true %>" cssClass="category-permissions-actions" defaultState="open" extended="<%= true %>" id="assetCategoryPermissionsPanel" persistState="<%= true %>" title="permissions">
					<liferay-ui:input-permissions
						modelName="<%= AssetCategory.class.getName() %>"
					/>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" helpMessage="properties-are-a-way-to-add-more-detailed-information-to-a-specific-category" id="assetCategoryPropertiesPanel" persistState="<%= true %>" title="properties">
				<aui:fieldset id="categoryPropertiesId">

					<%
					for (int i = 0; i < categoryPropertiesIndexes.length; i++) {
						int categoryPropertiesIndex = categoryPropertiesIndexes[i];

						AssetCategoryProperty categoryProperty = categoryProperties.get(i);
					%>

						<aui:model-context bean="<%= categoryProperty %>" model="<%= AssetCategoryProperty.class %>" />

						<div class="lfr-form-row lfr-form-row-inline">
							<div class="row-fields">
								<aui:input fieldParam='<%= "key" + categoryPropertiesIndex %>' id='<%= "key" + categoryPropertiesIndex %>' name="key" />

								<aui:input fieldParam='<%= "value" + categoryPropertiesIndex %>' id='<%= "value" + categoryPropertiesIndex %>' name="value" />
							</div>
						</div>

					<%
					}
					%>

					<aui:input name="categoryPropertiesIndexes" type="hidden" value="<%= StringUtil.merge(categoryPropertiesIndexes) %>" />
				</aui:fieldset>
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: 'fieldset#<portlet:namespace />categoryPropertiesId',
			fieldIndexes: '<portlet:namespace />categoryPropertiesIndexes',
			namespace: '<portlet:namespace />'
		}
	).render();

	var categoryPropertiesTrigger = A.one('fieldset#<portlet:namespace />categoryPropertiesId');

	if (categoryPropertiesTrigger) {
		categoryPropertiesTrigger.setData('autoFieldsInstance', autoFields);
	}
</aui:script>