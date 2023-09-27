<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<BlogsStatsUser> statsUsers = null;

if (selectionMethod.equals("users")) {
	if (organizationId > 0) {
		statsUsers = BlogsStatsUserLocalServiceUtil.getOrganizationStatsUsers(organizationId, 0, max);
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
		SearchContainer<BlogsStatsUser> searchContainer = new SearchContainer();

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("user");
		headerNames.add("posts");
		headerNames.add("date");

		if (displayStyle.equals("user-name")) {
			searchContainer.setHeaderNames(headerNames);
		}

		boolean statsUserRendered = false;

		List<com.liferay.portal.kernel.dao.search.ResultRow> resultRows = searchContainer.getResultRows();

		for (int i = 0; i < statsUsers.size(); i++) {
			BlogsStatsUser statsUser = statsUsers.get(i);

			try {
				Group group = GroupLocalServiceUtil.getGroup(statsUser.getGroupId());
				User user2 = UserLocalServiceUtil.getUserById(statsUser.getStatsUserId());

				int entriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(group.getGroupId(), user2.getUserId(), WorkflowConstants.STATUS_APPROVED);

				List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupUserEntries(group.getGroupId(), user2.getUserId(), WorkflowConstants.STATUS_APPROVED, 0, max, new EntryModifiedDateComparator());

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

				BlogsEntry entry = entries.get(0);

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
					markupView="deprecated"
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