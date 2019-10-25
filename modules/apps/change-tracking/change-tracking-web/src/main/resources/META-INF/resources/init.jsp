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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/site-navigation" prefix="liferay-site-navigation" %><%@
taglib uri="http://liferay.com/tld/soy" prefix="soy" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.change.tracking.constants.CTConstants" %><%@
page import="com.liferay.change.tracking.constants.CTPortletKeys" %><%@
page import="com.liferay.change.tracking.exception.CTCollectionDescriptionException" %><%@
page import="com.liferay.change.tracking.exception.CTCollectionNameException" %><%@
page import="com.liferay.change.tracking.model.CTCollection" %><%@
page import="com.liferay.change.tracking.model.CTEntry" %><%@
page import="com.liferay.change.tracking.web.internal.constants.CTWebKeys" %><%@
page import="com.liferay.change.tracking.web.internal.display.CTEntryDiffDisplay" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsConfigurationDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsHistoryDetailsDisplayContext" %><%@
page import="com.liferay.change.tracking.web.internal.display.context.ChangeListsHistoryDisplayContext" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.DisplayTerms" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.Map" %><%@
page import="java.util.Objects" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
portletDisplay.setShowStagingIcon(false);
%>