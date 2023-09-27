<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

long parentMicroblogsEntryId = ParamUtil.getLong(request, "parentMicroblogsEntryId");

List<MicroblogsEntry> microblogsEntries = MicroblogsEntryLocalServiceUtil.getParentMicroblogsEntryMicroblogsEntries(MicroblogsEntryConstants.TYPE_REPLY, parentMicroblogsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new EntryCreateDateComparator(true));

request.setAttribute(WebKeys.MICROBLOGS_ENTRIES, microblogsEntries);

PortletURL microblogsEntriesURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/microblogs/view.jsp"
).setTabs1(
	ParamUtil.getString(request, "tabs1", "timeline")
).setParameter(
	"cur", cur
).setWindowState(
	WindowState.NORMAL
).buildPortletURL();

request.setAttribute(WebKeys.MICROBLOGS_ENTRIES_URL, microblogsEntriesURL);
%>

<div class="comments-container-content">

	<%
	request.setAttribute("view_comments.jsp-comment", "true");
	request.setAttribute("view_comments.jsp-parentMicroblogsEntryId", parentMicroblogsEntryId);
	%>

	<c:if test="<%= !microblogsEntries.isEmpty() %>">
		<liferay-util:include page="/microblogs/view_microblogs_entries.jsp" servletContext="<%= application %>" />
	</c:if>

	<c:if test="<%= MicroblogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>">
		<liferay-util:include page="/microblogs/edit_microblogs_entry.jsp" servletContext="<%= application %>" />
	</c:if>
</div>