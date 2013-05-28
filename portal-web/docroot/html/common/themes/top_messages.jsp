<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<c:if test="<%= ShutdownUtil.isInProcess() %>">
	<div class="alert alert-block" id="lfrShutdownMessage">
		<span class="notice-label"><liferay-ui:message key="maintenance-alert" /></span> <span class="notice-date"><%= FastDateFormatFactoryUtil.getTime(locale).format(Time.getDate(CalendarFactoryUtil.getCalendar(timeZone))) %> <%= timeZone.getDisplayName(false, TimeZone.SHORT, locale) %></span>
		<span class="notice-message"><%= LanguageUtil.format(pageContext, "the-portal-will-shutdown-for-maintenance-in-x-minutes", String.valueOf(ShutdownUtil.getInProcess() / Time.MINUTE), false) %></span>

		<c:if test="<%= Validator.isNotNull(ShutdownUtil.getMessage()) %>">
			<span class="custom-shutdown-message"><%= HtmlUtil.escape(ShutdownUtil.getMessage()) %></span>
		</c:if>
	</div>
</c:if>

<%
String message = GetterUtil.getString(SessionMessages.get(request, "portalMessageMessage"));

if (Validator.isNotNull(message)) {
	String cssClass = GetterUtil.getString(SessionMessages.get(request, "portalMessageCssClass"), "alert-info");
	int timeout = GetterUtil.getInteger(SessionMessages.get(request, "portalMessageTimeout"), 10000);
	boolean useAnimation = GetterUtil.getBoolean(SessionMessages.get(request, "portalMessageAnimation"), true);
%>

	<aui:script use="liferay-notice">
		var banner = new Liferay.Notice(
			{
				animationConfig:
					{
						duration: 2,
						top: '0px'
					},
				closeText: false,
				content: '<liferay-ui:message key="<%= message %>" /><button type="button" class="close">&times;</button>',
				noticeClass: 'hide taglib-portal-message <%= cssClass %>',
				timeout: <%= timeout %>,
				toggleText: false,
				useAnimation: <%= useAnimation %>
			}
		);

		banner.show();
	</aui:script>

<%
}
%>