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

Collection<String> activityCounterNames = SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ACTOR);

activityCounterNames.addAll(SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ASSET));

activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENTS);
activityCounterNames.add(SocialActivityCounterConstants.NAME_ASSET_ACTIVITIES);
activityCounterNames.add(SocialActivityCounterConstants.NAME_USER_ACTIVITIES);

List<Tuple> activityCounterNameList = new ArrayList<Tuple>();

for (String activityCounterName : activityCounterNames) {
	if (activityCounterName.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || activityCounterName.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
		continue;
	}

	activityCounterNameList.add(new Tuple(activityCounterName, LanguageUtil.get(pageContext, "social.counter."+ activityCounterName)));
}

Collections.sort(activityCounterNameList, new Comparator<Tuple>() {
	public int compare(Tuple t1, Tuple t2) {
		String s1 = (String)t1.getObject(1);
		String s2 = (String)t2.getObject(1);

		return s1.compareTo(s2);
	}
});
%>

<div class="aui-field-row">
	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-first-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayActivityCounterName" + index + "--" %>'>

		<%
		for (Tuple tuple : activityCounterNameList) {
			String activityCounterName = (String)tuple.getObject(0);
		%>

			<aui:option label='<%= tuple.getObject(1).toString() %>' selected="<%= activityCounterName.equals(displayActivityCounterName) %>" value="<%= activityCounterName %>" />

		<%
		}
		%>

	</aui:select>

	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-second-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--chartType" + index + "--" %>'>
		<aui:option label="group-statistics-chart-type-area-diagram" selected='<%= chartType.equals("area") %>' value="area" />
		<aui:option label="group-statistics-chart-type-column-diagram" selected='<%= chartType.equals("column") %>' value="column" />
		<aui:option label="group-statistics-chart-type-activity-distribution" selected='<%= chartType.equals("pie") %>' value="pie" />
		<aui:option label="group-statistics-chart-type-tag-cloud" selected='<%= chartType.equals("tagCloud") %>' value="tagCloud" />
	</aui:select>

	<span class="aui-field aui-field-inline inline-text">
		<liferay-ui:message key="group-statistics-add-counter-third-text" />
	</span>

	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--dataRange" + index + "--" %>'>
		<aui:option label="group-statistics-data-range-this-year" selected='<%= dataRange.equals("year") %>' value="year" />
		<aui:option label="group-statistics-data-range-last-12-months" selected='<%= dataRange.equals("12months") %>' value="12months" />
	</aui:select>
</div>