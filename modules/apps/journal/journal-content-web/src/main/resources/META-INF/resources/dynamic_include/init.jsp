<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.journal.content.web.internal.display.context.JournalContentDisplayContext" %><%@
page import="com.liferay.journal.content.web.internal.servlet.taglib.PortletHeaderActionDropdownItemsProvider" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<liferay-theme:defineObjects />

<%
JournalContentDisplayContext journalContentDisplayContext = (JournalContentDisplayContext)request.getAttribute(JournalContentDisplayContext.getRequestAttributeName(portletDisplay.getId()));
%>