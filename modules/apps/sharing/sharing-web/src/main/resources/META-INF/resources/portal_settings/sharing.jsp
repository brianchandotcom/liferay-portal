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

<%@ include file="/portal_settings/init.jsp" %>

<%
SharingConfiguration sharingConfiguration = (SharingConfiguration)request.getAttribute(SharingConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/sharing" />

<aui:input label="enabled" name='<%= PortalSettingsSharingConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="toggle-switch" value="<%= sharingConfiguration.isEnabled() %>" />