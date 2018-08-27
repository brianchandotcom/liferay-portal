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

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="assetListEntries"
		searchContainer="<%= assetListDisplayContext.getAssetListEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.list.model.AssetListEntry"
			keyProperty="assetListEntryId"
			modelVar="assetListEntry"
		>
			<liferay-ui:search-container-column-icon
				icon="list"
				toggleRowChecker="<%= false %>"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5>
					<%= assetListEntry.getTitle() %>
				</h5>

				<h6 class="text-default">
					<strong><%= assetListDisplayContext.getAssetListEntryType(assetListEntry.getType()) %></strong>
				</h6>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>