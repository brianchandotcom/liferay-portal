<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

int status = WorkflowConstants.STATUS_APPROVED;

if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcRenderCommandName" value="/document_library/select_file_version" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
	<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
</liferay-portlet:renderURL>

<clay:container-fluid>
	<aui:form action="<%= portletURL %>" method="post" name="selectFileVersionFm">
		<liferay-ui:search-container
			id="fileVersionSearchContainer"
			iteratorURL="<%= portletURL %>"
			total="<%= fileEntry.getFileVersionsCount(status) %>"
		>
			<liferay-ui:search-container-results
				results="<%= fileEntry.getFileVersions(status) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.FileVersion"
				modelVar="curFileVersion"
			>
				<liferay-ui:search-container-column-text
					name="name"
					truncate="<%= true %>"
				>
					<c:choose>
						<c:when test="<%= fileVersion.getFileVersionId() != curFileVersion.getFileVersionId() %>">
							<aui:a
								cssClass="selector-button"
								data='<%=
									HashMapBuilder.<String, Object>put(
										"sourceversion", curFileVersion.getFileVersionId()
									).put(
										"targetversion", fileVersion.getFileVersionId()
									).build()
								%>'
								href="javascript:void(0);"
							>
								<%= HtmlUtil.escape(curFileVersion.getTitle()) %>
							</aui:a>
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(curFileVersion.getTitle()) %>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="version"
					property="version"
				/>

				<liferay-ui:search-container-column-date
					name="date"
					value="<%= curFileVersion.getModifiedDate() %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>