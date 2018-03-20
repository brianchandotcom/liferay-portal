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
String keywords = ParamUtil.getString(request, "keywords");

String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectAddMenuItem");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_more_menu_items.jsp");
portletURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));
portletURL.setParameter("eventName", eventName);

SearchContainer ddmStructuresSearchContainer = new SearchContainer(renderRequest, portletURL, null, "no-results-were-found");

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");

ddmStructuresSearchContainer.setOrderByCol(orderByCol);

boolean orderByAsc = false;

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator orderByComparator = new StructureModifiedDateComparator(orderByAsc);

ddmStructuresSearchContainer.setOrderByComparator(orderByComparator);

ddmStructuresSearchContainer.setOrderByType(orderByType);

List<DDMStructure> ddmStructures = JournalFolderServiceUtil.searchDDMStructures(themeDisplay.getCompanyId(), PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), journalDisplayContext.getFolderId(), journalDisplayContext.getRestrictionType(), keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

int ddmStructuresCount = ddmStructures.size();

ddmStructuresSearchContainer.setTotal(ddmStructuresCount);

ddmStructuresSearchContainer.setResults(ddmStructures);
%>

<c:if test="<%= journalDisplayContext.getAddMenuFavItemsLength() == 0 %>">
	<liferay-ui:alert
		message='<%= LanguageUtil.format(resourceBundle, "you-can-add-as-many-as-x-favorites-in-your-quick-menu", journalWebConfiguration.maxAddMenuItems()) %>'
		timeout="<%= 0 %>"
		type="info"
	/>
</c:if>

<liferay-ui:error exception="<%= MaxAddMenuFavItemsException.class %>" message='<%= LanguageUtil.format(resourceBundle, "you-cannot-add-more-than-x-favorites", journalWebConfiguration.maxAddMenuItems()) %>' />

<c:if test="<%= journalDisplayContext.getAddMenuFavItemsLength() >= journalWebConfiguration.maxAddMenuItems() %>">
	<liferay-ui:alert
		message='<%= LanguageUtil.get(resourceBundle, "right-now-your-quick-menu-is-full-of-favorites-if-you-want-to-add-another-one-please-remove-at-least-one-of-them") %>'
		timeout="<%= 0 %>"
		type="warning"
	/>
</c:if>

<clay:navigation-bar
	items="<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "all-menu-items"));
					});
			}
		}
	%>"
/>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= ddmStructuresSearchContainer.getOrderByCol() %>"
			orderByType="<%= ddmStructuresSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<li>
			<aui:form action="<%= portletURL.toString() %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="addMenuItemFm">
	<liferay-ui:search-container
		searchContainer="<%= ddmStructuresSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			cssClass="selectable"
			escapedModel="<%= true %>"
			modelVar="ddmStructure"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("ddmStructureKey", ddmStructure.getStructureKey());
			%>

			<liferay-ui:search-container-column-text
				name="menu-item-name"
			>
				<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
					<%= ddmStructure.getUnambiguousName(ddmStructures, themeDisplay.getScopeGroupId(), locale) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

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

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />addMenuItemFm').delegate(
		'click',
		function(event) {
			Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(eventName) %>',
				{
					ddmStructureKey: event.currentTarget.attr('data-ddmStructureKey')
				}
			);

			Util.getWindow().destroy();
		},
		'.selector-button'
	);
</aui:script>