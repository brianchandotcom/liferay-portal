<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/categorization_filter/init.jsp" %>

<%
String assetType = GetterUtil.getString((String)request.getAttribute("liferay-asset:categorization-filter:assetType"), "content");

long assetCategoryId = ParamUtil.getLong(request, "categoryId");

String assetTagName = ParamUtil.getString(request, "tag");

long[] groupIds = GetterUtil.getLongValues(request.getAttribute("liferay-asset:categorization-filter:groupIds"), new long[] {layout.getGroupId()});

long[] assetTagIds = AssetTagLocalServiceUtil.getTagIds(groupIds, assetTagName);

if (Validator.isNotNull(assetTagName) && (assetTagIds.length == 0)) {
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

PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:categorization-filter:portletURL");

if (portletURL == null) {
	portletURL = renderResponse.createRenderURL();
}
%>

<liferay-util:buffer
	var="removeCategory"
>
	<c:if test="<%= assetCategory != null %>">
		<portlet:renderURL var="viewURLWithoutCategory">
			<portlet:param name="categoryId" value="0" />
		</portlet:renderURL>

		<clay:label
			dismissible="<%= true %>"
			displayType="dark"
			large="<%= true %>"
		>
			<clay:label-item-expand><%= assetCategoryTitle %></clay:label-item-expand>

			<clay:label-item-after>
				<a href="<%= viewURLWithoutCategory %>" title="<liferay-ui:message key="remove" />">
					<clay:icon
						symbol="times-circle"
					/>
				</a>
			</clay:label-item-after>
		</clay:label>
	</c:if>
</liferay-util:buffer>

<liferay-util:buffer
	var="removeTag"
>
	<c:if test="<%= Validator.isNotNull(assetTagName) %>">
		<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="viewURLWithoutTag">
			<liferay-portlet:param name="tag" value="" />
		</liferay-portlet:renderURL>

		<clay:label
			dismissible="<%= true %>"
			displayType="dark"
			large="<%= true %>"
		>
			<clay:label-item-expand><%= HtmlUtil.escape(assetTagName) %></clay:label-item-expand>

			<clay:label-item-after>
				<a href="<%= viewURLWithoutTag %>" title="<liferay-ui:message key="remove" />">
					<clay:icon
						symbol="times-circle"
					/>
				</a>
			</clay:label-item-after>
		</clay:label>
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