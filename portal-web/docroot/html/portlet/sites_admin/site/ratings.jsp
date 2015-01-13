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
	String[] portletIds = RatingsDataTransformerHelperUtil.getPortletIds();

	for (String portletId : portletIds) {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
	%>

		<p><%= PortalUtil.getPortletTitle(portlet, application, locale) %></p>

		<%
		String[] classNames = RatingsDataTransformerHelperUtil.getClassNames(portletId);

		for (String className : classNames) {
		%>

			<%
			String propertyName = className + StringPool.UNDERLINE + "RatingsType";

			String companyRatingsType = PrefsParamUtil.getString(companyPortletPreferences, request, propertyName, RatingsDataTransformerHelperUtil.getDefaultType(portletId, className));

			String ratingsType = PropertiesParamUtil.getString(groupTypeSettings, request, propertyName, companyRatingsType);
			%>

			<div class="ratings-type-select">
				<aui:select label='<%= (classNames.length > 1) ? ResourceActionsUtil.getModelResource(locale, className) : "" %>' name='<%= "TypeSettingsProperties--" + propertyName + "--" %>' value="<%= ratingsType %>">
					<aui:option label='<%= LanguageUtil.get(request, "like") %>' value="like" />
					<aui:option label='<%= LanguageUtil.get(request, "stars") %>' value="stars" />
					<aui:option label='<%= LanguageUtil.get(request, "thumbs") %>' value="thumbs" />
				</aui:select>
			</div>

	<%
		}
	}
	%>

</aui:fieldset>