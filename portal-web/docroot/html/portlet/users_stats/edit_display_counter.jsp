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

<%@ include file="/html/portlet/users_stats/init.jsp" %>

<%
int index = ParamUtil.getInteger(request, "index", GetterUtil.getInteger((String)request.getAttribute("configuration.jsp-index")));

String value = PrefsParamUtil.getString(preferences, request, "displayCounter" + index);

Collection<String> counters = SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_ACTOR);

counters.addAll(SocialConfigurationUtil.getActivityCounterNames(SocialActivityCounterConstants.TYPE_CREATOR));
counters.add(SocialActivityCounterConstants.NAME_USER_ACHIEVEMENT);
%>

<div class="aui-field-row query-row">
	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayCounter" + index + "--" %>'>

		<%
			for (String counter : counters) {
				if (counter.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) || counter.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
					continue;
				}
		%>

			<aui:option label='<%="social.counter."+ counter %>' selected="<%=counter.equals(value) %>" value="<%=counter %>" />

		<%
			}
		%>

	</aui:select>
</div>