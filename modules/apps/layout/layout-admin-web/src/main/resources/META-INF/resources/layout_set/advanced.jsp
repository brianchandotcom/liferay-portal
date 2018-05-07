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
Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.GUEST);

boolean mergeGuestPublicPages = PropertiesParamUtil.getBoolean(layoutsAdminDisplayContext.getGroupTypeSettings(), request, "mergeGuestPublicPages");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="advanced"
/>

<c:choose>
	<c:when test="<%= !layoutsAdminDisplayContext.isPrivateLayout() && (layoutsAdminDisplayContext.getLiveGroupId() != guestGroup.getGroupId()) %>">
		<aui:input helpMessage='<%= LanguageUtil.format(request, "you-can-configure-the-top-level-pages-of-this-public-site-to-merge-with-the-top-level-pages-of-the-public-x-site", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' label='<%= LanguageUtil.format(request, "merge-x-public-pages", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' name="mergeGuestPublicPages" type="checkbox" value="<%= mergeGuestPublicPages %>" />
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="there-are-no-available-advanced-settings-for-these-pages" />
		</div>
	</c:otherwise>
</c:choose>