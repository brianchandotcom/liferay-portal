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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
%>

<liferay-ui:tabs names="details,versions" refresh="<%= false %>" type="dropdown">
	<liferay-ui:section>
		<div class="sidebar-body">
			<dl>
				<dt class="h5">
					<liferay-ui:message key="title" />
				</dt>

				<dd>
					<%= HtmlUtil.escape(kbArticle.getTitle()) %>
				</dd>

				<dt class="h5">
					<liferay-ui:message key="author" />
				</dt>

				<dd>
					<%= HtmlUtil.escape(kbArticle.getUserName()) %>
				</dd>

				<dt class="h5">
					<liferay-ui:message key="status" />
				</dt>

				<dd>
					<span class="text-capitalize"><%= HtmlUtil.escape(KnowledgeBaseUtil.getStatusLabel(kbArticle.getStatus())) %></span>
				</dd>

				<dt class="h5">
					<liferay-ui:message key="priority" />
				</dt>

				<dd>
					<%= kbArticle.getPriority() %>
				</dd>

				<dt class="h5">
					<liferay-ui:message key="create-date" />
				</dt>

				<dd>
					<%= dateFormatDateTime.format(kbArticle.getCreateDate()) %>
				</dd>

				<dt class="h5">
					<liferay-ui:message key="modified-date" />
				</dt>

				<dd>
					<%= dateFormatDateTime.format(kbArticle.getModifiedDate()) %>
				</dd>

				<dt class="h5">
					<liferay-ui:message key="views" />
				</dt>

				<dd>
					<%= kbArticle.getViewCount() %>
				</dd>
			</dl>
		</div>
	</liferay-ui:section>

	<liferay-ui:section>
		<div class="sidebar-body">
			<liferay-util:include page="/admin/common/article_history.jsp" servletContext="<%= application %>" />
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>