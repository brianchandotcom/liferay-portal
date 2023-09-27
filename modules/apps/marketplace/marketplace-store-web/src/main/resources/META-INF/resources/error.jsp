<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<style type="text/css">
	a {
		border: 2px solid #1C75B9;
		border-radius: 4px;
		color: #1C75B9;
		line-height: 20px;
		padding: 8px 14px;
		text-decoration: none;
	}

	a:hover {
		border-color: #65B6F0;
		color: #65B6F0;
	}

	body {
		font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
		font-size: 16px;
	}

	p {
		background: #F1D1D8;
		border: 1px solid transparent;
		border-radius: 4px;
		color: #D77C8A;
		padding: 14px;
		margin: 2em 0;
	}

	.error {
		margin: 0 auto;
		max-width: 960px;
		padding: 6em 4em;
	}
</style>

<div class="error">
	<img src="<%= PortalUtil.getPathContext(request) %>/images/logo.svg" />

	<p>
		<liferay-ui:message key="could-not-connect-to-the-liferay-marketplace" />
	</p>

	<a href="http://www.liferay.com/marketplace" target="_blank"><liferay-ui:message key="browse-the-marketplace-on-liferay.com" /></a>
</div>