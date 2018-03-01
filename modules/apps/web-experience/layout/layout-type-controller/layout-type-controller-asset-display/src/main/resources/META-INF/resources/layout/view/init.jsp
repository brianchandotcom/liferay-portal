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

<%@ page import="com.liferay.portal.kernel.io.unsync.UnsyncStringWriter" %><%@
page import="com.liferay.portal.kernel.template.StringTemplateResource" %><%@
page import="com.liferay.portal.kernel.template.Template" %><%@
page import="com.liferay.portal.kernel.template.TemplateConstants" %><%@
page import="com.liferay.portal.kernel.template.TemplateManager" %><%@
page import="com.liferay.portal.kernel.template.TemplateManagerUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateResource" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %>

<%@ include file="/init.jsp" %>

<%
List<FragmentEntryLink> fragmentEntryLinks = (List<FragmentEntryLink>)request.getAttribute(AssetDisplayLayoutTypeControllerWebKeys.LAYOUT_FRAGMENTS);
%>