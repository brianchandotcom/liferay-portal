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

<aui:fieldset markupView="lexicon">
	<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>" id="selectionStyleDynamic" label="dynamic" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC %>" />

	<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>" id="selectionStyleManual" label="manual" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_MANUAL %>" />

	<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleAssetList() %>" id="selectionStyleAssetList" label="collection" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST %>" />
</aui:fieldset>

<script>
	function <portlet:namespace />chooseSelectionStyle() {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				cmd: 'selection-style',
			},
		});
	}
</script>