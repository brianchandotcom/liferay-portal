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
RoleItemSelectorViewDisplayContext roleItemSelectorViewDisplayContext = (RoleItemSelectorViewDisplayContext)request.getAttribute(RoleItemSelectorViewConstants.ROLE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	displayContext="<%= roleItemSelectorViewDisplayContext %>"
/>

<clay:container
	className="container-form-lg container-view"
	id='<%= renderResponse.getNamespace() + "roleSelectorWrapper" %>'
>
	<liferay-ui:search-container
		searchContainer="<%= roleItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			cssClass="entry"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			String cssClass = "table-cell-content";

			RowChecker rowChecker = searchContainer.getRowChecker();

			if ((rowChecker != null) && rowChecker.isDisabled(role)) {
				cssClass += " text-muted";
			}
			%>

			<liferay-ui:search-container-column-text
				cssClass="<%= cssClass %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="<%= cssClass %>"
				property="description"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectItemHandler = dom.delegate(
		document.getElementById('<portlet:namespace />roleSelectorWrapper'),
		'change',
		'.entry input',
		function (event) {
			var checked = Liferay.Util.listCheckedExcept(
				document.getElementById(
					'<portlet:namespace /><%= roleItemSelectorViewDisplayContext.getSearchContainerId() %>'
				),
				'<portlet:namespace />allRowIds'
			);

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(roleItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
				{
					data: {
						value: checked,
					},
				}
			);
		}
	);

	Liferay.on('destroyPortlet', function removeListener() {
		selectItemHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	});
</aui:script>