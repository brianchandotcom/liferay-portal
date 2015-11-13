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
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

GroupPortletRatingsDefinitionDisplayContext groupPortletRatingsDefinitionDisplayContext = new GroupPortletRatingsDefinitionDisplayContext(groupTypeSettings, request);

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

CompanyPortletRatingsDefinitionDisplayContext companyPortletRatingsDefinitionDisplayContext = new CompanyPortletRatingsDefinitionDisplayContext(companyPortletPreferences, request);
%>

<liferay-ui:error-marker key="errorSection" value="ratings" />

<h3><liferay-ui:message key="ratings" /></h3>

<p><liferay-ui:message key="select-the-ratings-type-for-the-following-applications" /></p>

<aui:fieldset id="ratingsSettingsContainer">

	<%
	Map<String, Map<String, RatingsType>> groupRatingsTypeMaps = groupPortletRatingsDefinitionDisplayContext.getGroupRatingsTypeMaps();

	for (String portletId : groupRatingsTypeMaps.keySet()) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
	%>

		<p>
			<strong><%= PortalUtil.getPortletTitle(portlet, application, locale) %></strong>
		</p>

		<%
		Map<String, RatingsType> ratingsTypeMap = groupRatingsTypeMaps.get(portletId);

		Set<String> classNames = ratingsTypeMap.keySet();

		for (String className : classNames) {
			String propertyKey = RatingsDataTransformerUtil.getPropertyKey(className);

			RatingsType ratingsType = ratingsTypeMap.get(className);
		%>

			<aui:select label="<%= (classNames.size() > 1) ? ResourceActionsUtil.getModelResource(locale, className) : StringPool.BLANK %>" name='<%= "TypeSettingsProperties--" + propertyKey + "--" %>'>
				<aui:option label='<%= LanguageUtil.format(request, "default-value-x", companyPortletRatingsDefinitionDisplayContext.getRatingsType(portletId, className)) %>' selected="<%= ratingsType == null %>" value="<%= StringPool.BLANK %>" />

				<%
				for (RatingsType curRatingsType : RatingsType.values()) {
				%>

					<aui:option label="<%= LanguageUtil.get(request, curRatingsType.getValue()) %>" selected="<%= Validator.equals(ratingsType, curRatingsType) %>" value="<%= curRatingsType.getValue() %>" />

				<%
				}
				%>

			</aui:select>

	<%
		}
	}
	%>

</aui:fieldset>

<aui:script use="aui-base">
	var ratingsSettingsContainer = A.one('#<portlet:namespace />ratingsSettingsContainer');

	var ratingsTypeChanged = false;

	ratingsSettingsContainer.delegate(
		'change',
		function(event) {
			ratingsTypeChanged = true;
		},
		'select'
	);

	var form = A.one('#<portlet:namespace />fm');

	form.on(
		'submit',
		function(event) {
			if (ratingsTypeChanged && !confirm('<%= UnicodeLanguageUtil.get(request, "existing-ratings-data-values-will-be-adapted-to-match-the-new-ratings-type-even-though-it-may-not-be-accurate") %>')) {
				event.preventDefault();

				event.stopImmediatePropagation();
			}
		}
	);
</aui:script>