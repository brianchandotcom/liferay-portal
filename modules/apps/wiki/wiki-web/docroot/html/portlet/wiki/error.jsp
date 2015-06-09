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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="please-enter-a-valid-page-title" />

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchPageException.class.getName()) %>">

	<%
	long nodeId = ParamUtil.getLong(request, "nodeId");

	if (nodeId == 0) {
		WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

		if (node != null) {
			nodeId = node.getNodeId();
		}
	}

	String title = ParamUtil.getString(request, "title");

	boolean hasDraftPage = false;

	if (nodeId > 0) {
		hasDraftPage = WikiPageLocalServiceUtil.hasDraftPage(nodeId, title);
	}

	PortletURL searchURL = renderResponse.createRenderURL();

	searchURL.setParameter("struts_action", "/wiki/search");
	searchURL.setParameter("redirect", currentURL);
	searchURL.setParameter("nodeId", String.valueOf(nodeId));
	searchURL.setParameter("keywords", title);

	PortletURL editPageURL = renderResponse.createRenderURL();

	editPageURL.setParameter("struts_action", "/wiki/edit_page");
	editPageURL.setParameter("redirect", currentURL);
	editPageURL.setParameter("nodeId", String.valueOf(nodeId));
	editPageURL.setParameter("title", title);
	%>

	<c:choose>
		<c:when test="<%= hasDraftPage %>">

			<%
			WikiPage draftPage = WikiPageLocalServiceUtil.getDraftPage(nodeId, title);

			boolean editableDraft = false;

			if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || (draftPage.getUserId() == user.getUserId())) {
				editableDraft = true;
			}
			%>

			<c:choose>
				<c:when test="<%= editableDraft %>">
					<div class="alert alert-info">
						<liferay-ui:message key="this-page-has-an-associated-draft-that-is-not-yet-published" />
					</div>

					<div class="btn-toolbar">

						<%
						String taglibEditPage = "location.href = '" + editPageURL.toString() + "';";
						%>

						<aui:button onClick="<%= taglibEditPage %>" value="edit-draft" />
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<liferay-ui:message key="this-page-has-already-been-started-by-another-author" />
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="this-page-is-empty.-use-the-buttons-below-to-create-it-or-to-search-for-the-words-in-the-title" />
			</div>

			<div class="btn-toolbar">

				<%
				String taglibSearch = "location.href = '" + searchURL.toString() + "';";
				%>

				<aui:button onClick="<%= taglibSearch %>" value='<%= LanguageUtil.format(request, "search-for-x", HtmlUtil.escapeAttribute(title), false) %>' />

				<%
				String taglibEditPage = "location.href = '" + editPageURL.toString() + "';";
				%>

				<aui:button onClick="<%= taglibEditPage %>" value='<%= LanguageUtil.format(request, "create-page-x", HtmlUtil.escapeAttribute(title), false) %>' />
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-page-title" />
<liferay-ui:error exception="<%= PrincipalException.MustBeAuthenticated.class %>" message="please-sign-in-to-access-this-application" />
<liferay-ui:error exception="<%= PrincipalException.MustBeCompanyAdmin.class %>" message="you-do-not-have-the-required-permissions" />

<liferay-ui:error exception="<%= PrincipalException.MustBeEnabled.class %>">

	<%
	PrincipalException.MustBeEnabled pe = (PrincipalException.MustBeEnabled)errorException;
	%>

	<liferay-ui:message arguments="<%= pe.resourceName %>" key="x-is-not-enabled" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= PrincipalException.MustBeInvokedByPost.class %>" message="an-unexpected-error-occurred-while-connecting-to-the-specified-url" />
<liferay-ui:error exception="<%= PrincipalException.MustBeMarketplaceAdmin.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeOmniadmin.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeOwnedByCurrentUser.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBePortletStrutsPath.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustBeSupportedActionForRole.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeValidPortlet.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHavePermission.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveUserGroupRole.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveUserRole.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidPermissionChecker.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidPortletId.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidPrincipalName.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustInitializePermissionChecker.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustNotBeGroupAdmin.class %>" message="you-do-not-have-the-required-permissions" />