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

<%@ include file="/html/taglib/ui/error_principal/init.jsp" %>

<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeAuthenticated.class %>" message="please-sign-in-to-access-this-application" />
<liferay-ui:error exception="<%= PrincipalException.MustBeCompanyAdmin.class %>" message="you-do-not-have-the-required-permissions" />

<liferay-ui:error exception="<%= PrincipalException.MustBeEnabled.class %>">

	<%
	PrincipalException.MustBeEnabled pe = (PrincipalException.MustBeEnabled)errorException;
	%>

	<liferay-ui:message arguments="<%= pe.resourceName %>" key="x-is-not-enabled" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= PrincipalException.MustBeInvokedUsingPost.class %>" message="an-unexpected-error-occurred-while-connecting-to-the-specified-url" />
<liferay-ui:error exception="<%= PrincipalException.MustBeOmniadmin.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBePortletStrutsPath.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustHavePermission.class %>" message="you-do-not-have-the-required-permissions" />