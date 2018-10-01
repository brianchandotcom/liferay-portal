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
			<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
		</div>
	</div>

	<aui:input id="assetListEntryId" name="preferences--assetListEntryId--" type="hidden" value="<%= (assetListEntry != null) ? assetListEntry.getAssetListEntryId() : StringPool.BLANK %>" />
</div>

<aui:button name="selectAssetList" value="select" />

<aui:script use="liferay-item-selector-dialog">
	var assetListEntryId = A.one('#<portlet:namespace />assetListEntryId');
	var assetListRemove = A.one('#<portlet:namespace />assetListRemove');
	var assetListTitle = A.one('#<portlet:namespace />assetListTitle');

	A.one('#<portlet:namespace />selectAssetList').on(
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
					title: '<liferay-ui:message key="select-asset-list" />',
					uri: '<%= assetPublisherDisplayContext.getAssetListSelectorURL() %>'
				},
				function(event) {
					assetListEntryId.val(event.assetlistentryid);

					assetListTitle.html(event.assetlistentrytitle);

					assetListRemove.removeClass('hide');
				}
			);
		}
	);

	assetListRemove.on(
		'click',
		function(event) {
			assetListTitle.html('<liferay-ui:message key="none" />');

			assetListEntryId.val('');

			assetListRemove.addClass('hide');
		}
	);
</aui:script>