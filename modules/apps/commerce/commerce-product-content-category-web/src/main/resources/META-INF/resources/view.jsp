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
			String imgURL = cpCategoryContentDisplayContext.getDefaultImageSrc(themeDisplay);
			%>

			<c:if test="<%= Validator.isNotNull(imgURL) %>">
				<div class="category-image">
					<img class="img-fluid" src="<%= HtmlUtil.escapeAttribute(imgURL) %>" />
				</div>
			</c:if>

			<div class="container-fluid">
				<h1 class="category-title"><%= HtmlUtil.escape(assetCategory.getTitle(languageId)) %></h1>

				<p class="category-description"><%= HtmlUtil.escape(assetCategory.getDescription(languageId)) %></p>
			</div>
		</div>
	</c:if>
</liferay-ddm:template-renderer>