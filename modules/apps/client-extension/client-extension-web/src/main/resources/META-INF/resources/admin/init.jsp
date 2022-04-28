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

<%@ page import="com.liferay.client.extension.exception.ClientExtensionEntryCustomElementCSSURLsException" %><%@
page import="com.liferay.client.extension.exception.ClientExtensionEntryCustomElementHTMLElementNameException" %><%@
page import="com.liferay.client.extension.exception.ClientExtensionEntryCustomElementURLsException" %><%@
page import="com.liferay.client.extension.exception.ClientExtensionEntryFriendlyURLMappingException" %><%@
page import="com.liferay.client.extension.exception.ClientExtensionEntryIFrameURLException" %><%@
page import="com.liferay.client.extension.web.internal.constants.RemoteAppAdminFDSNames" %><%@
page import="com.liferay.client.extension.web.internal.constants.RemoteAppAdminWebKeys" %><%@
page import="com.liferay.client.extension.web.internal.display.context.EditRemoteAppEntryDisplayContext" %><%@
page import="com.liferay.client.extension.web.internal.display.context.RemoteAppAdminDisplayContext" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %>

<%@ page import="java.util.Arrays" %>