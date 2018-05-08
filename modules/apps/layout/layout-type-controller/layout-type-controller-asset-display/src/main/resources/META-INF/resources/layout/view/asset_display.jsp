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

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(fragmentEntryLinks) %>">

		<%
		Map<String, Object> fieldValues = assetDisplayLayoutTypeControllerDisplayContext.getAssetDisplayFieldsValues();

		StringBundler sb = new StringBundler(fragmentEntryLinks.size());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			sb.append(FragmentEntryRenderUtil.renderFragmentEntryLink(fragmentEntryLink, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE, fieldValues, request, response));
		}
		%>

		<%= sb.toString() %>
	</c:when>
	<c:otherwise>
		<div class="sheet">
			<div class="sheet-header">
				<h2 class="sheet-title">
					<%= assetEntry.getTitle(locale) %>
				</h2>

				<div class="sheet-text">
					<%= assetEntry.getDescription(locale) %>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />