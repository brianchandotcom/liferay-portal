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

<%@ include file="/add_menu/init.jsp" %>

<%
boolean inline = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:add-menu:inline"));
int menuItemsCount = GetterUtil.getInteger(request.getAttribute("liferay-frontend:add-menu:menuItemsCount"));
int maxItems = GetterUtil.getInteger(request.getAttribute("liferay-frontend:add-menu:maxItems"));
List<MenuItemGroup> menuItemGroups = (List<MenuItemGroup>)request.getAttribute("liferay-frontend:add-menu:menuItemGroups");
int numMenuItems = GetterUtil.getInteger(request.getAttribute("liferay-frontend:add-menu:numMenuItems"));
int total = GetterUtil.getInteger(request.getAttribute("liferay-frontend:add-menu:total"));
String viewMoreURL = (String)request.getAttribute("liferay-frontend:add-menu:viewMoreURL");
%>

<c:choose>
	<c:when test="<%= total == 1 %>">

		<%
		MenuItemGroup menuItemGroup = menuItemGroups.get(0);

		List<MenuItem> menuItems = menuItemGroup.getMenuItems();

		MenuItem menuItem = menuItems.get(0);

		String id = menuItem.getId();

		if (Validator.isNull(id)) {
			id = "menuItem";
		}

		String title = menuItem.getLabel();

		if (Validator.isNull(title)) {
			title = LanguageUtil.get(request, "new-item");
		}
		%>

		<a <%= AUIUtil.buildData(menuItem.getAnchorData()) %> class="<%= menuItem.getCssClass() %> btn btn-action <%= inline ? StringPool.BLANK : "btn-bottom-right" %> btn-primary lfr-portal-tooltip" data-qa-id="addButton" href="<%= HtmlUtil.escapeAttribute(menuItem.getUrl()) %>" id="<%= namespace + id %>" title="<%= HtmlUtil.escapeAttribute(title) %>">
			<aui:icon image="plus" markupView="lexicon" />
		</a>
	</c:when>
	<c:otherwise>
		<div class="add-menu btn-action-secondary <%= inline ? StringPool.BLANK : "btn-bottom-right" %> dropdown">
			<button aria-expanded="false" class="btn btn-primary" data-qa-id="addButton" data-toggle="liferay-dropdown" type="button">
				<aui:icon image="plus" markupView="lexicon" />
			</button>

			<ul class="dropdown-menu <%= inline ? "dropdown-menu-right" : "dropdown-menu-left-side-bottom" %>">

				<%
				boolean customizeAddMenuAdviceMessage = GetterUtil.getBoolean(SessionClicks.get(request, "com.liferay.addmenu_customizeAddMenuAdviceMessage", null));
				%>

				<c:if test="<%= !customizeAddMenuAdviceMessage && Validator.isNotNull(viewMoreURL) && (menuItemsCount > maxItems) %>">
					<li class="active add-menu-advice">
						<a class="dropdown-item" href="javascript:;"><liferay-ui:message key="you-can-customize-this-menu-or-see-all-you-have-by-clicking-more" /></a>
					</li>
				</c:if>

				<%
				int index = 0;

				for (MenuItemGroup menuItemGroup : menuItemGroups) {
					List<MenuItem> menuItems = menuItemGroup.getMenuItems();
				%>

					<c:if test="<%= !menuItems.isEmpty() %>">
						<c:if test="<%= Validator.isNotNull(menuItemGroup.getLabel()) %>">
							<li class="dropdown-header">
								<liferay-ui:message key="<%= menuItemGroup.getLabel() %>" />
							</li>
						</c:if>

						<%
						for (MenuItem menuItem : menuItems) {
							String id = menuItem.getId();

							if (Validator.isNull(id)) {
								id = "menuItem" + index;
							}
						%>

							<li>
								<a <%= AUIUtil.buildData(menuItem.getAnchorData()) %> class="<%= menuItem.getCssClass() %> dropdown-item" href="<%= HtmlUtil.escapeAttribute(menuItem.getUrl()) %>" id="<%= namespace + id %>" title="<%= HtmlUtil.escape(menuItem.getLabel()) %>"><%= HtmlUtil.escape(menuItem.getLabel()) %></a>
							</li>

						<%
							index++;

							if (index >= numMenuItems) {
								break;
							}
						}
						%>

						<c:if test="<%= menuItemGroup.isShowDivider() %>">
							<li class="divider"></li>
						</c:if>
					</c:if>

				<%
					if (index >= numMenuItems) {
						break;
					}
				}
				%>

				<c:if test="<%= menuItemsCount > maxItems %>">
					<li class="dropdown-header">
						<liferay-ui:message arguments="<%= new Object[] {maxItems, menuItemsCount} %>" key="showing-x-of-x-items" />
					</li>

					<c:if test="<%= Validator.isNotNull(viewMoreURL) %>">
						<li class="divider"></li>

						<li>
							<a class="dropdown-item text-center" href="javascript:;" id="<%= namespace %>viewMoreButton">
								<strong><liferay-ui:message key="more" /></strong>
							</a>
						</li>

						<aui:script use="liferay-util-window">
							var viewMoreAddMenuElements = A.one('#<%= namespace %>viewMoreButton');

							viewMoreAddMenuElements.on('click', function(event) {
								Liferay.Util.Session.set(
									'com.liferay.addmenu_customizeAddMenuAdviceMessage',
									true
								);

								Liferay.Util.openWindow({
									dialog: {
										destroyOnHide: true,
										modal: true
									},
									id: '<%= namespace %>selectAddMenuItem',
									title: '<liferay-ui:message key="more" />',
									uri: '<%= viewMoreURL %>'
								});
							});
						</aui:script>
					</c:if>
				</c:if>
			</ul>
		</div>
	</c:otherwise>
</c:choose>