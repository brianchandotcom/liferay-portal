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
User selUser = (User)request.getAttribute("user.selUser");

String languageId = BeanParamUtil.getString(selUser, request, "languageId", user.getLanguageId());
String timeZoneId = BeanParamUtil.getString(selUser, request, "timeZoneId", user.getTimeZoneId());
%>

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<aui:fieldset>
	<aui:input label="time-zone" name="timeZoneId" type="timeZone" value="<%= timeZoneId %>" />

	<aui:input name="greeting" />
</aui:fieldset>