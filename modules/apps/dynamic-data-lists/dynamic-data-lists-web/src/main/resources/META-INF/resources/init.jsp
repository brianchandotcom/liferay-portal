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
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.dynamic.data.lists.constants.DDLActionKeys" %><%@
page import="com.liferay.dynamic.data.lists.constants.DDLPortletKeys" %><%@
page import="com.liferay.dynamic.data.lists.constants.DDLWebKeys" %><%@
page import="com.liferay.dynamic.data.lists.exception.NoSuchRecordException" %><%@
page import="com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException" %><%@
page import="com.liferay.dynamic.data.lists.exception.RecordSetDDMStructureIdException" %><%@
page import="com.liferay.dynamic.data.lists.exception.RecordSetNameException" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecord" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordConstants" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordSet" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordSetConstants" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordVersion" %><%@
page import="com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.lists.service.DDLRecordSetServiceUtil" %><%@
page import="com.liferay.dynamic.data.lists.service.DDLRecordVersionServiceUtil" %><%@
page import="com.liferay.dynamic.data.lists.service.permission.DDLPermission" %><%@
page import="com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission" %><%@
page import="com.liferay.dynamic.data.lists.util.DDLUtil" %><%@
page import="com.liferay.dynamic.data.lists.web.configuration.DDLWebConfiguration" %><%@
page import="com.liferay.dynamic.data.lists.web.display.context.DDLDisplayContext" %><%@
page import="com.liferay.dynamic.data.lists.web.display.context.DDLViewRecordsDisplayContext" %><%@
page import="com.liferay.dynamic.data.lists.web.portlet.DDLPortletUtil" %><%@
page import="com.liferay.dynamic.data.lists.web.search.RecordSetDisplayTerms" %><%@
page import="com.liferay.dynamic.data.lists.web.search.RecordSetSearch" %><%@
page import="com.liferay.dynamic.data.lists.web.search.RecordSetSearchTerms" %><%@
page import="com.liferay.dynamic.data.lists.web.template.DDLDisplayTemplateTransformer" %><%@
page import="com.liferay.dynamic.data.mapping.exception.NoSuchStructureException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormField" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructureVersion" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplate" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplateConstants" %><%@
page import="com.liferay.dynamic.data.mapping.model.LocalizedValue" %><%@
page import="com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer" %><%@
page import="com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistryUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %><%@
page import="com.liferay.dynamic.data.mapping.storage.StorageEngineUtil" %><%@
page import="com.liferay.dynamic.data.mapping.util.DDMUtil" %><%@
page import="com.liferay.portal.exception.NoSuchWorkflowDefinitionLinkException" %><%@
page import="com.liferay.portal.exception.PortletPreferencesException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.DisplayTerms" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.search.BaseModelSearchResult" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.webdav.WebDAVUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil" %><%@
page import="com.liferay.portal.model.*" %><%@
page import="com.liferay.portal.model.impl.*" %><%@
page import="com.liferay.portal.service.*" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PrefsPropsUtil" %><%@
page import="com.liferay.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portlet.documentlibrary.exception.DuplicateFileEntryException" %><%@
page import="com.liferay.portlet.documentlibrary.exception.FileSizeException" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
DDLDisplayContext ddlDisplayContext = new DDLDisplayContext(request);

DDLWebConfiguration ddlWebConfiguration = (DDLWebConfiguration)request.getAttribute(DDLWebConfiguration.class.getName());
%>

<%@ include file="/init-ext.jsp" %>