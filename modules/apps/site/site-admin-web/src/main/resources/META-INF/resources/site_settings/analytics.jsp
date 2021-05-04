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
Group siteGroup = themeDisplay.getSiteGroup();

Group liveGroup = null;

if (siteGroup.isStagingGroup()) {
	liveGroup = siteGroup.getLiveGroup();
}
else {
	liveGroup = siteGroup;
}

UnicodeProperties groupTypeSettings = liveGroup.getTypeSettingsProperties();

String[] analyticsTypes = PrefsPropsUtil.getStringArray(company.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES, StringPool.NEW_LINE);

for (String analyticsType : analyticsTypes) {
%>

	<c:choose>
		<c:when test='<%= StringUtil.equalsIgnoreCase(analyticsType, "google") %>'>
			<aui:input helpMessage="set-the-google-analytics-id-that-is-used-for-this-set-of-pages" label="google-analytics-id" name="googleAnalyticsId" type="text" value='<%= PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsId") %>' />

			<aui:input helpMessage="set-the-google-analytics-create-custom-options-that-are-used-for-this-set-of-pages" label="google-analytics-create-custom-configuration" name="googleAnalyticsCreateCustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsCreateCustomConfiguration") %>' />

			<aui:input helpMessage="set-the-google-analytics-custom-options-that-are-used-for-this-set-of-pages" label="google-analytics-custom-configuration" name="googleAnalyticsCustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsCustomConfiguration") %>' />
		</c:when>
		<c:otherwise>

			<%
			String analyticsName = TextFormatter.format(analyticsType, TextFormatter.J);
			%>

			<aui:input helpMessage='<%= LanguageUtil.format(request, "set-the-script-for-x-that-is-used-for-this-set-of-pages", analyticsName, false) %>' label="<%= analyticsName %>" name="<%= Sites.ANALYTICS_PREFIX + analyticsType %>" type="textarea" value="<%= PropertiesParamUtil.getString(groupTypeSettings, request, Sites.ANALYTICS_PREFIX + analyticsType) %>" wrap="soft" />
		</c:otherwise>
	</c:choose>

<%
}
%>