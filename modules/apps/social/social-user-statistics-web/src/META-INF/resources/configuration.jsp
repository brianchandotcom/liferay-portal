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
userStatisticsPortletInstanceConfiguration = settingsFactory.getSettings(SocialUserStatisticsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource()));

int displayActivityCounterNameCount = userStatisticsPortletInstanceConfiguration.displayActivityCounterName().length;

if (displayActivityCounterNameCount == 0) {
	displayActivityCounterNameCount = 1;
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="userStatisticsRankingsPanel" persistState="<%= true %>" title="ranking">
		<aui:input label="rank-by-contribution" name="preferences--rankByContribution--" type="checkbox" value="<%= userStatisticsPortletInstanceConfiguration.rankByContribution() %>" />

		<aui:input label="rank-by-participation" name="preferences--rankByParticipation--" type="checkbox" value="<%= userStatisticsPortletInstanceConfiguration.rankByParticipation() %>" />
	</liferay-ui:panel>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="userStatisticsSettingsPanel" persistState="<%= true %>" title="settings">
		<aui:input label="show-header-text" name="preferences--showHeaderText--" type="checkbox" value="<%= userStatisticsPortletInstanceConfiguration.showHeaderText() %>" />

		<aui:input label="show-totals" name="preferences--showTotals--" type="checkbox" value="<%= userStatisticsPortletInstanceConfiguration.showTotals() %>" />
	</liferay-ui:panel>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="userStatisticsDisplayActivityCounterNamesPanel" persistState="<%= true %>" title="counters">
		<div id="<portlet:namespace />displayActivityCounterNames">
			<aui:input label="display-additional-activity-counters" name="preferences--displayAdditionalActivityCounters--" type="checkbox" value="<%= userStatisticsPortletInstanceConfiguration.displayAdditionalActivityCounters() %>" />

			<aui:fieldset label="">

				<%
				for (int displayActivityCounterNameIndex = 0; displayActivityCounterNameIndex < displayActivityCounterNameCount; displayActivityCounterNameIndex++) {
				%>

					<div class="lfr-form-row">
						<div class="row-fields">
							<liferay-util:include page="/add_activity_counter.jsp" servletContext="<%= application %>">
								<liferay-util:param name="index" value="<%= String.valueOf(displayActivityCounterNameIndex) %>" />
							</liferay-util:include>
						</div>
					</div>

				<%
				}
				%>

			</aui:fieldset>
		</div>

		<aui:script use="liferay-auto-fields">
			var autoFields = new Liferay.AutoFields(
				{
					contentBox: '#<portlet:namespace />displayActivityCounterNames > fieldset',
					namespace: '<portlet:namespace />',
					url: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="mvcRenderCommandName" value="/portlet_configuration/add_user_statistics_activity_counter" /><liferay-portlet:param name="index" value="<%= String.valueOf(displayActivityCounterNameCount) %>" /></liferay-portlet:renderURL>'
				}
			).render();
		</aui:script>
	</liferay-ui:panel>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>