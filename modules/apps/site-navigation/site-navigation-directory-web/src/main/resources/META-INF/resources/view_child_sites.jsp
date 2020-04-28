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
int groupLevel = GetterUtil.getInteger(request.getAttribute("view.jsp-groupLevel"));
long parentGroupId = GetterUtil.getLong(request.getAttribute("view.jsp-parentGroupId"));

List<Group> childGroups = GroupLocalServiceUtil.getGroups(themeDisplay.getCompanyId(), parentGroupId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

childGroups = ListUtil.filter(childGroups, group -> group.hasPrivateLayouts() || group.hasPublicLayouts());
%>

<c:if test="<%= !childGroups.isEmpty() %>">
	<ul class="level-<%= groupLevel %> sites">

		<%
		for (Group childGroup : childGroups) {
			String className = StringPool.BLANK;

			if (Objects.equals(sitesDirectoryDisplayContext.getDisplayStyle(), "list-hierarchy")) {
				className += "open ";
			}

			if (scopeGroupId == childGroup.getGroupId()) {
				className += "selected";
			}
		%>

			<li class="<%= className %>">
				<c:choose>
					<c:when test="<%= childGroup.getGroupId() != themeDisplay.getScopeGroupId() %>">
						<a class="<%= className %>" href="<%= HtmlUtil.escapeHREF(childGroup.getDisplayURL(themeDisplay, !childGroup.hasPublicLayouts())) %>">
							<%= HtmlUtil.escape(childGroup.getDescriptiveName(themeDisplay.getLocale())) %>
						</a>
					</c:when>
					<c:otherwise>
						<span class="<%= className %>">
							<%= HtmlUtil.escape(childGroup.getDescriptiveName(themeDisplay.getLocale())) %>
						</span>
					</c:otherwise>
				</c:choose>

				<c:if test='<%= Objects.equals(sitesDirectoryDisplayContext.getDisplayStyle(), "list-hierarchy") %>'>

					<%
					request.setAttribute("view.jsp-groupLevel", ++groupLevel);
					request.setAttribute("view.jsp-parentGroupId", childGroup.getGroupId());
					%>

					<liferay-util:include page="/view_child_sites.jsp" servletContext="<%= application %>" />
				</c:if>
			</li>

		<%
		}
		%>

	</ul>
</c:if>