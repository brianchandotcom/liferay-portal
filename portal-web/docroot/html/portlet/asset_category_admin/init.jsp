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

<%@ page import="com.liferay.portal.kernel.util.PredicateFilter" %><%@
page import="com.liferay.portlet.asset.model.AssetCategoryConstants" %><%@
page import="com.liferay.portlet.asset.model.AssetCategoryProperty" %><%@
page import="com.liferay.portlet.asset.model.impl.AssetCategoryPropertyImpl" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryPropertyServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetCategoryPermission" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetPermission" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetVocabularyPermission" %><%@
page import="com.liferay.portlet.asset.util.AssetVocabularySettingsHelper" %>

<%@ include file="/html/portlet/asset_category_admin/init-ext.jsp" %>