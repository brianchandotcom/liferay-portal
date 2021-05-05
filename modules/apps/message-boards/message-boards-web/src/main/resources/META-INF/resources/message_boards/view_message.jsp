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
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE_DISPLAY);

MBMessage message = messageDisplay.getMessage();

MBThread thread = messageDisplay.getThread();

AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(MBMessage.class.getName(), message.getMessageId());

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);

AssetEntryServiceUtil.incrementViewCounter(layoutAssetEntry);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

MBBreadcrumbUtil.addPortletBreadcrumbEntries(message, request, renderResponse);
%>

<liferay-editor:resources
	editorName="<%= MBUtil.getEditorName(messageFormat) %>"
/>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid container-fluid-max-xl\"" : StringPool.BLANK %>>
	<c:if test="<%= !portletTitleBasedNavigation %>">

		<%
		String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName", "/message_boards/view");
		%>

		<%@ include file="/message_boards/nav.jspf" %>
	</c:if>

	<div <%= !portletTitleBasedNavigation ? "class=\"main-content-body\"" : StringPool.BLANK %>>
		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showParentGroups="<%= false %>"
			/>
		</c:if>

		<liferay-util:include page="/message_boards/view_message_content.jsp" servletContext="<%= application %>" />
	</div>
</div>

<aui:script require="frontend-js-web/liferay/util/run_scripts_in_element.es as runScriptsInElement">
	window['<portlet:namespace />addReplyToMessage'] = function (messageId, quote) {
		var addQuickReplyContainer = document.querySelector(
			'#<portlet:namespace />addReplyToMessage' + messageId + ' .panel'
		);

		if (addQuickReplyContainer) {
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/message_boards/get_edit_message_quick" var="editMessageQuickURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:resourceURL>

			var editMessageQuickURL = Liferay.Util.addParams(
				'<portlet:namespace />messageId=' + messageId,
				'<%= editMessageQuickURL.toString() %>'
			);

			if (quote) {
				editMessageQuickURL = Liferay.Util.addParams(
					'<portlet:namespace />quote=true',
					editMessageQuickURL
				);
			}

			var addQuickReplyLoadingMask = document.querySelector(
				'#<portlet:namespace />addReplyToMessage' +
					messageId +
					' .loading-animation'
			);

			addQuickReplyContainer.classList.add('hide');
			addQuickReplyLoadingMask.classList.remove('hide');

			Liferay.Util.fetch(editMessageQuickURL)
				.then((response) => {
					return response.text();
				})
				.then((response) => {
					addQuickReplyContainer.innerHTML = response;

					runScriptsInElement.default(addQuickReplyContainer);

					addQuickReplyContainer.classList.remove('hide');
					addQuickReplyLoadingMask.classList.add('hide');

					var parentMessageIdInput = addQuickReplyContainer.querySelector(
						'#<portlet:namespace />parentMessageId'
					);

					if (parentMessageIdInput) {
						parentMessageIdInput.value = messageId;
					}

					var editorName =
						'<portlet:namespace />replyMessageBody' + messageId;

					Liferay.componentReady(editorName).then((editor) => {
						editor.focus();
					});

					if (addQuickReplyContainer) {
						addQuickReplyContainer.scrollIntoView(true);
					}

					Liferay.Util.toggleDisabled(
						'#<portlet:namespace />replyMessageButton' + messageId,
						true
					);
				});
		}
	};
</aui:script>

<aui:script>
	function <portlet:namespace />hideReplyMessage(messageId) {
		var addQuickReplyContainer = document.querySelector(
			'#<portlet:namespace />addReplyToMessage' + messageId + ' .panel'
		);

		if (addQuickReplyContainer) {
			addQuickReplyContainer.classList.add('hide');
		}

		Liferay.Util.toggleDisabled(
			'#<portlet:namespace />replyMessageButton' + messageId,
			false
		);
	}

	<c:if test="<%= thread.getRootMessageId() != message.getMessageId() %>">
		var message = document.getElementById(
			'<portlet:namespace />message_' + <%= message.getMessageId() %>
		);

		if (message) {
			message.scrollIntoView(true);
		}
	</c:if>
</aui:script>

<%
MBThreadFlagLocalServiceUtil.addThreadFlag(themeDisplay.getUserId(), thread, new ServiceContext());

PortalUtil.setPageSubtitle(message.getSubject(), request);
PortalUtil.setPageDescription(message.getSubject(), request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(MBMessage.class.getName(), message.getMessageId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
%>