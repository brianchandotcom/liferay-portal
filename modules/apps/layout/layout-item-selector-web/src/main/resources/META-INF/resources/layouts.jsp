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
long groupId = themeDisplay.getScopeGroupId();

Group group = GroupLocalServiceUtil.fetchGroup(groupId);

request.setAttribute(WebKeys.GROUP, group);

LayoutItemSelectorViewDisplayContext layoutItemSelectorViewDisplayContext = (LayoutItemSelectorViewDisplayContext)request.getAttribute(LayoutItemSelectorView.LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

LayoutItemSelectorCriterion layoutItemSelectorCriterion = layoutItemSelectorViewDisplayContext.getLayoutItemSelectorCriterion();
%>

<liferay-ui:tabs names='<%= ((group.getPublicLayoutsPageCount() > 0) && (group.getPrivateLayoutsPageCount() > 0)) ? "public-pages,private-pages" : null %>' refresh="<%= false %>">

	<%
	boolean checkContentDisplayPage = layoutItemSelectorCriterion.isCheckDisplayPage();
	String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");
	long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);

	PortletURL editLayoutURL = PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.EDIT);

	editLayoutURL.setParameter("redirect", currentURL);
	editLayoutURL.setParameter("groupId", String.valueOf(groupId));
	editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());
	%>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">

		<%
		editLayoutURL.setParameter("tabs1", "public-pages");
		%>

		<liferay-ui:section>
			<div>
				<liferay-ui:layouts-tree
					checkContentDisplayPage="<%= checkContentDisplayPage %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					portletURL="<%= editLayoutURL %>"
					rootNodeName="<%= group.getLayoutRootNodeName(false, themeDisplay.getLocale()) %>"
					saveState="<%= false %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					selPlid="<%= selPlid %>"
					treeId="treeContainerPublicPages"
				/>
			</div>
		</liferay-ui:section>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">

		<%
		editLayoutURL.setParameter("tabs1", "private-pages");
		%>

		<liferay-ui:section>
			<div>
				<liferay-ui:layouts-tree
					checkContentDisplayPage="<%= checkContentDisplayPage %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					portletURL="<%= editLayoutURL %>"
					privateLayout="<%= true %>"
					rootNodeName="<%= group.getLayoutRootNodeName(true, themeDisplay.getLocale()) %>"
					saveState="<%= false %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					selPlid="<%= selPlid %>"
					treeId="treeContainerPrivatePages"
				/>
			</div>
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>

<div class="alert" id="<portlet:namespace />selectPageMessage">
	<aui:button cssClass="selector-button" disabled="<%= true %>" value="choose" />

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

	var selectPageMessage = A.one('#<portlet:namespace />selectPageMessage');

	var button = selectPageMessage.one('.selector-button');

	button.on(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			Util.getOpener().Liferay.fire(
				'<%= layoutItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
				{

					<%
					String ckEditorFuncNum = ParamUtil.getString(request, "CKEditorFuncNum");
					%>

					<c:if test="<%= Validator.isNotNull(ckEditorFuncNum) %>">
						ckeditorfuncnum: <%= ckEditorFuncNum %>,
					</c:if>

					layoutpath: currentTarget.attr('data-layoutpath'),
					returnType: currentTarget.attr('data-returnType'),
					value: currentTarget.attr('data-value')
				}
			);

			Util.getWindow().destroy();
		}
	);

	var setSelectedPage = function(event) {
		var disabled = true;

		var messageText = '<%= UnicodeLanguageUtil.get(request, "there-is-no-selected-page") %>';

		var messageType = 'alert';

		var lastSelectedNode = event.newVal;

		var labelEl = lastSelectedNode.get('labelEl');

		var link = labelEl.one('a');

		var url = link.attr('data-url');
		var uuid = link.attr('data-uuid');

		if (link && url) {
			disabled = false;

			messageText = getChosenPagePath(lastSelectedNode);

			messageType = 'info';

			button.attr('data-layoutpath', messageText);

			<%
			String itemSelectorReturnTypeName = StringPool.BLANK;

			for (ItemSelectorReturnType desiredItemSelectorReturnType : layoutItemSelectorCriterion.getDesiredItemSelectorReturnTypes()) {
				itemSelectorReturnTypeName = ClassUtil.getClassName(desiredItemSelectorReturnType);

				break;
			}

			if (Validator.isNull(itemSelectorReturnTypeName)) {
				throw new IllegalArgumentException("Invalid item selector return type " + itemSelectorReturnTypeName);
			}
			%>

			button.attr('data-returnType', '<%= itemSelectorReturnTypeName %>');

			<c:choose>
				<c:when test="<%= itemSelectorReturnTypeName.equals(URLItemSelectorReturnType.class.getName()) %>">
					button.attr('data-value', url);
				</c:when>
				<c:when test="<%= Validator.equals(itemSelectorReturnTypeName, UUIDItemSelectorReturnType.class.getName()) %>">
					button.attr('data-value', uuid);
				</c:when>
			</c:choose>
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
</aui:script>