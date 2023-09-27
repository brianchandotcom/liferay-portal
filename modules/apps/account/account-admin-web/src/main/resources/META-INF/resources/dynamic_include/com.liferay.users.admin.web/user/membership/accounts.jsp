<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:include page="/common/common_user_account_entries.jsp" servletContext="<%= application %>">
	<liferay-util:param name="singleSelect" value="<%= Boolean.FALSE.toString() %>" />
</liferay-util:include>