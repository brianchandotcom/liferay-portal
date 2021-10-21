<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/commerce-ui" prefix="commerce-ui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/item-selector" prefix="liferay-item-selector" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %><%@
page import="com.liferay.commerce.product.content.constants.CPContentWebKeys" %><%@
page import="com.liferay.commerce.product.content.util.CPContentHelper" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context.CSDiagramCPTypeDisplayContext" %><%@
page import="com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context.CSDiagramSettingDisplayContext" %><%@
page import="com.liferay.commerce.shop.by.diagram.admin.web.internal.type.DefaultCSDiagramType" %><%@
page import="com.liferay.commerce.shop.by.diagram.constants.CSDiagramWebKeys" %><%@
page import="com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting" %><%@
page import="com.liferay.commerce.shop.by.diagram.type.CSDiagramType" %><%@
page import="com.liferay.document.library.kernel.exception.NoSuchFileEntryException" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.petra.string.StringUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponseFactory" %>

<%@ page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />