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

<%@ page import="com.liferay.oauth.model.OAuthApplication" %><%@
page import="com.liferay.oauth.model.OAuthApplicationConstants" %><%@
page import="com.liferay.oauth.service.OAuthApplicationLocalServiceUtil" %><%@
page import="com.liferay.oauth.service.OAuthUserLocalServiceUtil" %><%@
page import="com.liferay.oauth.service.permission.OAuthApplicationPermission" %><%@
page import="com.liferay.oauth.service.permission.OAuthPermission" %><%@
page import="com.liferay.oauth.service.permission.OAuthUserPermission" %><%@
page import="com.liferay.oauth.util.OAuthAccessor" %><%@
page import="com.liferay.oauth.util.OAuthActionKeys" %><%@
page import="com.liferay.oauth.util.OAuthConsumer" %><%@
page import="com.liferay.oauth.util.OAuthMessage" %><%@
page import="com.liferay.oauth.util.OAuthUtil" %><%@
page import="com.liferay.oauth.util.OAuthWebKeys" %><%@
page import="com.liferay.oauth.web.internal.search.OAuthApplicationDisplayTerms" %><%@
page import="com.liferay.oauth.web.internal.search.OAuthApplicationSearch" %><%@
page import="com.liferay.portal.kernel.exception.ImageTypeException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredFieldException" %><%@
page import="com.liferay.portal.kernel.oauth.OAuthException" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.upload.UploadException" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.net.MalformedURLException" %>

<%@ page import="java.util.LinkedHashMap" %>

<%@ page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />