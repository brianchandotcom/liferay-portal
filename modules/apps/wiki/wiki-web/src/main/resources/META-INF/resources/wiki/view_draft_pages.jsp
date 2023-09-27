<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<div class="main-content-body mt-4">
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