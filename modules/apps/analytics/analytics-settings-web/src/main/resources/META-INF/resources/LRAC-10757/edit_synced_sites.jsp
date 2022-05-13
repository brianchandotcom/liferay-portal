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

<%@ include file="/LRAC-10757/init.jsp" %>

<%
QADisplayContext qaDisplayContext = (QADisplayContext)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_DISPLAY_CONTEXT);
%>

<c:if test="<%= qaDisplayContext.isWizardMode() %>">
	<h1> Wizard Mode</h1>
</c:if>

<c:choose>
	<c:when test="<%= qaDisplayContext.isWizardMode() %>">
		<p>Test success, we are in Wizard Mode.</p>
	</c:when>
	<c:otherwise>
		<p>Test FAIL, we are NOT in Wizard Mode.</p>
	</c:otherwise>
</c:choose>