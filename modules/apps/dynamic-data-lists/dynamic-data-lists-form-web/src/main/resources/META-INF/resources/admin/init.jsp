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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.dynamic.data.lists.exception.RecordSetNameException" %><%@
page import="com.liferay.dynamic.data.lists.exception.RecordSetSettingsException" %><%@
page import="com.liferay.dynamic.data.lists.form.web.display.context.DDLFormAdminDisplayContext" %><%@
page import="com.liferay.dynamic.data.lists.form.web.display.context.DDLFormViewRecordsDisplayContext" %><%@
page import="com.liferay.dynamic.data.lists.form.web.search.RecordSetDisplayTerms" %><%@
page import="com.liferay.dynamic.data.lists.form.web.search.RecordSetSearch" %><%@
page import="com.liferay.dynamic.data.lists.form.web.util.DDLFormEmailNotificationUtil" %><%@
page import="com.liferay.dynamic.data.lists.form.web.util.DDLFormPortletUtil" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecord" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordSet" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureDefinitionException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureLayoutException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureNameException" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructureVersion" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %><%@
page import="com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException" %><%@
page import="com.liferay.dynamic.data.mapping.validator.DDMFormValidationException" %><%@
page import="com.liferay.portal.NoSuchWorkflowDefinitionLinkException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocalizationUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.WorkflowDefinitionLink" %><%@
page import="com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

DDLFormAdminDisplayContext ddlFormAdminDisplayContext = new DDLFormAdminDisplayContext(renderRequest, renderResponse);
%>

<%@ include file="/admin/init-ext.jsp" %>

<aui:script>
	Liferay.namespace('Forms').portletNamespace = '<portlet:namespace />';
</aui:script>