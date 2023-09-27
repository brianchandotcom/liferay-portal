<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/init.jsp" %>

<%
String url = StringPool.BLANK;

if (selLayout != null) {
	UnicodeProperties typeSettingsUnicodeProperties = selLayout.getTypeSettingsProperties();

	url = typeSettingsUnicodeProperties.getProperty("url", StringPool.BLANK);
}
%>

<aui:input cssClass="lfr-input-text-container" id="url" label="url" name="TypeSettingsProperties--url--" type="text" value="<%= url %>">
	<aui:validator errorMessage="please-enter-a-valid-url" name="required" />
</aui:input>