<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EmptyFDSDisplayContext emptyFDSDisplayContext = new EmptyFDSDisplayContext(request);
%>

<p>Headless display tag customizing the empty state</p>
<frontend-data-set:headless-display
	apiURL="<%= emptyFDSDisplayContext.getAPIURL() %>"
	emptyState="<%= emptyFDSDisplayContext.getEmptyState() %>"
	id="<%= FDSSampleFDSNames.EMPTY %>"
/>