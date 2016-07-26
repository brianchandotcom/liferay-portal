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

<c:if test="<%= PortalUtil.isRSSFeedsEnabled() && assetPublisherDisplayContext.isSelectionStyleDynamic() %>">
	<liferay-ui:rss-settings
		delta="<%= assetPublisherDisplayContext.getRSSDelta() %>"
		displayStyle="<%= assetPublisherDisplayContext.getRSSDisplayStyle() %>"
		displayStyles="<%= new String[] {RSSUtil.DISPLAY_STYLE_ABSTRACT, RSSUtil.DISPLAY_STYLE_TITLE} %>"
		enabled="<%= assetPublisherDisplayContext.isEnableRSS() %>"
		feedType="<%= assetPublisherDisplayContext.getRSSFeedType() %>"
		name="<%= assetPublisherDisplayContext.getRSSName() %>"
		nameEnabled="<%= true %>"
	/>
</c:if>