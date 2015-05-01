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

<%@ include file="/html/taglib/ui/entry_selector/init.jsp" %>

<div class="lfr-entry-selector" id="<%= namespace + id %>entrySelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" value='<%= ListUtil.toString(selectedEntries, "key") %>' />

	<ul class="list-inline list-unstyled selected-entries">

		<%
		for (SelectableEntry entry : selectedEntries) {
		%>

			<li class="list-entry" data-key="<%= entry.getKey() %>">
				<span class="label label-circle label-entry">
					<%= entry.getLabel(locale) %>

					<button class="remove-button" type="button">
						<i class="icon-remove"></i>
					</button>
				</span>
			</li>

		<%
		}
		%>

	</ul>

	<aui:button cssClass="select-button" name='<%= id + "selectButton" %>' value="select" />
</div>

<aui:script use="liferay-entry-selector">
	var entries = [];

	<%
	for (SelectableEntry entry : entries) {
	%>

		entries.push(
			{
				icon: '<%= entry.getIcon() %>',
				key: '<%= entry.getKey() %>',
				label: '<%= entry.getLabel(locale) %>'
	}
		);

	<%
	}
	%>

	var selectedEntries = [];

	<%
	for (SelectableEntry entry : selectedEntries) {
	%>

		selectedEntries.push('<%= entry.getKey() %>');

	<%
	}
	%>

	var selector = new Liferay.EntrySelector(
		{
			dialogTitle: '<liferay-ui:message key="<%= title %>" />',
			entries: entries,
			rootNode: '#<%= namespace + id %>entrySelector',
			selectedEntries: selectedEntries
		}
	);
</aui:script>