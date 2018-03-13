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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
SearchEntry entry = (SearchEntry)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY);

AnnouncementsDelivery delivery = (AnnouncementsDelivery)row.getObject();

boolean defaultValue = false;
boolean disabled = false;
String messageKey = StringPool.BLANK;
String param = "announcementsType" + delivery.getType();

int index = entry.getIndex();

if (index == 1) {
	defaultValue = delivery.isEmail();
	messageKey = "receive-x-announcements-via-email";
	param += "Email";
}
else if (index == 2) {
	defaultValue = delivery.isSms();
	messageKey = "receive-x-announcements-via-sms";
	param += "Sms";
}
else if (index == 3) {
	defaultValue = delivery.isWebsite();
	disabled = true;
	messageKey = "receive-x-announcements-via-website";
	param += "Website";
}
%>

<aui:input disabled="<%= disabled %>" label="" name="<%= param %>" title="<%= LanguageUtil.format(request, messageKey, delivery.getType()) %>" type="checkbox" value="<%= defaultValue %>" />