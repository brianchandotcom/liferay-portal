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
TrashManagementToolbarDisplayContext trashManagementToolbarDisplayContext = new TrashManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, trashDisplayContext);
%>

<clay:management-toolbar-v2
	displayContext="<%= trashManagementToolbarDisplayContext %>"
/>

<liferay-util:include page="/restore_path.jsp" servletContext="<%= application %>" />

<div class="closed sidenav-container sidenav-right" id="<%= liferayPortletResponse.getNamespace() + "infoPanelId" %>">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/trash/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="trash"
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<clay:container-fluid>
			<c:if test="<%= Validator.isNull(trashDisplayContext.getKeywords()) %>">
				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= trashDisplayContext.getPortletBreadcrumbEntries() %>"
				/>
			</c:if>

			<portlet:actionURL name="deleteEntries" var="deleteTrashEntriesURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:form action="<%= deleteTrashEntriesURL %>" name="fm">
				<liferay-ui:error exception="<%= RestoreEntryException.class %>">

					<%
					RestoreEntryException ree = (RestoreEntryException)errorException;
					%>

					<c:if test="<%= ree.getType() == RestoreEntryException.DUPLICATE %>">
						<liferay-ui:message key="unable-to-move-this-item-to-the-selected-destination" />
					</c:if>

					<c:if test="<%= ree.getType() == RestoreEntryException.INVALID_CONTAINER %>">
						<liferay-ui:message key="the-destination-you-selected-is-an-invalid-container.-please-select-a-different-destination" />
					</c:if>

					<c:if test="<%= ree.getType() == RestoreEntryException.INVALID_STATUS %>">
						<liferay-ui:message key="unable-to-restore-this-item" />
					</c:if>
				</liferay-ui:error>

				<liferay-ui:error exception="<%= TrashEntryException.class %>" message="unable-to-move-this-item-to-the-recycle-bin" />

				<liferay-ui:error exception="<%= TrashPermissionException.class %>">

					<%
					TrashPermissionException tpe = (TrashPermissionException)errorException;
					%>

					<c:if test="<%= tpe.getType() == TrashPermissionException.DELETE %>">
						<liferay-ui:message key="you-do-not-have-permission-to-delete-this-item" />
					</c:if>

					<c:if test="<%= tpe.getType() == TrashPermissionException.EMPTY_TRASH %>">
						<liferay-ui:message key="unable-to-completely-empty-trash-you-do-not-have-permission-to-delete-one-or-more-items" />
					</c:if>

					<c:if test="<%= tpe.getType() == TrashPermissionException.MOVE %>">
						<liferay-ui:message key="you-do-not-have-permission-to-move-this-item-to-the-selected-destination" />
					</c:if>

					<c:if test="<%= tpe.getType() == TrashPermissionException.RESTORE %>">
						<liferay-ui:message key="you-do-not-have-permission-to-restore-this-item" />
					</c:if>

					<c:if test="<%= tpe.getType() == TrashPermissionException.RESTORE_OVERWRITE %>">
						<liferay-ui:message key="you-do-not-have-permission-to-replace-an-existing-item-with-the-selected-one" />
					</c:if>

					<c:if test="<%= tpe.getType() == TrashPermissionException.RESTORE_RENAME %>">
						<liferay-ui:message key="you-do-not-have-permission-to-rename-this-item" />
					</c:if>
				</liferay-ui:error>

				<liferay-ui:search-container
					id="trash"
					searchContainer="<%= trashDisplayContext.getEntrySearch() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.trash.model.TrashEntry"
						keyProperty="entryId"
						modelVar="trashEntry"
						rowVar="row"
					>

						<%
						TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());

						TrashRenderer trashRenderer = trashHandler.getTrashRenderer(trashEntry.getClassPK());

						String viewContentURLString = null;

						if (trashRenderer != null) {
							PortletURL viewContentURL = renderResponse.createRenderURL();

							viewContentURL.setParameter("mvcPath", "/view_content.jsp");

							if (trashEntry.getRootEntry() != null) {
								viewContentURL.setParameter("classNameId", String.valueOf(trashEntry.getClassNameId()));
								viewContentURL.setParameter("classPK", String.valueOf(trashEntry.getClassPK()));
							}
							else {
								viewContentURL.setParameter("trashEntryId", String.valueOf(trashEntry.getEntryId()));
							}

							viewContentURLString = viewContentURL.toString();
						}

						row.setData(
							HashMapBuilder.<String, Object>put(
								"actions", trashManagementToolbarDisplayContext.getAvailableActions(trashEntry)
							).build());
						%>

						<c:choose>
							<c:when test="<%= trashDisplayContext.isDescriptiveView() %>">
								<liferay-ui:search-container-column-icon
									icon="<%= trashRenderer.getIconCssClass() %>"
									toggleRowChecker="<%= true %>"
								/>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<h6 class="text-default">
										<liferay-ui:message arguments="<%= dateFormatDateTime.format(trashEntry.getCreateDate()) %>" key="removed-x" />
									</h6>

									<h5>
										<aui:a href="<%= viewContentURLString %>">
											<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>
										</aui:a>
									</h5>

									<h6 class="text-default">
										<strong><liferay-ui:message key="type" />:</strong> <%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>
									</h6>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text>
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
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test="<%= trashDisplayContext.isIconView() %>">
								<liferay-ui:search-container-column-text>
									<clay:vertical-card
										verticalCard="<%= new TrashEntryVerticalCard(trashEntry, trashRenderer, liferayPortletResponse, renderRequest, searchContainer.getRowChecker(), viewContentURLString) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test="<%= trashDisplayContext.isListView() %>">
								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand table-cell-minw-200 table-title"
									name="name"
								>
									<aui:a href="<%= viewContentURLString %>">
										<%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %>
									</aui:a>

									<c:if test="<%= trashEntry.getRootEntry() != null %>">

										<%
										TrashEntry rootEntry = trashEntry.getRootEntry();

										TrashHandler rootTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(rootEntry.getClassName());

										TrashRenderer rootTrashRenderer = rootTrashHandler.getTrashRenderer(rootEntry.getClassPK());

										String viewRootContentURLString = null;

										if (rootTrashRenderer != null) {
											PortletURL viewContentURL = renderResponse.createRenderURL();

											viewContentURL.setParameter("mvcPath", "/view_content.jsp");
											viewContentURL.setParameter("trashEntryId", String.valueOf(rootEntry.getEntryId()));

											viewRootContentURLString = viewContentURL.toString();
										}
										%>

										<liferay-util:buffer
											var="rootEntryIcon"
										>
											<liferay-ui:icon
												label="<%= true %>"
												message="<%= HtmlUtil.escape(rootTrashRenderer.getTitle(locale)) %>"
												method="get"
												url="<%= viewRootContentURLString %>"
											/>
										</liferay-util:buffer>

										<span class="trash-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="<%= rootTrashHandler.getDeleteMessage() %>" translateArguments="<%= false %>" />)</span>
									</c:if>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smaller table-cell-minw-150"
									name="type"
									value="<%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>"
								/>

								<liferay-ui:search-container-column-date
									cssClass="table-cell-expand-smaller table-cell-ws-nowrap"
									name="removed-date"
									value="<%= trashEntry.getCreateDate() %>"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand-smallest table-cell-minw-150"
									name="removed-by"
									value="<%= HtmlUtil.escape(trashEntry.getUserName()) %>"
								/>

								<liferay-ui:search-container-column-text>
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
								</liferay-ui:search-container-column-text>
							</c:when>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= trashDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
						type='<%= trashDisplayContext.isApproximate() ? "more" : "regular" %>'
					/>
				</liferay-ui:search-container>
			</aui:form>
		</clay:container-fluid>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= trashManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= trashManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER %>"
	module="js/EntriesDefaultEventHandler.es"
/>