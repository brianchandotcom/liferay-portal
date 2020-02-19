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
SharingConfiguration groupSharingConfiguration = (SharingConfiguration)request.getAttribute(SharingWebKeys.GROUP_SHARING_CONFIGURATION);
%>

<c:if test="<%= groupSharingConfiguration.isAvailable() %>">
	<liferay-frontend:fieldset
		collapsible="true"
		cssClass="panel-group-flush"
		label='<%= LanguageUtil.get(request, "sharing") %>'
	>
		<aui:input helpMessage="asset-library-sharing-help" label="asset-library-sharing-enabled" name="TypeSettingsProperties--sharingEnabled--" type="toggle-switch" value="<%= groupSharingConfiguration.isEnabled() %>" />
	</liferay-frontend:fieldset>
</c:if>