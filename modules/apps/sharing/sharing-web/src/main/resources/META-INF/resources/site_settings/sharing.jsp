<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/site_settings/init.jsp" %>

<%
SharingConfiguration groupSharingConfiguration = (SharingConfiguration)request.getAttribute(SharingWebKeys.GROUP_SHARING_CONFIGURATION);
%>

<aui:input helpMessage="sharing-help" inlineLabel="right" label="sharing-enabled" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--sharingEnabled--" type="toggle-switch" value="<%= groupSharingConfiguration.isEnabled() %>" />