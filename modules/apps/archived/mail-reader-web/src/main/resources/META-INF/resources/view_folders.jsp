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
long accountId = ParamUtil.getLong(request, "accountId");

MailManager mailManager = MailManager.getInstance(request);
%>

<c:if test="<%= mailManager != null %>">
	<div class="controls-list well">
		<liferay-ui:icon-menu
			direction="left-side"
			icon="<%= StringPool.BLANK %>"
			markupView="lexicon"
			message="<%= StringPool.BLANK %>"
			showWhenSingleIcon="<%= true %>"
		>

			<%
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"messageId", 0
			).put(
				"messageType", "new"
			).put(
				"replyMessageId", 0
			).build();
			%>

			<liferay-ui:icon
				cssClass="compose-message"
				data="<%= data %>"
				icon="envelope"
				message="compose"
				url="javascript:void(0);"
			/>

			<%
			Account mailAccount = AccountLocalServiceUtil.getAccount(accountId);

			for (Folder folder : mailManager.getFolders(accountId, true, true)) {
				String folderIcon = "icon-folder-open";

				if (folder.getFolderId() == mailAccount.getInboxFolderId()) {
					folderIcon = "icon-inbox";
				}
				else if (folder.getFolderId() == mailAccount.getDraftFolderId()) {
					folderIcon = "icon-pencil";
				}
				else if (folder.getFolderId() == mailAccount.getSentFolderId()) {
					folderIcon = "icon-folder-close";
				}
				else if (folder.getFolderId() == mailAccount.getTrashFolderId()) {
					folderIcon = "icon-trash";
				}
			%>

				<liferay-ui:icon
					cssClass="messages-link"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"accountId", accountId
						).put(
							"folderId", folder.getFolderId()
						).put(
							"keywords", ""
						).put(
							"orderByField", MailConstants.ORDER_BY_SENT_DATE
						).put(
							"orderByType", "desc"
						).put(
							"pageNumber", 1
						).build()
					%>'
					icon="<%= folderIcon %>"
					message='<%= folder.getDisplayName() + " (" + MessageLocalServiceUtil.getFolderUnreadMessagesCount(folder.getFolderId()) + ")" %>'
					url="javascript:void(0);"
				/>

			<%
			}
			%>

			<liferay-ui:icon
				cssClass="manage-folders"
				data="<%= data %>"
				icon="cogs"
				message="manage-folders"
				url="javascript:void(0);"
			/>

			<liferay-ui:icon
				cssClass="edit-account"
				data="<%= data %>"
				icon="cog"
				message="edit-account"
				url="javascript:void(0);"
			/>
		</liferay-ui:icon-menu>
	</div>
</c:if>