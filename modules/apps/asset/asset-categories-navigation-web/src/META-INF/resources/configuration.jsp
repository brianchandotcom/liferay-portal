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

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset>
		<aui:select label="vocabularies" name="preferences--allAssetVocabularies--">
			<aui:option label="all" selected="<%= assetCategoriesNavigationPortletInstanceConfiguration.allAssetVocabularies() %>" value="<%= true %>" />
			<aui:option label="filter[action]" selected="<%= !assetCategoriesNavigationPortletInstanceConfiguration.allAssetVocabularies() %>" value="<%= false %>" />
		</aui:select>

		<aui:input name="preferences--assetVocabularyIds--" type="hidden" />

		<div class="<%= assetCategoriesNavigationPortletInstanceConfiguration.allAssetVocabularies() ? "hide" : "" %>" id="<portlet:namespace />assetVocabulariesBoxes">
			<liferay-ui:input-move-boxes
				leftBoxName="currentAssetVocabularyIds"
				leftList="<%= assetCategoriesNavigationDisplayContext.getCurrentVocabularyNames() %>"
				leftReorder="true"
				leftTitle="current"
				rightBoxName="availableAssetVocabularyIds"
				rightList="<%= assetCategoriesNavigationDisplayContext.getAvailableVocabularyNames() %>"
				rightTitle="available"
			/>
		</div>

		<div class="display-template">
			<liferay-ui:ddm-template-selector
				className="<%= AssetCategory.class.getName() %>"
				displayStyle="<%= assetCategoriesNavigationPortletInstanceConfiguration.displayStyle() %>"
				displayStyleGroupId="<%= assetCategoriesNavigationPortletInstanceConfiguration.displayStyleGroupId(themeDisplay.getScopeGroupId()) %>"
				refreshURL="<%= configurationRenderURL %>"
				showEmptyOption="<%= true %>"
			/>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assetVocabularyIds').val(Liferay.Util.listSelect(form.fm('currentAssetVocabularyIds')));

		submitForm(form);
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />allAssetVocabularies', 'false', '<portlet:namespace />assetVocabulariesBoxes');
</aui:script>