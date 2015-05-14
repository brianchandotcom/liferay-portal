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

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.journal.content.asset.addon.entry.comments.CommentsContentMetadataAssetAddonEntry" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portlet.PortletURLUtil" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>

<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects />

<%
String viewMode = ParamUtil.getString(request, "viewMode");

JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);
CommentsContentMetadataAssetAddonEntry commentsContentMetadataAssetAddonEntry = (CommentsContentMetadataAssetAddonEntry)request.getAttribute(WebKeys.ASSET_ADDON_ENTRY);

int discussionMessagesCount = MBMessageLocalServiceUtil.getDiscussionMessagesCount(PortalUtil.getClassNameId(JournalArticle.class.getName()), articleDisplay.getResourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);
%>

<div class="content-metadata-entry content-metadata-ratings">
	<c:if test="<%= discussionMessagesCount > 0 %>">
		<liferay-ui:header
			title="comments"
		/>
	</c:if>

	<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

	<portlet:resourceURL var="discussionPaginationURL">
		<portlet:param name="invokeTaglibDiscussion" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:resourceURL>

	<liferay-ui:discussion
		className="<%= JournalArticle.class.getName() %>"
		classPK="<%= articleDisplay.getResourcePrimKey() %>"
		formAction="<%= discussionURL %>"
		hideControls="<%= viewMode.equals(Constants.PRINT) %>"
		paginationURL="<%= discussionPaginationURL %>"
		ratingsEnabled="<%= commentsContentMetadataAssetAddonEntry.isCommentsRatingsSelected(request) && !viewMode.equals(Constants.PRINT) %>"
		redirect="<%= currentURLObj.toString() %>"
		userId="<%= articleDisplay.getUserId() %>"
	/>
</div>