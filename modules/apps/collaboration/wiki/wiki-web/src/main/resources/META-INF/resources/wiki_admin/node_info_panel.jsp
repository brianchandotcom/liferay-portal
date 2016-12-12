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
WikiNodeInfoPanelDisplayContext wikiNodeInfoPanelDisplayContext = wikiDisplayContextProvider.getWikiNodeInfoPanelDisplayContext(request, response);
%>

<div class="sidebar-header">
	<c:choose>
		<c:when test="<%= wikiNodeInfoPanelDisplayContext.isSingleNodeSelection() %>">
			<ul class="sidebar-actions">

				<%
				request.setAttribute("node_info_panel.jsp-wikiNode", wikiNodeInfoPanelDisplayContext.getFirstNode());
				%>

				<li>
					<liferay-util:include page="/wiki/subscribe.jsp" servletContext="<%= application %>" />
				</li>
				<li>
					<liferay-util:include page="/wiki/node_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<%
			WikiNode node = wikiNodeInfoPanelDisplayContext.getFirstNode();
			%>

			<h4 class="sidebar-title">
				<%= HtmlUtil.escape(node.getName()) %>
			</h4>

			<h5>
				<liferay-ui:message key="wiki" />
			</h5>
		</c:when>
		<c:when test="<%= wikiNodeInfoPanelDisplayContext.isMultipleNodeSelection() %>">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= wikiNodeInfoPanelDisplayContext.getSelectedNodesCount() %>" key="x-items-are-selected" /></h4>
		</c:when>
		<c:otherwise>
			<h4 class="sidebar-title"><liferay-ui:message key="wikis" /></h4>
		</c:otherwise>
	</c:choose>
</div>

<liferay-ui:tabs cssClass="navbar-no-collapse" names="details" refresh="<%= false %>" type="dropdown">
	<liferay-ui:section>
		<div class="sidebar-body">
			<dl class="sidebar-block">
				<c:choose>
					<c:when test="<%= wikiNodeInfoPanelDisplayContext.isSingleNodeSelection() %>">

						<%
						WikiNode node = wikiNodeInfoPanelDisplayContext.getFirstNode();
						%>

						<c:if test="<%= Validator.isNotNull(node.getDescription()) %>">
							<dt class="h5">
								<liferay-ui:message key="description" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(node.getDescription()) %>
							</dd>
						</c:if>

						<dt class="h5">
							<liferay-ui:message key="total-pages" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="orphan-pages" />
						</dt>

						<%
						List<WikiPage> orphanPages = WikiPageServiceUtil.getOrphans(scopeGroupId, node.getNodeId());
						%>

						<dd class="h6 sidebar-caption">
							<%= orphanPages.size() %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="last-modified" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= dateFormatDateTime.format(node.getModifiedDate()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="create-date" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= dateFormatDateTime.format(node.getModifiedDate()) %>
						</dd>
					</c:when>
					<c:when test="<%= wikiNodeInfoPanelDisplayContext.isMultipleNodeSelection() %>">
						<dt class="h5">
							<liferay-ui:message arguments="<%= wikiNodeInfoPanelDisplayContext.getSelectedNodesCount() %>" key="x-items-are-selected" />
						</dt>
					</c:when>
					<c:otherwise>
						<dt class="h5">
							<liferay-ui:message key="num-of-items" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= wikiNodeInfoPanelDisplayContext.getNodesCount() %>
						</dd>
					</c:otherwise>
				</c:choose>
			</dl>
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>