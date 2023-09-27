<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
WikiNodeInfoPanelDisplayContext wikiNodeInfoPanelDisplayContext = new WikiNodeInfoPanelDisplayContext(request);
%>

<div class="sidebar-header">
	<c:choose>
		<c:when test="<%= wikiNodeInfoPanelDisplayContext.isSingleNodeSelection() %>">

			<%
			WikiNode node = wikiNodeInfoPanelDisplayContext.getFirstNode();
			%>

			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title">
						<%= HtmlUtil.escape(node.getName()) %>
					</h4>

					<h5 class="component-subtitle">
						<liferay-ui:message key="wiki" />
					</h5>
				</div>

				<div class="autofit-col">
					<ul class="autofit-padded-no-gutters autofit-row">

						<%
						request.setAttribute("node_info_panel.jsp-wikiNode", wikiNodeInfoPanelDisplayContext.getFirstNode());
						%>

						<li class="autofit-col">
							<liferay-util:include page="/wiki/subscribe.jsp" servletContext="<%= application %>" />
						</li>
						<li class="autofit-col">
							<liferay-util:include page="/wiki/node_action.jsp" servletContext="<%= application %>" />
						</li>
					</ul>
				</div>
			</div>
		</c:when>
		<c:when test="<%= wikiNodeInfoPanelDisplayContext.isMultipleNodeSelection() %>">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><liferay-ui:message arguments="<%= wikiNodeInfoPanelDisplayContext.getSelectedNodesCount() %>" key="x-items-are-selected" /></h4>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h4 class="component-title"><liferay-ui:message key="wikis" /></h4>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<clay:navigation-bar
	navigationItems='<%=
		NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(LanguageUtil.get(request, "details"));
			}
		).build()
	%>'
/>

<div class="sidebar-body">
	<dl class="sidebar-dl sidebar-section">
		<c:choose>
			<c:when test="<%= wikiNodeInfoPanelDisplayContext.isSingleNodeSelection() %>">

				<%
				WikiNode node = wikiNodeInfoPanelDisplayContext.getFirstNode();
				%>

				<c:if test="<%= Validator.isNotNull(node.getDescription()) %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="description" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.escape(node.getDescription()) %>
					</dd>
				</c:if>

				<dt class="sidebar-dt">
					<liferay-ui:message key="total-pages" />
				</dt>
				<dd class="sidebar-dd">
					<%= WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true) %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="orphan-pages" />
				</dt>

				<%
				List<WikiPage> orphanPages = WikiPageServiceUtil.getOrphans(node);
				%>

				<dd class="sidebar-dd">
					<%= orphanPages.size() %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="last-modified" />
				</dt>
				<dd class="sidebar-dd">
					<%= dateFormatDateTime.format(node.getModifiedDate()) %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="create-date" />
				</dt>
				<dd class="sidebar-dd">
					<%= dateFormatDateTime.format(node.getModifiedDate()) %>
				</dd>
			</c:when>
			<c:when test="<%= wikiNodeInfoPanelDisplayContext.isMultipleNodeSelection() %>">
				<dt class="sidebar-dt">
					<liferay-ui:message arguments="<%= wikiNodeInfoPanelDisplayContext.getSelectedNodesCount() %>" key="x-items-are-selected" />
				</dt>
			</c:when>
			<c:otherwise>
				<dt class="sidebar-dt">
					<liferay-ui:message key="num-of-items" />
				</dt>
				<dd class="sidebar-dd">
					<%= wikiNodeInfoPanelDisplayContext.getNodesCount() %>
				</dd>
			</c:otherwise>
		</c:choose>
	</dl>
</div>