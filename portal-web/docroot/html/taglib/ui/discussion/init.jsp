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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.comments.context.CommentDisplayContextProviderUtil" %><%@
page import="com.liferay.portal.comments.context.util.DiscussionRequestHelper" %><%@
page import="com.liferay.portal.comments.context.util.DiscussionTaglibHelper" %><%@
page import="com.liferay.portal.kernel.comments.CommentConstants" %><%@
page import="com.liferay.portal.kernel.comments.CommentManagerUtil" %><%@
page import="com.liferay.portal.kernel.comments.Discussion" %><%@
page import="com.liferay.portal.kernel.comments.DiscussionComment" %><%@
page import="com.liferay.portal.kernel.comments.DiscussionCommentIterator" %><%@
page import="com.liferay.portal.kernel.comments.DiscussionPermission" %><%@
page import="com.liferay.portal.kernel.comments.WorkflowableComment" %><%@
page import="com.liferay.portal.kernel.comments.context.CommentSectionDisplayContext" %><%@
page import="com.liferay.portal.kernel.comments.context.CommentTreeDisplayContext" %><%@
page import="com.liferay.portal.service.ServiceContextFunction" %>

<portlet:defineObjects />