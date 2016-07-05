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
List<AddMenuItem> addMenuFavItems = (List<AddMenuItem>)request.getAttribute("liferay-frontend:add-menu:addMenuFavItems");
List<AddMenuItem> addMenuItems = (List<AddMenuItem>)request.getAttribute("liferay-frontend:add-menu:addMenuItems");
List<AddMenuItem> addMenuPrimaryItems = (List<AddMenuItem>)request.getAttribute("liferay-frontend:add-menu:addMenuPrimaryItems");
List<AddMenuItem> addMenuRecentItems = (List<AddMenuItem>)request.getAttribute("liferay-frontend:add-menu:addMenuRecentItems");
int maxItems = (int)request.getAttribute("liferay-frontend:add-menu:maxItems");
String viewMoreUrl = (String)request.getAttribute("liferay-frontend:add-menu:viewMoreUrl");

int allAddMenuItemsCount = addMenuFavItems.size() + addMenuItems.size() + addMenuRecentItems.size();
%>

<c:choose>
	<c:when test="<%= allAddMenuItemsCount + addMenuPrimaryItems.size() == 1 %>">

		<%
		AddMenuItem addMenuItem = addMenuItems.get(0);

		String id = addMenuItem.getId();

		if (Validator.isNull(id)) {
			id = "menuItem";
		}

		String title = addMenuItem.getLabel();

		if (Validator.isNull(title)) {
			title = LanguageUtil.get(request, "new-item");
		}
		%>

		<a <%= AUIUtil.buildData(addMenuItem.getAnchorData()) %> class="btn btn-action btn-bottom-right btn-primary" data-placement="left" data-qa-id="addButton" data-toggle="tooltip" href="<%= HtmlUtil.escapeAttribute(addMenuItem.getUrl()) %>" id="<%= namespace + id %>" title="<%= title %>">
			<aui:icon image="plus" markupView="lexicon" />
		</a>

		<aui:script sandbox="<%= true %>">
			$(document).ready(
				function() {
					$('[data-toggle="tooltip"]').tooltip();
				}
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="add-menu btn-action-secondary btn-bottom-right dropdown">
			<button aria-expanded="false" class="btn btn-primary" data-qa-id="addButton" data-toggle="dropdown" type="button">
				<aui:icon image="plus" markupView="lexicon" />
			</button>

			<ul class="dropdown-menu dropdown-menu-left-side-bottom">
				<li class="active">
					<a href="javascript:;"><liferay-ui:message key="you-can-customize-this-menu-or-see-all-you-have-by-pressing-more" /></a>
				</li>

				<%
				for (int i = 0; i< addMenuPrimaryItems.size(); i++) {
					AddMenuItem addMenuPrimaryItem = addMenuPrimaryItems.get(i);

					String id = addMenuPrimaryItem.getId();

					if (Validator.isNull(id)) {
						id = "menuPrimaryItem" + i;
					}
				%>

					<li>
						<a <%= AUIUtil.buildData(addMenuPrimaryItem.getAnchorData()) %> href="<%= HtmlUtil.escapeAttribute(addMenuPrimaryItem.getUrl()) %>" id="<%= namespace + id %>"><%= HtmlUtil.escape(addMenuPrimaryItem.getLabel()) %></a>
					</li>

				<%
				}
				%>

				<li class="divider"></li>

				<c:if test="<%= addMenuFavItems.size() > 0 %>">
					<li class="dropdown-header">
						<liferay-ui:message key="favorites" />
					</li>
				</c:if>

				<%
				for (int i = 0; i < addMenuFavItems.size() && (i < allAddMenuItemsCount); i++) {
					AddMenuItem addMenuFavItem = addMenuFavItems.get(i);

					String id = addMenuFavItem.getId();

					if (Validator.isNull(id)) {
						id = "menuFavItem" + i;
					}
				%>

					<li>
						<a <%= AUIUtil.buildData(addMenuFavItem.getAnchorData()) %> href="<%= HtmlUtil.escapeAttribute(addMenuFavItem.getUrl()) %>" id="<%= namespace + id %>"><%= HtmlUtil.escape(addMenuFavItem.getLabel()) %></a>
					</li>

				<%
				}
				%>

				<c:if test="<%= (addMenuFavItems.size() < maxItems) && ((addMenuRecentItems.size() > 0) || (addMenuItems.size() > 0)) %>">
					<c:if test="<%= addMenuFavItems.size() > 0 %>">
						<li class="divider"></li>
					</c:if>

					<%
					for (int i = 0; i < addMenuRecentItems.size() && ((addMenuFavItems.size() + i) < maxItems); i++) {
						AddMenuItem addMenuRecentItem = addMenuRecentItems.get(i);

						String id = addMenuRecentItem.getId();

						if (Validator.isNull(id)) {
							id = "menuRecentItem" + i;
						}
					%>

						<li>
							<a <%= AUIUtil.buildData(addMenuRecentItem.getAnchorData()) %> href="<%= HtmlUtil.escapeAttribute(addMenuRecentItem.getUrl()) %>" id="<%= namespace + id %>"><%= HtmlUtil.escape(addMenuRecentItem.getLabel()) %></a>
						</li>

					<%
					}
					%>

					<c:if test="<%= (addMenuFavItems.size() + addMenuRecentItems.size()) < maxItems %>">

						<%
						for (int i = 0; i < addMenuItems.size() && ((addMenuFavItems.size() + addMenuRecentItems.size() + i) < maxItems); i++) {
							AddMenuItem addMenuItem = addMenuItems.get(i);

							String id = addMenuItem.getId();

							if (Validator.isNull(id)) {
								id = "menuItem" + i;
							}
						%>

							<li>
								<a <%= AUIUtil.buildData(addMenuItem.getAnchorData()) %> href="<%= HtmlUtil.escapeAttribute(addMenuItem.getUrl()) %>" id="<%= namespace + id %>"><%= HtmlUtil.escape(addMenuItem.getLabel()) %></a>
							</li>

						<%
						}
						%>

					</c:if>
				</c:if>

				<c:if test="<%= allAddMenuItemsCount > maxItems %>">
					<li class="dropdown-header">
						<liferay-ui:message arguments="<%= new Object[] {maxItems, allAddMenuItemsCount} %>" key="showing-x-of-x-items" />
					</li>

					<c:if test="<%= Validator.isNotNull(viewMoreUrl) %>">
						<li class="divider"></li>

						<li>
							<a class="text-center" href="javascript:;" id="<%= namespace %>viewMoreButton">
								<strong><liferay-ui:message key="more" /></strong>
							</a>
						</li>

						<aui:script use="liferay-util-window">
							var viewMoreAddMenuElements = A.one('#<%= namespace %>viewMoreButton');

							viewMoreAddMenuElements.on(
								'click',
								function(event) {
									Liferay.Util.openWindow(
										{
											dialog: {
												destroyOnHide: true,
												modal: true
											},
											id: '<%= namespace %>viewMoreAddMenuElements',
											title: '<liferay-ui:message key="more" />',
											uri: '<%= viewMoreUrl %>'
										}
									);
								}
							);
						</aui:script>
					</c:if>
				</c:if>
			</ul>
		</div>
	</c:otherwise>
</c:choose>