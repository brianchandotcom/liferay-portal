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

<%@ page import="com.liferay.portlet.social.model.SocialActivityCounter" %><%@
page import="com.liferay.portlet.social.model.SocialActivityCounterConstants" %><%@
page import="com.liferay.portlet.social.model.impl.SocialActivityCounterImpl" %><%@
page import="com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.util.SocialConfigurationUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean displayAdditionalCounters = GetterUtil.getBoolean(PrefsParamUtil.getString(preferences, request, "displayAdditionalCounters"), true);

int[] displayCounterNameIndexes = null;

String displayCounterNameIndexesParam = PrefsParamUtil.getString(preferences, request, "displayCounterNameIndexes");

if (Validator.isNotNull(displayCounterNameIndexesParam)) {
	displayCounterNameIndexes = StringUtil.split(displayCounterNameIndexesParam, 0);
}
else {
	displayCounterNameIndexes = new int[] {0};
}

boolean rankByContribution = GetterUtil.getBoolean(PrefsParamUtil.getString(preferences, request, "rankByContribution"), true);
boolean rankByParticipation = GetterUtil.getBoolean(PrefsParamUtil.getString(preferences, request, "rankByParticipation"), true);
%>