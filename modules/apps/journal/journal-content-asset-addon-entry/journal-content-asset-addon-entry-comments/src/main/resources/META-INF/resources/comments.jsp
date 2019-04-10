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

<%@ taglib uri="http://liferay.com/tld/comment" prefix="liferay-comment" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>

<%@ page import="com.liferay.journal.content.asset.addon.entry.comments.internal.CommentsContentMetadataAssetAddonEntry" %><%@
page import="com.liferay.journal.model.JournalArticle" %><%@
page import="com.liferay.journal.model.JournalArticleDisplay" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<liferay-frontend:defineObjects />

<%
CommentsContentMetadataAssetAddonEntry commentsContentMetadataAssetAddonEntry = (CommentsContentMetadataAssetAddonEntry)request.getAttribute(WebKeys.ASSET_ADDON_ENTRY);
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

String viewMode = ParamUtil.getString(request, "viewMode");
%>

<div class="content-metadata-asset-addon-entry content-metadata-comments">
	<liferay-comment:discussion
		className="<%= JournalArticle.class.getName() %>"
		classPK="<%= articleDisplay.getResourcePrimKey() %>"
		hideControls="<%= viewMode.equals(Constants.PRINT) %>"
		ratingsEnabled="<%= commentsContentMetadataAssetAddonEntry.isCommentsRatingsSelected(request) && !viewMode.equals(Constants.PRINT) %>"
		redirect="<%= currentURLObj.toString() %>"
		userId="<%= articleDisplay.getUserId() %>"
	/>
</div>