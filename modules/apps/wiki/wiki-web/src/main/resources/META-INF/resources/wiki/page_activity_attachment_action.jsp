<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SocialActivity socialActivity = null;

if (row == null) {
	socialActivity = (SocialActivity)request.getAttribute("page_info_panel.jsp-socialActivity");
}
else {
	socialActivity = (SocialActivity)row.getObject();
}

JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(HtmlUtil.unescape(socialActivity.getExtraData()));

FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(extraDataJSONObject.getLong("fileEntryId"));

WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= fileEntry.isInTrash() && (socialActivity.getType() == SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) && WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
		<portlet:actionURL name="/wiki/edit_page_attachment" var="restoreEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
			<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
			<portlet:param name="fileName" value="<%= fileEntry.getTitle() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="restore-attachment"
			url="<%= restoreEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= !fileEntry.isInTrash() && ((socialActivity.getType() == SocialActivityConstants.TYPE_ADD_ATTACHMENT) || (socialActivity.getType() == SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) && WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
			<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
			<portlet:param name="fileName" value="<%= fileEntry.getTitle() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message='<%= trashHelper.isTrashEnabled(scopeGroupId) ? "remove-attachment" : "delete-attachment" %>'
			trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>