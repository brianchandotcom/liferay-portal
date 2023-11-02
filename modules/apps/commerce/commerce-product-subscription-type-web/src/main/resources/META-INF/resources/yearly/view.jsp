<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				const exactDayOfYearInputContainer = document.getElementById(
					'<portlet:namespace />yearly--exactDayOfYearInputContainer'
				);

				if (
					document.getElementById('<portlet:namespace />yearly--yearlyMode')
						.value ===
					'<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR %>'
				) {
					exactDayOfYearInputContainer.classList.remove('hide');
				}
				else {
					if (!exactDayOfYearInputContainer.classList.contains('hide'))
						exactDayOfYearInputContainer.classList.add('hide');
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
				const deliveryExactDayOfYearInputContainer = document.getElementById(
					'<portlet:namespace />yearly--deliveryExactDayOfYearInputContainer'
				);

				if (
					document.getElementById(
						'<portlet:namespace />yearly--deliveryYearlyMode'
					).value === '<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR %>'
				) {
					deliveryExactDayOfYearInputContainer.classList.remove('hide');
				}
				else {
					if (!deliveryExactDayOfYearInputContainer.classList.contains('hide'))
						deliveryExactDayOfYearInputContainer.classList.add('hide');
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>