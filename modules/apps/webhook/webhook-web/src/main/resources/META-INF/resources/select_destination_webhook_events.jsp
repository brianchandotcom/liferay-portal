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
SelectDestinationWebhookEventsDisplayContext selectDestinationWebhookEventsDisplayContext = (SelectDestinationWebhookEventsDisplayContext)request.getAttribute(WebhookWebKeys.SELECT_DESTINATION_WEBHOOK_EVENTS_DISPLAY_CONTEXT);
%>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />destinationWebhookEventsWrapper">
	<liferay-ui:search-container
		id="destinationWebhookEvents"
		searchContainer="<%= selectDestinationWebhookEventsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.messaging.Destination.WebhookEvent"
			keyProperty="key"
			modelVar="webhookEvent"
			rowIdProperty="key"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="name"
				value="<%= LanguageUtil.get(request, webhookEvent.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="description"
				value="<%= LanguageUtil.get(request, webhookEvent.getDescription()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />destinationWebhookEvents'
	);

	searchContainer.on('rowToggled', (event) => {
		var selectedItems = event.elements.allSelectedElements;

		var data = [];

		if (selectedItems.size() > 0) {
			data = selectedItems.attr('value');
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(selectDestinationWebhookEventsDisplayContext.getItemSelectedEventName()) %>',
			{
				data: data,
			}
		);
	});
</aui:script>