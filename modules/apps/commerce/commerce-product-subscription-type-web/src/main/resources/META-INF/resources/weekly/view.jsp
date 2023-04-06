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
WeeklyCPSubscriptionTypeDisplayContext weeklyCPSubscriptionTypeDisplayContext = (WeeklyCPSubscriptionTypeDisplayContext)request.getAttribute("view.jsp-weeklyCPSubscriptionTypeDisplayContext");

int selectedWeekDay = weeklyCPSubscriptionTypeDisplayContext.getSelectedWeekDay();
%>

<c:choose>
	<c:when test="<%= weeklyCPSubscriptionTypeDisplayContext.isPayment() %>">
		<aui:select label="on" name="subscriptionTypeSettings--weekly--weekDay--">

			<%
			for (int weekDay : weeklyCPSubscriptionTypeDisplayContext.getCalendarWeekDays()) {
			%>

				<aui:option label="<%= weeklyCPSubscriptionTypeDisplayContext.getWeekDayDisplayName(weekDay) %>" selected="<%= selectedWeekDay == weekDay %>" value="<%= weekDay %>" />

			<%
			}
			%>

		</aui:select>
	</c:when>
	<c:otherwise>
		<aui:select label="on" name="deliverySubscriptionTypeSettings--weekly--deliveryWeekDay--">

			<%
			for (int weekDay : weeklyCPSubscriptionTypeDisplayContext.getCalendarWeekDays()) {
			%>

				<aui:option label="<%= weeklyCPSubscriptionTypeDisplayContext.getWeekDayDisplayName(weekDay) %>" selected="<%= selectedWeekDay == weekDay %>" value="<%= weekDay %>" />

			<%
			}
			%>

		</aui:select>
	</c:otherwise>
</c:choose>