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
socialUserStatisticsPortletInstanceConfiguration = ConfigurationProviderUtil.getConfiguration(SocialUserStatisticsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource()));

int displayActivityCounterNameCount = socialUserStatisticsPortletInstanceConfiguration.displayActivityCounterName().length;

if (displayActivityCounterNameCount == 0) {
	displayActivityCounterNameCount = 1;
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="userStatisticsRankingsPanel" persistState="<%= true %>" title="ranking">
				<aui:input label="rank-by-contribution" name="preferences--rankByContribution--" type="checkbox" value="<%= socialUserStatisticsPortletInstanceConfiguration.rankByContribution() %>" />

				<aui:input label="rank-by-participation" name="preferences--rankByParticipation--" type="checkbox" value="<%= socialUserStatisticsPortletInstanceConfiguration.rankByParticipation() %>" />
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="userStatisticsSettingsPanel" persistState="<%= true %>" title="settings">
				<aui:input label="show-header-text" name="preferences--showHeaderText--" type="checkbox" value="<%= socialUserStatisticsPortletInstanceConfiguration.showHeaderText() %>" />

				<aui:input label="show-totals" name="preferences--showTotals--" type="checkbox" value="<%= socialUserStatisticsPortletInstanceConfiguration.showTotals() %>" />
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="userStatisticsDisplayActivityCounterNamesPanel" persistState="<%= true %>" title="counters">
				<div id="<portlet:namespace />displayActivityCounterNames">
					<aui:input label="display-additional-activity-counters" name="preferences--displayAdditionalActivityCounters--" type="checkbox" value="<%= socialUserStatisticsPortletInstanceConfiguration.displayAdditionalActivityCounters() %>" />

					<aui:fieldset label="">

						<%
						for (int displayActivityCounterNameIndex = 0; displayActivityCounterNameIndex < displayActivityCounterNameCount; displayActivityCounterNameIndex++) {
						%>

							<div class="lfr-form-row">
								<div class="row-fields">
									<liferay-util:include page="/add_activity_counter.jsp" servletContext="<%= application %>">
										<liferay-util:param name="portletResource" value="<%= portletName %>" />
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
							url: '<liferay-portlet:renderURL portletName="<%= SocialUserStatisticsPortletKeys.SOCIAL_USER_STATISTICS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="mvcPath" value="/add_activity_counter.jsp" /><liferay-portlet:param name="index" value="<%= String.valueOf(displayActivityCounterNameCount) %>" /><liferay-portlet:param name="portletResource" value="<%= portletName %>" /></liferay-portlet:renderURL>'
						}
					).render();
				</aui:script>
			</liferay-ui:panel>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>