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
UnicodeProperties groupTypeSettings = (UnicodeProperties)request.getAttribute("site.groupTypeSettings");

String[] analyticsTypes = PrefsPropsUtil.getStringArray(company.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES, StringPool.NEW_LINE);

for (String analyticsType : analyticsTypes) {
%>

	<c:choose>
		<c:when test='<%= StringUtil.equalsIgnoreCase(analyticsType, "google") %>'>
			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-id" name="googleAnalyticsId" type="text" value='<%= PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsId") %>' />

				<span class="form-text"><liferay-ui:message key="set-the-google-analytics-id-that-is-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-create-custom-configuration" name="googleAnalyticsCreateCustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsCreateCustomConfiguration") %>' />

				<span class="form-text"><liferay-ui:message key="set-the-google-analytics-create-custom-options-that-are-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-custom-configuration" name="googleAnalyticsCustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettings, request, "googleAnalyticsCustomConfiguration") %>' />

				<span class="form-text"><liferay-ui:message key="set-the-google-analytics-custom-options-that-are-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>

			<%
			String analyticsName = TextFormatter.format(analyticsType, TextFormatter.J);
			%>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="<%= analyticsName %>" name="<%= Sites.ANALYTICS_PREFIX + analyticsType %>" type="textarea" value="<%= PropertiesParamUtil.getString(groupTypeSettings, request, Sites.ANALYTICS_PREFIX + analyticsType) %>" wrap="soft" />

				<span class="form-text"><liferay-ui:message arguments="<%= analyticsName %>" key="set-the-script-for-x-that-is-used-for-this-set-of-pages" translateArguments="<%= false %>" /></span>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>

<%
}
%>