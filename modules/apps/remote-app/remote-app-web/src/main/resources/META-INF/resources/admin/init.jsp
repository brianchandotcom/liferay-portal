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

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.remote.app.exception.RemoteAppEntryCustomElementCSSURLsException" %><%@
page import="com.liferay.remote.app.exception.RemoteAppEntryCustomElementHTMLElementNameException" %><%@
page import="com.liferay.remote.app.exception.RemoteAppEntryCustomElementURLsException" %><%@
page import="com.liferay.remote.app.exception.RemoteAppEntryIFrameURLException" %><%@
page import="com.liferay.remote.app.web.internal.constants.RemoteAppAdminConstants" %><%@
page import="com.liferay.remote.app.web.internal.constants.RemoteAppAdminWebKeys" %><%@
page import="com.liferay.remote.app.web.internal.display.context.EditRemoteAppEntryDisplayContext" %><%@
page import="com.liferay.remote.app.web.internal.display.context.RemoteAppAdminDisplayContext" %>

<%@ page import="java.util.Arrays" %>