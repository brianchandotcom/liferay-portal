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
MonthlyCPSubscriptionTypeDisplayContext monthlyCPSubscriptionTypeDisplayContext = (MonthlyCPSubscriptionTypeDisplayContext)request.getAttribute("view.jsp-monthlyCPSubscriptionTypeDisplayContext");

int selectedMonthlyMode = monthlyCPSubscriptionTypeDisplayContext.getSelectedMonthlyMode();
%>

<c:choose>
	<c:when test="<%= monthlyCPSubscriptionTypeDisplayContext.isPayment() %>">
		<aui:select label="mode" name="subscriptionTypeSettings--monthly--monthlyMode--" onChange="event.preventDefault(); changeMonthlyCPSubscriptionTypeSettingsMode();">

			<%
			for (int mode : CPSubscriptionTypeConstants.MONTHLY_MODES) {
			%>

				<aui:option label="<%= CPSubscriptionTypeConstants.getMonthlyCPSubscriptionTypeModeLabel(mode) %>" selected="<%= selectedMonthlyMode == mode %>" value="<%= mode %>" />

			<%
			}
			%>

		</aui:select>

		<div class="<%= (selectedMonthlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />monthly--monthDayInputContainer">
			<aui:input label="on" name="subscriptionTypeSettings--monthly--monthDay--" value="<%= monthlyCPSubscriptionTypeDisplayContext.getMonthDay() %>">
				<aui:validator name="digits" />
				<aui:validator name="max">31</aui:validator>
				<aui:validator name="min">1</aui:validator>
			</aui:input>
		</div>

		<aui:script>
			function changeMonthlyCPSubscriptionTypeSettingsMode() {
				var A = AUI();

				if (
					A.one('#<portlet:namespace />monthly--monthlyMode').val() ==
					'<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH %>'
				) {
					A.one(
						'#<portlet:namespace />monthly--monthDayInputContainer'
					).removeClass('hide');
				}
				else {
					if (
						!A.one(
							'#<portlet:namespace />monthly--monthDayInputContainer'
						).hasClass('hide')
					) {
						A.one(
							'#<portlet:namespace />monthly--monthDayInputContainer'
						).addClass('hide');
					}
				}
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:select label="mode" name="deliverySubscriptionTypeSettings--monthly--deliveryMonthlyMode--" onChange="event.preventDefault(); changeMonthlyDeliveryCPSubscriptionTypeSettingsMode();">

			<%
			for (int mode : CPSubscriptionTypeConstants.MONTHLY_MODES) {
			%>

				<aui:option label="<%= CPSubscriptionTypeConstants.getMonthlyCPSubscriptionTypeModeLabel(mode) %>" selected="<%= selectedMonthlyMode == mode %>" value="<%= mode %>" />

			<%
			}
			%>

		</aui:select>

		<div class="<%= (selectedMonthlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />monthly--deliveryMonthDayInputContainer">
			<aui:input label="on" name="deliverySubscriptionTypeSettings--monthly--deliveryMonthDay--" value="<%= monthlyCPSubscriptionTypeDisplayContext.getMonthDay() %>">
				<aui:validator name="digits" />
				<aui:validator name="max">31</aui:validator>
				<aui:validator name="min">1</aui:validator>
			</aui:input>
		</div>

		<aui:script>
			function changeMonthlyDeliveryCPSubscriptionTypeSettingsMode() {
				var A = AUI();

				if (
					A.one('#<portlet:namespace />monthly--deliveryMonthlyMode').val() ==
					'<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH %>'
				) {
					A.one(
						'#<portlet:namespace />monthly--deliveryMonthDayInputContainer'
					).removeClass('hide');
				}
				else {
					if (
						!A.one(
							'#<portlet:namespace />monthly--deliveryMonthDayInputContainer'
						).hasClass('hide')
					) {
						A.one(
							'#<portlet:namespace />monthly--deliveryMonthDayInputContainer'
						).addClass('hide');
					}
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>