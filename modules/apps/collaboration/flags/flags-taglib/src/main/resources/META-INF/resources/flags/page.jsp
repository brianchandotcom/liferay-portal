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

<%@ include file="/flags/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-flags:flags:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-flags:flags:classPK"));
String contentTitle = GetterUtil.getString((String)request.getAttribute("liferay-flags:flags:contentTitle"));
boolean enabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-flags:flags:enabled"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-flags:flags:label"), true);
String message = GetterUtil.getString((String)request.getAttribute("liferay-flags:flags:message"), "flag");
long reportedUserId = GetterUtil.getLong((String)request.getAttribute("liferay-flags:flags:reportedUserId"));

String cssClass = randomNamespace;

if (enabled) {
	cssClass = randomNamespace + " flag-enable";
}
%>

<liferay-portlet:renderURL portletName="<%= PortletKeys.FLAGS %>" var="editEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/flags/edit_entry" /></liferay-portlet:renderURL>

<%
String flagsPortletNamespace = PortalUtil.getPortletNamespace(PortletKeys.FLAGS);
JSONObject dataJSON = JSONFactoryUtil.createJSONObject();

dataJSON.put(flagsPortletNamespace + "className", className);
dataJSON.put(flagsPortletNamespace + "classPK", classPK);
dataJSON.put(flagsPortletNamespace + "contentTitle", contentTitle);
dataJSON.put(flagsPortletNamespace + "contentURL", PortalUtil.getPortalURL(request) + currentURL);
dataJSON.put(flagsPortletNamespace + "reportedUserId", reportedUserId);

SoyContext context = new SoyContext();
context.put("cssClass", cssClass);
context.put("data", dataJSON);
context.put("enable", enable);
context.put("id", randomNamespace + "id");
context.put("label", label);
context.put("message", LanguageUtil.get(request, message));
context.put("pathThemeImages", themeDisplay.getPathThemeImages());
context.put("signedUser", flagsGroupServiceConfiguration.guestUsersEnabled() || themeDisplay.isSignedIn());
context.put("uri", editEntryURL.toString());
%>

<div class="taglib-flags" title="<liferay-ui:message key='<%= !enable ? message : "flags-are-disabled-because-this-entry-is-in-the-recycle-bin" %>' />">
	<soy:template-renderer
		context="<%= context %>"
		module="flags-taglib/flags/js/Flags.es"
		templateNamespace="Flags.render"
	/>
</div>