<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= BannedUserException.class %>" message="you-have-been-banned-by-the-moderator" />
<liferay-ui:error exception="<%= LockedThreadException.class %>" message="thread-is-locked" />
<liferay-ui:error exception="<%= NoSuchCategoryException.class %>" message="the-category-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchMessageException.class %>" message="the-message-could-not-be-found" />
<liferay-ui:error exception="<%= RequiredMessageException.class %>" message="you-cannot-delete-a-root-message-that-has-more-than-one-immediate-reply" />

<liferay-ui:error-principal />