<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String tagName = ParamUtil.getString(request, "tag");

String title = "pages-with-tag-x";
String description = null;

try {
	AssetTag assetTag = AssetTagLocalServiceUtil.getTag(scopeGroupId, tagName);

	AssetTagProperty assetTagProperty = AssetTagPropertyLocalServiceUtil.getTagProperty(assetTag.getTagId(), "description");

	description = assetTagProperty.getValue();
}
catch (NoSuchTagException nste) {
}
catch (NoSuchTagPropertyException nstpe) {
}
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:header
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title="<%= LanguageUtil.format(pageContext, title, HtmlUtil.escape(tagName)) %>"
/>

<c:if test="<%= Validator.isNotNull(description) %>">
	<p class="tag-description">
		<%= description %>
	</p>
</c:if>

<liferay-util:include page="/html/portlet/wiki/page_iterator.jsp">
	<liferay-util:param name="type" value="tagged_pages" />
</liferay-util:include>