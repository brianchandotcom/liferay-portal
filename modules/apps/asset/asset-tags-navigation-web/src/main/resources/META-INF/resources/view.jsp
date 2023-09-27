<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetTagsNavigationDisplayContext assetTagsNavigationDisplayContext = (AssetTagsNavigationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-ddm:template-renderer
	className="<%= AssetTag.class.getName() %>"
	contextObjects='<%=
		HashMapBuilder.<String, Object>put(
			"assetTagsNavigationDisplayContext", assetTagsNavigationDisplayContext
		).put(
			"scopeGroupId", Long.valueOf(scopeGroupId)
		).build()
	%>'
	displayStyle="<%= displayStyle %>"
	displayStyleGroupId="<%= displayStyleGroupId %>"
	entries="<%= assetTagsNavigationDisplayContext.getAssetTags() %>"
>
	<liferay-asset:asset-tags-navigation
		classNameId="<%= classNameId %>"
		displayStyle="<%= displayStyle %>"
		hidePortletWhenEmpty="<%= true %>"
		maxAssetTags="<%= maxAssetTags %>"
		showAssetCount="<%= showAssetCount %>"
		showZeroAssetCount="<%= showZeroAssetCount %>"
	/>
</liferay-ddm:template-renderer>