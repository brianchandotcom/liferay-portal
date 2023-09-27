<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/article/init.jsp" %>

<div id="<portlet:namespace />message-container"></div>

<div class="kb-article-container">
	<liferay-util:include page="/admin/common/view_kb_article.jsp" servletContext="<%= application %>" />
</div>