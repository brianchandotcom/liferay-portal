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

GroupPortletRatingsDefinitionDisplayContext groupPortletRatingsDefinitionDisplayContext = new GroupPortletRatingsDefinitionDisplayContext(groupTypeSettings, request);

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

CompanyPortletRatingsDefinitionDisplayContext companyPortletRatingsDefinitionDisplayContext = new CompanyPortletRatingsDefinitionDisplayContext(companyPortletPreferences, request);
%>

<p class="text-secondary"><liferay-ui:message key="select-the-ratings-type-for-the-following-applications" /></p>

<aui:fieldset id='<%= liferayPortletResponse.getNamespace() + "ratingsSettingsContainer" %>'>

	<%
	Map<String, Map<String, RatingsType>> groupRatingsTypeMaps = groupPortletRatingsDefinitionDisplayContext.getGroupRatingsTypeMaps();

	for (Map.Entry<String, Map<String, RatingsType>> entry : groupRatingsTypeMaps.entrySet()) {
		String portletId = entry.getKey();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
	%>

		<h4 class="text-default">
			<%= PortalUtil.getPortletTitle(portlet, application, locale) %>
		</h4>

		<%
		Map<String, RatingsType> ratingsTypeMap = entry.getValue();

		Set<Map.Entry<String, RatingsType>> ratingsTypeMapEntries = ratingsTypeMap.entrySet();

		for (Map.Entry<String, RatingsType> ratingsTypeMapEntry : ratingsTypeMapEntries) {
			String className = ratingsTypeMapEntry.getKey();

			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(className);

			RatingsType ratingsType = ratingsTypeMapEntry.getValue();
		%>

			<aui:select label="<%= (ratingsTypeMapEntries.size() > 1) ? ResourceActionsUtil.getModelResource(locale, className) : StringPool.BLANK %>" name='<%= "RatingsType--" + propertyKey + "--" %>'>
				<aui:option label='<%= LanguageUtil.format(request, "default-value-x", companyPortletRatingsDefinitionDisplayContext.getRatingsType(portletId, className)) %>' selected="<%= ratingsType == null %>" value="<%= StringPool.BLANK %>" />

				<%
				for (RatingsType curRatingsType : RatingsType.values()) {
				%>

					<aui:option label="<%= LanguageUtil.get(request, curRatingsType.getValue()) %>" selected="<%= Objects.equals(ratingsType, curRatingsType) %>" value="<%= curRatingsType.getValue() %>" />

				<%
				}
				%>

			</aui:select>

	<%
		}
	}
	%>

</aui:fieldset>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "ratings" %>'
	module="js/site/Ratings"
/>