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
JournalViewMoreMenuItemsDisplayContext journalViewMoreMenuItemsDisplayContext = new JournalViewMoreMenuItemsDisplayContext(renderRequest, renderResponse, journalDisplayContext.getFolderId(), journalDisplayContext.getRestrictionType());
%>

<c:if test="<%= journalDisplayContext.getAddMenuFavItemsLength() == 0 %>">
	<clay:stripe
		message='<%= LanguageUtil.format(resourceBundle, "you-can-add-as-many-as-x-favorites-in-your-quick-menu", journalWebConfiguration.maxAddMenuItems()) %>'
	/>
</c:if>

<liferay-ui:error exception="<%= MaxAddMenuFavItemsException.class %>" message='<%= LanguageUtil.format(resourceBundle, "you-cannot-add-more-than-x-favorites", journalWebConfiguration.maxAddMenuItems()) %>' />

<c:if test="<%= journalDisplayContext.getAddMenuFavItemsLength() >= journalWebConfiguration.maxAddMenuItems() %>">
	<clay:stripe
		displayType="warning"
		message="right-now-your-quick-menu-is-full-of-favorites-if-you-want-to-add-another-one-please-remove-at-least-one-of-them"
	/>
</c:if>

<clay:navigation-bar
	navigationItems="<%= journalViewMoreMenuItemsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar-v2
	displayContext="<%= new JournalViewMoreMenuItemsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalViewMoreMenuItemsDisplayContext) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="addMenuItemFm">
	<liferay-ui:search-container
		searchContainer="<%= journalViewMoreMenuItemsDisplayContext.getDDMStructuresSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			escapedModel="<%= true %>"
			modelVar="ddmStructure"
		>
			<liferay-ui:search-container-column-text
				name="menu-item-name"
			>
				<aui:a
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"ddmStructureKey", ddmStructure.getStructureKey()
						).build()
					%>'
					href="javascript:;"
				>
					<%= ddmStructure.getUnambiguousName(journalViewMoreMenuItemsDisplayContext.getDDMStructures(), themeDisplay.getScopeGroupId(), locale) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="scope"
				value="<%= journalViewMoreMenuItemsDisplayContext.getDDMStructureScopeName(ddmStructure, locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="user"
				property="userName"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-jsp
				align="center"
				name='<%= LanguageUtil.format(request, "add-to-favorites-x", String.valueOf(journalDisplayContext.getAddMenuFavItemsLength())) %>'
				path="/view_more_menu_items_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var Util = Liferay.Util;

	var addMenuItemFm = document.getElementById(
		'<portlet:namespace />addMenuItemFm'
	);

	var delegate = delegateModule.default;

	delegate(addMenuItemFm, 'click', '.selector-button', function (event) {
		Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(journalViewMoreMenuItemsDisplayContext.getEventName()) %>',
			{
				ddmStructureKey: event.delegateTarget.getAttribute(
					'data-ddmStructureKey'
				),
			}
		);
	});
</aui:script>