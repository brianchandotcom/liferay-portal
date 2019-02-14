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

<%@ include file="/asset_tags_summary/init.jsp" %>

<%
String[] assetTagNames = StringUtil.split((String)request.getAttribute("liferay-asset:asset-tags-summary:assetTagNames"));
String className = (String)request.getAttribute("liferay-asset:asset-tags-summary:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-tags-summary:classPK"));
String message = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-summary:message"));
String paramName = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-tags-summary:paramName"), "tag");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:asset-tags-summary:portletURL");

if (assetTagNames.length == 0) {
	List<AssetTag> tags = (List<AssetTag>)request.getAttribute("liferay-asset:asset-tags-summary:assetTags");

	if (ListUtil.isEmpty(tags)) {
		tags = AssetTagServiceUtil.getTags(className, classPK);
	}

	assetTagNames = ListUtil.toArray(tags, AssetTag.NAME_ACCESSOR);
}
%>

<c:if test="<%= assetTagNames.length > 0 %>">
	<span class="taglib-asset-tags-summary">
		<%= Validator.isNotNull(message) ? (LanguageUtil.get(resourceBundle, message) + ": ") : "" %>

		<c:choose>
			<c:when test="<%= portletURL != null %>">

				<%
				for (int i = 0; i < assetTagNames.length; i++) {
					portletURL.setParameter(paramName, assetTagNames[i]);
				%>

					<a class="label label-lg label-secondary text-uppercase" href="<%= HtmlUtil.escape(portletURL.toString()) %>"><%= assetTagNames[i] %></a>

				<%
				}
				%>

			</c:when>
			<c:otherwise>

				<%
				for (int i = 0; i < assetTagNames.length; i++) {
				%>

					<span class="label label-lg label-secondary text-uppercase"><%= assetTagNames[i] %></span>

				<%
				}
				%>

			</c:otherwise>
		</c:choose>
	</span>
</c:if>