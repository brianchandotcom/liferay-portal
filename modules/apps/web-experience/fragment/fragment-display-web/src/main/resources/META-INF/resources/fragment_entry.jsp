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
FragmentEntry fragmentEntry = fragmentEntryDisplayContext.getFragmentEntry();
%>

<aui:input id="fragmentEntryId" name="fragmentEntryId" type="hidden" value='<%= Validator.isNull(fragmentEntry) ? "0" : fragmentEntry.getFragmentEntryId() %>' />

<div class="fragment-entry-preview row">
	<div class="col-md-3 col-sm-6 col-xs-12">
		<p class="text-muted"><liferay-ui:message key="fragment-entry" /></p>

		<div class="fragment-entry-preview-container">
			<c:if test="<%= fragmentEntry != null %>">
				<div class="fragment-entry-preview row">
					<div class="col-md-8 col-sm-6 col-xs-12">
						<liferay-util:include page="/fragment_entry_preview.jsp" servletContext="<%= application %>" />
					</div>
				</div>
			</c:if>
		</div>

		<div class="button-holder">
			<aui:button cssClass="fragment-entry-selector" name="fragmentEntrySelector" value='<%= Validator.isNull(fragmentEntry) ? "select" : "change" %>' />

			<c:if test="<%= fragmentEntry != null %>">
				<aui:button name="removeFragmentEntry" value="remove" />
			</c:if>
		</div>
	</div>
</div>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:script use="liferay-item-selector-dialog">
	var fragmentEntryId = $('#<portlet:namespace/>fragmentEntryId');

	$('#<portlet:namespace />fragmentEntrySelector').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<%= fragmentEntryDisplayContext.getEventName() %>',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								fragmentEntryId.val(selectedItem.fragmentEntryId);

								retrieveFragmentEntry(selectedItem.fragmentEntryId);
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-fragment-entry" />',
					url: '<%= fragmentEntryDisplayContext.getItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace/>removeFragmentEntry').on(
		'click',
		function() {
			retrieveFragmentEntry(-1);
		}
	);

	function retrieveFragmentEntry(fragmentEntryId) {
		var uri = '<%= configurationRenderURL %>';

		uri = Liferay.Util.addParams('<portlet:namespace />fragmentEntryId=' + fragmentEntryId, uri);

		location.href = uri;
	}
</aui:script>