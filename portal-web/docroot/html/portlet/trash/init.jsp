<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Sort" %><%@
page import="com.liferay.portal.kernel.search.SortFactoryUtil" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandler" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.trash.TrashRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory" %><%@
page import="com.liferay.portlet.trash.model.TrashEntry" %><%@
page import="com.liferay.portlet.trash.model.TrashEntryList" %><%@
page import="com.liferay.portlet.trash.model.impl.TrashEntryImpl" %><%@
page import="com.liferay.portlet.trash.search.EntrySearch" %><%@
page import="com.liferay.portlet.trash.search.EntrySearchTerms" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryServiceUtil" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %>

<%@ include file="/html/portlet/trash/init-ext.jsp" %>

<%
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>