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
String redirect = ParamUtil.getString(request, "redirect");

WebhookEntry webhookEntry = (WebhookEntry)request.getAttribute(WebhookWebKeys.WEBHOOK_ENTRY);

long webhookEntryId = BeanParamUtil.getLong(webhookEntry, request, "webhookEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((webhookEntry == null) ? LanguageUtil.get(request, "add-webhook") : webhookEntry.getName());

EditWebhookEntryDisplayContext editWebhookEntryDisplayContext = (EditWebhookEntryDisplayContext)renderRequest.getAttribute(WebhookWebKeys.EDIT_WEBHOOK_ENTRY_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/webhook/edit_webhook_entry" var="editWebhookEntryURL" />

<clay:container-fluid>
	<aui:form action="<%= editWebhookEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveWebhookEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="webhookEntryId" type="hidden" value="<%= webhookEntryId %>" />

		<liferay-ui:error exception="<%= DuplicateWebhookEntryException.class %>" />
		<liferay-ui:error exception="<%= WebhookEntryDestinationNameException.class %>" />
		<liferay-ui:error exception="<%= WebhookEntryDestinationWebhookEventKeysException.class %>" />
		<liferay-ui:error exception="<%= WebhookEntryNameException.class %>" />
		<liferay-ui:error exception="<%= WebhookEntryURLException.class %>" />

		<aui:model-context bean="<%= webhookEntry %>" model="<%= WebhookEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:select label="destination-name" name="destinationName" onChange='<%= liferayPortletResponse.getNamespace() + "resetDestinationWebhookEventKeys();" %>'>

					<%
					for (String destinationName : editWebhookEntryDisplayContext.getDestinationNames()) {
					%>

						<aui:option label="<%= destinationName %>" selected="<%= (webhookEntry != null) && (webhookEntry.getDestinationName() == destinationName) %>" value="<%= destinationName %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="destination-webhook-events" name="destinationWebhookEventKeysFieldWrapper">
					<aui:input name="destinationWebhookEventKeys" type="hidden" value="<%= (webhookEntry == null) ? WebhookConstants.DESTINATION_WEBHOOK_EVENT_KEYS_ALL : webhookEntry.getDestinationWebhookEventKeys() %>" />

					<aui:input id="allDestinationWebhookEventKeysToggle" label="" labelOff="selected-only" labelOn="all" name="allDestinationWebhookEventKeysToggle" onChange='<%= liferayPortletResponse.getNamespace() + "toggleDestinationWebhookEventKeys();" %>' type="toggle-switch" value="<%= editWebhookEntryDisplayContext.isAllDestinationWebhookKeys() %>" />

					<aui:button-row>
						<aui:button cssClass='<%= editWebhookEntryDisplayContext.isAllDestinationWebhookKeys() ? "hide" : "" %>' name="selectDestinationWebhookEventKeysButton" value="select" />
					</aui:button-row>
				</aui:field-wrapper>

				<aui:input name="URL" />

				<aui:input name="secret" />

				<aui:field-wrapper cssClass="form-group lfr-input-text-container">
					<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= (webhookEntry != null) && webhookEntry.isActive() %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />resetDestinationWebhookEventKeys() {
		const allDestinationWebhookEventKeysToggle = document.getElementById(
			'<portlet:namespace />allDestinationWebhookEventKeysToggle'
		);

		if (allDestinationWebhookEventKeysToggle) {
			allDestinationWebhookEventKeysToggle.checked = true;

			<portlet:namespace />toggleDestinationWebhookEventKeys();
		}
	}

	function <portlet:namespace />saveWebhookEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (webhookEntry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />toggleDestinationWebhookEventKeys() {
		const destinationWebhookEventKeys = document.getElementById(
			'<portlet:namespace />destinationWebhookEventKeys'
		);

		const allDestinationWebhookEventKeysToggle = document.getElementById(
			'<portlet:namespace />allDestinationWebhookEventKeysToggle'
		);

		const selectDestinationWebhookEventKeysButton = document.getElementById(
			'<portlet:namespace />selectDestinationWebhookEventKeysButton'
		);

		if (
			destinationWebhookEventKeys &&
			allDestinationWebhookEventKeysToggle &&
			selectDestinationWebhookEventKeysButton
		) {
			if (allDestinationWebhookEventKeysToggle.checked) {
				selectDestinationWebhookEventKeysButton.classList.add('hide');
				destinationWebhookEventKeys.value =
					'<%= WebhookConstants.DESTINATION_WEBHOOK_EVENT_KEYS_ALL %>';
			}
			else {
				selectDestinationWebhookEventKeysButton.classList.remove('hide');
			}
		}
	}
</aui:script>

<aui:script sandbox="<%= true %>">
	const destinationName = document.getElementById(
		'<portlet:namespace />destinationName'
	);

	const destinationWebhookEventKeys = document.getElementById(
		'<portlet:namespace />destinationWebhookEventKeys'
	);

	const selectDestinationWebhookEventKeysButton = document.getElementById(
		'<portlet:namespace />selectDestinationWebhookEventKeysButton'
	);

	if (
		destinationName &&
		destinationWebhookEventKeys &&
		selectDestinationWebhookEventKeysButton
	) {
		selectDestinationWebhookEventKeysButton.addEventListener(
			'click',
			(event) => {
				event.preventDefault();

				var uri =
					'<%= editWebhookEntryDisplayContext.getDestinationWebhookEventKeysSelectorUrl() %>';

				uri = Liferay.Util.addParams(
					'<%= PortalUtil.getPortletNamespace(WebhookPortletKeys.WEBHOOK) %>destinationName=' +
						destinationName.value,
					uri
				);

				uri = Liferay.Util.addParams(
					'<%= PortalUtil.getPortletNamespace(WebhookPortletKeys.WEBHOOK) %>destinationWebHookEventKeys=' +
						destinationWebhookEventKeys.value,
					uri
				);

				Liferay.Util.openSelectionModal({
					multiple: true,
					onSelect: function (selectedItems) {
						if (selectedItems && selectedItems.length) {
							var selectedDestinationWebhookEventKeys = [];

							Array.prototype.forEach.call(
								selectedItems,
								(selectedDestinationWebhookEventKey) => {
									selectedDestinationWebhookEventKeys.push(
										selectedDestinationWebhookEventKey
									);
								}
							);

							destinationWebhookEventKeys.value = selectedDestinationWebhookEventKeys.join(
								','
							);
						}
					},
					selectEventName:
						'<%= editWebhookEntryDisplayContext.getItemSelectedEventName() %>',
					title:
						'<liferay-ui:message key="select-destination-webhook-events" />',
					url: uri,
				});
			}
		);
	}
</aui:script>