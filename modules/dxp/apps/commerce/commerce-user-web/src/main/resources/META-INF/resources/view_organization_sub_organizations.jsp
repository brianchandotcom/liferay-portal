<%@ page import="javax.portlet.PortletURL" %>
<%@ page
        import="com.liferay.commerce.user.web.internal.display.context.CommerceOrganizationMembersDisplayContext" %>
<%@ page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>
<%@ page
        import="com.liferay.commerce.user.web.internal.display.context.CommerceOrganizationSubOrganizationsDisplayContext" %>
<%@ page import="com.liferay.portal.kernel.search.Field" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%--
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
    CommerceOrganizationSubOrganizationsDisplayContext commerceOrganizationSubOrganizationsDisplayContext = (CommerceOrganizationSubOrganizationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="users-container" id="<portlet:namespace />entriesContainer">
    <liferay-ui:search-container
            id="organizations"
            searchContainer="<%= commerceOrganizationSubOrganizationsDisplayContext.getSearchContainer() %>"
    >

        <liferay-ui:search-container-row
                className="com.liferay.portal.kernel.model.Organization"
                cssClass="entry-display-style"
                keyProperty="organizationId"
                modelVar="organization"
        >
            <liferay-ui:search-container-column-text
                name="name"
                value="<%= organization.getName() %>"
            />
            <liferay-ui:search-container-column-text
                title="path"
            >
                <%= HtmlUtil.escape(commerceOrganizationSubOrganizationsDisplayContext.getPath(organization)) %> > <strong><%= HtmlUtil.escape(organization.getName()) %></strong>
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-jsp
                    cssClass="entry-action-column"
                    path="/sub_organization_action.jsp"
            />
        </liferay-ui:search-container-row>

        <liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commerceOrganizationSubOrganizationsDisplayContext.getSearchContainer() %>" />
    </liferay-ui:search-container>
</div>