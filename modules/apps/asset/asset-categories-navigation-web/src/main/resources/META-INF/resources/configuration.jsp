<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset>
			<div class="display-template">
				<liferay-template:template-selector
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
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				assetVocabularyIds: Liferay.Util.getSelectedOptionValues(
					Liferay.Util.getFormElement(form, 'currentAssetVocabularyIds')
				),
			},
		});
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />allAssetVocabularies',
		'false',
		'<portlet:namespace />assetVocabulariesBoxes'
	);
</aui:script>