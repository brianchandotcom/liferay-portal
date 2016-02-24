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

<%@ include file="/wiki/init.jsp" %>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<div class="main-content-body">
	<liferay-ui:header
		title="draft-pages"
	/>

	<liferay-util:include page="/wiki/page_iterator.jsp" servletContext="<%= application %>">
		<liferay-util:param name="navigation" value="draft-pages" />
	</liferay-util:include>

	<c:if test="<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(company.getCompanyId(), scopeGroupId, WikiPage.class.getName()) %>">
		<h2><liferay-ui:message key="pending-approval" /></h2>

		<liferay-util:include page="/wiki/page_iterator.jsp" servletContext="<%= application %>">
			<liferay-util:param name="navigation" value="pending-pages" />
		</liferay-util:include>
	</c:if>
</div>