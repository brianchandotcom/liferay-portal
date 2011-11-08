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

long assetEntryId = 0;
long classPK = 0;

if (article != null) {
	classPK = article.getResourcePrimKey();

	if (!article.isApproved() && (article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), article.getPrimaryKey());

		if (assetEntry != null) {
			assetEntryId = assetEntry.getEntryId();
			classPK = article.getPrimaryKey();
		}
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="related-assets" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="related-assets" /></h3>

<aui:fieldset>
	<liferay-ui:input-asset-links
		assetEntryId="<%= assetEntryId %>"
		className="<%= JournalArticle.class.getName() %>"
		classPK="<%= classPK %>"
	/>
</aui:fieldset>