<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String className = ParamUtil.getString(request, "className", Layout.class.getName());
long classPK = ParamUtil.getLong(request, "classPK", layout.getPlid());

AssetEntry assetEntry = (AssetEntry)request.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY);
AssetRenderer<?> assetRenderer = null;

if (assetEntry != null) {
	assetRenderer = assetEntry.getAssetRenderer();

	if (layout.isTypeAssetDisplay()) {
		className = assetEntry.getClassName();
		classPK = assetEntry.getClassPK();
	}
}
%>

<c:if test="<%= LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.VIEW) && ((assetRenderer == null) || assetRenderer.isCommentable()) %>">
	<liferay-comment:discussion
		className="<%= className %>"
		classPK="<%= classPK %>"
		formName="fm"
		redirect="<%= currentURL %>"
		userId="<%= user.getUserId() %>"
	/>
</c:if>