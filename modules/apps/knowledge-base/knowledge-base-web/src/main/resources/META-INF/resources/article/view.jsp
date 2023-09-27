<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/article/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
%>

<c:choose>
	<c:when test="<%= kbArticle != null %>">
		<liferay-util:include page="/article/view_kb_article.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>

		<%
		renderRequest.setAttribute(KBWebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		%>

		<div class="alert alert-info portlet-configuration">
			<aui:a href="<%= portletDisplay.getURLConfiguration() %>" label="please-configure-this-portlet-to-make-it-visible-to-all-users" onClick="<%= portletDisplay.getURLConfigurationJS() %>" />
		</div>
	</c:otherwise>
</c:choose>