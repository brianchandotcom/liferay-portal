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
JournalArticle article = journalContentDisplayContext.getArticle();
AssetRenderer<JournalArticle> assetRenderer = journalContentDisplayContext.getAssetRenderer();
%>

<div class="article-preview-content">
	<div class="card-horizontal">
		<div class="card-row">
			<div class="card-col-5">
				<div class="card-media-primary" style="background-image: url('<%= HtmlUtil.escapeAttribute(assetRenderer.getThumbnailPath(liferayPortletRequest)) %>');"></div>
			</div>

			<div class="card-col-7 card-col-gutters">
				<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />

				<h4><%= HtmlUtil.escapeAttribute(assetRenderer.getTitle(locale)) %></h4>

				<p><%= assetRenderer.getSummary() %></p>

				<liferay-ui:user-display
					showLink="<%= false %>"
					userId="<%= assetRenderer.getUserId() %>"
					view="lexicon"
				/>
			</div>
		</div>
	</div>
</div>