<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String iconURL = ParamUtil.getString(request, "iconURL");
%>

<clay:sticker
	cssClass="search-container"
	displaytype="secondary"
>
	<c:choose>
		<c:when test='<%= iconURL.contains(".svg#") %>'>
			<svg class="lexicon-icon">
				<use xlink:href="<%= iconURL %>" />
			</svg>
		</c:when>
		<c:when test="<%= Validator.isUrl(iconURL) %>">
			<img alt="thumbnail" class="img-fluid" src="<%= iconURL %>" />
		</c:when>
	</c:choose>
</clay:sticker>