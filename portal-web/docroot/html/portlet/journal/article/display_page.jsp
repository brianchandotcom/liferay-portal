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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

Layout refererLayout = null;
boolean selected = false;

if ((article == null) || article.isNew()) {
	long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

	if (refererPlid > 0) {
		refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="display-page" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="display-page" /></h3>

<%
List<Layout> privateGroupLayouts = JournalUtil.getDefaultAssetPublisherLayouts(scopeGroupId, true);
List<Layout> publicGroupLayouts = JournalUtil.getDefaultAssetPublisherLayouts(scopeGroupId, false);

if (privateGroupLayouts.isEmpty() && publicGroupLayouts.isEmpty()) {
%>

	<liferay-ui:message key="there-are-no-pages-configured-to-be-the-display-page" />

<%
}
else {
%>

	<aui:select helpMessage="default-display-page-help" label="default-display-page" name="layoutUuid" showEmptyOption="<%= true %>">

	<%
	if (!publicGroupLayouts.isEmpty()) {
	%>

		<optgroup label="<liferay-ui:message key="public-pages" />">

	<%
		for (Layout groupLayout : publicGroupLayouts) {
			selected = layoutUuid.equals(groupLayout.getUuid()) || ((refererLayout != null) && groupLayout.equals(refererLayout));
	%>

			<aui:option label="<%= groupLayout.getName(defaultLanguageId) %>" selected="<%= selected %>" value="<%= groupLayout.getUuid() %>" />

	<%
		}
	%>

		</optgroup>

	<%
	}

	if (!privateGroupLayouts.isEmpty()) {
	%>

		<optgroup label="<liferay-ui:message key="private-pages" />">

	<%
		for (Layout groupLayout : privateGroupLayouts) {
			selected = layoutUuid.equals(groupLayout.getUuid()) || ((refererLayout != null) && groupLayout.equals(refererLayout));
	%>

			<aui:option label="<%= groupLayout.getName(defaultLanguageId) %>" selected="<%= selected %>" value="<%= groupLayout.getUuid() %>" />

	<%
		}
	%>

		</optgroup>
	<%
	}
	%>

	</aui:select>

<%
}
%>

<c:if test="<%= Validator.isNotNull(layoutUuid) %>">

	<%
	Layout defaultDisplayLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, scopeGroupId);

	AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

	AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(article.getResourcePrimKey());

	String urlViewInContext = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, currentURL);
	%>

	<c:if test="<%= Validator.isNotNull(urlViewInContext) %>">
		<a href="<%= urlViewInContext %>" target="blank"><%= LanguageUtil.format(pageContext, "view-content-in-x", defaultDisplayLayout.getName(locale)) %></a>
	</c:if>
</c:if>