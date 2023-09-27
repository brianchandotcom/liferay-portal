<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

					<liferay-util:include page="/view_child_groups.jsp" servletContext="<%= application %>" />
				</c:if>
			</li>

		<%
		}
		%>

	</ul>
</c:if>