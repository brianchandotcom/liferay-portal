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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portlet.asset.AssetTagException" %><%@
page import="com.liferay.portlet.asset.DuplicateTagException" %><%@
page import="com.liferay.portlet.asset.NoSuchTagException" %><%@
page import="com.liferay.portlet.asset.model.AssetTagProperty" %><%@
page import="com.liferay.portlet.asset.model.impl.AssetTagPropertyImpl" %><%@
page import="com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetPermission" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetTagPermission" %>

<%@ include file="/html/portlet/asset_tag_admin/init-ext.jsp" %>