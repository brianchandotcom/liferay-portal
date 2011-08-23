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

<%
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

StringBundler rowSB = new StringBundler();

rowSB.append("<a class=\"entry-title\" href=\"");
rowSB.append(viewURL);
rowSB.append("\">");

if (assetRendererFactory != null) {
	rowSB.append("<img alt=\"\" src=\"");
	rowSB.append(assetRendererFactory.getIconPath(renderRequest));
	rowSB.append("\" /> ");
}

rowSB.append(StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms));
rowSB.append("</a>");

rowSB.append("<br /><span class=\"entry-type\">");
rowSB.append(ResourceActionsUtil.getModelResource(themeDisplay.getLocale(), entryClassName));
rowSB.append("</span>");

if (Validator.isNotNull(entrySummary)) {
	rowSB.append("<br />");
	rowSB.append(StringUtil.highlight(HtmlUtil.escape(entrySummary), queryTerms));
}

// Tags

String[] tags = document.getValues(Field.ASSET_TAG_NAMES);

if (Validator.isNotNull(tags[0])) {
	rowSB.append("<br />");

	for (int k = 0; k < tags.length; k++) {
		String tag = tags[k];

		String newKeywords = tag.trim();

		PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

		tagURL.setParameter(Field.ASSET_TAG_NAMES, newKeywords);

		if (k == 0) {
			rowSB.append("<div class=\"entry-tags\">");
			rowSB.append("<div class=\"taglib-asset-tags-summary\">");
		}

		rowSB.append("<a class=\"tag\" href=\"");
		rowSB.append(tagURL.toString());
		rowSB.append("\">");
		rowSB.append(tag);
		rowSB.append("</a>");

		if ((k + 1) == tags.length) {
			rowSB.append("</div>");
			rowSB.append("</div>");
		}
	}
}

// Categories

String[] categoryIds = document.getValues(Field.ASSET_CATEGORY_IDS);

if (Validator.isNotNull(categoryIds[0])) {
	if (Validator.isNotNull(tags[0])) {
		rowSB.append("<br />");
	}

	for (int k = 0; k < categoryIds.length; k++) {

		String categoryId = categoryIds[k];

		AssetCategory category = null;

		try {
			category = AssetCategoryLocalServiceUtil.getCategory(GetterUtil.getLong(categoryId));
		}
		catch (NoSuchCategoryException nsce) {
			continue;
		}

		AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(category.getVocabularyId());

		PortletURL categoryURL = PortletURLUtil.clone(portletURL, renderResponse);

		categoryURL.setParameter(Field.ASSET_CATEGORY_NAMES, category.getName());

		if (k == 0) {
			rowSB.append("<div class=\"entry-categories\">");
			rowSB.append("<div class=\"taglib-asset-categories-summary\">");
			rowSB.append("<span class=\"asset-vocabulary\">");
			rowSB.append(HtmlUtil.escape(vocabulary.getTitle(locale)));
			rowSB.append(":</span> ");
		}

		rowSB.append("<a class=\"asset-category\" href=\"");
		rowSB.append(categoryURL.toString());
		rowSB.append("\">");
		rowSB.append(_buildCategoryPath(category, locale));
		rowSB.append("</a>");

		if ((k + 1) == categoryIds.length) {
			rowSB.append("</div>");
			rowSB.append("</div>");
		}
	}
}
%>

<span class="document">
	<%= rowSB.toString() %>
</span>