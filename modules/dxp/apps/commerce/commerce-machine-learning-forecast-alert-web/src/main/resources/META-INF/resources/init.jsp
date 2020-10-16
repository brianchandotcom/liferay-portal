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

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.account.model.CommerceAccount" %><%@
page import="com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertActionKeys" %><%@
page import="com.liferay.commerce.machine.learning.forecast.alert.constants.CommerceMLForecastAlertConstants" %><%@
page import="com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry" %><%@
page import="com.liferay.commerce.machine.learning.forecast.alert.web.internal.display.context.CommerceMLForecastAlertEntryListDisplayContext" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />