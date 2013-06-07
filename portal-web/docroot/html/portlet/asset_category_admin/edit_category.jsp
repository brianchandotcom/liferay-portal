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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_asset_category_admin_edit_category") + StringPool.UNDERLINE;

AssetCategory category = (AssetCategory)request.getAttribute(WebKeys.ASSET_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "categoryId");

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId");

List<AssetVocabulary> vocabularies = (List<AssetVocabulary>)request.getAttribute(WebKeys.ASSET_VOCABULARIES);

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

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

<portlet:actionURL var="editCategoryURL">
	<portlet:param name="struts_action" value="/asset_category_admin/edit_category" />
</portlet:actionURL>

<aui:form action="<%= editCategoryURL %>" cssClass="update-category-form" method="get" name='<%= randomNamespace + "fm" %>'>
	<div class="hide lfr-message-response" id="categoryMessagesEdit"></div>

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= category == null ? Constants.ADD : Constants.UPDATE %>" />

	<aui:model-context bean="<%= category %>" model="<%= AssetCategory.class %>" />

	<aui:fieldset>
		<div>
			<div class="add-category-layer asset-category-layer">
				<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />
				<aui:input name="parentCategoryId" type="hidden" value="<%= parentCategoryId %>" />

				<aui:input autoFocus="<%= true %>" cssClass="category-name" label="name" name="title" />

				<aui:input name="description" />

				<c:choose>
					<c:when test="<%= parentCategoryId == 0 %>">
						<aui:select cssClass="vocabulary-select-list" label="to-vocabulary" name="vocabularyId">

							<%
							for (AssetVocabulary vocabulary : vocabularies) {
							%>

								<aui:option label="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>" selected="<%= vocabulary.getVocabularyId() == vocabularyId %>" value="<%= vocabulary.getVocabularyId() %>" />

							<%
							}
							%>

						</aui:select>
					</c:when>
					<c:otherwise>
						<aui:input name="vocabularyId" type="hidden" value="<%= vocabularyId %>" />
					</c:otherwise>
				</c:choose>

				<liferay-ui:panel-container extended="<%= false %>" id="assetCategoryPanelContainer" persistState="<%= true %>">
					<c:if test="<%= category == null %>">
						<liferay-ui:panel collapsible="<%= true %>" cssClass="category-permissions-actions" defaultState="open" extended="<%= true %>" id="assetCategoryPermissionsPanel" persistState="<%= true %>" title="permissions">
							<liferay-ui:input-permissions
								modelName="<%= AssetCategory.class.getName() %>"
							/>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" helpMessage="properties-are-a-way-to-add-more-detailed-information-to-a-specific-category" id="assetCategoryPropertiesPanel" persistState="<%= true %>" title="properties">
						<aui:fieldset cssClass="category-categoryProperties" id="categoryProperties">

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

					<c:if test="<%= category != null %>">
						<c:if test="<%= AssetCategoryPermission.contains(permissionChecker, category, ActionKeys.DELETE) %>">
							<aui:button id="deleteCategoryButton" value="delete" />
						</c:if>

						<c:if test="<%= AssetCategoryPermission.contains(permissionChecker, category, ActionKeys.PERMISSIONS) %>">
							<liferay-security:permissionsURL
								modelResource="<%= AssetCategory.class.getName() %>"
								modelResourceDescription="<%= category.getTitle(locale) %>"
								resourcePrimKey="<%= String.valueOf(category.getCategoryId()) %>"
								var="permissionsURL"
								windowState="<%= LiferayWindowState.POP_UP.toString() %>"
							/>

							<aui:button data-url="<%= permissionsURL %>" id="updateCategoryPermissions" value="permissions" />
						</c:if>
					</c:if>

					<aui:button cssClass="close-panel" type="cancel" value="close" />
				</aui:button-row>
			</div>
		</div>
	</aui:fieldset>
</aui:form>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: 'fieldset#<portlet:namespace />categoryProperties',
			fieldIndexes: '<portlet:namespace />categoryPropertiesIndexes'
		}
	).render();

	var categoryPropertiesTrigger = A.one('fieldset#<portlet:namespace />categoryProperties');

	if (categoryPropertiesTrigger) {
		categoryPropertiesTrigger.setData('autoFieldsInstance', autoFields);
	}
</aui:script>