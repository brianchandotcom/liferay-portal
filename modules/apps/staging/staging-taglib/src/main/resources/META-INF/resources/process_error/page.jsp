<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_error/init.jsp" %>

<c:if test="<%= authException %>">
	<%@ include file="/process_error/error/error_auth_exception.jspf" %>
</c:if>

<c:if test="<%= duplicateLockException %>">
	<%@ include file="/process_error/error/error_duplicate_lock_exception.jspf" %>
</c:if>

<c:if test="<%= illegalArgumentException %>">
	<%@ include file="/process_error/error/error_illegal_argument_exception.jspf" %>
</c:if>

<c:if test="<%= layoutPrototypeException %>">
	<%@ include file="/process_error/error/error_layout_prototype_exception.jspf" %>
</c:if>

<c:if test="<%= noSuchExceptions %>">
	<%@ include file="/process_error/error/error_no_such_exceptions.jspf" %>
</c:if>

<c:if test="<%= remoteExportException %>">
	<%@ include file="/process_error/error/error_remote_export_exception.jspf" %>
</c:if>

<c:if test="<%= remoteOptionsException %>">
	<%@ include file="/process_error/error/error_remote_options_exception.jspf" %>
</c:if>

<c:if test="<%= systemException %>">
	<%@ include file="/process_error/error/error_system_exception.jspf" %>
</c:if>