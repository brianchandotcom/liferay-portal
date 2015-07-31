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

<%@ include file="/init.jsp" %>

<%
AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

request.setAttribute("view.jsp-showIconLabel", true);

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

boolean viewInContext = ((Boolean)request.getAttribute("view.jsp-viewInContext")).booleanValue();

String viewURL = AssetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetEntry, viewInContext);
%>

<div class="asset-abstract <%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) ? "default-asset-publisher" : StringPool.BLANK %>">
	<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />

	<h4 class="asset-title">
		<c:if test="<%= Validator.isNotNull(viewURL) %>">
			<a href="<%= viewURL %>">
		</c:if>

		<i class="<%= assetRenderer.getIconCssClass() %>"></i>

		<%= HtmlUtil.escape(title) %>

		<c:if test="<%= Validator.isNotNull(viewURL) %>">
			</a>
		</c:if>
	</h4>

	<%
	String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();
	%>

	<c:if test='<%= ArrayUtil.contains(metadataFields, String.valueOf("author")) %>'>
		<div class="asset-author">

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
		</div>
	</c:if>

	<div class="asset-content">
		<div class="asset-summary">
			<liferay-ui:asset-display
				abstractLength="<%= assetPublisherDisplayContext.getAbstractLength() %>"
				assetEntry="<%= assetEntry %>"
				assetRenderer="<%= assetRenderer %>"
				assetRendererFactory="<%= assetRendererFactory %>"
				template="<%= AssetRenderer.TEMPLATE_ABSTRACT %>"
				viewURL="<%= viewURL %>"
			/>
		</div>
	</div>

	<liferay-ui:asset-metadata
		className="<%= assetEntry.getClassName() %>"
		classPK="<%= assetEntry.getClassPK() %>"
		filterByMetadata="<%= true %>"
		metadataFields="<%= metadataFields %>"
	/>
</div>