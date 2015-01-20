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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="ratings" />

<h3><liferay-ui:message key="ratings" /></h3>

<div class="alert alert-info">
	<p><liferay-ui:message key="changing-rating-type-could-produce-inaccurate-info" /></p>
</div>

<p><liferay-ui:message key="select-the-rating-type-for-the-portlets" /></p>

<aui:fieldset>

	<%
	String[] portletIds = PortletRatingsDefinitionUtil.getPortletIds();

	for (String portletId : portletIds) {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
	%>

		<p><%= PortalUtil.getPortletTitle(portlet, application, locale) %></p>

		<%
		String[] classNames = PortletRatingsDefinitionUtil.getClassNames(portletId);

		for (String className : classNames) {
			String propertyName = className + StringPool.UNDERLINE + "RatingsType";

			PortletRatingsDefinition.RatingsType defaultRatingsType = PortletRatingsDefinitionUtil.getDefaultType(portletId, className);

			String companyRatingsTypeString = PrefsParamUtil.getString(companyPortletPreferences, request, propertyName, defaultRatingsType.toString());

			String ratingsTypeString = PropertiesParamUtil.getString(groupTypeSettings, request, propertyName, companyRatingsTypeString);
		%>

			<div class="ratings-type-select">
				<aui:select label='<%= (classNames.length > 1) ? ResourceActionsUtil.getModelResource(locale, className) : "" %>' name='<%= "TypeSettingsProperties--" + propertyName + "--" %>'>

					<%
					for (PortletRatingsDefinition.RatingsType curRatingsType : PortletRatingsDefinition.RatingsType.values()) {
					%>

						<aui:option label="<%= LanguageUtil.get(request, curRatingsType.getValue()) %>" selected="<%= ratingsTypeString.equals(curRatingsType.getValue()) %>" value="<%= curRatingsType.getValue() %>" />

					<%
					}
					%>

				</aui:select>
			</div>

	<%
		}
	}
	%>

</aui:fieldset>