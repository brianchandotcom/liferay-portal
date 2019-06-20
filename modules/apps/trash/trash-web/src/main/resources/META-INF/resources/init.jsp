<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/trash" prefix="liferay-trash" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.exception.TrashPermissionException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.ClassedModel" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandler" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.trash.TrashRenderer" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Time" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.trash.exception.RestoreEntryException" %><%@
page import="com.liferay.trash.exception.TrashEntryException" %><%@
page import="com.liferay.trash.model.TrashEntry" %><%@
page import="com.liferay.trash.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.trash.web.internal.constants.TrashWebKeys" %><%@
page import="com.liferay.trash.web.internal.dao.search.TrashResultRowSplitter" %><%@
page import="com.liferay.trash.web.internal.display.context.TrashContainerManagementToolbarDisplayContext" %><%@
page import="com.liferay.trash.web.internal.display.context.TrashContainerModelDisplayContext" %><%@
page import="com.liferay.trash.web.internal.display.context.TrashDisplayContext" %><%@
page import="com.liferay.trash.web.internal.display.context.TrashManagementToolbarDisplayContext" %><%@
page import="com.liferay.trash.web.internal.servlet.taglib.clay.TrashContentHorizontalCard" %><%@
page import="com.liferay.trash.web.internal.servlet.taglib.clay.TrashContentVerticalCard" %><%@
page import="com.liferay.trash.web.internal.servlet.taglib.clay.TrashEntryVerticalCard" %><%@
page import="com.liferay.trash.web.internal.servlet.taglib.util.TrashContainerActionDropdownItemsProvider" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<liferay-trash:defineObjects />

<portlet:defineObjects />

<%
TrashDisplayContext trashDisplayContext = new TrashDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

String trashEntriesMaxAgeTimeDescription = LanguageUtil.getTimeDescription(locale, trashHelper.getMaxAge(themeDisplay.getScopeGroup()) * Time.MINUTE, true);

String description = LanguageUtil.get(request, "javax.portlet.description.com_liferay_trash_web_portlet_TrashPortlet") + LanguageUtil.format(request, "entries-that-have-been-in-the-recycle-bin-for-more-than-x-are-automatically-deleted", StringUtil.toLowerCase(trashEntriesMaxAgeTimeDescription), false);

portletDisplay.setDescription(description);
portletDisplay.setShowStagingIcon(false);
%>

<%@ include file="/init-ext.jsp" %>