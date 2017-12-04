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

<%@ include file="/init.jsp" %>

<%
String netvibesURL = ParamUtil.getString(request, "netvibesURL");
String openSocialURL = ParamUtil.getString(request, "openSocialURL");
String widgetURL = ParamUtil.getString(request, "widgetURL");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(widgetURL) %>">
		<p>
			<liferay-ui:message key="share-this-application-on-any-website" />
		</p>

		<textarea class="col-md-12 lfr-textarea" onClick="this.select();" rows="10">
			<iframe frameborder="0" height="100%" src="<%= HtmlUtil.escapeAttribute(widgetURL) %>" width="100%"></iframe>
		</textarea>
	</c:when>
	<c:when test="<%= Validator.isNotNull(netvibesURL) %>">
		<p>
			<aui:a href='<%= HttpUtil.addParameter("http://eco.netvibes.com/apps/submit/info", "url", netvibesURL) %>' target="_blank"><liferay-ui:message key="add-this-application-to-netvibes" /></aui:a>
		</p>

		<aui:input label="" name="netvibesURL" type="resource" value="<%= netvibesURL %>" />
	</c:when>
	<c:when test="<%= Validator.isNotNull(openSocialURL) %>">
		<p>
			<liferay-ui:message key="share-this-application-on-an-open-social-platform" />
		</p>

		<aui:input label="" name="openSocialURL" type="resource" value="<%= openSocialURL %>" />
	</c:when>
</c:choose>