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