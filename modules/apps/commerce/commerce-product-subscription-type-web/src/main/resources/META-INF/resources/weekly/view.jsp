<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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