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

<%@ include file="/document_library/init.jsp" %>

<ul class="list-group sidebar-list-group">

	<%
	FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");

	int status = WorkflowConstants.STATUS_APPROVED;

	if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	int start = QueryUtil.ALL_POS;
	int end = QueryUtil.ALL_POS;

	if (FeatureFlagManagerUtil.isEnabled("LPS-175915")) {
		start = 0;
		end = 10;
	}

	for (FileVersion fileVersion : fileEntry.getFileVersions(status, start, end)) {
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

	<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-175915") && (end > 0 && fileEntry.getFileVersionsCount(status) >= end) %>'>
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