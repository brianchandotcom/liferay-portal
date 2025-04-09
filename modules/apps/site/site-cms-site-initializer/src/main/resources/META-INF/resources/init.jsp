<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend-data-set" prefix="frontend-data-set" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.AllSectionDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.AllSpacesSectionDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.ContentsSectionDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.EditCategoryDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.EditVocabularyDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.FilesSectionDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.StructureUsagesDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.StructuresSectionDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.ViewCategoriesDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.ViewDashboardDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.ViewTagsDisplayContext" %><%@
page import="com.liferay.site.cms.site.initializer.internal.display.context.ViewVocabulariesDisplayContext" %>

<liferay-theme:defineObjects />

<liferay-util:html-top>
	<aui:link href='<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/main.css") %>' rel="stylesheet" type="text/css" />
</liferay-util:html-top>