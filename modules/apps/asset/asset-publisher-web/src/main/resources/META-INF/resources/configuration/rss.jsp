<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-rss:rss-settings
	delta="<%= assetPublisherDisplayContext.getRSSDelta() %>"
	displayStyle="<%= assetPublisherDisplayContext.getRSSDisplayStyle() %>"
	displayStyles="<%= new String[] {RSSUtil.DISPLAY_STYLE_ABSTRACT, RSSUtil.DISPLAY_STYLE_TITLE} %>"
	enabled="<%= assetPublisherDisplayContext.isEnableRSS() %>"
	feedType="<%= assetPublisherDisplayContext.getRSSFeedType() %>"
	name="<%= assetPublisherDisplayContext.getRSSName() %>"
	nameEnabled="<%= true %>"
/>