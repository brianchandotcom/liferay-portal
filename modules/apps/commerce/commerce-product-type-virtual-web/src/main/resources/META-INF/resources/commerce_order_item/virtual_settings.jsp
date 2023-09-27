<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceVirtualOrderItemEditDisplayContext commerceVirtualOrderItemEditDisplayContext = (CommerceVirtualOrderItemEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceVirtualOrderItemEditDisplayContext.getCommerceOrder();
CommerceVirtualOrderItem commerceVirtualOrderItem = commerceVirtualOrderItemEditDisplayContext.getCommerceVirtualOrderItem();

FileEntry fileEntry = commerceVirtualOrderItemEditDisplayContext.getFileEntry();

long fileEntryId = BeanParamUtil.getLong(commerceVirtualOrderItem, request, "fileEntryId");

String textCssClass = "text-default ";

boolean useFileEntry = false;

if (fileEntryId > 0) {
	textCssClass += "hide";

	useFileEntry = true;
}

long durationDays = 0;

if ((commerceVirtualOrderItem != null) && (commerceVirtualOrderItem.getDuration() > 0)) {
	durationDays = commerceVirtualOrderItem.getDuration() / Time.DAY;
}
%>

<portlet:actionURL name="/commerce_order/edit_commerce_virtual_order_item" var="editCommerceVirtualOrderItemActionURL" />

<aui:form action="<%= editCommerceVirtualOrderItemActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />
	<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceVirtualOrderItem.getCommerceOrderItemId() %>" />
	<aui:input name="commerceVirtualOrderItemId" type="hidden" value="<%= commerceVirtualOrderItem.getCommerceVirtualOrderItemId() %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= (commerceVirtualOrderItem == null) ? StringPool.BLANK : commerceVirtualOrderItem.getFileEntryId() %>" />

	<aui:model-context bean="<%= commerceVirtualOrderItem %>" model="<%= CommerceVirtualOrderItem.class %>" />

	<liferay-ui:error exception="<%= CommerceVirtualOrderItemException.class %>" message="please-enter-a-valid-url-or-select-an-existing-file" />
	<liferay-ui:error exception="<%= CommerceVirtualOrderItemFileEntryIdException.class %>" message="please-select-an-existing-file" />
	<liferay-ui:error exception="<%= CommerceVirtualOrderItemUrlException.class %>" message="please-enter-a-valid-url" />

	<div class="sheet">
		<div class="panel-group panel-group-flush">
			<aui:fieldset>
				<div class="row">
					<div class="col-md-3">
						<h4 class="text-default"><liferay-ui:message key="insert-the-url-or-select-a-file-of-your-virtual-product" /></h4>
					</div>

					<div class="col-md-9">
						<aui:input disabled="<%= useFileEntry %>" name="url" />

						<h4 class="<%= textCssClass %>" id="lfr-virtual-order-item-button-row-message"><liferay-ui:message key="or" /></h4>

						<p class="text-default">
							<span class="<%= (fileEntryId > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />fileEntryRemove" role="button">
								<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
							</span>
							<span id="<portlet:namespace />fileEntryNameInput">
								<c:choose>
									<c:when test="<%= fileEntry != null %>">
										<a href="<%= HtmlUtil.escapeHREF(commerceVirtualOrderItemEditDisplayContext.getDownloadFileEntryURL()) %>">
											<%= HtmlUtil.escape(fileEntry.getFileName()) %>
										</a>
									</c:when>
									<c:otherwise>
										<span class="text-muted"><liferay-ui:message key="none" /></span>
									</c:otherwise>
								</c:choose>
							</span>
						</p>

						<aui:button name="selectFile" value="select" />
					</div>
				</div>
			</aui:fieldset>

			<aui:fieldset>
				<aui:select name="activationStatus">

					<%
					for (int activationStatus : commerceVirtualOrderItemEditDisplayContext.getActivationStatuses()) {
					%>

						<aui:option label="<%= commerceVirtualOrderItemEditDisplayContext.getActivationStatusLabel(activationStatus) %>" selected="<%= (commerceVirtualOrderItem != null) && (activationStatus == commerceVirtualOrderItem.getActivationStatus()) %>" value="<%= activationStatus %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input helpMessage="duration-help" label="duration" name="durationDays" suffix="days" type="long" value="<%= durationDays %>">
					<aui:validator name="number" />
				</aui:input>

				<aui:input label="number-of-downloads" name="usages" />

				<aui:input label="max-number-of-downloads" name="maxUsages" />

				<aui:input name="active" />
			</aui:fieldset>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= String.valueOf(commerceVirtualOrderItemEditDisplayContext.getCommerceOrderItemsPortletURL()) %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>">
	const fileEntryNameInput = document.getElementById(
		'<portlet:namespace />fileEntryNameInput'
	);

	const fileEntryRemove = document.getElementById(
		'<portlet:namespace />fileEntryRemove'
	);

	const selectFile = document.getElementById('<portlet:namespace />selectFile');

	if (fileEntryNameInput && fileEntryRemove && selectFile) {
		selectFile.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: (selectedItem) => {
					if (!selectedItem) {
						return;
					}

					const value = JSON.parse(selectedItem.value);

					const fileEntryIdInput = document.getElementById(
						'<portlet:namespace />fileEntryId'
					);

					if (fileEntryIdInput) {
						fileEntryIdInput.value = value.fileEntryId;
					}

					const url = document.getElementById('<portlet:namespace />url');

					if (url) {
						url.setAttribute('disabled', true);
					}

					const message = document.getElementById(
						'lfr-virtual-order-item-button-row-message'
					);

					if (message) {
						message.classList.add('hide');
					}

					fileEntryRemove.classList.remove('hide');

					fileEntryNameInput.innerHTML =
						'<a>' + Liferay.Util.escape(value.title) + '</a>';
				},
				selectEventName: 'uploadCommerceVirtualOrderItem',
				title: '<liferay-ui:message key="select-file" />',
				url:
					'<%= commerceVirtualOrderItemEditDisplayContext.getFileEntryItemSelectorURL() %>',
			});
		});

		fileEntryRemove.addEventListener('click', (event) => {
			event.preventDefault();

			const fileEntryIdInput = document.getElementById(
				'<portlet:namespace />fileEntryId'
			);

			if (fileEntryIdInput) {
				fileEntryIdInput.value = 0;
			}

			const url = document.getElementById('<portlet:namespace />url');

			if (url) {
				url.setAttribute('disabled', false);
			}

			const message = document.getElementById(
				'lfr-virtual-order-item-button-row-message'
			);

			if (message) {
				message.classList.remove('hide');
			}

			fileEntryNameInput.innerText = '<liferay-ui:message key="none" />';

			fileEntryRemove.classList.add('hide');
		});
	}
</aui:script>