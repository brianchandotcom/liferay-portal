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

<%@ page import="com.liferay.portal.TrashPermissionException" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandler" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.trash.TrashRenderer" %><%@
page import="com.liferay.portlet.trash.DuplicateEntryException" %><%@
page import="com.liferay.portlet.trash.model.TrashEntryList" %><%@
page import="com.liferay.portlet.trash.model.impl.TrashEntryImpl" %><%@
page import="com.liferay.portlet.trash.search.EntrySearch" %><%@
page import="com.liferay.portlet.trash.search.EntrySearchTerms" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryServiceUtil" %>

<%@ include file="/html/portlet/trash/init-ext.jsp" %>