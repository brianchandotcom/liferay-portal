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

<%@ include file="/html/portlet/portlet_sharing/init.jsp" %>

<%
String netvibesURL = ParamUtil.getString(request, "netvibesURL");
String widgetURL = ParamUtil.getString(request, "widgetURL");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(widgetURL) %>">
		<p>
			<liferay-ui:message key="share-this-application-on-any-website" />
		</p>

		<textarea class="lfr-textarea" onClick="Liferay.Util.selectAndCopy(this);">&lt;script src=&quot;<%= themeDisplay.getPortalURL() %><%= themeDisplay.getPathContext() %>/html/js/liferay/widget.js&quot; type=&quot;text/javascript&quot;&gt;&lt;/script&gt;
&lt;script type=&quot;text/javascript&quot;&gt;
Liferay.Widget({ url: &#x27;<%= HtmlUtil.escape(widgetURL) %>&#x27;});
&lt;/script&gt;</textarea>
	</c:when>
	<c:when test="<%= Validator.isNotNull(netvibesURL) %>">
		<p>
			<aui:a href="http://eco.netvibes.com/submit/widget" target="_blank"><liferay-ui:message key="add-this-application-to-netvibes" /></aui:a>
		</p>

		<liferay-ui:input-resource url="<%= netvibesURL %>" />
	</c:when>
</c:choose>