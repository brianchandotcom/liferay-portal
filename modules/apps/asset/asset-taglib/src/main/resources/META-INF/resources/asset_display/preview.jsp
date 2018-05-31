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

<%@ include file="/asset_display/init.jsp" %>

<%
AssetEntry assetEntry = (AssetEntry)request.getAttribute("liferay-asset:asset-display:assetEntry");

AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);
%>

<div class="asset-preview">

	<%
	String imagePreviewURL = assetRenderer.getURLImagePreview(liferayPortletRequest);
	%>

	<c:if test="<%= Validator.isNotNull(imagePreviewURL) %>">
		<div class="asset-image-preview">
			<img alt="<%= HtmlUtil.escapeAttribute(assetRenderer.getTitle(themeDisplay.getLocale())) %>" src="<%= HtmlUtil.escapeAttribute(imagePreviewURL) %>" />
		</div>
	</c:if>

	<div class="asset-title">
		<%= HtmlUtil.escape(assetRenderer.getTitle(themeDisplay.getLocale())) %>
	</div>

	<%
	String publishDateString = StringPool.BLANK;

	if (assetEntry.getPublishDate() != null) {
		Format displayFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("MMMM d, yyyy", locale, timeZone);

		publishDateString = CharPool.OPEN_PARENTHESIS + displayFormatDate.format(assetEntry.getPublishDate()) + CharPool.CLOSE_PARENTHESIS;
	}
	%>

	<div class="asset-information">
		<span class="user-name"><%= HtmlUtil.escape(assetRenderer.getUserName()) %></span>&nbsp; <span class="display-date"><%= publishDateString %></span>
	</div>

	<div class="asset-summary">
		<%= HtmlUtil.escape(StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), 320)) %>
	</div>

	<div class="asset-metadata">
		<div class="categories">
			<liferay-asset:asset-categories-summary
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
			/>
		</div>

		<div class="tags">
			<liferay-asset:asset-tags-summary
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
			/>
		</div>
	</div>

	<%
	Map<String, Object> data = new HashMap<String, Object>();

	data.put("class-name", assetEntry.getClassName());
	data.put("class-pk", assetEntry.getClassPK());
	data.put("instanceable", Boolean.TRUE);

	String portletId = PortletProviderUtil.getPortletId(assetEntry.getClassName(), PortletProvider.Action.ADD);

	data.put("portlet-id", portletId);

	data.put("title", HtmlUtil.escape(assetRenderer.getTitle(themeDisplay.getLocale())));
	%>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.ADD_TO_PAGE) %>">
		<aui:button cssClass="add-button-preview" data="<%= data %>" value="add" />
	</c:if>
</div>