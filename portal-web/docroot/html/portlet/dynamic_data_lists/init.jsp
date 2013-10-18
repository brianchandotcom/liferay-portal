<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.NoSuchWorkflowDefinitionLinkException" %><%@
page import="com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException" %><%@
page import="com.liferay.portlet.dynamicdatalists.RecordSetDDMStructureIdException" %><%@
page import="com.liferay.portlet.dynamicdatalists.RecordSetNameException" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecord" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants" %><%@
page import="com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion" %><%@
page import="com.liferay.portlet.dynamicdatalists.search.RecordSetDisplayTerms" %><%@
page import="com.liferay.portlet.dynamicdatalists.search.RecordSetSearch" %><%@
page import="com.liferay.portlet.dynamicdatalists.search.RecordSetSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.permission.DDLPermission" %><%@
page import="com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission" %><%@
page import="com.liferay.portlet.dynamicdatalists.util.DDLUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.FieldConstants" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.Fields" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMXSDUtil" %>

<%@ include file="/html/portlet/dynamic_data_lists/init-ext.jsp" %>