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
Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="logo"
/>

<liferay-ui:error exception="<%= FileSizeException.class %>">

	<%
	FileSizeException fileSizeException = (FileSizeException)errorException;
	%>

	<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(fileSizeException.getMaxSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<p class="text-muted">

	<%
	Group group = layoutsAdminDisplayContext.getGroup();
	%>

	<c:choose>
		<c:when test="<%= group.isPrivateLayoutsEnabled() %>">
			<liferay-ui:message key='<%= "upload-a-logo-for-the-" + (layoutsAdminDisplayContext.isPrivateLayout() ? "private" : "public") + "-pages-that-is-used-instead-of-the-default-enterprise-logo" %>' />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="upload-a-logo-for-pages-that-is-used-instead-of-the-default-enterprise-logo" />
		</c:otherwise>
	</c:choose>
</p>

<c:if test="<%= liveGroup.isLayoutSetPrototype() && !PropsValues.LAYOUT_SET_PROTOTYPE_PROPAGATE_LOGO %>">
	<div class="alert alert-warning">
		<liferay-ui:message key="modifying-the-site-template-logo-only-affects-sites-that-are-not-yet-created" />
	</div>
</c:if>

<%
String companyLogoURL = themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId());

boolean defaultLogo = false;

if (selLayoutSet.getLogoId() == 0) {
	defaultLogo = true;
}
else {
	LayoutSet guestGroupLayoutSet = layoutsAdminDisplayContext.getGuestGroupLayoutSet(company.getCompanyId());

	if (selLayoutSet.getLogoId() == guestGroupLayoutSet.getLogoId()) {
		defaultLogo = true;
	}
}
%>

<liferay-ui:logo-selector
	currentLogoURL='<%= (selLayoutSet.getLogoId() == 0) ? companyLogoURL : themeDisplay.getPathImage() + "/layout_set_logo?img_id=" + selLayoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(selLayoutSet.getLogoId()) %>'
	defaultLogo="<%= defaultLogo %>"
	defaultLogoURL="<%= companyLogoURL %>"
	logoDisplaySelector=".layoutset-logo"
	showButtons="<%= GroupPermissionUtil.contains(permissionChecker, layoutsAdminDisplayContext.getSelGroup(), ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet) %>"
	tempImageFileName="<%= String.valueOf(selLayoutSet.getLayoutSetId()) %>"
/>

<%
Theme selTheme = selLayoutSet.getTheme();

boolean showSiteNameSupported = GetterUtil.getBoolean(selTheme.getSetting("show-site-name-supported"), true);

boolean showSiteNameDefault = GetterUtil.getBoolean(selTheme.getSetting("show-site-name-default"), showSiteNameSupported);
%>

<aui:input disabled="<%= !showSiteNameSupported %>" helpMessage='<%= showSiteNameSupported ? StringPool.BLANK : "the-theme-selected-for-the-site-does-not-support-displaying-the-title" %>' inlineLabel="right" label="show-site-name" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--showSiteName--" type="toggle-switch" value='<%= GetterUtil.getBoolean(selLayoutSet.getSettingsProperty("showSiteName"), showSiteNameDefault) %>' />