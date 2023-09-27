<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute(WebKeys.LAYOUT_REVISION);

LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());

Layout targetLayout = LayoutLocalServiceUtil.getLayout(layoutRevision.getPlid());

String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(targetLayout, themeDisplay);

String layoutSetBranchName = layoutSetBranch.getName();

if (LayoutSetBranchConstants.MASTER_BRANCH_NAME.equals(layoutSetBranchName)) {
	layoutSetBranchName = LanguageUtil.get(request, layoutSetBranchName);
}

String layoutBranchName = layoutBranch.getName();

if (LayoutBranchConstants.MASTER_BRANCH_NAME.equals(layoutBranchName)) {
	layoutBranchName = LanguageUtil.get(request, layoutBranchName);
}
%>

<strong><liferay-ui:message key="page" />:</strong> <a href="<%= HtmlUtil.escapeHREF(layoutFriendlyURL) %>?layoutSetBranchId=<%= layoutRevision.getLayoutSetBranchId() %>&layoutRevisionId=<%= layoutRevision.getLayoutRevisionId() %>"><%= HtmlUtil.escape(targetLayout.getHTMLTitle(locale)) %></a><br />

<strong><liferay-ui:message key="site-pages-variation" />:</strong> <%= HtmlUtil.escape(layoutSetBranchName) %><br />

<strong><liferay-ui:message key="page-variation" />:</strong> <%= HtmlUtil.escape(layoutBranchName) %><br />

<strong><liferay-ui:message key="revision-id" />:</strong> <%= layoutRevision.getLayoutRevisionId() %>