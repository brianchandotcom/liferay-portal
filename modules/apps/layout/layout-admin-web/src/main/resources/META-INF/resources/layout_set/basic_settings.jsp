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

<liferay-frontend:fieldset
	collapsed="<%= false %>"
	collapsible="<%= true %>"
	label="favicon"
>
	<react:component
		module="js/layout/look_and_feel/Favicon"
		props="<%= layoutsAdminDisplayContext.getFaviconButtonProps() %>"
	/>
</liferay-frontend:fieldset>

<c:if test="<%= company.isSiteLogo() %>">

	<%
	Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
	LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
	%>

	<liferay-frontend:fieldset
		collapsed="<%= false %>"
		collapsible="<%= true %>"
		label="logo"
	>
		<liferay-ui:error exception="<%= FileSizeException.class %>">

			<%
			FileSizeException fileSizeException = (FileSizeException)errorException;
			%>

			<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(fileSizeException.getMaxSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<c:if test="<%= liveGroup.isLayoutSetPrototype() && !PropsValues.LAYOUT_SET_PROTOTYPE_PROPAGATE_LOGO %>">
			<div class="alert alert-warning">
				<liferay-ui:message key="modifying-the-site-template-logo-only-affects-sites-that-are-not-yet-created" />
			</div>
		</c:if>

		<%
		Group group = layoutsAdminDisplayContext.getGroup();

		String companyLogoURL = themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId());

		String description = null;

		if (group.isPrivateLayoutsEnabled()) {
			description = LanguageUtil.get(request, "upload-a-logo-for-the-" + (layoutsAdminDisplayContext.isPrivateLayout() ? "private" : "public") + "-pages-that-is-used-instead-of-the-default-enterprise-logo");
		}
		else {
			description = LanguageUtil.get(request, "upload-a-logo-for-pages-that-is-used-instead-of-the-default-enterprise-logo");
		}
		%>

		<liferay-frontend:logo-selector
			currentLogoURL='<%= (selLayoutSet.getLogoId() == 0) ? companyLogoURL : themeDisplay.getPathImage() + "/layout_set_logo?img_id=" + selLayoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(selLayoutSet.getLogoId()) %>'
			defaultLogoURL="<%= companyLogoURL %>"
			description="<%= description %>"
		/>

		<%
		Theme selTheme = selLayoutSet.getTheme();

		boolean showSiteNameSupported = GetterUtil.getBoolean(selTheme.getSetting("show-site-name-supported"), true);

		boolean showSiteNameDefault = GetterUtil.getBoolean(selTheme.getSetting("show-site-name-default"), showSiteNameSupported);
		%>

		<aui:input aria-describedby='<%= showSiteNameSupported ? StringPool.BLANK : liferayPortletResponse.getNamespace() + "showSiteNameDescription" %>' disabled="<%= !showSiteNameSupported %>" label="show-site-name" labelCssClass="font-weight-normal" name="TypeSettingsProperties--showSiteName--" type="checkbox" value='<%= GetterUtil.getBoolean(selLayoutSet.getSettingsProperty("showSiteName"), showSiteNameDefault) %>' wrapperCssClass="mb-2" />

		<c:if test="<%= !showSiteNameSupported %>">
			<p class="mb-0 text-3 text-secondary" id="<portlet:namespace />showSiteNameDescription">
				<liferay-ui:message key="the-theme-selected-for-the-site-does-not-support-displaying-the-title" />
			</p>
		</c:if>
	</liferay-frontend:fieldset>
</c:if>