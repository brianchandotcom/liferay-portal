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
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (selUser == null) ? Constants.ADD : Constants.UPDATE %>" />

<div aria-labelledby="<portlet:namespace />userDisplayData" class="form-group" role="group">
	<div class="sheet-subtitle" id="<portlet:namespace />userDisplayData">
		<liferay-ui:message key="user-display-data" />
	</div>

	<liferay-util:include page="/user/user_display_data.jsp" servletContext="<%= application %>" />
</div>

<div aria-labelledby="<portlet:namespace />personalInformation" class="form-group" role="group">
	<div class="sheet-subtitle" id="<portlet:namespace />personalInformation">
		<liferay-ui:message key="personal-information" />
	</div>

	<liferay-util:include page="/user/personal_information.jsp" servletContext="<%= application %>" />
</div>

<clay:sheet-section>
	<div class="sheet-subtitle"><liferay-ui:message key="more-information" /></div>

	<div class="form-group">
		<liferay-util:include page="/user/categorization.jsp" servletContext="<%= application %>" />
	</div>

	<div class="form-group">
		<liferay-util:include page="/user/comments.jsp" servletContext="<%= application %>" />
	</div>
</clay:sheet-section>

<clay:sheet-section>
	<liferay-util:include page="/user/custom_fields.jsp" servletContext="<%= application %>" />
</clay:sheet-section>