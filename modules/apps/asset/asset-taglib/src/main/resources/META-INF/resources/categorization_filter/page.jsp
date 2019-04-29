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

AssetCategory assetCategory = null;

String assetCategoryTitle = null;
String assetVocabularyTitle = null;

if (assetCategoryId != 0) {
	assetCategory = AssetCategoryLocalServiceUtil.fetchAssetCategory(assetCategoryId);

	if (assetCategory != null) {
		assetCategoryTitle = HtmlUtil.escape(assetCategory.getTitle(locale));

		AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

		assetVocabularyTitle = HtmlUtil.escape(assetVocabulary.getTitle(locale));
	}
}
%>

<liferay-util:buffer
	var="removeCategory"
>
	<c:if test="<%= assetCategory != null %>">
		<portlet:renderURL var="viewURLWithoutCategory">
			<portlet:param name="categoryId" value="0" />
		</portlet:renderURL>

		<span class="label label-dark label-dismissible label-lg text-uppercase">
			<span class="label-item label-item-expand"><%= assetCategoryTitle %></span>

			<span class="label-item label-item-after">
				<a href="<%= viewURLWithoutCategory %>" title="<liferay-ui:message key="remove" />">
					<aui:icon image="times-circle" markupView="lexicon" />
				</a>
			</span>
		</span>
	</c:if>
</liferay-util:buffer>

<liferay-util:buffer
	var="removeTag"
>
	<c:if test="<%= Validator.isNotNull(assetTagName) %>">
		<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="viewURLWithoutTag">
			<liferay-portlet:param name="tag" value="" />
		</liferay-portlet:renderURL>

		<span class="label label-dark label-dismissible label-lg text-uppercase">
			<span class="label-item label-item-expand"><%= HtmlUtil.escape(assetTagName) %></span>

			<span class="label-item label-item-after">
				<a href="<%= viewURLWithoutTag %>" title="<liferay-ui:message key="remove" />">
					<aui:icon image="times-circle" markupView="lexicon" />
				</a>
			</span>
		</span>
	</c:if>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= (assetCategory != null) && Validator.isNotNull(assetTagName) %>">

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
	<c:when test="<%= assetCategory != null %>">

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