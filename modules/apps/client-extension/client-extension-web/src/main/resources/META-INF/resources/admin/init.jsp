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

<%@ taglib uri="http://liferay.com/tld/learn" prefix="liferay-learn" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.client.extension.exception.ClientExtensionEntryNameException" %><%@
page import="com.liferay.client.extension.exception.ClientExtensionEntryTypeSettingsException" %><%@
page import="com.liferay.client.extension.type.CustomElementCET" %><%@
page import="com.liferay.client.extension.type.FDSCellRendererCET" %><%@
page import="com.liferay.client.extension.type.GlobalCSSCET" %><%@
page import="com.liferay.client.extension.type.GlobalJSCET" %><%@
page import="com.liferay.client.extension.type.IFrameCET" %><%@
page import="com.liferay.client.extension.type.JSImportMapsEntryCET" %><%@
page import="com.liferay.client.extension.type.StaticContentCET" %><%@
page import="com.liferay.client.extension.type.ThemeCSSCET" %><%@
page import="com.liferay.client.extension.type.ThemeFaviconCET" %><%@
page import="com.liferay.client.extension.type.ThemeJSCET" %><%@
page import="com.liferay.client.extension.type.ThemeSpritemapCET" %><%@
page import="com.liferay.client.extension.type.annotation.CETProperty" %><%@
page import="com.liferay.client.extension.web.internal.constants.ClientExtensionAdminFDSNames" %><%@
page import="com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebKeys" %><%@
page import="com.liferay.client.extension.web.internal.display.context.ClientExtensionAdminDisplayContext" %><%@
page import="com.liferay.client.extension.web.internal.display.context.EditClientExtensionEntryDisplayContext" %><%@
page import="com.liferay.client.extension.web.internal.display.context.ViewClientExtensionEntryDisplayContext" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %>

<%@ page import="java.lang.reflect.Method" %>

<%@ page import="java.util.Collection" %>