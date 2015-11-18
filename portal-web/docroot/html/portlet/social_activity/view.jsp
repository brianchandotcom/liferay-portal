<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/social_activity/init.jsp" %>

<%
Map<String, Boolean> activitySettingsMap = (Map<String, Boolean>)request.getAttribute(WebKeys.SOCIAL_ACTIVITY_SETTINGS_MAP);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/social_activity/view");
%>

<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />

<portlet:actionURL var="saveActivitySettingsURL">
	<portlet:param name="struts_action" value="/social_activity/view" />
</portlet:actionURL>

<aui:form action="<%= saveActivitySettingsURL.toString() %>" cssClass="update-socialactivity-form" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input id="settingsJSON" name="settingsJSON" type="hidden" />

	<h4>
		<liferay-ui:message key="enable-social-activity-for" />:
	</h4>

	<aui:row cssClass="social-activity social-activity-settings" id="settings">
		<aui:col cssClass="social-activity-items" width="<%= 20 %>">

			<%
			for (String className : activitySettingsMap.keySet()) {
				String localizedClassName = ResourceActionsUtil.getModelResource(locale, className);

				boolean enabled = activitySettingsMap.get(className);
			%>

				<h4 class="social-activity-item" data-modelName="<%= className %>" title="<%= localizedClassName %>">
					<div class="social-activity-item-content">
						<aui:input disabled="<%= !SocialActivityPermissionUtil.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.CONFIGURATION) %>" inlineField="<%= true %>" label="" name='<%= className + ".enabled" %>' title="enabled" type="checkbox" value="<%= enabled %>" />

						<a class="settings-label" href="javascript:;"><%= localizedClassName %></a>
					</div>
				</h4>

			<%
			}
			%>

		</aui:col>
		<aui:col cssClass="social-activity-details" width="<%= 80 %>" />
	</aui:row>

	<%
	List<String> activityDefinitionLanguageKeys = new ArrayList<String>();

	for (String modelName : activitySettingsMap.keySet()) {
		List<SocialActivityDefinition> activityDefinitions = SocialConfigurationUtil.getActivityDefinitions(modelName);

		for (SocialActivityDefinition activityDefinition : activityDefinitions) {
			activityDefinitionLanguageKeys.add("'" + modelName + "." + activityDefinition.getLanguageKey() + "': \"" + activityDefinition.getName(locale) + "\"");
		}
	}
	%>

	<aui:script use="liferay-social-activity-admin">
		new Liferay.Portlet.SocialActivity.Admin(
			{
				activityDefinitionLanguageKeys: {
					<%= StringUtil.merge(activityDefinitionLanguageKeys) %>
				},
				counterSettings: {
					contributionIncrements: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_CONTRIBUTION_INCREMENTS) %>],
					contributionLimitValues: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_CONTRIBUTION_LIMIT_VALUES) %>],
					participationIncrements: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_PARTICIPATION_INCREMENTS) %>],
					participationLimitValues: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_PARTICIPATION_LIMIT_VALUES) %>]
				},
				namespace: '<portlet:namespace />',
				portletId: '<%= portletDisplay.getId() %>'
			}
		);
	</aui:script>
</aui:form>