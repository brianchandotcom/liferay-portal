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
Collection<String> counters = ResourceActionsUtil.getSocialActivityCounters(SocialActivityConstants.OWNER_TYPE_ACTOR);
counters.addAll(ResourceActionsUtil.getSocialActivityCounters(SocialActivityConstants.OWNER_TYPE_CREATOR));

counters.add(SocialActivityConstants.STAT_USER_ACHIEVEMENT);
%>

<div class="aui-field-row query-row">
	<aui:select inlineField="<%= true %>" label="" name='<%= "preferences--displayCounter" + index + "--" %>'>
		<%
			for (String counter : counters) {
				if (counter.equals(SocialActivityConstants.STAT_CONTRIBUTION) || counter.equals(SocialActivityConstants.STAT_PARTICIPATION)) {
					continue;
				}
		%>
		<aui:option label='<%="social.counter."+ counter %>' selected="<%=counter.equals(value) %>" value="<%=counter %>" />
		<%
			}
		%>
	</aui:select>
</div>