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
taglib uri="http://liferay.com/tld/commerce-ui" prefix="commerce-ui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.frontend.model.HeaderActionModel" %><%@
page import="com.liferay.commerce.frontend.util.HeaderHelperUtil" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.BaseModel" %><%@
page import="com.liferay.portal.kernel.model.WorkflowedModel" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PortletKeys" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowTask" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
List<HeaderActionModel> actions = (List<HeaderActionModel>)request.getAttribute("liferay-commerce:header:actions");
Object bean = request.getAttribute("liferay-commerce:header:bean");
String beanIdLabel = (String)request.getAttribute("liferay-commerce:header:beanIdLabel");
String cssClasses = (String)request.getAttribute("liferay-commerce:header:cssClasses");
List<DropdownItem> dropdownItems = (List<DropdownItem>)request.getAttribute("liferay-commerce:header:dropdownItems");
String externalReferenceCode = (String)request.getAttribute("liferay-commerce:header:externalReferenceCode");
String externalReferenceCodeEditUrl = (String)request.getAttribute("liferay-commerce:header:externalReferenceCodeEditUrl");
boolean fullWidth = (boolean)request.getAttribute("liferay-commerce:header:fullWidth");
Class<?> model = (Class<?>)request.getAttribute("liferay-commerce:header:model");
String previewUrl = (String)request.getAttribute("liferay-commerce:header:previewUrl");
String thumbnailUrl = (String)request.getAttribute("liferay-commerce:header:thumbnailUrl");
String title = (String)request.getAttribute("liferay-commerce:header:title");
PortletURL transitionPortletURL = (PortletURL)request.getAttribute("liferay-commerce:header:transitionPortletURL");
String version = (String)request.getAttribute("liferay-commerce:header:version");
String wrapperCssClasses = (String)request.getAttribute("liferay-commerce:header:wrapperCssClasses");

long beanId = 0;

BaseModel<?> beanBaseModel = (BaseModel)bean;

if (beanBaseModel != null) {
	beanId = (long)beanBaseModel.getPrimaryKeyObj();
}

WorkflowTask reviewWorkflowTask = HeaderHelperUtil.getReviewWorkflowTask(themeDisplay.getCompanyId(), themeDisplay.getUserId(), beanId, model.getName());

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>