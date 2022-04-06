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

<liferay-util:include page="/mail_fields.jsp" servletContext="<%= application %>">
	<liferay-util:param name="companyId" value="<%= String.valueOf(CompanyConstants.SYSTEM) %>" />
</liferay-util:include>

<aui:button-row>
	<aui:button cssClass="save-server-button" data-cmd="updateMail" value="save" />
</aui:button-row>