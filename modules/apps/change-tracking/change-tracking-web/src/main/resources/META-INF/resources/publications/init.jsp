<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PublicationsDisplayContext publicationsDisplayContext = (PublicationsDisplayContext)request.getAttribute(CTWebKeys.PUBLICATIONS_DISPLAY_CONTEXT);

CTDisplayRendererRegistry ctDisplayRendererRegistry = null;

if (publicationsDisplayContext != null) {
	ctDisplayRendererRegistry = publicationsDisplayContext.getCtDisplayRendererRegistry();
}
%>