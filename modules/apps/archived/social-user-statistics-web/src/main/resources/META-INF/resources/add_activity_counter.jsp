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
String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceNamespace = PortalUtil.getPortletNamespace(portletResource);

int index = ParamUtil.getInteger(request, "index");

socialUserStatisticsPortletInstanceConfiguration = ConfigurationProviderUtil.getConfiguration(SocialUserStatisticsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource()));

String displayActivityCounterName = "";

String[] displayActivityCounterNames = socialUserStatisticsPortletInstanceConfiguration.displayActivityCounterName();

if (index < displayActivityCounterNames.length) {
	displayActivityCounterName = displayActivityCounterNames[index];
}

List<String> activityCounterNames = SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ACTOR);

activityCounterNames.addAll(SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_CREATOR));

activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENTS);

Collections.sort(activityCounterNames, new SocialActivityCounterNameComparator(locale));
%>

<div class="field-row query-row">
	<aui:select inlineField="<%= true %>" label="" name='<%= portletResourceNamespace + "displayActivityCounterName" + index %>' title="display-activity-counter-name" useNamespace="<%= false %>">

		<%
		for (String activityCounterName : activityCounterNames) {
			if (activityCounterName.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || activityCounterName.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
				continue;
			}
		%>

			<aui:option label='<%= "user.statistics." + activityCounterName %>' selected="<%= activityCounterName.equals(displayActivityCounterName) %>" value="<%= activityCounterName %>" />

		<%
		}
		%>

	</aui:select>
</div>