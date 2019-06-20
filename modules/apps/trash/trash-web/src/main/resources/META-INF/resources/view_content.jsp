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
TrashHandler trashHandler = trashDisplayContext.getTrashHandler();
%>

<liferay-util:include page="/restore_path.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test="<%= trashHandler.isContainerModel() %>">
		<clay:management-toolbar
			displayContext="<%= new TrashContainerManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, trashDisplayContext) %>"
		/>

		<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/trash/info_panel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
			>
				<liferay-util:include page="/view_content_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>

			<div class="sidenav-content">
				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= trashDisplayContext.getBaseModelBreadcrumbEntries() %>"
				/>

				<liferay-ui:search-container
					id="trash"
					searchContainer="<%= trashDisplayContext.getTrashContainerSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.TrashedModel"
						modelVar="curTrashedModel"
					>

						<%
						String modelClassName = ((ClassedModel)curTrashedModel).getModelClassName();

						TrashHandler curTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(modelClassName);

						TrashRenderer curTrashRenderer = curTrashHandler.getTrashRenderer(curTrashedModel.getTrashEntryClassPK());

						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("mvcPath", "/view_content.jsp");
						rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(curTrashRenderer.getClassName())));
						rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
						%>

						<c:choose>
							<c:when test="<%= trashDisplayContext.isDescriptiveView() %>">
								<liferay-ui:search-container-column-icon
									icon="<%= curTrashRenderer.getIconCssClass() %>"
									toggleRowChecker="<%= false %>"
								/>

								<liferay-ui:search-container-column-text
									colspan="<%= 2 %>"
								>
									<h5>
										<aui:a href="<%= rowURL.toString() %>">
											<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
										</aui:a>
									</h5>

									<h6 class="text-default">
										<liferay-ui:message key="type" /> <%= ResourceActionsUtil.getModelResource(locale, curTrashRenderer.getClassName()) %>
									</h6>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										defaultEventHandler="<%= TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER %>"
										dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(modelClassName, curTrashedModel.getTrashEntryClassPK()) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test="<%= trashDisplayContext.isIconView() %>">

								<%
								row.setCssClass("entry-card lfr-asset-item");
								%>

								<liferay-ui:search-container-column-text>
									<c:choose>
										<c:when test="<%= !curTrashHandler.isContainerModel() %>">
											<clay:vertical-card
												verticalCard="<%= new TrashContentVerticalCard(curTrashedModel, curTrashRenderer, renderRequest, liferayPortletResponse, rowURL.toString()) %>"
											/>
										</c:when>
										<c:otherwise>
											<clay:horizontal-card
												horizontalCard="<%= new TrashContentHorizontalCard(curTrashedModel, curTrashRenderer, renderRequest, liferayPortletResponse, rowURL.toString()) %>"
											/>
										</c:otherwise>
									</c:choose>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:when test="<%= trashDisplayContext.isListView() %>">
								<liferay-ui:search-container-column-text
									name="name"
									truncate="<%= true %>"
								>
									<aui:a href="<%= rowURL.toString() %>">
										<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
									</aui:a>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text>
									<clay:dropdown-actions
										defaultEventHandler="<%= TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER %>"
										dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(modelClassName, curTrashedModel.getTrashEntryClassPK()) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= trashDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
						resultRowSplitter="<%= new TrashResultRowSplitter() %>"
					/>
				</liferay-ui:search-container>
			</div>
		</div>
	</c:when>
	<c:otherwise>

		<%
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(trashDisplayContext.getViewContentRedirectURL());

		TrashRenderer trashRenderer = trashDisplayContext.getTrashRenderer();

		renderResponse.setTitle(trashRenderer.getTitle(locale));
		%>

		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<liferay-asset:asset-display
						renderer="<%= trashRenderer %>"
					/>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</c:otherwise>
</c:choose>

<liferay-frontend:component
	componentId="<%= TrashWebKeys.TRASH_ENTRIES_DEFAULT_EVENT_HANDLER %>"
	module="js/EntriesDefaultEventHandler.es"
/>