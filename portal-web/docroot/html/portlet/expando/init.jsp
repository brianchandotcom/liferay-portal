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

<%@ page import="com.liferay.portlet.expando.ColumnNameException" %><%@
page import="com.liferay.portlet.expando.ColumnTypeException" %><%@
page import="com.liferay.portlet.expando.DuplicateColumnNameException" %><%@
page import="com.liferay.portlet.expando.NoSuchColumnException" %><%@
page import="com.liferay.portlet.expando.ValueDataException" %><%@
page import="com.liferay.portlet.expando.model.CustomAttributesDisplay" %><%@
page import="com.liferay.portlet.expando.model.ExpandoColumn" %><%@
page import="com.liferay.portlet.expando.model.ExpandoColumnConstants" %><%@
page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil" %><%@
page import="com.liferay.portlet.expando.service.permission.ExpandoColumnPermissionUtil" %><%@
page import="com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil" %><%@
page import="com.liferay.portlet.expando.util.comparator.CustomAttributesDisplayComparator" %>

<%@ include file="/html/portlet/expando/init-ext.jsp" %>