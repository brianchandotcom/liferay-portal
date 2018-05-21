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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.dynamic.data.lists.constants.DDLWebKeys" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecord" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordSet" %><%@
page import="com.liferay.dynamic.data.lists.model.DDLRecordVersion" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplate" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys" %><%@
page import="com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink" %>

<liferay-theme:defineObjects />