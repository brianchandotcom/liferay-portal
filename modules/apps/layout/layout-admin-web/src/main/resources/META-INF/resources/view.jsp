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
Group group = layoutsAdminDisplayContext.getGroup();

SitesUtil.addPortletBreadcrumbEntries(group, layoutsAdminDisplayContext.getPagesName(), layoutsAdminDisplayContext.getRedirectURL(), request, renderResponse);
%>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;

	String type = BeanParamUtil.getString(layoutsAdminDisplayContext.getSelLayout(), request, "type");
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= Validator.isNull(lte.getLayoutType()) ? type : "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
	</c:if>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT_PERMISSION %>">
		<liferay-ui:message key="you-cannot-delete-this-page-because-the-next-page-is-not-vieweable-by-unathenticated-users-and-so-cannot-be-the-first-page" />
	</c:if>

	<c:if test="<%= lte.getType() == LayoutTypeException.NOT_INSTANCEABLE %>">
		<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-be-selected" />
	</c:if>

	<c:if test="<%= lte.getType() == LayoutTypeException.NOT_PARENTABLE %>">
		<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-have-child-pages" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= LayoutNameException.class %>" message="please-enter-a-valid-name" />

<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

	<%
	RequiredLayoutException rle = (RequiredLayoutException)errorException;
	%>

	<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
		<liferay-ui:message key="you-must-have-at-least-one-page" />
	</c:if>
</liferay-ui:error>

<div class="container-fluid-1280">
	<div class="lfr-app-column-view manage-view row">
		<c:choose>
			<c:when test="<%= layoutsAdminDisplayContext.getSelPlid() > 0 %>">
				<liferay-util:include page="/edit_layout.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/edit_layout_set.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</div>
</div>