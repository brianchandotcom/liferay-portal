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

<%@ include file="/init.jsp" %>

<%
LayoutItemSelectorViewDisplayContext layoutItemSelectorViewDisplayContext = (LayoutItemSelectorViewDisplayContext)request.getAttribute(BaseLayoutsItemSelectorView.LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

LayoutItemSelectorCriterion layoutItemSelectorCriterion = layoutItemSelectorViewDisplayContext.getLayoutItemSelectorCriterion();

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="container-fluid-1280 layouts-selector">
	<div class="card-horizontal main-content-card">
		<div class="card-row card-row-padded">
			<liferay-layout:layouts-tree
				checkContentDisplayPage="<%= layoutItemSelectorCriterion.isCheckDisplayPage() %>"
				draggableTree="<%= false %>"
				expandFirstNode="<%= true %>"
				groupId="<%= scopeGroupId %>"
				portletURL="<%= layoutItemSelectorViewDisplayContext.getEditLayoutURL() %>"
				privateLayout="<%= layoutItemSelectorViewDisplayContext.isPrivateLayout() %>"
				rootNodeName="<%= layoutItemSelectorViewDisplayContext.getRootNodeName() %>"
				saveState="<%= false %>"
				selectedLayoutIds="<%= layoutItemSelectorViewDisplayContext.getSelectedLayoutIds() %>"
				selPlid="<%= layoutItemSelectorViewDisplayContext.getSelPlid() %>"
				treeId="treeContainer"
			/>
		</div>
	</div>
</div>

<aui:script use="aui-base">
	var LString = A.Lang.String;

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

		var lastSelectedNode = event.newVal;

		var labelEl = lastSelectedNode.get('labelEl');

		var link = labelEl.one('a');

		var url = link.attr('data-url');
		var uuid = link.attr('data-uuid');

		var data = {};

		if (link && url) {
			disabled = false;

			data.layoutpath = getChosenPagePath(lastSelectedNode);

			<c:choose>
				<c:when test="<%= Objects.equals(layoutItemSelectorViewDisplayContext.getItemSelectorReturnTypeName(), URLItemSelectorReturnType.class.getName()) %>">
					data.value = url;
				</c:when>
				<c:when test="<%= Objects.equals(layoutItemSelectorViewDisplayContext.getItemSelectorReturnTypeName(), UUIDItemSelectorReturnType.class.getName()) %>">
					data.value = uuid;
				</c:when>
			</c:choose>
		}

		<c:if test="<%= Validator.isNotNull(layoutItemSelectorViewDisplayContext.getCkEditorFuncNum()) %>">
			data.ckeditorfuncnum: <%= layoutItemSelectorViewDisplayContext.getCkEditorFuncNum() %>;
		</c:if>

		Liferay.Util.getOpener().Liferay.fire(
			'<%= layoutItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
			{
				data: data
			}
		);
	};

	var container = A.one('#<portlet:namespace />treeContainerOutput');

	if (container) {
		container.swallowEvent('click', true);

		var tree = container.getData('tree-view');

		tree.after('lastSelectedChange', setSelectedPage);
	}
</aui:script>