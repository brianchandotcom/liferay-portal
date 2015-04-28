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

<%@ include file="/html/taglib/init.jsp" %>

<%
List<SelectableEntry> entries = (List<SelectableEntry>)request.getAttribute("liferay-ui:entry-selector:entries");
String hiddenInput = (String)request.getAttribute("liferay-ui:entry-selector:hiddenInput");
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:entry-selector:id"));
List<SelectableEntry> selectedEntries = (List<SelectableEntry>)request.getAttribute("liferay-ui:entry-selector:selectedEntries");
String title = GetterUtil.getString((String)request.getAttribute("liferay-ui:entry-selector:title"));
%>

<div class="lfr-entry-selector" id="<%= namespace + id %>entrySelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" value='<%= ListUtil.toString(selectedEntries, "key") %>' />

	<ul class="list-inline list-unstyled selected-entries">

		<%
		for (SelectableEntry entry : selectedEntries) {
		%>

			<li class="list-entry" data-key="<%= entry.getKey() %>">
				<span class="label label-circle label-entry">
					<liferay-ui:message key="<%= entry.getLabel() %>" />

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
				label: '<%= LanguageUtil.get(request, entry.getLabel()) %>'
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
			dialogTitle: '<%= LanguageUtil.get(request, title) %>',
			entries: entries,
			rootNode: '#<%= namespace + id %>entrySelector',
			selectedEntries: selectedEntries
		}
	);
</aui:script>