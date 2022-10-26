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

<%@ include file="/display/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBNavigationDisplayContext kbNavigationDisplayContext = new KBNavigationDisplayContext(renderRequest, portalPreferences, kbDisplayPortletInstanceConfiguration, kbArticle);

request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT, kbNavigationDisplayContext);
%>

<c:choose>
	<c:when test="<%= kbArticle != null %>">
		<clay:row>
			<c:if test="<%= kbNavigationDisplayContext.isLeftNavigationVisible() %>">
				<clay:col
					md="3"
				>
					<liferay-util:include page="/display/view_navigation.jsp" servletContext="<%= application %>" />
				</clay:col>
			</c:if>

			<clay:col
				md='<%= kbNavigationDisplayContext.isLeftNavigationVisible() ? "9" : "12" %>'
			>
				<liferay-util:include page="/display/view_kb_article.jsp" servletContext="<%= application %>" />
			</clay:col>
		</clay:row>
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