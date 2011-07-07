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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

long layoutRevisionId = StagingUtil.getRecentLayoutRevisionId(request, layoutSetBranchId, plid);

List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutSetBranchId, LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionIdComparator(true));
%>

<div id="<portlet:namespace />revisionsToolbar"></div>

<aui:script use="aui-toolbar,liferay-staging" position="inline">
	Liferay.Staging.Branching.init(
		{
			namespace: '<portlet:namespace />'
		}
	);

	new A.Toolbar(
		{
			activeState: false,
			boundingBox: '#<portlet:namespace />revisionsToolbar',
			children: [
				{
					handler: function (event) {
					},
					icon: 'trash',
					label: '<liferay-ui:message key="clear-history" />'
				}
			]
		}
	).render();
</aui:script>

<c:if test="<%= !layoutRevisions.isEmpty() %>">
	<ul class="layout-revision-container layout-revision-container-root">
		<%= _getGraph(pageContext, layoutRevisionId, layoutRevisions) %>
	</ul>
</c:if>

<%!
public String _getGraph(PageContext pageContext, long layoutRevisionId, List<LayoutRevision> layoutRevisions) throws PortalException, SystemException {
	ServletContext servletContext = pageContext.getServletContext();
	HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
	HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();

	ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

	StringBundler sb = new StringBundler();

	for (LayoutRevision layoutRevision : layoutRevisions) {
		sb.append("<li class=\"layout-revision\"><div class=\"layout-revision-details");

		if (layoutRevision.getLayoutRevisionId() == layoutRevisionId) {
			sb.append(" layout-revision-current");
		}

		if (layoutRevision.isHead()) {
			sb.append(" layout-revision-head");
		}

		sb.append("\"><span class=\"selection-details\">");
		sb.append("<a class=\"selection-handle\" href=\"#\" data-layoutRevisionId=\"");
		sb.append(layoutRevision.getLayoutRevisionId());
		sb.append("\" data-layoutSetBranchId=\"");
		sb.append(layoutRevision.getLayoutSetBranchId());
		sb.append("\">");

		if (layoutRevision.isMajor()) {
			sb.append("<strong>");
			sb.append(layoutRevision.getLayoutRevisionId());
			sb.append("</strong>");
		}
		else {
			sb.append(layoutRevision.getLayoutRevisionId());
		}

		sb.append("</a>");

		User curUser = UserLocalServiceUtil.getUserById(layoutRevision.getUserId());

		sb.append(" - <a class=\"user-handle\" href=\"");
		sb.append(curUser.getDisplayURL(themeDisplay));
		sb.append("\">");
		sb.append(curUser.getFullName());
		sb.append("</a>");

		if (layoutRevision.isPending()) {
			sb.append(" - <span class=\"status\">");
			sb.append(themeDisplay.translate("pending"));
			sb.append("</span>");
		}

		sb.append("</span>");

		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/html/portlet/staging_bar/layout_revision_action.jsp");

		request.setAttribute("layout_revisions.jsp-layoutRevision", layoutRevision);

		StringServletResponse stringResponse = new StringServletResponse(response);

		if (requestDispatcher != null) {
			try {
				requestDispatcher.include(request, stringResponse);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		sb.append(stringResponse.getString());
		sb.append("</div>");

		List<LayoutRevision> curLayoutRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getLayoutRevisionId(), layoutRevision.getPlid(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionIdComparator(true));

		if (!layoutRevisions.isEmpty()) {
			sb.append("<ul class=\"layout-revision-container\">");

			sb.append(_getGraph(pageContext, layoutRevisionId, curLayoutRevisions));

			sb.append("</ul>");
		}

		sb.append("</li>");
	}

	return sb.toString();
}
%>