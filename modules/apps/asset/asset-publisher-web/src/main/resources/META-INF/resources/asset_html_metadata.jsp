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
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

String summary = StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), assetPublisherDisplayContext.getAbstractLength());

PortalUtil.setPageSubtitle(title, request);
PortalUtil.setPageDescription(summary, request);
PortalUtil.setPageKeywords(AssetUtil.getAssetKeywords(assetEntry.getClassName(), assetEntry.getClassPK()), request);
%>