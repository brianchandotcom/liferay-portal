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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectUserGroup");

User selUser = PortalUtil.getSelectedUser(request);

SelectUserGroupManagementToolbarDisplayContext selectUserGroupManagementToolbarDisplayContext = new SelectUserGroupManagementToolbarDisplayContext(request, renderRequest, renderResponse);

PortletURL portletURL = selectUserGroupManagementToolbarDisplayContext.getPortletURL();

SearchContainer userGroupSearch = selectUserGroupManagementToolbarDisplayContext.getSearchContainer(filterManageableUserGroups);

renderResponse.setTitle(LanguageUtil.get(request, "user-groups"));
%>

<clay:management-toolbar
	clearResultsURL="<%= selectUserGroupManagementToolbarDisplayContext.getClearResultsURL() %>"
	itemsTotal="<%= userGroupSearch.getTotal() %>"
	searchActionURL="<%= selectUserGroupManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	viewTypeItems="<%= selectUserGroupManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectUserGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= userGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			escapedModel="<%= false %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= HtmlUtil.escape(userGroup.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				value="<%= HtmlUtil.escape(userGroup.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= UserGroupMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, userGroup.getUserGroupId()) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("entityid", userGroup.getUserGroupId());
					data.put("entityname", userGroup.getName());

					boolean disabled = false;

					if (selUser != null) {
						for (long curUserGroupId : selUser.getUserGroupIds()) {
							if (curUserGroupId == userGroup.getUserGroupId()) {
								disabled = true;

								break;
							}
						}
					}
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= disabled %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	var openingLiferay = Util.getOpener().Liferay;

	openingLiferay.fire('<portlet:namespace />enableRemovedUserGroups', {
		selectors: A.all('.selector-button:disabled')
	});

	Util.selectEntityHandler(
		'#<portlet:namespace />selectUserGroupFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>