<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_categories_navigation/init.jsp" %>

<%
AssetCategoriesNavigationDisplayContext assetCategoriesNavigationDisplayContext = new AssetCategoriesNavigationDisplayContext(request, renderResponse);
%>

<c:choose>
	<c:when test="<%= !assetCategoriesNavigationDisplayContext.hasCategories() %>">
		<c:if test="<%= assetCategoriesNavigationDisplayContext.hidePortletWhenEmpty() %>">

			<%
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
			%>

		</c:if>

		<clay:alert
			displayType="info"
			message="there-are-no-categories"
		/>
	</c:when>
	<c:otherwise>
		<div class="categories-tree container-fluid container-fluid-max-xl" id="<%= assetCategoriesNavigationDisplayContext.getNamespace() %>categoriesContainer">
			<react:component
				module="asset_categories_navigation/js/AssetCategoriesNavigationTreeView"
				props="<%= assetCategoriesNavigationDisplayContext.getData() %>"
			/>
		</div>
	</c:otherwise>
</c:choose>

<%
if (assetCategoriesNavigationDisplayContext.getCategoryId() > 0) {
	AssetCategoryUtil.addPortletBreadcrumbEntries(assetCategoriesNavigationDisplayContext.getCategoryId(), request, renderResponse.createRenderURL(), false);
}
%>