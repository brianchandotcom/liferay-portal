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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.journal.content.asset.addon.entry.comments.CommentsContentMetadataAssetAddonEntry" %>
<%@ page import="com.liferay.journal.model.JournalArticle" %>
<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.comment.CommentManagerUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>

<liferay-frontend:defineObjects />

<portlet:defineObjects />

<%
CommentsContentMetadataAssetAddonEntry commentsContentMetadataAssetAddonEntry = (CommentsContentMetadataAssetAddonEntry)request.getAttribute(WebKeys.ASSET_ADDON_ENTRY);
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

String viewMode = ParamUtil.getString(request, "viewMode");
%>

<div class="content-metadata-asset-addon-entry content-metadata-comments">
	<c:if test="<%= CommentManagerUtil.getCommentsCount(JournalArticle.class.getName(), articleDisplay.getResourcePrimKey()) > 0 %>">
		<liferay-ui:header
			title="comments"
		/>
	</c:if>

	<liferay-ui:discussion
		className="<%= JournalArticle.class.getName() %>"
		classPK="<%= articleDisplay.getResourcePrimKey() %>"
		hideControls="<%= viewMode.equals(Constants.PRINT) %>"
		ratingsEnabled="<%= commentsContentMetadataAssetAddonEntry.isCommentsRatingsSelected(request) && !viewMode.equals(Constants.PRINT) %>"
		redirect="<%= currentURLObj.toString() %>"
		userId="<%= articleDisplay.getUserId() %>"
	/>
</div>