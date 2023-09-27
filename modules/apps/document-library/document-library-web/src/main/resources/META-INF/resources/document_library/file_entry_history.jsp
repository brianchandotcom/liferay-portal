<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<ul class="list-group sidebar-list-group">

	<%
	FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");

	int status = WorkflowConstants.STATUS_APPROVED;

	if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	int start = 0;
	int end = 10;

	for (FileVersion fileVersion : ListUtil.sort(fileEntry.getFileVersions(status, start, end), new FileVersionVersionComparator(false))) {
	%>

		<li class="list-group-item list-group-item-flex">
			<clay:content-col
				expand="<%= true %>"
			>
				<div class="list-group-title">
					<liferay-ui:message arguments="<%= fileVersion.getVersion() %>" key="version-x" />
				</div>

				<div class="list-group-subtitle">
					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(fileVersion.getUserName()), dateFormatDateTime.format(fileVersion.getCreateDate())} %>" key="by-x-on-x" translateArguments="<%= false %>" />
				</div>

				<div class="list-group-subtext">
					<c:choose>
						<c:when test="<%= Validator.isNull(fileVersion.getChangeLog()) %>">
							<liferay-ui:message key="no-change-log" />
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(fileVersion.getChangeLog()) %>
						</c:otherwise>
					</c:choose>
				</div>
			</clay:content-col>

			<clay:content-col>

				<%
				DLViewFileEntryHistoryDisplayContext dlViewFileEntryHistoryDisplayContext = dlDisplayContextProvider.getDLViewFileEntryHistoryDisplayContext(request, response, fileVersion);
				%>

				<clay:dropdown-actions
					aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
					dropdownItems="<%= dlViewFileEntryHistoryDisplayContext.getActionDropdownItems() %>"
					propsTransformer="document_library/js/DLFileEntryDropdownPropsTransformer"
				/>
			</clay:content-col>
		</li>

	<%
	}
	%>

	<c:if test="<%= fileEntry.getFileVersionsCount(status) >= end %>">
		<portlet:renderURL var="viewMoreURL">
			<portlet:param name="mvcRenderCommandName" value="/document_library/view_file_entry_history" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
		</portlet:renderURL>

		<div class="m-4 text-center">
			<clay:link
				displayType="secondary"
				href="<%= viewMoreURL %>"
				label="view-more"
				small="<%= true %>"
				type="button"
			/>
		</div>
	</c:if>
</ul>