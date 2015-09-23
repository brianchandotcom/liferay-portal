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

<%@ include file="/html/taglib/ui/asset_metadata/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-ui:asset-metadata:className");
long classPK = GetterUtil.getLong(request.getAttribute("liferay-ui:asset-metadata:classPK"));
boolean filterByMetadata = GetterUtil.getBoolean(request.getAttribute("liferay-ui:asset-metadata:filterByMetadata"));
String metadataField = (String)request.getAttribute("liferay-ui:asset-metadata:metadataField");

AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(className, classPK);

String iconCssClass = StringPool.BLANK;
String label = LanguageUtil.get(request, metadataField);
String metadataFieldCssClass = "metadata-" + metadataField;
String value = null;

if (metadataField.equals("create-date")) {
	iconCssClass = "icon-calendar";
	value = dateFormatDate.format(assetEntry.getCreateDate());
}
else if (metadataField.equals("modified-date")) {
	iconCssClass = "icon-calendar";
	value = dateFormatDate.format(assetEntry.getModifiedDate());
}
else if (metadataField.equals("publish-date")) {
	iconCssClass = "icon-calendar";

	if (assetEntry.getPublishDate() == null) {
		value = StringPool.BLANK;
	}
	else {
		value = dateFormatDate.format(assetEntry.getPublishDate());
	}
}
else if (metadataField.equals("expiration-date")) {
	iconCssClass = "icon-calendar";

	if (assetEntry.getExpirationDate() == null) {
		value = StringPool.BLANK;
	}
	else {
		value = dateFormatDate.format(assetEntry.getExpirationDate());
	}
}
else if (metadataField.equals("priority")) {
	iconCssClass = "icon-long-arrow-up";

	value = LanguageUtil.get(request, "priority") + StringPool.COLON + StringPool.SPACE + assetEntry.getPriority();
}
else if (metadataField.equals("author")) {
	iconCssClass = "icon-user";

	metadataFieldCssClass = StringPool.BLANK;

	AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(classPK);

	String userName = PortalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName());

	value = LanguageUtil.get(request, "by") + StringPool.SPACE + HtmlUtil.escape(userName);
}
else if (metadataField.equals("view-count")) {
	int viewCount = assetEntry.getViewCount();

	value = viewCount + StringPool.SPACE + LanguageUtil.get(request, (viewCount == 1) ? "view" : "views");
}
else if (metadataField.equals("categories")) {
	List<AssetCategory> categories = assetEntry.getCategories();

	if (!categories.isEmpty()) {
		value = "categories";
	}
}
else if (metadataField.equals("tags")) {
	List<AssetTag> tags = assetEntry.getTags();

	if (!tags.isEmpty()) {
		value = "tags";
	}
}
%>

<c:if test="<%= Validator.isNotNull(value) %>">
	<aui:column cssClass="help-block">
		<dt class="metadata-entry-label"><%= label %></dt>

		<dd class="metadata-entry <%= metadataFieldCssClass %> <%= iconCssClass %>">
			<c:choose>
				<c:when test='<%= value.equals("author") %>' >

					<%
					User userDisplay = UserLocalServiceUtil.getUser(assetRenderer.getUserId());

					String displayDate = StringPool.BLANK;

					if (assetEntry.getPublishDate() != null) {
						displayDate = LanguageUtil.format(request, "x-ago", LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - assetEntry.getPublishDate().getTime(), true), false);
					}
					else if (assetEntry.getModifiedDate() != null) {
						displayDate = LanguageUtil.format(request, "x-ago", LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - assetEntry.getModifiedDate().getTime(), true), false);
					}
					%>

					<div class="asset-avatar">
						<img alt="<%= HtmlUtil.escapeAttribute(userDisplay.getFullName()) %>" class="avatar img-circle" src="<%= HtmlUtil.escape(userDisplay.getPortraitURL(themeDisplay)) %>" />
					</div>

					<div class="asset-user-info">
						<span class="user-info"><%= userDisplay.getFullName() %></span>

						<span class="date-info"><%= displayDate %></span>
					</div>
				</c:when>
				<c:when test='<%= value.equals("categories") %>' >
					<liferay-ui:asset-categories-summary
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK () %>"
						portletURL="<%= filterByMetadata ? renderResponse.createRenderURL() : null %>"
					/>
				</c:when>
				<c:when test='<%= value.equals("tags") %>' >
					<liferay-ui:asset-tags-summary
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK () %>"
						portletURL="<%= filterByMetadata ? renderResponse.createRenderURL() : null %>"
					/>
				</c:when>
				<c:otherwise>
					<%= value %>
				</c:otherwise>
			</c:choose>
		</dd>
	</aui:column>
</c:if>
