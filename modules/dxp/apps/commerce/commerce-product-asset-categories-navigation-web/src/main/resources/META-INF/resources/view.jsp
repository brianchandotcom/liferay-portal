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

<div class="navbar" id="<portlet:namespace />cpAssetCategoryNavigation">
	<ul class="nav navbar-blank navbar-nav navbar-site">

		<%
		for (AssetCategory assetCategory : assetCategories) {
		%>

			<li class="lfr-nav-item">
				<a class="dropdown-toggle" href="#"><span><%= HtmlUtil.escape(assetCategory.getTitle(locale)) %></span></a>

				<ul class="child-menu dropdown-menu" role="menu">

					<%
					for (AssetCategory assetCategoryLevel1 : cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(assetCategory.getCategoryId())) {
					%>

						<li class="child-menu-level-1">
							<ul class="link-list">
								<li class="dropdown-header"><%= HtmlUtil.escape(assetCategoryLevel1.getTitle(locale)) %></li>
								<li class="child-menu-level-2">

									<%
									for (AssetCategory assetCategoryLevel2 : cpAssetCategoriesNavigationDisplayContext.getChildAssetCategories(assetCategoryLevel1.getCategoryId())) {
									%>

										<li>
											<a href="<%= cpAssetCategoriesNavigationDisplayContext.getFriendlyURL(assetCategoryLevel2.getCategoryId(), themeDisplay) %>" role="menuitem"><%= HtmlUtil.escape(assetCategoryLevel2.getTitle(locale)) %></a>
										</li>

									<%
									}
									%>

								</li>
							</ul>
						</li>

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

						<span class="dropdown-header"><%= assetCategory.getTitle(locale) %></span>
						<p class="description"><%= assetCategory.getDescription(locale) %></p>
					</div>
				</ul>
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