<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SharingConfiguration groupSharingConfiguration = (SharingConfiguration)request.getAttribute(SharingWebKeys.GROUP_SHARING_CONFIGURATION);
%>

<c:if test="<%= groupSharingConfiguration.isAvailable() %>">
	<liferay-frontend:fieldset
		collapsible="<%= true %>"
		cssClass="panel-group-flush"
		label='<%= LanguageUtil.get(request, "sharing") %>'
	>
		<aui:input helpMessage="asset-library-sharing-help" inlineLabel="right" label="asset-library-sharing-enabled" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--sharingEnabled--" type="toggle-switch" value="<%= groupSharingConfiguration.isEnabled() %>" />
	</liferay-frontend:fieldset>
</c:if>