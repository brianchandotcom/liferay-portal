<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/init.jsp" %>

<%
Layout draftLayout = themeDisplay.getLayout();

Layout publishedLayout = LayoutLocalServiceUtil.getLayout(draftLayout.getClassPK());

String siteExternalReferenceCode = themeDisplay.getScopeGroup(
).getExternalReferenceCode();
String sitePageExternalReferenceCode = publishedLayout.getExternalReferenceCode();

String pageSpecificationVersionsURL = "/o/headless-admin-site/v1.0/sites/" + siteExternalReferenceCode + "/site-pages/" + sitePageExternalReferenceCode + "/page-specification-versions";
%>

<react:component
	module="{VersionHistory} from layout-content-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"pageSpecificationVersionsURL", pageSpecificationVersionsURL
		).build()
	%>'
/>
