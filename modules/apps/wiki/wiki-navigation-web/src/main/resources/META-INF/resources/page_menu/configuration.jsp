<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/page_menu/init.jsp" %>

<%
List<WikiNode> wikiNodes = WikiNodeLocalServiceUtil.getNodes(scopeGroupId);

if ((selNodeId == 0) && (wikiNodes.size() == 1)) {
	WikiNode wikiNode = wikiNodes.get(0);

	selNodeId = wikiNode.getNodeId();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="the-node-could-not-be-found" />

		<liferay-frontend:fieldset>
			<aui:select label="node" name="preferences--selNodeId--" showEmptyOption="<%= true %>">

				<%
				for (WikiNode wikiNode : wikiNodes) {
				%>

					<aui:option label="<%= wikiNode.getName() %>" selected="<%= selNodeId == wikiNode.getNodeId() %>" value="<%= wikiNode.getNodeId() %>" />

				<%
				}
				%>

			</aui:select>

			<c:if test="<%= selNodeId > 0 %>">

				<%
				List<WikiPage> wikiPages = WikiPageServiceUtil.getNodePages(selNodeId, WikiNavigationConstants.MAX_PAGES);
				%>

				<aui:select label="page" name="preferences--selTitle--" showEmptyOption="<%= true %>">

					<%
					for (WikiPage curWikiPage : wikiPages) {
					%>

						<aui:option label="<%= curWikiPage.getTitle() %>" selected="<%= selTitle.equals(curWikiPage.getTitle()) %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>