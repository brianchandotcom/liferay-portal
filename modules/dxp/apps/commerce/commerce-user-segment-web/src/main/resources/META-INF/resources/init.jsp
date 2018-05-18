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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.user.segment.constants.CommerceUserSegmentScreenNavigationConstants" %><%@
page import="com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType" %><%@
page import="com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributor" %><%@
page import="com.liferay.commerce.user.segment.exception.CommerceUserSegmentCriterionTypeException" %><%@
page import="com.liferay.commerce.user.segment.exception.CommerceUserSegmentEntryKeyException" %><%@
page import="com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException" %><%@
page import="com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException" %><%@
page import="com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion" %><%@
page import="com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry" %><%@
page import="com.liferay.commerce.user.segment.web.internal.display.context.CommerceUserSegmentDisplayContext" %><%@
page import="com.liferay.commerce.user.segment.web.internal.display.context.OrganizationCommerceUserSegmentCriterionTypeDisplayContext" %><%@
page import="com.liferay.commerce.user.segment.web.internal.display.context.RoleCommerceUserSegmentCriterionTypeDisplayContext" %><%@
page import="com.liferay.commerce.user.segment.web.internal.display.context.UserCommerceUserSegmentCriterionTypeDisplayContext" %><%@
page import="com.liferay.commerce.user.segment.web.internal.display.context.UserGroupCommerceUserSegmentCriterionTypeDisplayContext" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.Role" %><%@
page import="com.liferay.portal.kernel.model.RoleConstants" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.model.UserGroup" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");
%>