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
	<p><liferay-ui:message key="changing-ratings-type-could-lead-to-inaccurate-information" /></p>
</div>

<p><liferay-ui:message key="select-the-ratings-type-for-the-following-portlets" /></p>

<aui:fieldset>

	<%
	PortletRatingsDefinitionDisplayContext portletRatingsDisplayContext = new PortletRatingsDefinitionDisplayContext();

	Map<String, Map<String, RatingsType>> portletRatingsDefinitionMap = portletRatingsDisplayContext.getPortletRatingsDefinitionMap();

	for (String portletId : portletRatingsDefinitionMap.keySet()) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
	%>

		<p>
			<strong><%= PortalUtil.getPortletTitle(portlet, application, locale) %></strong>
		</p>

		<%
		Map<String, RatingsType> ratingsTypeMap = portletRatingsDefinitionMap.get(portletId);

		Set<String> classNames = ratingsTypeMap.keySet();

		for (String className : classNames) {
			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(className);

			RatingsType defaultRatingsType = PortletRatingsDefinitionUtil.getDefaultRatingsType(className);

			String ratingsTypeString = PrefsParamUtil.getString(companyPortletPreferences, request, propertyKey, defaultRatingsType.toString());
			%>

			<div class="ratings-type-select">
				<aui:select label="<%= (classNames.size() > 1) ? ResourceActionsUtil.getModelResource(locale, className) : StringPool.BLANK %>" name='<%= "settings--" + propertyKey + "--" %>'>

					<%
					for (RatingsType curRatingsType : RatingsType.values()) {
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