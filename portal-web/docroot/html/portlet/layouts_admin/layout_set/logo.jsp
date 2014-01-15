<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
UnicodeProperties groupTypeSettings = (UnicodeProperties)request.getAttribute("edit_pages.jsp-groupTypeSettings");
LayoutSet selLayoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));
%>

<liferay-ui:error-marker key="errorSection" value="logo" />

<h3><liferay-ui:message key="logo" /></h3>

<aui:fieldset>
	<liferay-ui:error exception="<%= FileSizeException.class %>">

		<%
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}

		fileMaxSize /= 1024;
		%>

		<liferay-ui:message arguments="<%= fileMaxSize %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<p><%= LanguageUtil.get(pageContext, "upload-a-logo-for-the-" + (privateLayout ? "private" : "public") + "-pages-that-will-be-used-instead-of-the-default-enterprise-logo") %></p>

	<%
	String companyLogoURL = themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId());
	%>

	<liferay-ui:logo-selector
		currentLogoURL='<%= (selLayoutSet.getLogoId() == 0) ? companyLogoURL : themeDisplay.getPathImage() + "/layout_set_logo?img_id=" + selLayoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(selLayoutSet.getLogoId()) %>'
		defaultLogo="<%= selLayoutSet.getLogoId() == 0 %>"
		defaultLogoURL="<%= companyLogoURL %>"
		logoDisplaySelector=".layoutset-logo"
		tempImageFileName="<%= String.valueOf(selLayoutSet.getLayoutSetId()) %>"
	/>

	<%
	Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.GUEST);
	%>

	<%
	boolean showSiteNameSupported = GetterUtil.getBoolean(selLayoutSet.getTheme().getSetting("show-site-name-supported"), true);

	boolean showSiteNameDefault = GetterUtil.getBoolean(selLayoutSet.getTheme().getSetting("show-site-name-default"), showSiteNameSupported);

	boolean showSiteName = GetterUtil.getBoolean(selLayoutSet.getSettingsProperty("showSiteName"), showSiteNameDefault);
	%>

	<aui:input checked="<%= showSiteName %>" disabled="<%= !showSiteNameSupported %>" helpMessage='<%= showSiteNameSupported ? StringPool.BLANK : "the-theme-selected-for-the-site-does-not-support-displaying-the-title" %>' label="show-site-name" name="TypeSettingsProperties--showSiteName--" type="checkbox" />

</aui:fieldset>