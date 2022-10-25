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
					<clay:content-row
						cssClass="sidebar-section"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<h4 class="component-title"><%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %></h4>

							<p class="component-subtitle">
								<%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>
							</p>
						</clay:content-col>

						<clay:content-col>
							<ul class="autofit-padded-no-gutters autofit-row">
								<li class="autofit-col">
									<c:choose>
										<c:when test="<%= trashEntry.getRootEntry() == null %>">
											<clay:dropdown-actions
												additionalProps='<%=
													HashMapBuilder.<String, Object>put(
														"portletNamespace", liferayPortletResponse.getNamespace()
													).build()
												%>'
												aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
												dropdownItems="<%= trashDisplayContext.getTrashEntryActionDropdownItems(trashEntry) %>"
												propsTransformer="js/EntriesPropsTransformer"
											/>
										</c:when>
										<c:otherwise>
											<clay:dropdown-actions
												additionalProps='<%=
													HashMapBuilder.<String, Object>put(
														"portletNamespace", liferayPortletResponse.getNamespace()
													).build()
												%>'
												aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
												dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(trashRenderer.getClassName(), trashRenderer.getClassPK()) %>"
												propsTransformer="js/EntriesPropsTransformer"
											/>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</clay:content-col>
					</clay:content-row>
				</div>

				<clay:navigation-bar
					navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
				/>

				<div class="sidebar-body">
					<dl class="sidebar-dl sidebar-section">
						<dt class="sidebar-dt"><liferay-ui:message key="removed-date" /></dt>

						<dd class="sidebar-dd">
							<%= dateFormatDateTime.format(trashEntry.getCreateDate()) %>
						</dd>
						<dt class="sidebar-dt"><liferay-ui:message key="removed-by" /></dt>

						<dd class="sidebar-dd">
							<%= HtmlUtil.escape(trashEntry.getUserName()) %>
						</dd>
					</dl>
				</div>
			</c:when>
			<c:otherwise>
				<div class="sidebar-header">
					<clay:content-row
						cssClass="sidebar-section"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<h4 class="component-title"><liferay-ui:message arguments="<%= trashEntries.size() %>" key="x-items-are-selected" /></h4>
						</clay:content-col>

						<clay:content-col>
							<ul class="autofit-padded-no-gutters autofit-row">
								<li class="autofit-col">
								</li>
							</ul>
						</clay:content-col>
					</clay:content-row>
				</div>

				<clay:navigation-bar
					navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
				/>

				<div class="sidebar-body">
					<dl class="sidebar-dl sidebar-section">
						<dt class="sidebar-dt"><liferay-ui:message key="num-of-items" /></dt>

						<dd class="sidebar-dd">
							<%= trashEntries.size() %>
						</dd>
					</dl>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h4 class="component-title"><liferay-ui:message key="home" /></h4>
				</clay:content-col>
			</clay:content-row>
		</div>

		<clay:navigation-bar
			navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
		/>

		<div class="sidebar-body">
			<dl class="sidebar-dl sidebar-section">
				<dt class="sidebar-dt"><liferay-ui:message key="num-of-items" /></dt>

				<dd class="sidebar-dd">
					<%= TrashEntryLocalServiceUtil.getEntriesCount(themeDisplay.getScopeGroupId()) %>
				</dd>
			</dl>
		</div>
	</c:otherwise>
</c:choose>