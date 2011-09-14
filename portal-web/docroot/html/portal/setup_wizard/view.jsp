<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/common/init.jsp" %>

<%
UnicodeProperties properties = (UnicodeProperties)request.getAttribute("setup_wizard_properties");

boolean setupWizardFinished = GetterUtil.getBoolean((Boolean)session.getServletContext().getAttribute(WebKeys.SETUP_WIZARD_FINISHED));
%>

<html>
	<head>
		<title><liferay-ui:message key="welcome-to-liferay" /></title>

		<%@ include file="/html/common/themes/top_js.jspf" %>

		<style>
			<liferay-util:include page="/html/themes/classic/css/application.css" />
			<liferay-util:include page="/html/portal/css.jsp" />
			<liferay-util:include page="/html/portal/setup_wizard/css/main.jsp" />
		</style>
	</head>
	<body>
		<div id="wrapper" style="padding-left: 2%; padding-right: 2%">
			<div id="content">
				<div id="setup-wrapper">
					<header id="banner" role="banner">
						<hgroup id="heading" style="display: table; width: 100%">
							<div style="float: left; width: 50%">
								<h1 class="company-title">
									<img src="/html/portal/setup_wizard/images/company_logo.png" title="Liferay" alt="Liferay" />
								</h1>
							</div>
							<div id="setup-title">
								<h1>
									Basic Configuration
								</h1>
							</div>
						</hgroup>
					</header>

					<c:choose>
						<c:when test="<%= properties== null || !setupWizardFinished %>">
							<liferay-util:include page="/html/portal/setup_wizard/edit_properties.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portal/setup_wizard/success.jsp" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</body>
</html>