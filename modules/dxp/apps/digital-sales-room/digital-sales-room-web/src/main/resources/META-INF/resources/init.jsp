<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/frontend-data-set" prefix="frontend-data-set" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomFDSNames" %><%@
page import="com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomScreenNavigationEntryConstants" %><%@
page import="com.liferay.digital.sales.room.web.internal.display.context.EditDigitalSalesRoomTemplateSettingsDisplayContext" %><%@
page import="com.liferay.digital.sales.room.web.internal.display.context.ViewDigitalSalesRoomListDisplayContext" %><%@
page import="com.liferay.digital.sales.room.web.internal.display.context.ViewDigitalSalesRoomTemplateListDisplayContext" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />