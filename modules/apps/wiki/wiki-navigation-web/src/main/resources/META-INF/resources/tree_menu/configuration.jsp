<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/tree_menu/init.jsp" %>

<%
List<WikiNode> wikiNodes = WikiNodeLocalServiceUtil.getNodes(scopeGroupId);
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

			<aui:select name="preferences--depth--">
				<aui:option label="all" selected="<%= depth == WikiNavigationConstants.DEPTH_ALL %>" value="<%= WikiNavigationConstants.DEPTH_ALL %>" />

				<%
				for (int i = 1; i < 6; i++) {
				%>

					<aui:option label="<%= i %>" selected="<%= depth == i %>" />

				<%
				}
				%>

			</aui:select>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>