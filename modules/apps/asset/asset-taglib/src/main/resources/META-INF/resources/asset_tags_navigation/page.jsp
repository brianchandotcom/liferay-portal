<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_tags_navigation/init.jsp" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-tags-navigation:classNameId"));
String displayStyle = (String)request.getAttribute("liferay-asset:asset-tags-navigation:displayStyle");
boolean hidePortletWhenEmpty = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-navigation:hidePortletWhenEmpty"));
int maxAssetTags = GetterUtil.getInteger((String)request.getAttribute("liferay-asset:asset-tags-navigation:maxAssetTags"));
boolean showAssetCount = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-navigation:showAssetCount"));
boolean showZeroAssetCount = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-navigation:showZeroAssetCount"));

String tag = ParamUtil.getString(request, "tag");

AssetTagsNavigationDisplayContext assetTagsNavigationDisplayContext = new AssetTagsNavigationDisplayContext();

String tagsNavigation = assetTagsNavigationDisplayContext.buildTagsNavigation(scopeGroupId, tag, classNameId, displayStyle, maxAssetTags, renderResponse, showAssetCount, showZeroAssetCount);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(tagsNavigation) %>">
		<%= tagsNavigation %>
	</c:when>
	<c:otherwise>

		<%
		if (hidePortletWhenEmpty) {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
		%>

		<clay:alert
			displayType="info"
			message="there-are-no-tags"
		/>
	</c:otherwise>
</c:choose>

<%
if (Validator.isNotNull(tag)) {
	PortalUtil.addPortletBreadcrumbEntry(request, tag, currentURL, null, false);
}
%>