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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<c:choose>
		<c:when test="<%= SessionErrors.contains(request, PrincipalException.getNestedClasses()) %>">
			<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-permission-to-view-this-page" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="an-unexpected-error-occurred" />
		</c:otherwise>
	</c:choose>
</div>