<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
UnicodeProperties groupTypeSettingsUnicodeProperties = (UnicodeProperties)request.getAttribute("site.groupTypeSettings");

String[] analyticsTypes = PrefsPropsUtil.getStringArray(company.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES, StringPool.NEW_LINE);

for (String analyticsType : analyticsTypes) {
%>

	<c:choose>
		<c:when test='<%= StringUtil.equalsIgnoreCase(analyticsType, "google") %>'>
			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-id" name="googleAnalyticsId" type="text" value='<%= PropertiesParamUtil.getString(groupTypeSettingsUnicodeProperties, request, "googleAnalyticsId") %>' />

				<span class="small text-secondary"><liferay-ui:message key="set-the-google-analytics-id-that-is-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-create-custom-configuration" name="googleAnalyticsCreateCustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettingsUnicodeProperties, request, "googleAnalyticsCreateCustomConfiguration") %>' />

				<span class="small text-secondary"><liferay-ui:message key="set-the-google-analytics-create-custom-options-that-are-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-custom-configuration" name="googleAnalyticsCustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettingsUnicodeProperties, request, "googleAnalyticsCustomConfiguration") %>' />

				<span class="small text-secondary"><liferay-ui:message key="set-the-google-analytics-custom-options-that-are-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>
		</c:when>
		<c:when test='<%= StringUtil.equalsIgnoreCase(analyticsType, "googleAnalytics4") %>'>
			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-4-id" name="googleAnalytics4Id" type="text" value='<%= PropertiesParamUtil.getString(groupTypeSettingsUnicodeProperties, request, "googleAnalytics4Id") %>' />

				<span class="small text-secondary"><liferay-ui:message key="set-the-google-analytics-4-id-that-is-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="google-analytics-4-custom-configuration" name="googleAnalytics4CustomConfiguration" type="textarea" value='<%= PropertiesParamUtil.getString(groupTypeSettingsUnicodeProperties, request, "googleAnalytics4CustomConfiguration") %>' />

				<span class="small text-secondary"><liferay-ui:message key="set-the-google-analytics-4-custom-options-that-are-used-for-this-set-of-pages" /></span>
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>

			<%
			String analyticsName = TextFormatter.format(analyticsType, TextFormatter.J);
			%>

			<aui:field-wrapper cssClass="form-group">
				<aui:input label="<%= analyticsName %>" name="<%= Sites.ANALYTICS_PREFIX + analyticsType %>" type="textarea" value="<%= PropertiesParamUtil.getString(groupTypeSettingsUnicodeProperties, request, Sites.ANALYTICS_PREFIX + analyticsType) %>" wrap="soft" />

				<span class="small text-secondary"><liferay-ui:message arguments="<%= analyticsName %>" key="set-the-script-for-x-that-is-used-for-this-set-of-pages" translateArguments="<%= false %>" /></span>
			</aui:field-wrapper>
		</c:otherwise>
	</c:choose>

<%
}
%>