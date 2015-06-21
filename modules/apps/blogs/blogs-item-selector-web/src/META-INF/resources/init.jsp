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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/item-selector/ui" prefix="item-selector-ui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion" %><%@
page import="com.liferay.blogs.item.selector.web.BlogsItemSelectorView" %><%@
page import="com.liferay.blogs.item.selector.web.display.context.BlogsItemSelectorViewDisplayContext" %><%@
page import="com.liferay.item.selector.taglib.ItemSelectorBrowserReturnTypeUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.repository.model.Folder" %><%@
page import="com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %>

<portlet:defineObjects />

<liferay-theme:defineObjects/>