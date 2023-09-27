<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.comment.constants.CommentConstants" %><%@
page import="com.liferay.comment.taglib.internal.context.CommentDisplayContextProviderUtil" %><%@
page import="com.liferay.comment.taglib.internal.context.helper.DiscussionRequestHelper" %><%@
page import="com.liferay.comment.taglib.internal.context.helper.DiscussionTaglibHelper" %><%@
page import="com.liferay.comment.taglib.internal.permission.util.DiscussionPermissionUtil" %><%@
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
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.staging.StagingGroupHelper" %><%@
page import="com.liferay.staging.StagingGroupHelperUtil" %>

<portlet:defineObjects />