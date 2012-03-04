<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

String className = document.get(Field.ENTRY_CLASS_NAME);

String entryTitle = null;
String entrySummary = null;
PortletURL viewFullContentURL = null;
String viewURL = null;

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

if (assetRendererFactory != null) {
	long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

	long resourcePrimKey = GetterUtil.getLong(document.get(Field.ROOT_ENTRY_CLASS_PK));

	if (resourcePrimKey > 0) {
		classPK = resourcePrimKey;
	}

	AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);

	AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(classPK);

	viewFullContentURL = _getViewFullContentURL(request, themeDisplay, PortletKeys.ASSET_PUBLISHER, document);

	viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
	viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
	viewFullContentURL.setParameter("type", assetRendererFactory.getType());

	if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
		if ((assetRenderer.getGroupId() > 0) && (assetRenderer.getGroupId() != scopeGroupId)) {
			viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
		}

		viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
	}

	if (viewInContext) {
		String viewFullContentURLString = viewFullContentURL.toString();

		viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

		viewURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString);
	}
	else {
		viewURL = viewFullContentURL.toString();
	}

	entryTitle = assetRenderer.getTitle(locale);
	entrySummary = assetRenderer.getSummary(locale);
}
else {
	String portletId = document.get(Field.PORTLET_ID);

	viewFullContentURL = _getViewFullContentURL(request, themeDisplay, portletId, document);

	viewURL = viewFullContentURL.toString();

	Indexer indexer = IndexerRegistryUtil.getIndexer(className);

	String snippet = document.get(Field.SNIPPET);

	Summary summary = indexer.getSummary(document, locale, snippet, viewFullContentURL);

	if (viewInContext) {
		viewURL = viewFullContentURL.toString();
	}

	entryTitle = summary.getTitle();
	entrySummary = summary.getContent();
}

entrySummary = StringUtil.shorten(entrySummary, 200);

viewURL = _checkViewURL(themeDisplay, viewURL, currentURL);

String[] queryTerms = (String[])request.getAttribute("search.jsp-queryTerms");

PortletURL portletURL = (PortletURL)request.getAttribute("search.jsp-portletURL");
%>

<span class="asset-entry">
	<span class="asset-entry-type">
		<%= ResourceActionsUtil.getModelResource(themeDisplay.getLocale(), className) %>
	</span>

	<span class="asset-entry-title">
		<a href="<%= viewURL %>">
			<c:if test="<%= assetRendererFactory != null %>">
				<img alt="" src="<%= assetRendererFactory.getIconPath(renderRequest) %>" />
			</c:if>

			<%= StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms) %>
		</a>
	</span>

	<%
	String[] assetCategoryIds = document.getValues(Field.ASSET_CATEGORY_IDS);
	String[] assetTagNames = document.getValues(Field.ASSET_TAG_NAMES);
	%>

	<c:if test="<%= Validator.isNotNull(entrySummary) || Validator.isNotNull(assetCategoryIds[0]) || Validator.isNotNull(assetTagNames[0]) %>">
		<div class="asset-entry-content">
			<c:if test="<%= Validator.isNotNull(entrySummary) %>">
				<span class="asset-entry-summary">
					<%= StringUtil.highlight(HtmlUtil.escape(entrySummary), queryTerms) %>
				</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(assetTagNames[0]) %>">
				<div class="asset-entry-tags">

					<%
					for (int i = 0; i < assetTagNames.length; i++) {
						String assetTagName = assetTagNames[i].trim();

						PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

						tagURL.setParameter(Field.ASSET_TAG_NAMES, assetTagName);
					%>

						<c:if test="<%= i == 0 %>">
							<div class="taglib-asset-tags-summary">
						</c:if>

						<a class="tag" href="<%= tagURL.toString() %>"><%= assetTagName %></a>

						<c:if test="<%= (i + 1) == assetTagNames.length %>">
							</div>
						</c:if>

					<%
					}
					%>

				</div>
			</c:if>

			<c:if test="<%= Validator.isNotNull(assetCategoryIds[0]) %>">
				<div class="asset-entry-categories">

					<%
					for (int i = 0; i < assetCategoryIds.length; i++) {
						long assetCategoryId = GetterUtil.getLong(assetCategoryIds[i]);

						AssetCategory assetCategory = null;

						try {
							assetCategory = AssetCategoryLocalServiceUtil.getCategory(assetCategoryId);
						}
						catch (NoSuchCategoryException nsce) {
						}

						if (assetCategory == null) {
							continue;
						}

						AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(assetCategory.getVocabularyId());

						PortletURL categoryURL = PortletURLUtil.clone(portletURL, renderResponse);

						categoryURL.setParameter(Field.ASSET_CATEGORY_TITLES, assetCategory.getTitle(LocaleUtil.getDefault()));
					%>

						<c:if test="<%= i == 0 %>">
							<div class="taglib-asset-categories-summary">
								<span class="asset-vocabulary">
									<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>:
								</span>
						</c:if>

						<a class="asset-category" href="<%= categoryURL.toString() %>">
							<%= _buildAssetCategoryPath(assetCategory, locale) %>
						</a>

						<c:if test="<%= (i + 1) == assetCategoryIds.length %>">
							</div>
						</c:if>

					<%
					}
					%>

				</div>
			</c:if>
		</div>
	</c:if>
</span>