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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="analytics" />

<h3><liferay-ui:message key="analytics" /></h3>

<%
String[] analyticsTypes = PrefsPropsUtil.getStringArray(company.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES, StringPool.NEW_LINE);

for (String analyticsType : analyticsTypes) {
%>

	<c:choose>
		<c:when test='<%= StringUtil.equalsIgnoreCase(analyticsType, "google") %>'>

			<%
			String googleAnalyticsId = PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsId");
			%>

			<aui:input helpMessage="set-the-google-analytics-id-that-will-be-used-for-this-set-of-pages" label="google-analytics-id" name="googleAnalyticsId" size="30" type="text" value="<%= googleAnalyticsId %>" />
		</c:when>
		<c:otherwise>

			<%
			String analyticsName = TextFormatter.format(analyticsType, TextFormatter.J);

			String analyticsScript = PropertiesParamUtil.getString(groupTypeSettings, request, Sites.ANALYTICS_PREFIX + analyticsType);
			%>

			<aui:input cols="60" helpMessage='<%= LanguageUtil.format(pageContext, "set-the-script-for-x-that-will-be-used-for-this-set-of-pages", analyticsName, false) %>' label="<%= analyticsName %>" name="<%= Sites.ANALYTICS_PREFIX + analyticsType %>" rows="15" type="textarea" value="<%= analyticsScript %>" wrap="soft" />
		</c:otherwise>
	</c:choose>

<%
}
%>