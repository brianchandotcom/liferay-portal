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

<%@ include file="/html/portal/init.jsp" %>

<%
int status = ParamUtil.getInteger(request, "status");

if (status > 0) {
	response.setStatus(status);
}

String exception = ParamUtil.getString(request, "exception");

String url = ParamUtil.getString(request, "previousURL");

if (Validator.isNull(url)) {
	url = PortalUtil.getCurrentURL(request);
}

url = themeDisplay.getPortalURL() + url;

boolean noSuchResourceException = false;

for (String key : SessionErrors.keySet(request)) {
	key = key.substring(key.lastIndexOf(StringPool.PERIOD) + 1);

	if (key.startsWith("NoSuch") && key.endsWith("Exception")) {
		noSuchResourceException = true;
	}
}

if (Validator.isNotNull(exception)) {
	exception = exception.substring(exception.lastIndexOf(StringPool.PERIOD) + 1);

	if (exception.startsWith("NoSuch") && exception.endsWith("Exception")) {
		noSuchResourceException = true;
	}
}
%>

<c:choose>
	<c:when test="<%= SessionErrors.contains(request, PrincipalException.class.getName()) %>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="forbidden" />
		</h3>

		<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />

		<br /><br />

		<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, PortalException.class.getName()) || SessionErrors.contains(request, SystemException.class.getName()) %>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

		<br /><br />

		<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, TransformException.class.getName()) %>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message key="an-error-occurred-while-processing-the-requested-resource" />

		<br /><br />

		<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>

		<br /><br />

		<%
		TransformException te = (TransformException)SessionErrors.get(request, TransformException.class.getName());
		%>

		<div>
			<%= StringUtil.replace(te.getMessage(), new String[] {"<", "\n"}, new String[] {"&lt;", "<br />\n"}) %>
		</div>
	</c:when>
	<c:when test="<%= noSuchResourceException %>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="not-found" />
		</h3>

		<liferay-ui:message key="the-requested-resource-was-not-found" />

		<br /><br />

		<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>
	</c:when>
	<c:otherwise>
		<h3 class="alert alert-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

		<br /><br />

		<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>

		<%
		for (String key : SessionErrors.keySet(request)) {
			Object value = SessionErrors.get(request, key);

			if (value instanceof Exception) {
				Exception e = (Exception)value;

				_log.error(e.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
		%>

	</c:otherwise>
</c:choose>

<div class="separator"><!-- --></div>

<a href="javascript:history.go(-1);">&laquo; <liferay-ui:message key="back" /></a>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portal.status_jsp");
%>