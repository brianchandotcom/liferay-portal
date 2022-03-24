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
	managementToolbarDisplayContext="<%= roleItemSelectorViewDisplayContext %>"
/>

<clay:container-fluid
	cssClass="container-form-lg container-view"
	id='<%= liferayPortletResponse.getNamespace() + "roleSelectorWrapper" %>'
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
			String cssClass = "table-cell-expand";

			RowChecker rowChecker = searchContainer.getRowChecker();

			if ((rowChecker != null) && rowChecker.isDisabled(role)) {
				cssClass += " text-muted";
			}

			row.setData(
				HashMapBuilder.<String, Object>put(
					"id", role.getRoleId()
				).put(
					"name", role.getTitle(locale)
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="<%= cssClass %>"
				name="title"
				value="<%= role.getTitle(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="<%= cssClass %>"
				name="description"
				value="<%= role.getDescription(locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<c:choose>
	<c:when test="<%= roleItemSelectorViewDisplayContext.getItemSelectorCriterion() instanceof RoleItemSelectorCriterion %>">
		<aui:script require="frontend-js-web/index as frontendJsWeb">
			var {delegate} = frontendJsWeb;

			var selectItemHandler = delegate(
				document.getElementById('<portlet:namespace />roleSelectorWrapper'),
				'change',
				'.entry input',
				(event) => {
					var checked = Liferay.Util.getCheckedCheckboxes(
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
				selectItemHandler.dispose();

				Liferay.detach('destroyPortlet', removeListener);
			});
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script use="liferay-search-container">
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace /><%= HtmlUtil.escape(roleItemSelectorViewDisplayContext.getSearchContainerId()) %>'
			);

			searchContainer.on('rowToggled', (event) => {
				var allSelectedElements = event.elements.allSelectedElements;
				var selectedData = [];

				allSelectedElements.each(function () {
					var row = this.ancestor('tr');

					var data = row.getDOM().dataset;

					selectedData.push({
						id: data.id,
						name: data.name,
					});
				});

				Liferay.Util.getOpener().Liferay.fire(
					'<%= HtmlUtil.escapeJS(roleItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
					{
						data: selectedData,
					}
				);
			});
		</aui:script>
	</c:otherwise>
</c:choose>