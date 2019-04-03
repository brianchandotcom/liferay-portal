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

<liferay-ui:icon-menu
	direction="down"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
	triggerCssClass="btn btn-unstyled text-secondary"
>
	<c:if test="<%= editAssetListDisplayContext.getSegmentsEntryId() != SegmentsConstants.SEGMENTS_ENTRY_ID_DEFAULT %>">
		<portlet:actionURL name="/asset_list/delete_asset_list_entry_variation" var="deleteAssetListEntryVariationURL">
			<portlet:param name="redirect" value="<%= editAssetListDisplayContext.getRedirectURL() %>" />
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getAssetListEntryId()) %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getSegmentsEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAssetListEntryVariationURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>