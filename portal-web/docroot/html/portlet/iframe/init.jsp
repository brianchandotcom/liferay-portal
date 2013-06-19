<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portlet.iframe.util.IFrameUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String src = preferences.getValue("src", StringPool.BLANK);
boolean relative = GetterUtil.getBoolean(preferences.getValue("relative", StringPool.BLANK));

boolean auth = GetterUtil.getBoolean(preferences.getValue("auth", StringPool.BLANK));
String authType = preferences.getValue("authType", StringPool.BLANK);
String formMethod = preferences.getValue("formMethod", StringPool.BLANK);
String userNameField = preferences.getValue("userNameField", StringPool.BLANK);
String passwordField = preferences.getValue("passwordField", StringPool.BLANK);

String userName = null;
String password = null;

if (authType.equals("basic")) {
	userName = preferences.getValue("basicUserName", StringPool.BLANK);
	password = preferences.getValue("basicPassword", StringPool.BLANK);
}
else {
	userName = preferences.getValue("formUserName", StringPool.BLANK);
	password = preferences.getValue("formPassword", StringPool.BLANK);
}

String hiddenVariables = preferences.getValue("hiddenVariables", StringPool.BLANK);
boolean resizeAutomatically = GetterUtil.getBoolean(preferences.getValue("resizeAutomatically", StringPool.TRUE));
String heightMaximized = GetterUtil.getString(preferences.getValue("heightMaximized", "600"));
String heightNormal = GetterUtil.getString(preferences.getValue("heightNormal", "600"));
String width = GetterUtil.getString(preferences.getValue("width", "100%"));

String alt = preferences.getValue("alt", StringPool.BLANK);
String border = preferences.getValue("border", "0");
String bordercolor = preferences.getValue("bordercolor", "#000000");
String frameborder = preferences.getValue("frameborder", "0");
String hspace = preferences.getValue("hspace", "0");
String longdesc = preferences.getValue("longdesc", StringPool.BLANK);
String scrolling = preferences.getValue("scrolling", "auto");
String title = preferences.getValue("title", StringPool.BLANK);
String vspace = preferences.getValue("vspace", "0");

List<String> iframeVariables = new ArrayList<String>();

Enumeration<String> enu = request.getParameterNames();

while (enu.hasMoreElements()) {
	String name = enu.nextElement();

	if (name.startsWith(_IFRAME_PREFIX)) {
		iframeVariables.add(name.substring(_IFRAME_PREFIX.length()).concat(StringPool.EQUAL).concat(request.getParameter(name)));
	}
}
%>

<%@ include file="/html/portlet/iframe/init-ext.jsp" %>

<%!
private static final String _IFRAME_PREFIX = "iframe_";
%>