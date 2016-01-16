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

<%
WikiInfoPanelDisplayContext wikiInfoPanelDisplayContext = wikiDisplayContextProvider.getWikiInfoPanelDisplayContext(request, response);
%>

<div class="sidebar-header">
	<c:choose>
		<c:when test="<%= wikiInfoPanelDisplayContext.isSingleNodeSelection() %>">

			<%
			WikiNode node = wikiInfoPanelDisplayContext.getFirstNode();
			%>

			<h4>
				<%= HtmlUtil.escape(node.getName()) %>
			</h4>

			<p>
				<liferay-ui:message key="wiki" />
			</p>
		</c:when>
		<c:when test="<%= wikiInfoPanelDisplayContext.isMultipleNodeSelection() %>">
			<h4><liferay-ui:message arguments="<%= wikiInfoPanelDisplayContext.getSelectedNodesCount() %>" key="x-items-are-selected" /></h4>
		</c:when>
		<c:otherwise>
			<h4><liferay-ui:message key="wikis" /></h4>
		</c:otherwise>
	</c:choose>
</div>

<liferay-ui:tabs names="details" refresh="<%= false %>" type="dropdown">
	<liferay-ui:section>
		<div class="sidebar-body">
			<c:choose>
				<c:when test="<%= wikiInfoPanelDisplayContext.isSingleNodeSelection() %>">

					<%
					WikiNode node = wikiInfoPanelDisplayContext.getFirstNode();
					%>

					<c:if test="<%= Validator.isNotNull(node.getDescription()) %>">
						<h5><strong><liferay-ui:message key="description" /></strong></h5>

						<p>
							<%= HtmlUtil.escape(node.getDescription()) %>
						</p>
					</c:if>

					<h5><strong><liferay-ui:message key="total-pages" /></strong></h5>

					<p>
						<%= WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true) %>
					</p>

					<h5><strong><liferay-ui:message key="orphan-pages" /></strong></h5>

					<p>

						<%
						List<WikiPage> orphanPages = WikiPageServiceUtil.getOrphans(scopeGroupId, node.getNodeId());
						%>

						<%= orphanPages.size() %>
					</p>

					<h5><strong><liferay-ui:message key="last-modified" /></strong></h5>

					<p>
						<%= dateFormatDateTime.format(node.getModifiedDate()) %>
					</p>

					<h5><strong><liferay-ui:message key="create-date" /></strong></h5>

					<p>
						<%= dateFormatDateTime.format(node.getModifiedDate()) %>
					</p>
				</c:when>
				<c:when test="<%= wikiInfoPanelDisplayContext.isMultipleNodeSelection() %>">
					<h5><liferay-ui:message arguments="<%= wikiInfoPanelDisplayContext.getSelectedNodesCount() %>" key="x-items-are-selected" /></h5>
				</c:when>
				<c:otherwise>
					<h5><liferay-ui:message key="num-of-items" /></h5>

					<p>
						<%= wikiInfoPanelDisplayContext.getNodesCount() %>
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>