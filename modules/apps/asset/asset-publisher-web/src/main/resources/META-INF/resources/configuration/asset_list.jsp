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
AssetListEntry assetListEntry = assetPublisherDisplayContext.fetchAssetListEntry();
%>

<div class="form-group input-text-wrapper text-default">
	<div class="d-inline-block">
		<span id="<portlet:namespace />assetListTitle">
			<c:choose>
				<c:when test="<%= assetListEntry != null %>">
					<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
				</c:when>
				<c:otherwise>
					<span class="text-muted"><liferay-ui:message key="none" /></span>
				</c:otherwise>
			</c:choose>
		</span>

		<div class="d-inline-block <%= (assetListEntry == null) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />assetListRemove" role="button">
			<aui:icon cssClass="icon-monospaced" image="times-circle" markupView="lexicon" />
		</div>
	</div>

	<aui:input id="assetListEntryId" name="preferences--assetListEntryId--" type="hidden" value="<%= (assetListEntry != null) ? assetListEntry.getAssetListEntryId() : StringPool.BLANK %>" />
</div>

<aui:button name="selectAssetListButton" value="select" />

<script>
	var assetListEntryId = document.getElementById('<portlet:namespace />assetListEntryId');
	var assetListTitle = document.getElementById('<portlet:namespace />assetListTitle');

	var selectAssetListButton = document.getElementById('<portlet:namespace />selectAssetListButton');

	if (selectAssetListButton) {
		selectAssetListButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
						constrain: true,
						destroyOnHide: true
					},
						eventName: '<%= assetPublisherDisplayContext.getSelectAssetListEventName() %>',
						id: '<portlet:namespace />selectAssetList',
						title: '<liferay-ui:message key="select-content-set" />',
						uri: '<%= assetPublisherDisplayContext.getAssetListSelectorURL() %>'
					},
					function(event) {
						assetListEntryId.value = event.assetlistentryid;

						assetListTitle.innerHTML = event.assetlistentrytitle;

						assetListRemove.classList.remove('hide');
					}
				);
			}
		);
	}

	var assetListRemove = document.getElementById('<portlet:namespace />assetListRemove');

	if (assetListRemove) {
		assetListRemove.addEventListener(
			'click',
			function(event) {
				assetListTitle.innerHTML = '<liferay-ui:message key="none" />';

				assetListEntryId.value = '';

				assetListRemove.classList.add('hide');
			}
		);
	}
</script>