<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/api/jsonws/init.jsp" %>

<%
String contextPath = request.getParameter("contextPath");

if (contextPath == null) {
	contextPath = ContextPathUtil.getContextPath(application);
}
%>

<style>
	<%@ include file="/html/portal/api/jsonws/css.jspf" %>
</style>

<div id="wrapper">
	<header id="banner" role="banner">
		<hgroup id="heading">
			<h1 class="site-title">
				<a class="logo" href="<%= contextPath %>/api/jsonws" title="JSONWS API">
					<img alt="JSONWS API" height="<%= themeDisplay.getCompanyLogoHeight() %>" src="<%= HtmlUtil.escape(themeDisplay.getCompanyLogo()) %>" width="<%= themeDisplay.getCompanyLogoWidth() %>" />
				</a>

				<span class="site-name">
					JSONWS API
				</span>
			</h1>
		</hgroup>
	</header>

	<div id="content">
		<div id="main-content">
			<aui:layout>
				<aui:column columnWidth="25" cssClass="lfr-api-navigation">
					<liferay-util:include page="/html/portal/api/jsonws/actions.jsp" />
				</aui:column>
				<aui:column columnWidth="75" cssClass="lfr-api-details">
					<liferay-util:include page="/html/portal/api/jsonws/action.jsp" />
				</aui:column>
			</aui:layout>
		</div>
	</div>

	<footer id="footer" role="contentinfo">
		<p class="powered-by">
			<liferay-ui:message key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a>
		</p>
	</footer>
</div>