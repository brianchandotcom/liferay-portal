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
socialGroupStatisticsPortletInstanceConfiguration = ConfigurationProviderUtil.getConfiguration(SocialGroupStatisticsPortletInstanceConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource())));

String[] displayActivityCounterNames = socialGroupStatisticsPortletInstanceConfiguration.displayActivityCounterName();

int displayActivityCounterNameIndexCount = displayActivityCounterNames.length;

if (displayActivityCounterNameIndexCount == 0) {
	displayActivityCounterNameIndexCount = 1;
}

String[] displayActivityCounterNameIndexes = new String[displayActivityCounterNameIndexCount];
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="groupStatisticsDisplayActivityCounterNamesPanel"
				label="counters"
			>
				<div id="<portlet:namespace />displayActivityCounterNames">
					<liferay-frontend:fieldset
						label=""
					>

						<%
						for (int i = 0; i < displayActivityCounterNameIndexCount; i++) {
							String index = String.valueOf(i);

							displayActivityCounterNameIndexes[i] = index;
						%>

							<div class="lfr-form-row">
								<div class="row-fields">
									<liferay-util:include page="/add_activity_counter.jsp" servletContext="<%= application %>">
										<liferay-util:param name="portletResource" value="<%= portletName %>" />
										<liferay-util:param name="index" value="<%= index %>" />
									</liferay-util:include>
								</div>
							</div>

						<%
						}
						%>

					</liferay-frontend:fieldset>
				</div>

				<aui:input name="displayActivityCounterNameIndexes" type="hidden" value="<%= StringUtil.merge(displayActivityCounterNameIndexes) %>" />

				<aui:script use="liferay-auto-fields">
					new Liferay.AutoFields({
						contentBox: '#<portlet:namespace />displayActivityCounterNames > fieldset',
						fieldIndexes: '<portlet:namespace/>displayActivityCounterNameIndexes',
						namespace: '<portlet:namespace />',
						url:
							'<liferay-portlet:renderURL portletName="<%= SocialGroupStatisticsPortletKeys.SOCIAL_GROUP_STATISTICS %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><liferay-portlet:param name="mvcPath" value="/add_activity_counter.jsp" /><liferay-portlet:param name="portletResource" value="<%= portletName %>" /></liferay-portlet:renderURL>',
						urlNamespace:
							'<%= "_" + SocialGroupStatisticsPortletKeys.SOCIAL_GROUP_STATISTICS + "_" %>'
					}).render();
				</aui:script>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>