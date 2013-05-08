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

<%@ include file="/html/portal/api/jsonws/init.jsp" %>

<style>
	<%@ include file="/html/portal/api/jsonws/css.jspf" %>
</style>

<div id="wrapper">
	<header id="banner" role="banner">
		<hgroup id="heading">
			<h1 class="site-title">
				<a class="logo" href="<%= jsonWSContextPath %>" title="JSONWS API">
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
			<aui:row>
				<aui:col cssClass="lfr-api-navigation" width="<%= 25 %>">
					<liferay-util:include page="/html/portal/api/jsonws/actions.jsp" />
				</aui:col>
				<aui:col cssClass="lfr-api-details" width="<%= 75 %>">
					<liferay-util:include page="/html/portal/api/jsonws/action.jsp" />
				</aui:col>
			</aui:row>
		</div>
	</div>

	<footer id="footer" role="contentinfo">
		<p class="powered-by">
			<liferay-ui:message key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a>
		</p>
	</footer>
</div>