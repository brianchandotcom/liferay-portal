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
List statsUsers = null;

if (selectionMethod.equals("users")) {
	if (organizationId > 0) {
		statsUsers = BlogsStatsUserLocalServiceUtil.getOrganizationStatsUsers(organizationId, 0, max, new StatsUserLastPostDateComparator());
	}
	else {
		statsUsers = BlogsStatsUserLocalServiceUtil.getGroupsStatsUsers(company.getCompanyId(), scopeGroupId, 0, max);
	}
}
else {
	statsUsers = BlogsStatsUserLocalServiceUtil.getGroupStatsUsers(scopeGroupId, 0, max);
}
%>

<c:choose>
	<c:when test="<%= statsUsers.isEmpty() %>">
		<liferay-ui:message key="there-are-no-recent-bloggers" />
	</c:when>
	<c:otherwise>

		<%
		SearchContainer searchContainer = new SearchContainer();

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("user");
		headerNames.add("posts");
		headerNames.add("date");

		if (displayStyle.equals("user-name")) {
			searchContainer.setHeaderNames(headerNames);
		}

		boolean statsUserRendered = false;

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < statsUsers.size(); i++) {
			BlogsStatsUser statsUser = (BlogsStatsUser)statsUsers.get(i);

			try {
				Group group = GroupLocalServiceUtil.getGroup(statsUser.getGroupId());
				User user2 = UserLocalServiceUtil.getUserById(statsUser.getUserId());

				int entriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(group.getGroupId(), user2.getUserId(), WorkflowConstants.STATUS_APPROVED);

				List entries = BlogsEntryServiceUtil.getGroupUserEntries(group.getGroupId(), user2.getUserId(), WorkflowConstants.STATUS_APPROVED, 0, max, new EntryModifiedDateComparator());

				if (entries.isEmpty()) {
					if (!selectionMethod.equals("users")) {
						continue;
					}

					statsUser = BlogsStatsUserLocalServiceUtil.getStatsUser(scopeGroupId, user2.getUserId());

					entriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(scopeGroupId, user2.getUserId(), WorkflowConstants.STATUS_APPROVED);

					entries = BlogsEntryServiceUtil.getGroupUserEntries(scopeGroupId, user2.getUserId(), WorkflowConstants.STATUS_APPROVED, 0, max, new EntryModifiedDateComparator());

					if (entries.isEmpty()) {
						continue;
					}
				}

				statsUserRendered = true;

				BlogsEntry entry = (BlogsEntry)entries.get(0);

				StringBundler sb = new StringBundler(4);

				sb.append(themeDisplay.getPathMain());
				sb.append("/blogs/find_entry?entryId=");
				sb.append(entry.getEntryId());
				sb.append("&showAllEntries=0");

				String rowHREF = sb.toString();

				ResultRow row = new ResultRow(new Object[] {statsUser, rowHREF}, statsUser.getStatsUserId(), i);

				if (displayStyle.equals("user-name")) {

					// User

					row.addText(HtmlUtil.escape(user2.getFullName()), rowHREF);

					// Number of posts

					row.addText(String.valueOf(entriesCount), rowHREF);

					// Last post date

					row.addDate(entry.getModifiedDate(), rowHREF);
				}
				else {

					// User display

					row.addJSP("/user_display.jsp", application, request, response);
				}

				// Add result row

				resultRows.add(row);
			}
			catch (Exception e) {
			}
		}
		%>

		<c:choose>
			<c:when test="<%= statsUserRendered %>">
				<liferay-ui:search-iterator
					paginate="<%= false %>"
					searchContainer="<%= searchContainer %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="there-are-no-recent-bloggers" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>