<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectAssetCategoryInfoItemDisplayContext selectAssetCategoryInfoItemDisplayContext = (SelectAssetCategoryInfoItemDisplayContext)request.getAttribute(AssetCategoryItemSelectorWebKeys.SELECT_ASSET_CATEGORY_INFO_ITEM_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<div>
	<react:component
		module="js/SelectAssetCategoryInfoItem.es"
		props="<%= selectAssetCategoryInfoItemDisplayContext.getData() %>"
	/>
</div>