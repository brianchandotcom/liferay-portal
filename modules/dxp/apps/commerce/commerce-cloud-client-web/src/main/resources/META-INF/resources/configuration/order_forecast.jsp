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
EditConfigurationDisplayContext editConfigurationDisplayContext = (EditConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCloudClientConfiguration commerceCloudClientConfiguration = editConfigurationDisplayContext.getCommerceCloudClientConfiguration();
JSONObject orderForecastConfigurationJSONObject = editConfigurationDisplayContext.getOrderForecastConfiguration();
String redirect = editConfigurationDisplayContext.getViewCategoryURL();
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="ordersForecasting" />

<div class="sheet">
	<aui:fieldset>
		<c:choose>
			<c:when test='<%= orderForecastConfigurationJSONObject.has("exception") %>'>
				<div class="alert alert-danger">
					<liferay-ui:message key="commerce-cloud-is-temporarily-unavailable-or-not-configured-properly" />
				</div>
			</c:when>
			<c:otherwise>
				<aui:input label="enabled" name="orderForecastSyncEnabled" type="checkbox" value="<%= commerceCloudClientConfiguration.orderForecastSyncEnabled() %>" />

				<div class="<%= commerceCloudClientConfiguration.orderForecastSyncEnabled() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />orderForecastSyncOptions">
					<aui:input label="check-interval" name="orderForecastSyncCheckInterval" suffix="minutes" value="<%= commerceCloudClientConfiguration.orderForecastSyncCheckInterval() %>">
						<aui:validator name="digits" />
						<aui:validator name="min">1</aui:validator>
					</aui:input>

					<aui:select label="order-status" name="orderForecastSyncStatus">

						<%
						for (int orderStatus : CommerceCloudClientConstants.ORDER_FORECAST_SYNC_STATUSES) {
						%>

							<aui:option label="<%= editConfigurationDisplayContext.getOrderStatusLabel(orderStatus) %>" selected="<%= orderStatus == commerceCloudClientConfiguration.orderForecastSyncStatus() %>" value="<%= orderStatus %>" />

						<%
						}
						%>

					</aui:select>

					<liferay-util:include page="/configuration/multiple_choice.jsp" servletContext="<%= application %>">
						<liferay-util:param name="name" value="periods" />
						<liferay-util:param name="values" value="<%= StringUtil.merge(CommerceCloudClientConstants.ORDER_FORECAST_PERIODS) %>" />
					</liferay-util:include>

					<aui:input helpMessage="periods-ahead-help" label="periods-ahead" name="ahead" value='<%= orderForecastConfigurationJSONObject.getInt("ahead") %>'>
						<aui:validator name="digits" />
						<aui:validator name="min">1</aui:validator>
					</aui:input>

					<aui:select helpMessage="frequency-help" name="frequency">

						<%
						for (String frequency : CommerceCloudClientConstants.ORDER_FORECAST_FREQUENCIES) {
						%>

							<aui:option label="<%= StringUtil.toLowerCase(frequency) %>" selected='<%= frequency.equals(orderForecastConfigurationJSONObject.get("frequency")) %>' value="<%= frequency %>" />

						<%
						}
						%>

					</aui:select>

					<liferay-util:include page="/configuration/multiple_choice.jsp" servletContext="<%= application %>">
						<liferay-util:param name="name" value="levels" />
						<liferay-util:param name="values" value="<%= StringUtil.merge(CommerceCloudClientConstants.ORDER_FORECAST_LEVELS) %>" />
					</liferay-util:include>

					<liferay-util:include page="/configuration/multiple_choice.jsp" servletContext="<%= application %>">
						<liferay-util:param name="name" value="targets" />
						<liferay-util:param name="values" value="<%= StringUtil.merge(CommerceCloudClientConstants.ORDER_FORECAST_TARGETS) %>" />
					</liferay-util:include>

					<aui:input helpMessage="time-zone-offset-help" name="timeZoneOffset" value='<%= orderForecastConfigurationJSONObject.getString("timeZoneOffset") %>' />
				</div>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</div>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />orderForecastSyncEnabled', '<portlet:namespace />orderForecastSyncOptions');
</aui:script>