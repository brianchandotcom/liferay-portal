<%@ page
        import="com.liferay.commerce.user.web.internal.display.context.CommerceOrganizationDetailDisplayContext" %><%--
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
CommerceOrganizationDetailDisplayContext commerceOrganizationDetailDisplayContext = (CommerceOrganizationDetailDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationDetailDisplayContext.getSiteOrganization();
%>

<h1><%= organization.getName() %></h1>

<c:choose>
    <c:when test="<%= !commerceOrganizationDetailDisplayContext.siteHasOrganization()%>">
        <div class="alert alert-warning" role="alert">
            <span class="alert-indicator">
                <svg aria-hidden="true" class="lexicon-icon lexicon-icon-warning-full">
                    <use xlink:href="<%= themeDisplay.getPathThemeImages()%>/lexicon/icons.svg#warning-full"></use>
                </svg>
            </span>
            <liferay-ui:message key="organization-not-configured-for-this-site" />
        </div>
    </c:when>
    <c:otherwise>

        <liferay-frontend:screen-navigation
            key="<%= CommerceUserScreenNavigationConstants.SCREEN_NAVIGATION_KEY %>"
            modelBean="<%= commerceOrganizationDetailDisplayContext.getSiteOrganization() %>"
            portletURL="<%= currentURLObj %>"
        />
    </c:otherwise>
</c:choose>


