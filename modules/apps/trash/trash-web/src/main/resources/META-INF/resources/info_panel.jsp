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
List<TrashEntry> trashEntries = (List<TrashEntry>)request.getAttribute(TrashWebKeys.TRASH_ENTRIES);
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(trashEntries) %>">
		<c:choose>
			<c:when test="<%= trashEntries.size() == 1 %>">

				<%
				TrashEntry trashEntry = trashEntries.get(0);

				TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(trashEntry.getClassPK());
				%>

				<div class="sidebar-header">
					<ul class="sidebar-header-actions">
						<li>
							<c:choose>
								<c:when test="<%= trashEntry.getRootEntry() == null %>">
									<clay:dropdown-actions
										defaultEventHandler="<%= TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER %>"
										dropdownItems="<%= trashDisplayContext.getTrashEntryActionDropdownItems(trashEntry) %>"
									/>
								</c:when>
								<c:otherwise>
									<clay:dropdown-actions
										defaultEventHandler="<%= TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER %>"
										dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(trashRenderer.getClassName(), trashRenderer.getClassPK()) %>"
									/>
								</c:otherwise>
							</c:choose>
						</li>
					</ul>

					<h4><%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %></h4>

					<p>
						<%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>
					</p>
				</div>

				<clay:navigation-bar
					navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
				/>

				<div class="sidebar-body">
					<h5><liferay-ui:message key="removed-date" /></h5>

					<p>
						<%= dateFormatDateTime.format(trashEntry.getCreateDate()) %>
					</p>

					<h5><liferay-ui:message key="removed-by" /></h5>

					<p>
						<%= HtmlUtil.escape(trashEntry.getUserName()) %>
					</p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="sidebar-header">
					<h4><liferay-ui:message arguments="<%= trashEntries.size() %>" key="x-items-are-selected" /></h4>
				</div>

				<clay:navigation-bar
					navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
				/>

				<div class="sidebar-body">
					<h5><liferay-ui:message key="num-of-items" /></h5>

					<p>
						<%= trashEntries.size() %>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message key="home" /></h4>
		</div>

		<clay:navigation-bar
			navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="num-of-items" /></h5>

			<p>
				<%= TrashEntryLocalServiceUtil.getEntriesCount(themeDisplay.getScopeGroupId()) %>
			</p>
		</div>
	</c:otherwise>
</c:choose>