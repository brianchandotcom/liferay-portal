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
SiteSettingsConfigurationScreenContributor siteSettingsConfigurationScreenContributor = (SiteSettingsConfigurationScreenContributor)request.getAttribute(SiteAdminWebKeys.SITE_SETTINGS_CONFIGURATION_SCREEN_CONTRIBUTOR);

String redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}

Group siteGroup = themeDisplay.getSiteGroup();

Group liveGroup = null;

if (siteGroup.isStagingGroup()) {
	liveGroup = siteGroup.getLiveGroup();
}
else {
	liveGroup = siteGroup;
}
%>

<portlet:actionURL name='<%= GetterUtil.get(siteSettingsConfigurationScreenContributor.getSaveMVCActionCommandName(), "/site_admin/edit_site_settings") %>' var="editSiteSettingsURL">
	<portlet:param name="mvcRenderCommandName" value="/configuration_admin/view_configuration_screen" />
	<portlet:param name="configurationScreenKey" value="<%= siteSettingsConfigurationScreenContributor.getKey() %>" />
</portlet:actionURL>

<clay:sheet>
	<clay:content-row
		containerElement="h2"
		cssClass="mb-5"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<liferay-ui:message key="<%= siteSettingsConfigurationScreenContributor.getName(locale) %>" />
		</clay:content-col>
	</clay:content-row>

	<aui:form action="<%= editSiteSettingsURL %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="liveGroupId" type="hidden" value="<%= liveGroup.getGroupId() %>" />

		<liferay-util:include page="<%= siteSettingsConfigurationScreenContributor.getJspPath() %>" servletContext="<%= siteSettingsConfigurationScreenContributor.getServletContext() %>" />

		<clay:sheet-footer>
			<div class="btn-group">
				<div class="btn-group-item">
					<aui:button type="submit" value="save" />
				</div>

				<div class="btn-group-item">
					<aui:button href="<%= redirect %>" name="cancel" type="cancel" />
				</div>
			</div>
		</clay:sheet-footer>
	</aui:form>
</clay:sheet>