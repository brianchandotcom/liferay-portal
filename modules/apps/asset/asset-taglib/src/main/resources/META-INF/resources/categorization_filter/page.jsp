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

<%@ include file="/categorization_filter/init.jsp" %>

<%
String assetType = GetterUtil.getString((String)request.getAttribute("liferay-asset:categorization-filter:assetType"), "content");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:categorization-filter:portletURL");

if (portletURL == null) {
	portletURL = renderResponse.createRenderURL();
}

long assetCategoryId = ParamUtil.getLong(request, "categoryId");

String assetTagName = ParamUtil.getString(request, "tag");

if (Validator.isNotNull(assetTagName) && !AssetTagLocalServiceUtil.hasTag(layout.getGroupId(), assetTagName)) {
	assetTagName = null;
}

String assetCategoryTitle = null;
String assetVocabularyTitle = null;

if (assetCategoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(assetCategoryId);

	assetCategoryTitle = HtmlUtil.escape(assetCategory.getTitle(locale));

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabularyTitle = HtmlUtil.escape(assetVocabulary.getTitle(locale));
}
%>

<liferay-util:buffer
	var="removeCategory"
>
	<c:if test="<%= assetCategoryId != 0 %>">
		<span class="asset-entry badge badge-default badge-sm">
			<%= assetCategoryTitle %>

			<portlet:renderURL var="viewURLWithoutCategory">
				<portlet:param name="categoryId" value="0" />
			</portlet:renderURL>

			<a href="<%= viewURLWithoutCategory %>" title="<liferay-ui:message key="remove" />">
				<aui:icon cssClass="textboxlistentry-remove" image="times" markupView="lexicon" />
			</a>
		</span>
	</c:if>
</liferay-util:buffer>

<liferay-util:buffer
	var="removeTag"
>
	<c:if test="<%= Validator.isNotNull(assetTagName) %>">
		<span class="asset-entry badge badge-default badge-sm">
			<%= HtmlUtil.escape(assetTagName) %>

			<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="viewURLWithoutTag">
				<liferay-portlet:param name="tag" value="" />
			</liferay-portlet:renderURL>

			<a href="<%= viewURLWithoutTag %>" title="<liferay-ui:message key="remove" />">
				<aui:icon cssClass="textboxlistentry-remove" image="times" markupView="lexicon" />
			</a>
		</span>
	</c:if>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= (assetCategoryId != 0) && Validator.isNotNull(assetTagName) %>">

		<%
		AssetCategoryUtil.addPortletBreadcrumbEntries(assetCategoryId, request, portletURL);

		PortalUtil.addPortletBreadcrumbEntry(request, assetTagName, currentURL);

		PortalUtil.addPageKeywords(assetCategoryTitle, request);
		PortalUtil.addPageKeywords(assetTagName, request);
		%>

		<h2 class="entry-title taglib-categorization-filter">
			<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, removeCategory, removeTag} %>" key='<%= assetType.concat("-with-x-x-and-tag-x") %>' translateArguments="<%= false %>" />
		</h2>
	</c:when>
	<c:when test="<%= assetCategoryId != 0 %>">

		<%
		AssetCategoryUtil.addPortletBreadcrumbEntries(assetCategoryId, request, portletURL);

		PortalUtil.addPageKeywords(assetCategoryTitle, request);
		%>

		<h2 class="entry-title taglib-categorization-filter">
			<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, removeCategory} %>" key='<%= assetType.concat("-with-x-x") %>' translateArguments="<%= false %>" />
		</h2>
	</c:when>
	<c:when test="<%= Validator.isNotNull(assetTagName) %>">

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, assetTagName, currentURL);

		PortalUtil.addPageKeywords(assetTagName, request);
		%>

		<h2 class="entry-title taglib-categorization-filter">
			<liferay-ui:message arguments="<%= removeTag %>" key='<%= assetType.concat("-with-tag-x") %>' translateArguments="<%= false %>" />
		</h2>
	</c:when>
</c:choose>