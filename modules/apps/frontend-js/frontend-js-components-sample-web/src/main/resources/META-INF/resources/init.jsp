<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.js.components.sample.web.internal.constants.FrontendJSComponentsSampleWebKeys" %><%@
page import="com.liferay.frontend.js.components.sample.web.internal.display.context.TranslationManagerDisplayContext" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<liferay-theme:defineObjects />

<%
TranslationManagerDisplayContext translationManagerDisplayContext = (TranslationManagerDisplayContext)request.getAttribute(FrontendJSComponentsSampleWebKeys.TRANSLATION_MANAGER_DISPLAY_CONTEXT);
%>