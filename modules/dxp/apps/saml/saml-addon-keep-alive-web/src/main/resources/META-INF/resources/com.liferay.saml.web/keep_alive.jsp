<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String expandoAttribute = SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL;
%>

<aui:fieldset label="keep-alive">
	<aui:input name='<%= "ExpandoAttributeName--" + expandoAttribute + "--" %>' type="hidden" value="<%= expandoAttribute %>" />

	<aui:input label="keep-alive-url" name='<%= "ExpandoAttribute--" + expandoAttribute + "--" %>' value="<%= GetterUtil.getString(request.getAttribute(SamlWebKeys.SAML_KEEP_ALIVE_URL)) %>" />
</aui:fieldset>