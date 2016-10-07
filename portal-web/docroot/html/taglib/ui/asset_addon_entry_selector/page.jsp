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

<%@ include file="/html/taglib/ui/asset_addon_entry_selector/init.jsp" %>

<div class="lfr-asset-addon-entry-selector" id="<%= namespace + id %>assetAddonEntrySelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" value='<%= ListUtil.toString(selectedAssetAddonEntries, "key") %>' />

	<ul class="list-inline list-unstyled selected-entries">

		<%
		for (AssetAddonEntry assetAddonEntry : selectedAssetAddonEntries) {
		%>

			<li class="list-entry" data-key="<%= assetAddonEntry.getKey() %>" data-label="<%= assetAddonEntry.getLabel(locale) %>">
				<span class="label label-default">
					<%= assetAddonEntry.getLabel(locale) %>

					<aui:a cssClass="remove-button" href="javascript:;">
						<aui:icon image="times" markupView="lexicon" />
					</aui:a>
				</span>
			</li>

		<%
		}
		%>

	</ul>

	<aui:button cssClass="select-button" name='<%= id + "selectButton" %>' value="select" />
</div>

<aui:script use="liferay-asset-addon-entry-selector">
	var assetAddonEntries = [];

	<%
	for (AssetAddonEntry assetAddonEntry : assetAddonEntries) {
	%>

		assetAddonEntries.push(
			{
				icon: '<%= assetAddonEntry.getIcon() %>',
				key: '<%= assetAddonEntry.getKey() %>',
				label: '<%= assetAddonEntry.getLabel(locale) %>'
			}
		);

	<%
	}
	%>

	var selectedAssetAddonEntries = [];

	<%
	for (AssetAddonEntry assetAddonEntry : selectedAssetAddonEntries) {
	%>

		selectedAssetAddonEntries.push('<%= assetAddonEntry.getKey() %>');

	<%
	}
	%>

	var assetAddonEntrySelector = new Liferay.AssetAddonEntrySelector(
		{
			assetAddonEntries: assetAddonEntries,
			dialogTitle: '<liferay-ui:message key="<%= title %>" />',
			rootNode: '#<%= namespace + id %>assetAddonEntrySelector',
			selectedAssetAddonEntries: selectedAssetAddonEntries
		}
	);
</aui:script>