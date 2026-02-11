<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_categories_summary/init.jsp" %>

<%
AssetCategoriesSummaryDisplayContext assetCategoriesSummaryDisplayContext = new AssetCategoriesSummaryDisplayContext(request);

String paramName = assetCategoriesSummaryDisplayContext.getParamName();
PortletURL portletURL = assetCategoriesSummaryDisplayContext.getPortletURL();

List<AssetCategory> categories = assetCategoriesSummaryDisplayContext.getCategories();

List<AssetVocabulary> vocabularies = assetCategoriesSummaryDisplayContext.getVocabularies(scopeGroupId);

for (AssetVocabulary vocabulary : vocabularies) {
	List<AssetCategory> curCategories = assetCategoriesSummaryDisplayContext.filterCategories(categories, vocabulary);
%>

	<c:if test="<%= !curCategories.isEmpty() %>">
		<c:choose>
			<c:when test='<%= Objects.equals(assetCategoriesSummaryDisplayContext.getDisplayStyle(), "simple-category") %>'>
				<span class="taglib-asset-categories-summary">
					<c:choose>
						<c:when test="<%= portletURL != null %>">

							<%
							for (AssetCategory category : curCategories) {
								portletURL.setParameter(paramName, String.valueOf(category.getCategoryId()));
							%>

								<a class="label label-dark label-lg text-uppercase" href="<%= HtmlUtil.escape(portletURL.toString()) %>"><%= HtmlUtil.escape(category.getTitle(themeDisplay.getLocale())) %></a>

							<%
							}
							%>

						</c:when>
						<c:otherwise>

							<%
							for (AssetCategory category : curCategories) {
							%>

								<clay:label
									displayType="dark"
									label="<%= HtmlUtil.escape(category.getTitle(themeDisplay.getLocale())) %>"
									large="<%= true %>"
									translated="<%= false %>"
								/>

							<%
							}
							%>

						</c:otherwise>
					</c:choose>
				</span>
			</c:when>
			<c:otherwise>
				<span class="taglib-asset-categories-summary">
					<%= HtmlUtil.escape(vocabulary.getUnambiguousTitle(vocabularies, themeDisplay.getSiteGroupId(), themeDisplay.getLocale())) %>:

					<c:choose>
						<c:when test="<%= portletURL != null %>">

							<%
							for (AssetCategory category : curCategories) {
								portletURL.setParameter(paramName, String.valueOf(category.getCategoryId()));
							%>

								<a class="asset-category mb-1 mr-1 pr-2 text-uppercase" href="<%= HtmlUtil.escape(portletURL.toString()) %>"><%= assetCategoriesSummaryDisplayContext.buildCategoryPath(category, themeDisplay) %></a>

							<%
							}
							%>

						</c:when>
						<c:otherwise>

							<%
							for (AssetCategory category : curCategories) {
							%>

								<span class="asset-category mb-1 mr-1 pr-2 text-uppercase">
									<%= assetCategoriesSummaryDisplayContext.buildCategoryPath(category, themeDisplay) %>
								</span>

							<%
							}
							%>

						</c:otherwise>
					</c:choose>
				</span>
			</c:otherwise>
		</c:choose>
	</c:if>

<%
}
%>