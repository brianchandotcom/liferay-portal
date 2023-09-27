<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group group = themeDisplay.getScopeGroup();
%>

<c:if test="<%= MicroblogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) && group.isUser() && !layout.isPublicLayout() %>">
	<liferay-util:include page="/microblogs/edit_microblogs_entry.jsp" servletContext="<%= application %>" />
</c:if>

<c:if test="<%= showStatus %>">
	<div class="microblogs-container microblogs-status-container">

		<%
		List<MicroblogsEntry> microblogsEntries = null;

		long microblogsEntryUserId = themeDisplay.getUserId();

		if (group.isUser()) {
			microblogsEntryUserId = group.getClassPK();
		}

		if (microblogsEntryUserId == themeDisplay.getUserId()) {
			microblogsEntries = MicroblogsEntryLocalServiceUtil.getUserMicroblogsEntries(microblogsEntryUserId, 0, 0, 1);
		}
		else {
			microblogsEntries = MicroblogsEntryServiceUtil.getUserMicroblogsEntries(microblogsEntryUserId, 0, 0, 1);
		}

		request.setAttribute(WebKeys.MICROBLOGS_ENTRIES, microblogsEntries);

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/status_update/view.jsp"
		).setWindowState(
			WindowState.NORMAL
		).buildPortletURL();

		request.setAttribute(WebKeys.MICROBLOGS_ENTRIES_URL, portletURL);
		%>

		<liferay-util:include page="/microblogs/view_microblogs_entries.jsp" servletContext="<%= application %>" />
	</div>
</c:if>

<aui:script use="aui-base">
	AUI().ready(function () {
		Liferay.Microblogs.init({
			microblogsEntriesURL:
				'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/status_update/view.jsp" /></portlet:renderURL>',
		});
	});
</aui:script>