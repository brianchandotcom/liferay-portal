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

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.fragment.item.selector.web.internal.display.context.ContributedFragmentEntriesItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.item.selector.web.internal.display.context.DefaultFragmentEntriesDisplayContext" %><%@
page import="com.liferay.fragment.item.selector.web.internal.display.context.FragmentCollectionContributorsItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.item.selector.web.internal.display.context.FragmentCollectionsItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.item.selector.web.internal.display.context.FragmentEntriesDisplayContext" %><%@
page import="com.liferay.fragment.item.selector.web.internal.display.context.FragmentEntriesItemSelectorViewManagementToolbarDisplayContext" %><%@
page import="com.liferay.fragment.item.selector.web.internal.frontend.taglib.clay.servlet.FragmentCollectionContributorHorizontalCard" %><%@
page import="com.liferay.fragment.item.selector.web.internal.frontend.taglib.clay.servlet.FragmentCollectionHorizontalCard" %><%@
page import="com.liferay.fragment.item.selector.web.internal.frontend.taglib.clay.servlet.FragmentEntryVerticalCard" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />
<portlet:defineObjects />