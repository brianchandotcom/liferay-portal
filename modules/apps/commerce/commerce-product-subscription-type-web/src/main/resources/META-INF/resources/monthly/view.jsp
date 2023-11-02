<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				const monthDayInputContainer = document.getElementById(
					'<portlet:namespace />monthly--monthDayInputContainer'
				);

				if (
					document.getElementById('<portlet:namespace />monthly--monthlyMode')
						.value ===
					'<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH %>'
				) {
					monthDayInputContainer.classList.remove('hide');
				}
				else {
					if (!monthDayInputContainer.classList.contains('hide'))
						monthDayInputContainer.classList.add('hide');
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
				const deliveryMonthDayInputContainer = document.getElementById(
					'<portlet:namespace />monthly--deliveryMonthDayInputContainer'
				);

				if (
					document.getElementById(
						'<portlet:namespace />monthly--deliveryMonthlyMode'
					).value === '<%= CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH %>'
				) {
					deliveryMonthDayInputContainer.classList.remove('hide');
				}
				else {
					if (!deliveryMonthDayInputContainer.classList.contains('hide'))
						deliveryMonthDayInputContainer.classList.add('hide');
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>