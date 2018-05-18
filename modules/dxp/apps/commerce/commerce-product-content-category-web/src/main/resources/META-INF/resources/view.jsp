<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCategoryContentDisplayContext cpCategoryContentDisplayContext = (CPCategoryContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

AssetCategory assetCategory = cpCategoryContentDisplayContext.getAssetCategory();

List<AssetCategory> assetCategoryList = new ArrayList<>();

assetCategoryList.add(assetCategory);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpCategoryContentDisplayContext", cpCategoryContentDisplayContext);
%>

<liferay-ddm:template-renderer
	className="<%= CPCategoryContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
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
					<img class="img-fluid" src="<%= imgURL %>" />
				</div>
			</c:if>

			<div class="container-fluid">
				<h1 class="category-title"><%= assetCategory.getTitle(languageId) %></h1>
				<p class="category-description"><%= assetCategory.getDescription(languageId) %></p>
			</div>
		</div>
	</c:if>
</liferay-ddm:template-renderer>