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

<%@ include file="/html/portlet/group_statistics/init.jsp" %>

<%
int index = ParamUtil.getInteger(request, "index", GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-index")));

String displayActivityCounterName = PrefsParamUtil.getString(preferences, request, "displayActivityCounterName" + index);
String chartType = PrefsParamUtil.getString(preferences, request, "chartType" + index);
String dataRange = PrefsParamUtil.getString(preferences, request, "dataRange" + index);

Collection<String> activityCounterNames = SocialConfigurationUtil.getActivityCounterNames();

activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENT);
activityCounterNames.add(SocialActivityCounterConstants.NAME_ASSET_ACTIVITY);
activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACTIVITY);
%>

<div class="aui-field-row">
	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="show" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayActivityCounterName" + index + "--" %>'>

		<%
		for (String activityCounterName : activityCounterNames) {
			if (activityCounterName.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || activityCounterName.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
				continue;
			}
		%>

			<aui:option label='<%= LanguageUtil.format(pageContext, "social.counter."+ activityCounterName, new Object[] {LanguageUtil.get(pageContext, "assets")}) %>' selected="<%= activityCounterName.equals(displayActivityCounterName) %>" value="<%= activityCounterName %>" />

		<%
		}
		%>

	</aui:select>

	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="as" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--chartType" + index + "--" %>'>
		<aui:option label="area-diagram" selected='<%= chartType.equals("area") %>' value="area" />
		<aui:option label="column-diagram" selected='<%= chartType.equals("column") %>' value="column" />
		<aui:option label="activity-distribution" selected='<%= chartType.equals("pie") %>' value="pie" />
		<aui:option label="tag-cloud" selected='<%= chartType.equals("tagCloud") %>' value="tagCloud" />
	</aui:select>

	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="in" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--dataRange" + index + "--" %>'>
		<aui:option label="this-year" selected='<%= dataRange.equals("year") %>' value="year" />
		<aui:option label="last-12-months" selected='<%= dataRange.equals("12months") %>' value="12months" />
	</aui:select>
</div>