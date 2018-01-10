<%@ page import="javax.portlet.PortletURL" %>
<%@ page
        import="com.liferay.commerce.user.web.internal.display.context.CommerceOrganizationMembersDisplayContext" %>
<%@ page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%--
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
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>


<div class="users-container" id="<portlet:namespace />entriesContainer">
    <liferay-ui:search-container
            id="users"
            searchContainer="<%= commerceOrganizationMembersDisplayContext.getSearchContainer() %>"
    >
        <%
            String userStatus = (user.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "active" : "inactive";
        %>

        <liferay-ui:search-container-row
                className="com.liferay.portal.kernel.model.User"
                cssClass="entry-display-style"
                keyProperty="userId"
                modelVar="user"
        >
            <liferay-ui:search-container-column-text
                    cssClass="important table-cell-content"
                    name="name"
                    orderable="<%= true %>"
                    value="<%= user.getFullName() %>"
            />
            <liferay-ui:search-container-column-text
                    cssClass="table-cell-content"
                    name="status"
                    value="<%= LanguageUtil.get(request, userStatus) %>"
            />
        </liferay-ui:search-container-row>

        <liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commerceOrganizationMembersDisplayContext.getSearchContainer() %>" />
    </liferay-ui:search-container>
</div>