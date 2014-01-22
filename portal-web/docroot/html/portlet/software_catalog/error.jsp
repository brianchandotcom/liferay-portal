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

<%@ include file="/html/portlet/software_catalog/init.jsp" %>

<liferay-ui:error-header/>

<liferay-ui:error exception="<%= NoSuchFrameworkVersionException.class %>" message="the-framework-version-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchLicenseException.class %>" message="the-license-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchProductEntryException.class %>" message="the-product-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchProductVersionException.class %>" message="the-product-version-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />