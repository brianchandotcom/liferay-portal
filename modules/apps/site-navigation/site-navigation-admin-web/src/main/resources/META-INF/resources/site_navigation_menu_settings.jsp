<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

SiteNavigationMenu siteNavigationMenu = siteNavigationAdminDisplayContext.getSiteNavigationMenu();
%>

<portlet:actionURL name="/site_navigation_admin/edit_site_navigation_menu_settings" var="editSiteNavigationMenuSettingsURL" />

<aui:form action="<%= editSiteNavigationMenuSettingsURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenu.getSiteNavigationMenuId() %>" />
	<aui:input name="type" type="hidden" value="<%= siteNavigationMenu.getType() %>" />

	<%
	String typeKey = siteNavigationMenu.getTypeKey();
	%>

	<c:if test="<%= Validator.isNotNull(typeKey) %>">
		<div class="mb-4">
			<div class="h5">
				<strong><liferay-ui:message key="this-menu-will-act-as-the" /></strong>
			</div>

			<clay:icon
				symbol="check-circle-full"
			/>

			<liferay-ui:message key="<%= typeKey %>" />
		</div>
	</c:if>

	<aui:fieldset>
		<clay:checkbox
			checked="<%= siteNavigationMenu.isAuto() %>"
			id='<%= liferayPortletResponse.getNamespace() + "auto" %>'
			label="when-creating-a-new-page,-display-an-option-to-add-it-to-this-navigation-menu"
			name='<%= liferayPortletResponse.getNamespace() + "auto" %>'
		/>
	</aui:fieldset>

	<clay:button
		block="<%= true %>"
		label="save"
		type="submit"
	/>
</aui:form>