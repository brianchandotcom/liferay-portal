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
DLOpenerGoogleDriveCompanyConfiguration dlOpenerGoogleDriveCompanyConfiguration = (DLOpenerGoogleDriveCompanyConfiguration)request.getAttribute(DLOpenerGoogleDriveCompanyConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/document_library_opener_google_drive" />

<aui:input label="client-id" name='<%= PortalSettingsDLOpenerGoogleDriveConstants.FORM_PARAMETER_NAMESPACE + "clientId" %>' value="<%= dlOpenerGoogleDriveCompanyConfiguration.clientId() %>" />

<aui:input label="client-secret" name='<%= PortalSettingsDLOpenerGoogleDriveConstants.FORM_PARAMETER_NAMESPACE + "clientSecret" %>' value="<%= dlOpenerGoogleDriveCompanyConfiguration.clientSecret() %>" />