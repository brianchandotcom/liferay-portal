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

<%@ include file="/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

long messageId = BeanParamUtil.getLong(message, request, "messageId");

MBBreadcrumbUtil.addPortletBreadcrumbEntries(message, request, renderResponse);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/edit_message");
portletURL.setParameter("messageId", String.valueOf(message.getMessageId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), portletURL.toString());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "removed-attachments"), currentURL);

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("mvcRenderCommandName", "/message_boards/view_deleted_message_attachments");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("messageId", String.valueOf(messageId));
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="removed-attachments"
/>

<portlet:actionURL name="/message_boards/edit_message_attachments" var="emptyTrashURL">
	<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
</portlet:actionURL>

<%
String trashEntriesMaxAgeTimeDescription = LanguageUtil.getTimeDescription(locale, trashHelper.getMaxAge(themeDisplay.getScopeGroup()) * Time.MINUTE, true);
%>

<liferay-trash:empty
	confirmMessage="are-you-sure-you-want-to-remove-the-attachments-for-this-message"
	emptyMessage="remove-the-attachments-for-this-message"
	infoMessage='<%= LanguageUtil.format(request, "attachments-that-have-been-removed-for-more-than-x-will-be-automatically-deleted", trashEntriesMaxAgeTimeDescription, false) %>'
	portletURL="<%= emptyTrashURL.toString() %>"
	totalEntries="<%= message.getDeletedAttachmentsFileEntriesCount() %>"
/>

<liferay-ui:search-container
	emptyResultsMessage="this-message-does-not-have-file-attachments-in-the-recycle-bin"
	iteratorURL="<%= iteratorURL %>"
	total="<%= message.getDeletedAttachmentsFileEntriesCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= message.getDeletedAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.repository.model.FileEntry"
		escapedModel="<%= true %>"
		keyProperty="fileEntryId"
		modelVar="fileEntry"
	>

		<%
		String rowHREF = PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, "status=" + WorkflowConstants.STATUS_IN_TRASH);
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="file-name"
		>

			<%
			AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

			AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());
			%>

			<liferay-ui:icon
				icon="<%= assetRenderer.getIconCssClass() %>"
				label="<%= true %>"
				markupView="lexicon"
				message="<%= trashHelper.getOriginalTitle(fileEntry.getTitle()) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="size"
			value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/message_boards/deleted_message_attachment_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>