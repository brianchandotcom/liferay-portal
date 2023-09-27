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

String tagsNavigation = _buildTagsNavigation(scopeGroupId, tag, classNameId, displayStyle, maxAssetTags, renderResponse, showAssetCount, showZeroAssetCount);
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

<%!
private String _buildTagsNavigation(long scopeGroupId, String selectedTagName, long classNameId, String displayStyle, int maxAssetTags, RenderResponse renderResponse, boolean showAssetCount, boolean showZeroAssetCount) throws Exception {
	List<AssetTag> tags = null;

	if (showAssetCount && (classNameId > 0)) {
		tags = AssetTagServiceUtil.getTags(PortalUtil.getSiteGroupId(scopeGroupId), classNameId, null, 0, maxAssetTags, new AssetTagCountComparator());
	}
	else {
		tags = AssetTagServiceUtil.getGroupTags(PortalUtil.getSiteGroupId(scopeGroupId), 0, maxAssetTags, new AssetTagCountComparator());
	}

	if (tags.isEmpty()) {
		return null;
	}

	tags = ListUtil.sort(tags);

	StringBundler sb = new StringBundler();

	sb.append("<ul class=\"tag-items ");

	int maxCount = 1;
	int minCount = 1;

	if (displayStyle.equals("cloud")) {
		sb.append("tag-cloud");

		for (AssetTag tag : tags) {
			String tagName = tag.getName();

			int count = 0;

			if (classNameId > 0) {
				count = AssetTagServiceUtil.getVisibleAssetsTagsCount(scopeGroupId, classNameId, tagName);
			}
			else {
				count = AssetTagServiceUtil.getVisibleAssetsTagsCount(scopeGroupId, tagName);
			}

			if (!showZeroAssetCount && (count == 0)) {
				continue;
			}

			maxCount = Math.max(maxCount, count);
			minCount = Math.min(minCount, count);
		}
	}
	else {
		sb.append("tag-list");
	}

	sb.append("\">");

	double multiplier = 1;

	if (maxCount != minCount) {
		multiplier = (double)5 / (maxCount - minCount);
	}

	for (AssetTag tag : tags) {
		String tagName = tag.getName();

		int count = 0;

		if (classNameId > 0) {
			count = AssetTagServiceUtil.getVisibleAssetsTagsCount(scopeGroupId, classNameId, tagName);
		}
		else {
			count = AssetTagServiceUtil.getVisibleAssetsTagsCount(scopeGroupId, tagName);
		}

		int popularity = (int)(1 + ((maxCount - (maxCount - (count - minCount))) * multiplier));

		if (!showZeroAssetCount && (count == 0)) {
			continue;
		}

		sb.append("<li class=\"tag-popularity-");
		sb.append(popularity);
		sb.append("\"><span>");

		if (tagName.equals(selectedTagName)) {
			sb.append("<a class=\"tag-selected\" href=\"");

			PortletURL portletURL = PortletURLBuilder.createRenderURL(
				renderResponse
			).setParameter(
				"tag", StringPool.BLANK
			).buildPortletURL();

			sb.append(HtmlUtil.escape(portletURL.toString()));
		}
		else {
			sb.append("<a href=\"");

			PortletURL portletURL = PortletURLBuilder.createRenderURL(
				renderResponse
			).setParameter(
				"tag", tagName
			).buildPortletURL();

			sb.append(HtmlUtil.escape(portletURL.toString()));
		}

		sb.append("\">");
		sb.append(tagName);

		if (showAssetCount) {
			sb.append("<span class=\"tag-asset-count\">");
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(count);
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append("</span>");
		}

		sb.append("</a></span></li>");
	}

	sb.append("</ul>");

	return sb.toString();
}
%>