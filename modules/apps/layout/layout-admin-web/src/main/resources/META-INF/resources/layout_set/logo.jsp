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
boolean showButtons = GroupPermissionUtil.contains(permissionChecker, layoutsAdminDisplayContext.getSelGroup(), ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet);
%>

<liferay-ui:error-marker key="errorSection" value="logo" />

<aui:fieldset>
	<liferay-ui:error exception="<%= FileSizeException.class %>">

		<%
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}
		%>

		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<p><%= LanguageUtil.get(request, "upload-a-logo-for-the-" + (layoutsAdminDisplayContext.isPrivateLayout() ? "private" : "public") + "-pages-that-is-used-instead-of-the-default-enterprise-logo") %></p>

	<c:if test="<%= liveGroup.isLayoutSetPrototype() && !PropsValues.LAYOUT_SET_PROTOTYPE_PROPAGATE_LOGO %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="modifying-the-site-template-logo-only-affects-sites-that-are-not-yet-created" />
		</div>
	</c:if>

	<%
	String companyLogoURL = themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId());
	%>

	<liferay-ui:logo-selector
		currentLogoURL='<%= (selLayoutSet.getLogoId() == 0) ? companyLogoURL : themeDisplay.getPathImage() + "/layout_set_logo?img_id=" + selLayoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(selLayoutSet.getLogoId()) %>'
		defaultLogo="<%= selLayoutSet.getLogoId() == 0 %>"
		defaultLogoURL="<%= companyLogoURL %>"
		logoDisplaySelector=".layoutset-logo"
		showButtons="<%= showButtons %>"
		tempImageFileName="<%= String.valueOf(selLayoutSet.getLayoutSetId()) %>"
	/>

	<%
	boolean showSiteNameSupported = GetterUtil.getBoolean(selLayoutSet.getTheme().getSetting("show-site-name-supported"), true);

	boolean showSiteNameDefault = GetterUtil.getBoolean(selLayoutSet.getTheme().getSetting("show-site-name-default"), showSiteNameSupported);

	boolean showSiteName = GetterUtil.getBoolean(selLayoutSet.getSettingsProperty("showSiteName"), showSiteNameDefault);
	%>

	<aui:input checked="<%= showSiteName %>" disabled="<%= !showSiteNameSupported %>" helpMessage='<%= showSiteNameSupported ? StringPool.BLANK : "the-theme-selected-for-the-site-does-not-support-displaying-the-title" %>' label="show-site-name" name="TypeSettingsProperties--showSiteName--" type="checkbox" />
</aui:fieldset>