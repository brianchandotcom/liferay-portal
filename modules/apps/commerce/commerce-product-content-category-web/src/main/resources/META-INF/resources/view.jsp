<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCategoryContentDisplayContext cpCategoryContentDisplayContext = (CPCategoryContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

AssetCategory assetCategory = cpCategoryContentDisplayContext.getAssetCategory();

List<AssetCategory> assetCategoryList = new ArrayList<>();

assetCategoryList.add(assetCategory);
%>

<liferay-ddm:template-renderer
	className="<%= CPCategoryContentPortlet.class.getName() %>"
	contextObjects='<%=
		HashMapBuilder.<String, Object>put(
			"assetCategory", assetCategory
		).put(
			"cpCategoryContentDisplayContext", cpCategoryContentDisplayContext
		).build()
	%>'
	displayStyle="<%= cpCategoryContentDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpCategoryContentDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= assetCategoryList %>"
>
	<c:if test="<%= assetCategory != null %>">
		<div class="category-detail">

			<%
			String imgURL = cpCategoryContentDisplayContext.getDefaultImageSrc();
			%>

			<c:if test="<%= Validator.isNotNull(imgURL) %>">
				<div class="category-image">
					<img class="img-fluid" src="<%= HtmlUtil.escapeAttribute(imgURL) %>" />
				</div>
			</c:if>

			<div class="container-fluid">
				<h1 class="category-title"><%= HtmlUtil.escape(assetCategory.getTitle(languageId)) %></h1>

				<p class="category-description"><%= HtmlUtil.stripHtml(assetCategory.getDescription(languageId)) %></p>
			</div>
		</div>
	</c:if>
</liferay-ddm:template-renderer>