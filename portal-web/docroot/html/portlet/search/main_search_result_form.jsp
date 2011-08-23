<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Document document = (Document)row.getObject();

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
String[] queryTerms = (String[])request.getAttribute("view.jsp-queryTerms");

String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));
long resourcePrimKey = GetterUtil.getLong(document.get(Field.ROOT_ENTRY_CLASS_PK));
String portletId = document.get(Field.PORTLET_ID);
String snippet = document.get(Field.SNIPPET);

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entryClassName);

AssetRenderer assetRenderer = null;

PortletURL viewFullContentURL = null;

if (assetRendererFactory != null) {
	if (resourcePrimKey > 0) {
		classPK = resourcePrimKey;
	}

	AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(entryClassName, classPK);

	assetRenderer = assetRendererFactory.getAssetRenderer(classPK);

	viewFullContentURL = _getURL(request, themeDisplay, PortletKeys.ASSET_PUBLISHER, document);

	viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
	viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
	viewFullContentURL.setParameter("type", assetRendererFactory.getType());

	if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
		if ((assetRenderer.getGroupId() > 0) && (assetRenderer.getGroupId() != scopeGroupId)) {
			viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
		}

		viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
	}
}
else {
	viewFullContentURL = _getURL(request, themeDisplay, portletId, document);
}

String viewURL = null;

if (viewInContext) {
	String viewFullContentURLString = viewFullContentURL.toString();

	viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

	viewURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString);
}
else {
	viewURL = viewFullContentURL.toString();
}

if (Validator.isNull(viewURL)) {
	viewURL = viewFullContentURL.toString();
}

viewURL = _checkViewURL(themeDisplay, viewURL, currentURL);

String entryTitle = null;
String entrySummary = null;

if (assetRenderer != null) {
	entryTitle = assetRenderer.getTitle(locale);
	entrySummary = StringUtil.shorten(assetRenderer.getSummary(locale), 200);
}
else {
	Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

	Summary summaryObj = indexer.getSummary(document, locale, snippet, viewFullContentURL);

	entryTitle = summaryObj.getTitle();
	entrySummary = StringUtil.shorten(summaryObj.getContent(), 200);
}
%>

<span class="asset-entry">
	<span class="asset-entry-type">
		<%= ResourceActionsUtil.getModelResource(themeDisplay.getLocale(), entryClassName) %>
	</span>

	<span class="asset-entry-title">
		<a href="<%= viewURL %>">
			<c:if test="<%= assetRendererFactory != null %>">
				<img alt="\" src="<%= assetRendererFactory.getIconPath(renderRequest) %>" />
			</c:if>

			<%= StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms) %>
		</a>
	</span>

	<c:if test="<%= Validator.isNotNull(entrySummary) %>" >
		<span class="asset-entry-summary">
			<%= StringUtil.highlight(HtmlUtil.escape(entrySummary), queryTerms) %>
		</span>
	</c:if>

	<%
	String[] tags = document.getValues(Field.ASSET_TAG_NAMES);
	%>

	<c:if test="<%= Validator.isNotNull(tags[0]) %>">
		<div class="asset-entry-tags">

			<%
			for (int k = 0; k < tags.length; k++) {
				String tag = tags[k];

				String newKeywords = tag.trim();

				PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

				tagURL.setParameter(Field.ASSET_TAG_NAMES, newKeywords);
			%>

				<c:if test="<%= k == 0 %>">
					<div class="taglib-asset-tags-summary">
				</c:if>

				<a class="tag" href="<%= tagURL.toString() %>"><%= tag %></a>

				<c:if test="<%= (k + 1) == tags.length %>">
					</div>
				</c:if>

			<%
			}
			%>

		</div>
	</c:if>

	<%
	String[] categoryIds = document.getValues(Field.ASSET_CATEGORY_IDS);
	%>

	<c:if test="<%= Validator.isNotNull(categoryIds[0]) %>">
		<div class="asset-entry-categories">

			<%
			for (int k = 0; k < categoryIds.length; k++) {

				String categoryId = categoryIds[k];

				AssetCategory assetCategory = null;

				try {
					assetCategory = AssetCategoryLocalServiceUtil.getCategory(GetterUtil.getLong(categoryId));
				}
				catch (NoSuchCategoryException nsce) {
				}

				if (assetCategory == null) {
					continue;
				}

				AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(assetCategory.getVocabularyId());

				PortletURL categoryURL = PortletURLUtil.clone(portletURL, renderResponse);

				categoryURL.setParameter(Field.ASSET_CATEGORY_NAMES, assetCategory.getName());
			%>

				<c:if test="<%= k == 0 %>">
					<div class="taglib-asset-categories-summary">
						<span class="asset-vocabulary">
							<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>:
						</span>
				</c:if>

				<a class="asset-category" href="<%= categoryURL.toString() %>">
					<%= _buildCategoryPath(assetCategory, locale) %>
				</a>

				<c:if test="<%= (k + 1) == categoryIds.length %>">
					</div>
				</c:if>

			<%
			}
			%>

		</div>
	</c:if>
</span>