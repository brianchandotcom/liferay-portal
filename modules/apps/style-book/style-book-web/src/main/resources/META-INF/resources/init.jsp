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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/layout" prefix="liferay-layout" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.exportimport.kernel.staging.StagingUtil" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.petra.string.StringUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.style.book.exception.DuplicateStyleBookEntryKeyException" %><%@
page import="com.liferay.style.book.exception.StyleBookEntryFileException" %><%@
page import="com.liferay.style.book.web.internal.constants.StyleBookWebKeys" %><%@
page import="com.liferay.style.book.web.internal.display.context.EditStyleBookEntryDisplayContext" %><%@
page import="com.liferay.style.book.web.internal.display.context.ImportStyleBookDisplayContext" %><%@
page import="com.liferay.style.book.web.internal.display.context.StyleBookDisplayContext" %><%@
page import="com.liferay.style.book.web.internal.display.context.StyleBookManagementToolbarDisplayContext" %><%@
page import="com.liferay.style.book.web.internal.portlet.zip.StyleBookEntryZipProcessor" %><%@
page import="com.liferay.style.book.web.internal.servlet.taglib.clay.StyleBookVerticalCard" %>

<%@ page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%@ include file="/init-ext.jsp" %>