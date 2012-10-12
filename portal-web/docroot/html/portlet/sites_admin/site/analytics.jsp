<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String[] analyticsTypes = PropsValues.SITES_FORM_ANALYTICS;

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

<%
for (String analyticsType : analyticsTypes) {
%>

	<c:choose>
		<c:when test='<%= analyticsType.equals("google") %>'>
			<aui:field-wrapper helpMessage="set-the-google-analytics-id-that-will-be-used-for-this-set-of-pages" label="google-analytics-id">

				<%
				String googleAnalyticsId = PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsId");
				%>

				<input name="<portlet:namespace />googleAnalyticsId" size="30" type="text" value="<%= HtmlUtil.escape(googleAnalyticsId) %>" />
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>

			<%
			String analyticsName = TextFormatter.format(analyticsType, TextFormatter.J);
			%>

			<aui:field-wrapper helpMessage='<%= LanguageUtil.format(pageContext, "set-the-script-for-x-that-will-be-used-for-this-set-of-pages", analyticsName) %>' label="<%= analyticsName %>">

				<%
				String analyticsScript = PropertiesParamUtil.getString(groupTypeSettings, request, SitesUtil.ANALYTICS_PREFIX + analyticsType);
				%>

				<textarea cols="60" name="<portlet:namespace /><%= SitesUtil.ANALYTICS_PREFIX + analyticsType %>" rows="15" wrap="soft"><%= analyticsScript %></textarea>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>

<%
}
%>