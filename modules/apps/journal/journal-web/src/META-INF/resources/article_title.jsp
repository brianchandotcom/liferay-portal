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

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

JournalArticle article = (JournalArticle)row.getObject();

String rowURL = (String)request.getAttribute(SearchEntry.ENTRY_HREF);

AssetRendererFactory<JournalArticle> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(JournalArticle.class);

AssetRenderer<JournalArticle> assetRenderer = assetRendererFactory.getAssetRenderer(JournalArticleAssetRenderer.getClassPK(article));

Map<String, Object> data = new HashMap<String, Object>();

data.put("placement", "top");
data.put("toggle", "tooltip");
%>

<aui:a data="<%= data %>" href="<%= rowURL %>" title="<%= HtmlUtil.escape(article.getTitle(locale)) %>">
	<%= HtmlUtil.escape(article.getTitle(locale)) %>
</aui:a>

<c:if test="<%= article.getGroupId() != scopeGroupId %>">
	<small class="group-info">
		<dl>

			<%
			Group group = GroupLocalServiceUtil.getGroup(article.getGroupId());
			%>

			<c:if test="<%= !group.isLayout() || (group.getParentGroupId() != scopeGroupId) %>">
				<dt>
					<liferay-ui:message key="site" />:
				</dt>
				<dd>

					<%
					String groupDescriptiveName = null;

					if (group.isLayout()) {
						Group parentGroup = group.getParentGroup();

						groupDescriptiveName = parentGroup.getDescriptiveName(locale);
					}
					else {
						groupDescriptiveName = group.getDescriptiveName(locale);
					}
					%>

					<%= HtmlUtil.escape(groupDescriptiveName) %>
				</dd>
			</c:if>

			<c:if test="<%= group.isLayout() %>">
				<dt>
					<liferay-ui:message key="scope" />:
				</dt>
				<dd>
					<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
				</dd>
			</c:if>
		</dl>
	</small>
</c:if>