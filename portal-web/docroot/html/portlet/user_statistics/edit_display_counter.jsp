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

<%@ include file="/html/portlet/user_statistics/init.jsp" %>

<%
int index = ParamUtil.getInteger(request, "index", GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-index")));

String displayCounterNameValue = PrefsParamUtil.getString(preferences, request, "displayCounterName" + index);

Collection<String> activityCounterNames = SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ACTOR);

activityCounterNames.addAll(SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_CREATOR));
activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENT);
%>

<div class="aui-field-row query-row">
	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayCounterName" + index + "--" %>'>

		<%
		for (String activityCounterName : activityCounterNames) {
			if (activityCounterName.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || activityCounterName.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
				continue;
			}
		%>

			<aui:option label='<%= "social.counter." + activityCounterName %>' selected="<%= activityCounterName.equals(displayCounterNameValue) %>" value="<%= activityCounterName %>" />

		<%
		}
		%>

	</aui:select>
</div>