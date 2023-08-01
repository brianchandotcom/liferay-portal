<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
</div>