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

Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.GUEST);

boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "mergeGuestPublicPages");
%>

<liferay-ui:error-marker key="errorSection" value="advanced" />

<h3><liferay-ui:message key="advanced" /></h3>

<aui:fieldset>
	<c:choose>
		<c:when test="<%= !privateLayout && (liveGroup.getGroupId() != guestGroup.getGroupId()) %>">

			<%
			String taglibLabel = LanguageUtil.format(pageContext, "merge-x-public-pages", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false);
			String taglibHelpMessage = LanguageUtil.format(pageContext, "you-can-configure-the-top-level-pages-of-this-public-site-to-merge-with-the-top-level-pages-of-the-public-x-site", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false);
			%>

			<aui:input helpMessage="<%= taglibHelpMessage %>" label="<%= taglibLabel %>" name="mergeGuestPublicPages" type="checkbox" value="<%= mergeGuestPublicPages %>" />
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="there-are-no-available-advanced-settings-for-these-pages" />
			</div>
		</c:otherwise>
	</c:choose>
</aui:fieldset>