<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<strong><liferay-ui:message key="page" />:</strong> <a href="<%= HtmlUtil.escapeHREF(layoutFriendlyURL) + "?layoutSetBranchId=" + layoutRevision.getLayoutSetBranchId() + "&layoutRevisionId=" + layoutRevision.getLayoutRevisionId() %>"><%= HtmlUtil.escape(targetLayout.getHTMLTitle(locale)) %></a><br />

<strong><liferay-ui:message key="site-pages-variation" />:</strong> <%= HtmlUtil.escape(layoutSetBranchName) %><br />

<strong><liferay-ui:message key="page-variation" />:</strong> <%= HtmlUtil.escape(layoutBranchName) %><br />

<strong><liferay-ui:message key="revision-id" />:</strong> <%= layoutRevision.getLayoutRevisionId() %>