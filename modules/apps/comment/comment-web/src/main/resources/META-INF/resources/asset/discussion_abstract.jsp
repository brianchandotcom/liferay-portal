<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH), AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);

Comment comment = (Comment)request.getAttribute(WebKeys.COMMENT);
%>

<div class="asset-summary">
	<%= StringUtil.shorten(comment.getTranslatedBody(themeDisplay.getPathThemeImages()), abstractLength) %>
</div>