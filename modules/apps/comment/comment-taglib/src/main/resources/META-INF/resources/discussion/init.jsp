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

<%@ page import="com.liferay.comment.constants.CommentConstants" %><%@
page import="com.liferay.comment.taglib.internal.context.CommentDisplayContextProviderUtil" %><%@
page import="com.liferay.comment.taglib.internal.context.util.DiscussionRequestHelper" %><%@
page import="com.liferay.comment.taglib.internal.context.util.DiscussionTaglibHelper" %><%@
page import="com.liferay.portal.kernel.comment.CommentManagerUtil" %><%@
page import="com.liferay.portal.kernel.comment.Discussion" %><%@
page import="com.liferay.portal.kernel.comment.DiscussionComment" %><%@
page import="com.liferay.portal.kernel.comment.DiscussionCommentIterator" %><%@
page import="com.liferay.portal.kernel.comment.DiscussionPermission" %><%@
page import="com.liferay.portal.kernel.comment.WorkflowableComment" %><%@
page import="com.liferay.portal.kernel.comment.display.context.CommentSectionDisplayContext" %><%@
page import="com.liferay.portal.kernel.comment.display.context.CommentTreeDisplayContext" %><%@
page import="com.liferay.portal.kernel.security.auth.AuthTokenUtil" %><%@
page import="com.liferay.portal.kernel.service.ServiceContextFunction" %><%@
page import="com.liferay.staging.StagingGroupHelper" %><%@
page import="com.liferay.staging.StagingGroupHelperUtil" %>

<portlet:defineObjects />