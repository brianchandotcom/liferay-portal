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

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<div class="display-template">
					<liferay-ddm:template-selector
						className="<%= AssetCategory.class.getName() %>"
						displayStyle="<%= assetCategoriesNavigationPortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= assetCategoriesNavigationDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>

				<aui:select label="vocabularies" name="preferences--allAssetVocabularies--">
					<aui:option label="all" selected="<%= assetCategoriesNavigationPortletInstanceConfiguration.allAssetVocabularies() %>" value="<%= true %>" />
					<aui:option label="filter[action]" selected="<%= !assetCategoriesNavigationPortletInstanceConfiguration.allAssetVocabularies() %>" value="<%= false %>" />
				</aui:select>

				<aui:input name="preferences--assetVocabularyIds--" type="hidden" />

				<div class="<%= assetCategoriesNavigationPortletInstanceConfiguration.allAssetVocabularies() ? "hide" : "" %>" id="<portlet:namespace />assetVocabulariesBoxes">
					<liferay-ui:input-move-boxes
						leftBoxName="currentAssetVocabularyIds"
						leftList="<%= assetCategoriesNavigationDisplayContext.getCurrentVocabularyNames() %>"
						leftReorder="<%= Boolean.TRUE.toString() %>"
						leftTitle="current"
						rightBoxName="availableAssetVocabularyIds"
						rightList="<%= assetCategoriesNavigationDisplayContext.getAvailableVocabularyNames() %>"
						rightTitle="available"
					/>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				assetVocabularyIds: Liferay.Util.listSelect(
					Liferay.Util.getFormElement(form, 'currentAssetVocabularyIds')
				)
			}
		});
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />allAssetVocabularies',
		'false',
		'<portlet:namespace />assetVocabulariesBoxes'
	);
</aui:script>