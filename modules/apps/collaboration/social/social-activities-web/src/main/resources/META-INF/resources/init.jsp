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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.social.activities.web.constants.SocialActivitiesWebKeys" %><%@
page import="com.liferay.social.activities.web.internal.portlet.display.context.DefaultSocialActivitiesDisplayContext" %><%@
page import="com.liferay.social.activities.web.internal.portlet.display.context.SocialActivitiesDisplayContext" %><%@
page import="com.liferay.social.activities.web.internal.portlet.display.context.util.SocialActivitiesRequestHelper" %><%@
page import="com.liferay.social.activities.web.internal.util.SocialActivitiesQueryHelper" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SocialActivitiesRequestHelper socialActivitiesRequestHelper = new SocialActivitiesRequestHelper(request);
SocialActivitiesQueryHelper socialActivitiesQueryHelper = (SocialActivitiesQueryHelper)request.getAttribute(SocialActivitiesWebKeys.SOCIAL_ACTIVITIES_QUERY_HELPER);

SocialActivitiesDisplayContext socialActivitiesDisplayContext = new DefaultSocialActivitiesDisplayContext(socialActivitiesRequestHelper, socialActivitiesQueryHelper);
%>

<%@ include file="/init-ext.jsp" %>