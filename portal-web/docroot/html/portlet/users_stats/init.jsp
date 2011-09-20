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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portlet.social.model.SocialActivityConstants" %>
<%@ page import="com.liferay.portlet.social.model.SocialActivityUserStats" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityStatsEntryLocalServiceUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean displayAdditionalCounters = GetterUtil.getBoolean(PrefsParamUtil.getString(preferences, request, "displayAdditionalCounters"), true);
boolean rankOnParticipation = GetterUtil.getBoolean(PrefsParamUtil.getString(preferences, request, "rankOnParticipation"), true);
boolean rankOnContribution = GetterUtil.getBoolean(PrefsParamUtil.getString(preferences, request, "rankOnContribution"), true);

String displayCounterIndexesParam = PrefsParamUtil.getString(preferences, request, "displayCounterIndexes");

int[] displayCounterIndexes = null;

if (Validator.isNotNull(displayCounterIndexesParam)) {
	displayCounterIndexes = StringUtil.split(displayCounterIndexesParam, 0);
}
else {
	displayCounterIndexes = new int[] {0};
}
%>