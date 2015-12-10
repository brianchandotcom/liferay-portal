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

<%@ include file="/message_boards/init.jsp" %>

<%
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

String displayStyle = BeanPropertiesUtil.getString(category, "displayStyle", MBCategoryConstants.DEFAULT_DISPLAY_STYLE);

if (Validator.isNull(displayStyle)) {
	displayStyle = MBCategoryConstants.DEFAULT_DISPLAY_STYLE;
}

if ((message != null) && layout.isTypeControlPanel()) {
	MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);
}

AssetEntryServiceUtil.incrementViewCounter(MBMessage.class.getName(), message.getMessageId());

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<liferay-util:include page="/message_boards/top_links.jsp" servletContext="<%= application %>" />

	<div class="displayStyle-<%= displayStyle %>">
		<liferay-util:include page='<%= "/message_boards/view_message_" + displayStyle + ".jsp" %>' servletContext="<%= application %>" />
	</div>
</div>