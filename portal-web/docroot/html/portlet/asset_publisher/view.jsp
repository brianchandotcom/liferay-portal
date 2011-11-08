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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
if (mergeUrlTags || mergeLayoutTags) {
	String[] compilerTagNames = new String[0];

	if (mergeUrlTags) {
		compilerTagNames = (String[])request.getAttribute(WebKeys.TAGS_COMPILER_ENTRIES);
	}

	if (mergeLayoutTags) {
		Set<String> layoutTagNames = AssetUtil.getLayoutTagNames(request);

		if (!layoutTagNames.isEmpty()) {
			compilerTagNames = ArrayUtil.append(compilerTagNames, layoutTagNames.toArray(new String[layoutTagNames.size()]));
		}
	}

	String titleEntry = null;

	if ((compilerTagNames != null) && (compilerTagNames.length > 0)) {
		String[] newAssetTagNames = ArrayUtil.append(allAssetTagNames, compilerTagNames);

		allAssetTagNames = ArrayUtil.distinct(newAssetTagNames, new StringComparator());

		long[] allAssetTagIds = AssetTagLocalServiceUtil.getTagIds(scopeGroupId, allAssetTagNames);

		assetEntryQuery.setAllTagIds(allAssetTagIds);

		titleEntry = compilerTagNames[compilerTagNames.length - 1];
	}

	String portletTitle = HtmlUtil.unescape(portletDisplay.getTitle());

	portletTitle = AssetUtil.substituteTagPropertyVariables(scopeGroupId, titleEntry, portletTitle);

	renderResponse.setTitle(portletTitle);
}
else {
	allAssetTagNames = ArrayUtil.distinct(allAssetTagNames, new StringComparator());
}

for (String curAssetTagName : allAssetTagNames) {
	try {
		AssetTag assetTag = AssetTagLocalServiceUtil.getTag(scopeGroupId, curAssetTagName);

		AssetTagProperty journalTemplateIdProperty = AssetTagPropertyLocalServiceUtil.getTagProperty(assetTag.getTagId(), "journal-template-id");

		String journalTemplateId = journalTemplateIdProperty.getValue();

		request.setAttribute(WebKeys.JOURNAL_TEMPLATE_ID, journalTemplateId);

		break;
	}
	catch (NoSuchTagException nste) {
	}
	catch (NoSuchTagPropertyException nstpe) {
	}
}

if (enableTagBasedNavigation && selectionStyle.equals("manual") && ((assetEntryQuery.getAllCategoryIds().length > 0) || (assetEntryQuery.getAllTagIds().length > 0))) {
	selectionStyle = "dynamic";
}

Group group = themeDisplay.getScopeGroup();
%>

<c:if test="<%= (group != null) && (!group.hasStagingGroup() || group.isStagingGroup()) && !portletName.equals(PortletKeys.RELATED_ASSETS) %>">
	<aui:form name="fm">

		<%
		for (long groupId : groupIds) {
		%>

			<div class="add-asset-selector">
				<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>
			</div>

		<%
		}
		%>

	</aui:form>
</c:if>

<%
PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = null;

if (((assetCategoryId > 0) && (assetCategoryId != filterCategoryId)) || (Validator.isNotNull(assetTagName) && !assetTagName.equals(filterTagName))){
	filterCategoryId = assetCategoryId;
	filterTagName = assetTagName;

	searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 1, delta, portletURL, null, null);
}
else {
	searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, delta, portletURL, null, null);
}

portletURL.setParameter("filterCategoryId", String.valueOf(filterCategoryId));
portletURL.setParameter("filterTagName", filterTagName);

if (!paginationType.equals("none")) {
	searchContainer.setDelta(delta);
	searchContainer.setDeltaConfigurable(false);
}
%>

<c:if test="<%= showMetadataDescriptions %>">
	<c:choose>
		<c:when test='<%= (assetCategoryId > 0) && Validator.isNotNull(assetTagName) && selectionStyle.equals("dynamic") %>'>
			<h1 class="asset-categorization-title">
				<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, assetCategoryTitle, assetTagName} %>" key="content-with-x-x-and-tag-x" />
			</h1>

			<%
			AssetUtil.addPortletBreadcrumbEntries(assetCategoryId, request, portletURL);
			AssetUtil.addPortletBreadcrumbEntry(request, assetTagName, currentURL);
			%>

		</c:when>
		<c:otherwise>
			<c:if test='<%= (assetCategoryId > 0) && selectionStyle.equals("dynamic") %>'>
				<h1 class="asset-categorization-title">
					<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, assetCategoryTitle} %>" key="content-with-x-x" />
				</h1>

				<%
				AssetUtil.addPortletBreadcrumbEntries(assetCategoryId, request, portletURL);
				%>

			</c:if>

			<c:if test='<%= Validator.isNotNull(assetTagName) && selectionStyle.equals("dynamic") %>'>
				<h1 class="asset-categorization-title">
					<liferay-ui:message arguments="<%= assetTagName %>" key="content-with-tag-x" />
				</h1>

				<%
				AssetUtil.addPortletBreadcrumbEntry(request, assetTagName, currentURL);
				%>

			</c:if>
		</c:otherwise>
	</c:choose>

	<c:if test='<%= portletName.equals(PortletKeys.RELATED_ASSETS) && (assetEntryQuery.getLinkedAssetEntryId() > 0) %>'>
		<h1 class="related-assets-title">

			<%
			AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(assetEntryQuery.getLinkedAssetEntryId());
			%>

			<liferay-ui:message arguments="<%= assetEntry.getTitle(locale) %>" key="content-related-to-x" />
		</h1>
	</c:if>
</c:if>

<c:choose>
	<c:when test='<%= selectionStyle.equals("dynamic") %>'>
		<%@ include file="/html/portlet/asset_publisher/view_dynamic_list.jspf" %>
	</c:when>
	<c:when test='<%= selectionStyle.equals("manual") %>'>
		<%@ include file="/html/portlet/asset_publisher/view_manual.jspf" %>
	</c:when>
</c:choose>

<c:if test='<%= !paginationType.equals("none") && (searchContainer.getTotal() > searchContainer.getResults().size()) %>'>
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="<%= paginationType %>" />
</c:if>

<c:if test="<%= enableRSS %>">
	<portlet:resourceURL var="rssURL">
		<portlet:param name="struts_action" value="/asset_publisher/rss" />
	</portlet:resourceURL>

	<div class="subscribe">
		<liferay-ui:icon
			image="rss"
			label="<%= true %>"
			method="get"
			target="_blank"
			url="<%= rssURL %>"
		/>
	</div>

	<liferay-util:html-top>
		<link href="<%= HtmlUtil.escape(rssURL) %>" rel="alternate" title="RSS" type="application/rss+xml" />
	</liferay-util:html-top>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.asset_publisher.view_jsp");
%>