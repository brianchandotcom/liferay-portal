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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());
%>

<liferay-ui:error-marker key="errorSection" value="ratings" />

<h3><liferay-ui:message key="ratings" /></h3>

<div class="alert alert-info">
	<p><liferay-ui:message key="changing-rating-type-could-lead-to-inaccurate-info" /></p>
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
		%>

			<c:if test="<%= classNames.length > 1 %>">
				<p><%= ResourceActionsUtil.getModelResource(locale, className) %></p>
			</c:if>

			<%
			String propertyName = className + StringPool.UNDERLINE + "RatingsType";

			PortletRatingsDefinition.RatingsType defaultRatingsType = PortletRatingsDefinitionUtil.getDefaultType(portletId, className);

			String ratingsTypeString = PrefsParamUtil.getString(companyPortletPreferences, request, propertyName, defaultRatingsType.toString());
			%>

			<div class="row-fields">
				<aui:select label='<%= (classNames.length > 1) ? ResourceActionsUtil.getModelResource(locale, className) : "" %>' name='<%= "settings--" + propertyName + "--" %>'>

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