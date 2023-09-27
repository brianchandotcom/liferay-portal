<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input helpMessage='<%= LanguageUtil.get(resourceBundle, "custom-user-attributes-help") %>' label='<%= LanguageUtil.get(resourceBundle, "displayed-assets-must-match-these-custom-user-profile-attributes") %>' name="preferences--customUserAttributes--" value='<%= GetterUtil.getString(portletPreferences.getValue("customUserAttributes", StringPool.BLANK)) %>' />