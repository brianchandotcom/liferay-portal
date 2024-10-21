<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:fieldset markupView="lexicon">
	<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleAssetList() %>" id="selectionStyleAssetList" inlineField="<%= true %>" label="collection" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST %>" />

	<div class="d-flex">
		<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>" id="selectionStyleDynamic" inlineField="<%= true %>" label="dynamic" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC %>" />

		<liferay-frontend:feature-indicator
			type="deprecated"
		/>
	</div>

	<div class="d-flex">
		<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>" id="selectionStyleManual" inlineField="<%= true %>" label="manual" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_MANUAL %>" />

		<liferay-frontend:feature-indicator
			type="deprecated"
		/>
	</div>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />chooseSelectionStyle() {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				cmd: 'selection-style',
			},
		});
	}
</aui:script>