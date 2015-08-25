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
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.layout.type.controller.full.page.application.constants.FullPageApplicationLayoutTypeControllerWebKeys" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.Layout" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.util.WebKeys" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<%
Layout selLayout = (Layout)request.getAttribute(WebKeys.SEL_LAYOUT);
%>