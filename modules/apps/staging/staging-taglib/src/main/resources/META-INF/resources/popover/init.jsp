<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String id = GetterUtil.getString(request.getAttribute("liferay-staging:popover:id"));
String textKey = GetterUtil.getString(request.getAttribute("liferay-staging:popover:text"));
String titleKey = GetterUtil.getString(request.getAttribute("liferay-staging:popover:title"));

String domId = liferayPortletResponse.getNamespace() + id;
String text = Validator.isNotNull(textKey) ? LanguageUtil.get(request, textKey) : "";
String title = LanguageUtil.get(request, titleKey);
%>