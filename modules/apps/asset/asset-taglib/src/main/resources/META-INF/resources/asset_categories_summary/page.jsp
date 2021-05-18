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

<%@ include file="/asset_categories_summary/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-asset:asset-categories-summary:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-categories-summary:classPK"));
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-categories-summary:displayStyle"), "default");
String paramName = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-categories-summary:paramName"), "categoryId");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:asset-categories-summary:portletURL");
int[] visibleTypes = (int[])request.getAttribute("liferay-asset:asset-categories-summary:visibleTypes");

List<AssetCategory> categories = (List<AssetCategory>)request.getAttribute("liferay-asset:asset-categories-summary:assetCategories");

if (ListUtil.isEmpty(categories)) {
	categories = AssetCategoryServiceUtil.getCategories(className, classPK);
}

AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(className, classPK);

List<AssetVocabulary> vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(SiteConnectedGroupGroupProviderUtil.getCurrentAndAncestorSiteAndDepotGroupIds((assetEntry != null) ? assetEntry.getGroupId() : scopeGroupId));

for (AssetVocabulary vocabulary : vocabularies) {
	List<AssetCategory> curCategories = _filterCategories(categories, vocabulary, visibleTypes);
%>

	<c:if test="<%= !curCategories.isEmpty() %>">
		<c:choose>
			<c:when test='<%= displayStyle.equals("simple-category") %>'>
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

								<a class="asset-category text-uppercase" href="<%= HtmlUtil.escape(portletURL.toString()) %>"><%= _buildCategoryPath(category, themeDisplay) %></a>

							<%
							}
							%>

						</c:when>
						<c:otherwise>

							<%
							for (AssetCategory category : curCategories) {
							%>

								<span class="asset-category text-uppercase">
									<%= _buildCategoryPath(category, themeDisplay) %>
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

<%!
private String _buildCategoryPath(AssetCategory category, ThemeDisplay themeDisplay) throws PortalException, SystemException {
	List<AssetCategory> ancestorCategories = category.getAncestors();

	if (ancestorCategories.isEmpty()) {
		return HtmlUtil.escape(category.getTitle(themeDisplay.getLocale()));
	}

	Collections.reverse(ancestorCategories);

	StringBundler sb = new StringBundler(ancestorCategories.size() * 2 + 1);

	for (AssetCategory ancestorCategory : ancestorCategories) {
		sb.append(HtmlUtil.escape(ancestorCategory.getTitle(themeDisplay.getLocale())));
		sb.append(" &raquo; ");
	}

	sb.append(HtmlUtil.escape(category.getTitle(themeDisplay.getLocale())));

	return sb.toString();
}

private List<AssetCategory> _filterCategories(List<AssetCategory> categories, AssetVocabulary vocabulary, int[] visibleTypes) {
	List<AssetCategory> filteredCategories = new ArrayList<AssetCategory>();

	for (AssetCategory category : categories) {
		if ((category.getVocabularyId() == vocabulary.getVocabularyId()) && ArrayUtil.contains(visibleTypes, vocabulary.getVisibilityType())) {
			filteredCategories.add(category);
		}
	}

	return filteredCategories;
}
%>