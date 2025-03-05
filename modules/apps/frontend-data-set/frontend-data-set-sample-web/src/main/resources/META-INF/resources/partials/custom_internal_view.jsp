<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FDSSampleDisplayContext fdsSampleDisplayContext = (FDSSampleDisplayContext)request.getAttribute(FDSSampleWebKeys.FDS_SAMPLE_DISPLAY_CONTEXT);
%>

<p>Headless display tag configured with a custom internal view via propsTransformer</p>
<frontend-data-set:headless-display
	apiURL="<%= fdsSampleDisplayContext.getAPIURL() %>"
	id="<%= FDSSampleFDSNames.CUSTOM_INTERNAL_VIEW %>"
	style="fluid"
/>