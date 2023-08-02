<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<div class="btn-group mt-4">
				<div class="btn-group-item">
					<clay:button
						displayType="primary"
						label="save"
						type="submit"
					/>
				</div>

				<div class="btn-group-item">
					<clay:link
						displayType="secondary"
						href="<%= redirect %>"
						label="cancel"
						type="button"
					/>
				</div>
			</div>
		</clay:sheet-footer>
	</aui:form>
</clay:sheet>