<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String namespace = AUIUtil.getNamespace(liferayPortletRequest, liferayPortletResponse);

String configurationPrefix = GetterUtil.getString(request.getAttribute("liferay-map:map-provider-selector:configurationPrefix"), "TypeSettingsProperties");
String name = GetterUtil.getString(request.getAttribute("liferay-map:map-provider-selector:name"));
String mapProviderKey = GetterUtil.getString(request.getAttribute("liferay-map:map-provider-selector:mapProviderKey"));
Collection<MapProvider> mapProviders = (Collection<MapProvider>)request.getAttribute("liferay-map:map-provider-selector:mapProviders");
%>