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
List<AssetCategory> assetCategories = cpAssetCategoriesNavigationDisplayContext.getAssetCategories();
%>

<div class="cp-asset-category-navigation" id="<portlet:namespace />cpAssetCategoryNavigation">
	<ul class="navbar-site split-button-dropdowns">

		<%
		for (AssetCategory assetCategory : assetCategories) {
		%>

			<li>
				<a class="" href="#avoooo"><span><%= HtmlUtil.escape(assetCategory.getTitle(locale)) %></span></a>

				<div class="child-menu dropdown-menu" role="menu">
					<div class="container-fluid-1280">

						<%
						for (AssetCategory assetCategoryLevel1 : cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(assetCategory.getCategoryId())) {
						%>

							<div class="child-menu-level-1">
								<h5><%= HtmlUtil.escape(assetCategoryLevel1.getTitle(locale)) %></h5>

								<ul class="">

									<%
									for (AssetCategory assetCategoryLevel2 : cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(assetCategoryLevel1.getCategoryId())) {
									%>

										<li>
											<a href="<%= cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(assetCategoryLevel2.getCategoryId(), themeDisplay) %>" role="menuitem"><%= HtmlUtil.escape(assetCategoryLevel2.getTitle(locale)) %></a>
										</li>

									<%
									}
									%>

								</ul>

							</div>

						<%
						}
						%>

						<div class="category-description">

							<%
							String imgURL = cpAssetCategoriesNavigationDisplayContext.getDefaultImageSrc(assetCategory.getCategoryId(), themeDisplay);
							%>

							<c:if test="<%= Validator.isNotNull(imgURL) %>">
								<img src="<%= imgURL %>" />
							</c:if>

							<h5><%= assetCategory.getTitle(locale) %></h5>
							<p class="description"><%= assetCategory.getDescription(locale) %></p>
						</div>
					</div>
				</div>
			</li>

		<%
		}
		%>

	</ul>
</div>

<aui:script use="liferay-navigation-interaction">
	var navigation = A.one('#<portlet:namespace />cpAssetCategoryNavigation');

	Liferay.Data.NAV_INTERACTION_LIST_SELECTOR = '.navbar-site';
	Liferay.Data.NAV_LIST_SELECTOR = '.navbar-site';

	if (navigation) {
		navigation.plug(Liferay.NavigationInteraction);
	}
</aui:script>