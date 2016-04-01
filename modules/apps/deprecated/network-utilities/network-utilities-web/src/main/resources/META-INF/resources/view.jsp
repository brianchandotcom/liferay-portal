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
String cmd = ParamUtil.getString(request, Constants.CMD);

String tabs1 = ParamUtil.getString(request, "tabs1", "dns-lookup");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<aui:form action="<%= portletURL.toString() %>">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.SEARCH %>" />
	<aui:input name="tabs1" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs1) %>" />

	<liferay-ui:tabs
		names="dns-lookup,whois"
		url="<%= portletURL.toString() %>"
	/>

	<c:choose>
		<c:when test='<%= tabs1.equals("dns-lookup") %>'>

			<%
			String domain = ParamUtil.getString(request, "domain");

			DNSLookup dnsLookup = null;

			if (cmd.equals(Constants.SEARCH)) {
				dnsLookup = NetworkUtil.getDNSLookup(domain);

				if (dnsLookup == null) {
					SessionErrors.add(renderRequest, DNSLookup.class.getName());
				}
			}
			%>

			<liferay-ui:error exception="<%= DNSLookup.class %>" message="please-enter-a-valid-host-name-or-ip" />

			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="domain" />

			<c:if test="<%= dnsLookup != null %>">
				<liferay-ui:panel collapsible="<%= false %>" title="<%= HtmlUtil.escape(domain) %>">
					<pre>
<%= dnsLookup.getResults() %>
					</pre>
				</liferay-ui:panel>
			</c:if>
		</c:when>
		<c:when test='<%= tabs1.equals("whois") %>'>

			<%
			String domain = ParamUtil.getString(request, "domain");

			Whois whois = null;

			if (cmd.equals(Constants.SEARCH)) {
				whois = NetworkUtil.getWhois(domain);

				if (whois == null) {
					SessionErrors.add(renderRequest, Whois.class.getName());
				}
			}
			%>

			<liferay-ui:error exception="<%= Whois.class %>" message="an-unexpected-error-occurred" />

			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="domain" />

			<c:if test="<%= whois != null %>">
				<liferay-ui:panel collapsible="<%= false %>" title="<%= HtmlUtil.escape(domain) %>">
					<pre>
<%= whois.getResults() %>
					</pre>
				</liferay-ui:panel>
			</c:if>
		</c:when>
	</c:choose>
</aui:form>