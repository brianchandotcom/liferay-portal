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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
boolean openIdAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.OPEN_ID_AUTH_ENABLED, PropsValues.OPEN_ID_AUTH_ENABLED);
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "settings--" + PropsKeys.OPEN_ID_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= openIdAuthEnabled %>" />
</aui:fieldset>