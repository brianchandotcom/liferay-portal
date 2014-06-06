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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="java.net.URL" %>

<%
IntegerWrapper iconListIconCount = (IntegerWrapper)request.getAttribute("liferay-ui:icon-list:icon-count");

if (iconListIconCount != null) {
	iconListIconCount.increment();
}

boolean iconListShowWhenSingleIcon = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon-list:showWhenSingleIcon"));
Boolean iconListSingleIcon = (Boolean)request.getAttribute("liferay-ui:icon-list:single-icon");

IntegerWrapper iconMenuIconCount = (IntegerWrapper)request.getAttribute("liferay-ui:icon-menu:icon-count");

if (iconMenuIconCount != null) {
	iconMenuIconCount.increment();
}

boolean iconMenuShowWhenSingleIcon = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon-menu:showWhenSingleIcon"));
Boolean iconMenuSingleIcon = (Boolean)request.getAttribute("liferay-ui:icon-menu:single-icon");

String alt = (String)request.getAttribute("liferay-ui:icon:alt");
String ariaRole = (String)request.getAttribute("liferay-ui:icon:ariaRole");
String iconCssClass = (String)request.getAttribute("liferay-ui:icon:iconCssClass");
String id = (String)request.getAttribute("liferay-ui:icon:id");
String image = (String)request.getAttribute("liferay-ui:icon:image");
String imageHover = (String)request.getAttribute("liferay-ui:icon:imageHover");
String message = (String)request.getAttribute("liferay-ui:icon:message");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:cssClass"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-ui:icon:data");
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon:label"));
String lang = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:lang"));
String linkCssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:linkCssClass"));
boolean localizeMessage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon:localizeMessage"));
String method = (String)request.getAttribute("liferay-ui:icon:method");
String onClick = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:onClick"));
String src = (String)request.getAttribute("liferay-ui:icon:src");
String srcHover = (String)request.getAttribute("liferay-ui:icon:srcHover");
boolean toolTip = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon:toolTip"));
String target = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:target"));
String url = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon:url"));
boolean useDialog = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon:useDialog"));

if (data == null) {
	data = new HashMap<String, Object>(1);
}

if ((iconListIconCount != null) || (iconListSingleIcon != null)) {
	label = true;
}

if ((iconMenuIconCount != null) || (iconMenuSingleIcon != null)) {
	label = true;
}

if (message == null) {
	message = StringUtil.replace(image, StringPool.UNDERLINE, StringPool.DASH);
	message = StringUtil.replace(message, "../aui/", StringPool.BLANK);
}

if (useDialog && Validator.isNull(data.get("title"))) {
	data.put("title", HtmlUtil.stripHtml(localizeMessage ? LanguageUtil.get(pageContext, message) : message));
}

if (Validator.isNull(method)) {
	int pos = url.indexOf("p_p_lifecycle=0");

	if (pos != -1) {
		method = "get";
	}
	else {
		method = "post";
	}
}

boolean auiImage = (image != null) && image.startsWith(_AUI_PATH);

if (Validator.isNull(src)) {
	if (auiImage) {
		src = themeDisplay.getPathThemeImages().concat("/spacer.png");
	}
	else if (Validator.isNotNull(image)) {
		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/common/");
		sb.append(image);
		sb.append(".png");

		src = StringUtil.replace(sb.toString(), "common/../", "");
	}
}

if (Validator.isNull(srcHover) && Validator.isNotNull(imageHover)) {
	StringBundler sb = new StringBundler(4);

	sb.append(themeDisplay.getPathThemeImages());
	sb.append("/common/");
	sb.append(imageHover);
	sb.append(".png");

	srcHover = sb.toString();
}

String details = null;

if (alt != null) {
	details = " alt=\"" + LanguageUtil.get(pageContext, alt) + "\"";
}
else if (label) {
	details = " alt=\"\"";
}
else {
	StringBundler sb = new StringBundler(6);

	sb.append(" alt=\"");
	sb.append(LanguageUtil.get(pageContext, message));
	sb.append("\"");

	if (toolTip) {
		sb.append(" onmouseover=\"Liferay.Portal.ToolTip.show(this, '");
		sb.append(UnicodeLanguageUtil.get(pageContext, message));
		sb.append("')\"");
	}
	else {
		sb.append(" title=\"");
		sb.append(LanguageUtil.get(pageContext, message));
		sb.append("\"");
	}

	details = sb.toString();
}
%>

<%!
private static final String _AUI_PATH = "../aui/";
%>