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
JSONObject forecastingConfigurationJSONObject = editConfigurationDisplayContext.getForecastingConfiguration();
String redirect = editConfigurationDisplayContext.getViewCategoryURL();
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="forecasting" />

<div class="sheet">
	<aui:fieldset>
		<c:choose>
			<c:when test='<%= forecastingConfigurationJSONObject.has("exception") %>'>
				<div class="alert alert-danger">
					<liferay-ui:message key="commerce-cloud-is-temporarily-unavailable-or-not-configured-properly" />
				</div>
			</c:when>
			<c:otherwise>
				<aui:input label="enabled" name="forecastingEnabled" type="checkbox" value="<%= commerceCloudClientConfiguration.forecastingEnabled() %>" />

				<div class="<%= commerceCloudClientConfiguration.forecastingEnabled() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />forecastingSyncOptions">
					<aui:input label="orders-check-interval" name="forecastingOrdersCheckInterval" suffix="minutes" value="<%= commerceCloudClientConfiguration.forecastingOrdersCheckInterval() %>">
						<aui:validator name="digits" />
						<aui:validator name="min">1</aui:validator>
					</aui:input>

					<aui:select label="order-status" name="forecastingOrderStatus">

						<%
						for (int orderStatus : CommerceCloudClientConstants.ORDER_FORECAST_SYNC_STATUSES) {
						%>

							<aui:option label="<%= editConfigurationDisplayContext.getOrderStatusLabel(orderStatus) %>" selected="<%= orderStatus == commerceCloudClientConfiguration.forecastingOrderStatus() %>" value="<%= orderStatus %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select helpMessage="frequency-help" name="frequency">

						<%
						String frequency = forecastingConfigurationJSONObject.getString("frequency");

						for (String curFrequency : CommerceCloudClientConstants.ORDER_FORECAST_FREQUENCIES) {
						%>

							<aui:option label="<%= StringUtil.toLowerCase(curFrequency) %>" selected="<%= curFrequency.equals(frequency) %>" value="<%= curFrequency %>" />

						<%
						}
						%>

					</aui:select>

					<aui:input helpMessage="time-zone-offset-help" name="timeZoneOffset" value='<%= forecastingConfigurationJSONObject.getString("timeZoneOffset") %>' />

					<div id="<portlet:namespace />forecastingItems">

						<%
						JSONArray forecastingItemsConfigurationJSONArray = editConfigurationDisplayContext.getForecastingItemsConfiguration();

						for (int i = 0; i < forecastingItemsConfigurationJSONArray.length(); i++) {
							JSONObject jsonObject = forecastingItemsConfigurationJSONArray.getJSONObject(i);

							String level = jsonObject.getString("level");
							String period = jsonObject.getString("period");
							String target = jsonObject.getString("target");
						%>

							<div>
								<div class="lfr-form-row lfr-form-row-inline">
									<div class="row-fields">
										<aui:input helpMessage="ahead-help" inlineField="<%= true %>" label="ahead" name='<%= "forecastingItemAhead" + i %>' value='<%= jsonObject.getInt("ahead") %>'>
											<aui:validator name="digits" />
											<aui:validator name="min">1</aui:validator>
										</aui:input>

										<aui:select inlineField="<%= true %>" label="" name='<%= "forecastingItemPeriod" + i %>' title="period">
											<aui:option label="weeks" selected="<%= CommerceCloudClientConstants.ORDER_FORECAST_PERIOD_WEEKLY.equals(period) %>" value="<%= CommerceCloudClientConstants.ORDER_FORECAST_PERIOD_WEEKLY %>" />
											<aui:option label="months" selected="<%= CommerceCloudClientConstants.ORDER_FORECAST_PERIOD_MONTHLY.equals(period) %>" value="<%= CommerceCloudClientConstants.ORDER_FORECAST_PERIOD_MONTHLY %>" />
										</aui:select>

										<aui:select inlineField="<%= true %>" label="target" name='<%= "forecastingItemTarget" + i %>'>

											<%
											for (String curTarget : CommerceCloudClientConstants.ORDER_FORECAST_TARGETS) {
											%>

												<aui:option label="<%= editConfigurationDisplayContext.getForecastingConfigurationLabel(curTarget) %>" selected="<%= curTarget.equals(target) %>" value="<%= curTarget %>" />

											<%
											}
											%>

										</aui:select>

										<aui:select helpMessage="level-help" inlineField="<%= true %>" label="level" name='<%= "forecastingItemLevel" + i %>'>

											<%
											for (String curLevel : CommerceCloudClientConstants.ORDER_FORECAST_LEVELS) {
											%>

												<aui:option label="<%= editConfigurationDisplayContext.getForecastingConfigurationLabel(curLevel) %>" selected="<%= curLevel.equals(level) %>" value="<%= curLevel %>" />

											<%
											}
											%>

										</aui:select>
									</div>
								</div>
							</div>

						<%
						}
						%>

					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</div>

<aui:script use="aui-base,liferay-auto-fields">
	new Liferay.AutoFields(
		{
			contentBox: '#<portlet:namespace />forecastingItems',
			fieldIndexes: '<portlet:namespace />forecastingItemIndexes',
			namespace: '<portlet:namespace />'
		}
	).render();
</aui:script>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />forecastingEnabled', '<portlet:namespace />forecastingSyncOptions');
</aui:script>