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

<%@ include file="/html/portlet/document_selector/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.fetchGroup(groupId);

request.setAttribute(WebKeys.GROUP, group);

boolean showGroupsSelector = ParamUtil.getBoolean(request, "showGroupsSelector");
%>

<c:if test="<%= showGroupsSelector %>">
	<liferay-util:include page="/html/portlet/document_selector/group_selector.jsp">
		<liferay-util:param name="tabs1" value="pages" />
	</liferay-util:include>
</c:if>

<%
String tabs1Names = "";

if (group.getPublicLayoutsPageCount() > 0) {
	tabs1Names += "public-pages,";
}

if (group.getPrivateLayoutsPageCount() > 0) {
	tabs1Names += "private-pages";
}
%>

<liferay-ui:tabs names="<%= tabs1Names %>" refresh="<%= false %>">

	<%
	boolean checkContentDisplayPage = ParamUtil.getBoolean(request, "checkContentDisplayPage");
	String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");

	LayoutsAdminDisplayContext layoutsAdminDisplayContext = new LayoutsAdminDisplayContext(request, liferayPortletResponse);
	%>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		<liferay-ui:section>
			<div>
				<liferay-ui:layouts-tree
					checkContentDisplayPage="<%= checkContentDisplayPage %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					portletURL="<%= layoutsAdminDisplayContext.getEditLayoutURL() %>"
					rootNodeName="<%= layoutsAdminDisplayContext.getRootNodeName() %>"
					saveState="<%= false %>"
					selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					treeId="treeContainerPublicPages"
				/>
			</div>
		</liferay-ui:section>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		<liferay-ui:section>
			<div>
				<liferay-ui:layouts-tree
					checkContentDisplayPage="<%= checkContentDisplayPage %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					portletURL="<%= layoutsAdminDisplayContext.getEditLayoutURL() %>"
					privateLayout="<%= true %>"
					rootNodeName="<%= layoutsAdminDisplayContext.getRootNodeName() %>"
					saveState="<%= false %>"
					selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					treeId="treeContainerPrivatePages"
				/>
			</div>
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>

<div class="alert" id="<portlet:namespace />selectPageMessage">

	<%
	String ckEditorFuncNum = DocumentSelectorUtil.getCKEditorFuncNum(request);

	Map<String, String> params = new HashMap<String, String>();

	params.put("ckeditorfuncnum", ckEditorFuncNum);
	%>

	<aui:button cssClass="selector-button" data="<%= params %>" disabled="<%= true %>" value="choose" />

	<span class="selected-page-message">
		<liferay-ui:message key="there-is-no-selected-page" />
	</span>
</div>

<aui:script use="aui-base">
	var LString = A.Lang.String;
	var Util = Liferay.Util;

	var bindTreeUI = function(containerId) {
		var container = A.one('#<portlet:namespace />' + containerId);

		if (container) {
			container.swallowEvent('click', true);

			var tree = container.getData('tree-view');

			tree.after('lastSelectedChange', setSelectedPage);
		}
	};

	var getChosenPagePath = function(node) {
		var buffer = [];

		if (A.instanceOf(node, A.TreeNode)) {
			var labelText = LString.escapeHTML(node.get('labelEl').text());

			buffer.push(labelText);

			node.eachParent(
				function(treeNode) {
					var labelEl = treeNode.get('labelEl');

					if (labelEl) {
						labelText = LString.escapeHTML(labelEl.text());

						buffer.unshift(labelText);
					}
				}
			);
		}

		return buffer.join(' > ');
	};

	var setSelectedPage = function(event) {
		var disabled = true;

		var messageText = '<%= UnicodeLanguageUtil.get(request, "there-is-no-selected-page") %>';

		var messageType = 'alert';

		var lastSelectedNode = event.newVal;

		var labelEl = lastSelectedNode.get('labelEl');

		var link = labelEl.one('a');

		var url = link.attr('data-url');

		var uuid = link.attr('data-uuid');

		var selectPageMessage = A.one('#<portlet:namespace />selectPageMessage');

		var button = selectPageMessage.one('.selector-button');

		if (link && url) {
			disabled = false;

			messageText = getChosenPagePath(lastSelectedNode);

			messageType = 'info';

			button.attr('data-url', url);

			button.attr('data-uuid', uuid);

			button.attr('data-layoutpath', messageText);
		}

		Liferay.Util.toggleDisabled(button, disabled);

		selectPageMessage.one('.selected-page-message').html(messageText);

		selectPageMessage.attr('className', 'alert alert-' + messageType);
	};

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		bindTreeUI('treeContainerPublicPagesOutput');
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		bindTreeUI('treeContainerPrivatePagesOutput');
	</c:if>

	<%
	String eventName = ParamUtil.getString(request, "eventName");
	%>

	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectPageMessage', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>