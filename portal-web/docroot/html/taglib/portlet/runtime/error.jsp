<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<div class="alert alert-warning c-mt-3 lfr-configurator-visibility">
	<%= (String)request.getAttribute("liferay-portlet:runtime:errorMessage") %>
</div>