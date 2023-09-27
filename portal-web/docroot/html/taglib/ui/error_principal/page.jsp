<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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