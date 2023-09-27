<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input helpMessage='<%= LanguageUtil.get(resourceBundle, "set-the-google-maps-api-key-that-is-used-for-this-set-of-pages") %>' label='<%= LanguageUtil.get(resourceBundle, "google-maps-api-key") + " (" + LanguageUtil.get(request, "optional") + ")" %>' name='<%= googleMapsDisplayContext.getConfigurationPrefix() + "--googleMapsAPIKey--" %>' size="40" type="text" value="<%= googleMapsDisplayContext.getGoogleMapsAPIKey() %>" />