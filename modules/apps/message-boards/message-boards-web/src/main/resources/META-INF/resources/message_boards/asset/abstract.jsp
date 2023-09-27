<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH), AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

String summary = message.getBody();

if (message.isFormatBBCode()) {
	summary = com.liferay.message.boards.util.MBUtil.getBBCodeHTML(summary, themeDisplay.getPathThemeImages());
}
%>

<div class="asset-summary">
	<%= StringUtil.shorten(HtmlUtil.stripHtml(summary), abstractLength) %>
</div>