<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditCommerceOrderAttachmentDisplayContext editCommerceOrderAttachmentDisplayContext = (EditCommerceOrderAttachmentDisplayContext)request.getAttribute(EditCommerceOrderAttachmentDisplayContext.class.getName());

long attachmentId = editCommerceOrderAttachmentDisplayContext.getAttachmentId();
long fileEntryId = editCommerceOrderAttachmentDisplayContext.getFileEntryId();
String mode = editCommerceOrderAttachmentDisplayContext.getMode();
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, Objects.equals(mode, "edit") ? "edit-attachment" : "add-attachment") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= liferayPortletResponse.getNamespace() + "editCommerceOrderAttachment(event, this.form)" %>'>
		<aui:input name="attachmentId" type="hidden" value="<%= attachmentId %>" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "file") %>'
		>
			<aui:input label="file" name="attachmentFile" required='<%= !Objects.equals(mode, "edit") %>' type="file" />

			<c:if test="<%= fileEntryId > 0 %>">
				<div class="text-secondary">
					<liferay-ui:message key="current-file" />: <%= HtmlUtil.escape(editCommerceOrderAttachmentDisplayContext.getFileEntryName()) %>
				</div>
			</c:if>
		</commerce-ui:panel>

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<aui:input label="title" name="title" required="<%= true %>" value="<%= editCommerceOrderAttachmentDisplayContext.getTitle() %>" />

			<aui:select label="type" name="type" required="<%= true %>">
				<aui:option label="" selected="<%= Validator.isNull(editCommerceOrderAttachmentDisplayContext.getType()) %>" value="" />

				<%
				for (ListTypeEntry listTypeEntry : editCommerceOrderAttachmentDisplayContext.getAttachmentTypes()) {
				%>

					<aui:option label="<%= HtmlUtil.escape(listTypeEntry.getName(locale)) %>" selected="<%= Objects.equals(listTypeEntry.getKey(), editCommerceOrderAttachmentDisplayContext.getType()) %>" value="<%= HtmlUtil.escape(listTypeEntry.getKey()) %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input name="priority" type="text" value="<%= editCommerceOrderAttachmentDisplayContext.getPriority() %>" />

			<c:if test="<%= editCommerceOrderAttachmentDisplayContext.canViewRestrictedCommerceOrderAttachments() %>">
				<aui:input checked="<%= editCommerceOrderAttachmentDisplayContext.getRestricted() %>" label="restricted" name="restricted" type="checkbox" />
			</c:if>
		</commerce-ui:panel>

		<div class="dialog-footer">
			<clay:button
				data-qa-id="cancelButton"
				displayType="secondary"
				label="cancel"
				onClick="window.top.Liferay.fire('close-side-panel');"
			/>

			<clay:button
				data-qa-id="saveButton"
				displayType="primary"
				label="save"
				type="submit"
			/>
		</div>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"addURL", editCommerceOrderAttachmentDisplayContext.getAddURL()
				).put(
					"attachmentId", attachmentId
				).put(
					"commerceOrderId", editCommerceOrderAttachmentDisplayContext.getCommerceOrderId()
				).put(
					"editURL", editCommerceOrderAttachmentDisplayContext.getEditURL()
				).put(
					"fdsId", CommerceOrderFDSNames.ATTACHMENTS
				).put(
					"mode", mode
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
			module="{editCommerceOrderAttachment} from commerce-order-web"
		/>
	</aui:form>
</liferay-frontend:side-panel-content>