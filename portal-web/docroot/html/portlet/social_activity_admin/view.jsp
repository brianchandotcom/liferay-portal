<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/social_activity_admin/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/social_activity_admin/view");
%>

<portlet:actionURL var="saveActivitySettingsURL">
	<portlet:param name="struts_action" value="/social_activity_admin/view" />
</portlet:actionURL>

<aui:form action="<%= saveActivitySettingsURL.toString() %>" cssClass="update-socialactivity-form" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateSettings" />
	<aui:input name="redirect" value="<%= currentURL %>" type="hidden" />
	<aui:input id="jsonSettings" name="jsonSettings" type="hidden" />

	<h4>
		<liferay-ui:message key="enable-social-activity-for" />:
	</h4>

	<div class="social-activity social-activity-settings" id="<%= renderResponse.getNamespace() %>settings">
		<div class="social-activity-items">
		<%
		Map<String, Boolean> activitySettingsMap = (Map<String, Boolean>)request.getAttribute("social-activity-setting-mapping");

		for (String className : activitySettingsMap.keySet()) {
			String localizedClassName = ResourceActionsUtil.getModelResource(locale, className);

			boolean enabled = activitySettingsMap.get(className);
		%>

			<h4 class="social-activity-item" data-modelName="<%= className %>">
				<aui:input inline="<%= true %>" label="" name='<%= className + ".enabled" %>' type="checkbox" value="<%= enabled %>" title="" />

				<a class="settings-label" href="javascript:;"><liferay-ui:message key="<%= localizedClassName %>" /></a>
			</h4>

		<%
		}
		%>
		</div>
		<div class="social-activity-item-content">

		</div>
	</div>

	<%
		String[] strings = new String[activitySettingsMap.size()];

		int index = 0;

		for (String modelName : activitySettingsMap.keySet()) {
			List<SocialActivityDefinition> activityDefinitions  = SocialConfigurationUtil.getActivityDefinitions(modelName);

			String[] activities = new String[activityDefinitions.size()];

			for (int i=0; i<activityDefinitions.size(); i++) {
				SocialActivityDefinition activityDefinition = activityDefinitions.get(i);

				String languageKey = "social.activity.admin".concat(modelName.replace("com.liferay.portlet", ""));

				activities[i] = activityDefinition.getLanguageKey().concat(": '").concat(LanguageUtil.get(pageContext, languageKey)).concat(StringPool.PERIOD).concat(activityDefinition.getLanguageKey()).concat(StringPool.APOSTROPHE);
			}

			strings[index] = StringUtil.quote(modelName, StringPool.APOSTROPHE).concat(": {").concat(StringUtil.merge(activities)).concat("}");

			index++;
		}
	%>
	<aui:script use="liferay-social-activity-admin">
		new Liferay.Portlet.SocialActivity.Admin(
			{
				namespace: '<portlet:namespace />',
				portletId: '<%= portletDisplay.getId() %>',
				counterSettings: {
					participationLimitValues: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_PARTICIPATION_LIMIT_VALUES) %>],
					participationValues: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_PARTICIPATION_VALUES) %>],
					contributionLimitValues: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_CONTRIBUTION_LIMIT_VALUES) %>],
					contributionValues: [<%= StringUtil.merge(PropsValues.SOCIAL_ACTIVITY_CONTRIBUTION_VALUES) %>]
				},
				strings: {<%=StringUtil.merge(strings) %>}
			}
		);
	</aui:script>
</aui:form>