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
YearlyCPSubscriptionTypeDisplayContext yearlyCPSubscriptionTypeDisplayContext = (YearlyCPSubscriptionTypeDisplayContext)request.getAttribute("view.jsp-yearlyCPSubscriptionTypeDisplayContext");

int selectedMonth = yearlyCPSubscriptionTypeDisplayContext.getSelectedMonth();
int selectedYearlyMode = yearlyCPSubscriptionTypeDisplayContext.getSelectedYearlyMode();
%>

<c:choose>
	<c:when test="<%= yearlyCPSubscriptionTypeDisplayContext.isPayment() %>">
		<aui:select label="mode" name="subscriptionTypeSettings--yearly--yearlyMode--" onChange="event.preventDefault(); changeYearlyCPSubscriptionTypeSettingsMode();">

			<%
			for (int mode : CPSubscriptionTypeConstants.YEARLY_MODES) {
			%>

				<aui:option label="<%= CPSubscriptionTypeConstants.getYearlyCPSubscriptionTypeModeLabel(mode) %>" selected="<%= selectedYearlyMode == mode %>" value="<%= mode %>" />

			<%
			}
			%>

		</aui:select>

		<div class="<%= (selectedYearlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />yearly--exactDayOfYearInputContainer">
			<aui:select label="month" name="subscriptionTypeSettings--yearly--month--">

				<%
				for (int month : yearlyCPSubscriptionTypeDisplayContext.getCalendarMonths()) {
				%>

					<aui:option label="<%= yearlyCPSubscriptionTypeDisplayContext.getMonthDisplayName(month) %>" selected="<%= selectedMonth == month %>" value="<%= month %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="day" name="subscriptionTypeSettings--yearly--monthDay--" value="<%= yearlyCPSubscriptionTypeDisplayContext.getMonthDay() %>">
				<aui:validator name="digits" />
				<aui:validator name="max">31</aui:validator>
				<aui:validator name="min">1</aui:validator>
			</aui:input>
		</div>

		<aui:script>
			function changeYearlyCPSubscriptionTypeSettingsMode() {
				var A = AUI();

				if (
					A.one('#<portlet:namespace />yearly--yearlyMode').val() ==
					'<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR %>'
				) {
					A.one(
						'#<portlet:namespace />yearly--exactDayOfYearInputContainer'
					).removeClass('hide');
				}
				else {
					if (
						!A.one(
							'#<portlet:namespace />yearly--exactDayOfYearInputContainer'
						).hasClass('hide')
					) {
						A.one(
							'#<portlet:namespace />yearly--exactDayOfYearInputContainer'
						).addClass('hide');
					}
				}
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:select label="mode" name="deliverySubscriptionTypeSettings--yearly--deliveryYearlyMode--" onChange="event.preventDefault(); changeYearlyDeliveryCPSubscriptionTypeSettingsMode();">

			<%
			for (int mode : CPSubscriptionTypeConstants.YEARLY_MODES) {
			%>

				<aui:option label="<%= CPSubscriptionTypeConstants.getYearlyCPSubscriptionTypeModeLabel(mode) %>" selected="<%= selectedYearlyMode == mode %>" value="<%= mode %>" />

			<%
			}
			%>

		</aui:select>

		<div class="<%= (selectedYearlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />yearly--deliveryExactDayOfYearInputContainer">
			<aui:select label="month" name="deliverySubscriptionTypeSettings--yearly--deliveryMonth--">

				<%
				for (int month : yearlyCPSubscriptionTypeDisplayContext.getCalendarMonths()) {
				%>

					<aui:option label="<%= yearlyCPSubscriptionTypeDisplayContext.getMonthDisplayName(month) %>" selected="<%= selectedMonth == month %>" value="<%= month %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="day" name="deliverySubscriptionTypeSettings--yearly--deliveryMonthDay--" value="<%= yearlyCPSubscriptionTypeDisplayContext.getMonthDay() %>">
				<aui:validator name="digits" />
				<aui:validator name="max">31</aui:validator>
				<aui:validator name="min">1</aui:validator>
			</aui:input>
		</div>

		<aui:script>
			function changeYearlyDeliveryCPSubscriptionTypeSettingsMode() {
				var A = AUI();

				if (
					A.one('#<portlet:namespace />yearly--deliveryYearlyMode').val() ==
					'<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR %>'
				) {
					A.one(
						'#<portlet:namespace />yearly--deliveryExactDayOfYearInputContainer'
					).removeClass('hide');
				}
				else {
					if (
						!A.one(
							'#<portlet:namespace />yearly--deliveryExactDayOfYearInputContainer'
						).hasClass('hide')
					) {
						A.one(
							'#<portlet:namespace />yearly--deliveryExactDayOfYearInputContainer'
						).addClass('hide');
					}
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>