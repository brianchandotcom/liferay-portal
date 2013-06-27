<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_tags_navigation/init.jsp" %>

<%
long portletDisplayDDMTemplateId = PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplateId(themeDisplay, displayStyle);
%>

<c:choose>
	<c:when test="<%= portletDisplayDDMTemplateId > 0 %>">

		<%
		List<AssetTag> assetTags = null;

		if (classNameId > 0) {
			assetTags = AssetTagServiceUtil.getTags(scopeGroupId, classNameId, null, 0, maxAssetTags, new AssetTagCountComparator());
		}
		else {
			assetTags = AssetTagServiceUtil.getGroupTags(themeDisplay.getSiteGroupId(), 0, maxAssetTags, new AssetTagCountComparator());
		}

		assetTags = ListUtil.sort(assetTags);

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		contextObjects.put("scopeGroupId", new Long(scopeGroupId));
		%>

		<%= PortletDisplayTemplateUtil.renderDDMTemplate(pageContext, portletDisplayDDMTemplateId, assetTags, contextObjects) %>
	</c:when>
	<c:otherwise>
		<liferay-ui:asset-tags-navigation
			classNameId="<%= classNameId %>"
			displayStyle="<%= displayStyle %>"
			hidePortletWhenEmpty="<%= true %>"
			maxAssetTags="<%= maxAssetTags %>"
			showAssetCount="<%= showAssetCount %>"
			showZeroAssetCount="<%= showZeroAssetCount %>"
		/>
	</c:otherwise>
</c:choose>